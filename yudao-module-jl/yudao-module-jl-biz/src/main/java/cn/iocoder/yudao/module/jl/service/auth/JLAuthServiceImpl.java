package cn.iocoder.yudao.module.jl.service.auth;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginByPhoneReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.CustomerCreateReqVO;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerRepository;
import cn.iocoder.yudao.module.jl.service.crm.CustomerService;
import cn.iocoder.yudao.module.system.api.oauth2.OAuth2TokenApi;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.yudao.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.oauth2.OAuth2ClientConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.AUTH_WX_APP_PHONE_CODE_ERROR;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;

@Service
@Validated
public class JLAuthServiceImpl implements JLAuthService {


    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private CustomerService customerService;

    @Resource
    private OAuth2TokenApi oauth2TokenApi;



        @Override
        public JLAppLoginRespVO loginByPhoneCode(@Valid JLAppLoginByPhoneReqVO reqVO) {
            WxMaPhoneNumberInfo phoneNumberInfo;

            if(reqVO.getPhone()==null){
                try {
                    phoneNumberInfo = wxMaService.getUserService().getNewPhoneNoInfo(reqVO.getPhoneCode());
                } catch (Exception exception) {
                    throw exception(AUTH_WX_APP_PHONE_CODE_ERROR);
                }

            }else{
                phoneNumberInfo=new WxMaPhoneNumberInfo();
                phoneNumberInfo.setPhoneNumber(reqVO.getPhone());
            }


            Long customerId = null;

            JLAppLoginRespVO respVO = new JLAppLoginRespVO();

            //根据手机号查询用户信息，如果没有则创建用户
            Customer findCustomer = customerRepository.findByPhone(phoneNumberInfo.getPhoneNumber());
            if (findCustomer==null){
                //创建用户
                CustomerCreateReqVO createReqVO = new CustomerCreateReqVO();
                createReqVO.setPhone(phoneNumberInfo.getPhoneNumber());
                createReqVO.setName("");
                createReqVO.setSource("SELF_WX");
                createReqVO.setIsSeas(true);
                Long customer = customerService.createCustomer(createReqVO);
                customerId=customer;
            } else {
                customerId=findCustomer.getId();
            }

            //查询用户信息，并返回
            Customer customer = customerRepository.findById(customerId).orElseGet(() -> {
                throw exception(CUSTOMER_NOT_EXISTS);
            });
            respVO.setNickname(customer.getName());
            respVO.setUserId(customer.getId());
            respVO.setPhone(customer.getPhone());

            //设置token
            // 插入登陆日志
//            createLoginLog(user.getId(), mobile, logType, LoginResultEnum.SUCCESS);
            // 创建 Token 令牌
            OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
                    .setUserId(customerId).setUserType(UserTypeEnum.MEMBER.getValue())
                    .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));

            respVO.setToken(accessTokenRespDTO.getAccessToken());
            return respVO;
        }

}
