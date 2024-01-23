package cn.iocoder.yudao.module.jl.controller.admin.weixin;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.weixin.util.JsonUtils;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.outxmlbuilder.TextBuilder;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.Objects;
import java.util.Optional;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "微信 -- 企业微信")
@RestController
@RequestMapping("/wx/cp")
@Validated
public class WeixinCPController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WxCpService wxCpService;

    @Resource
    private UserRepository userRepository;

    @GetMapping("/callback")
    @Operation(summary = "微信认证的回调地址")
    @PermitAll
    public String callback(
            @RequestParam(name = "msg_signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }


        if (wxCpService.checkSignature(signature, timestamp, nonce, echostr)) {
            return new WxCpCryptUtil(wxCpService.getWxCpConfigStorage()).decrypt(echostr);
        }

        return "非法请求";
    }

    @GetMapping("/bind-user")
    @Operation(summary = "绑定企业微信的ID")
    @PermitAll
    public CommonResult binduser(@RequestParam(name = "code", required = true) String code) throws WxErrorException {
        Long userId = getLoginUserId();
        String accessToken = wxCpService.getAccessToken();
        // http get 请求
        String url = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo?access_token=" + accessToken + "&code=" + code;
        String result = HttpUtil.get(url);  // {"userid":"ChenKai","errcode":0,"errmsg":"ok"} 解析出 userid, errcode
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Integer errCode = jsonObject.getInt("errcode", 50000);
        String wxUserId = jsonObject.getStr("userid");
        if (errCode == 0 && wxUserId != null) {
            // 绑定用户
            int res = userRepository.updateUserWxCpId(wxUserId, userId);
            if (res == 1) {
                return CommonResult.success("绑定成功");
            }
        }
        return CommonResult.error(400,"绑定失败");
    }

    @GetMapping("/unbind-user")
    @Operation(summary = "解除绑定企业微信的ID")
    @PermitAll
    public CommonResult unBindUser() {
        Long userId = getLoginUserId();
        int res = userRepository.updateUserWxCpId(null, userId);
        if (res == 1) {
            return CommonResult.success("解除成功");
        }
        return CommonResult.error(400,"解除失败");
    }

//    当接受用户消息时，可能会获得以下值：
//    WxConsts.XmlMsgType.TEXT
//    WxConsts.XmlMsgType.IMAGE
//    WxConsts.XmlMsgType.VOICE
//    WxConsts.XmlMsgType.VIDEO
//    WxConsts.XmlMsgType.LOCATION
//    WxConsts.XmlMsgType.LINK
//    WxConsts.XmlMsgType.EVENT
//    当发送消息的时候使用：
//    WxConsts.XmlMsgType.TEXT
//    WxConsts.XmlMsgType.IMAGE
//    WxConsts.XmlMsgType.VOICE
//    WxConsts.XmlMsgType.VIDEO
//    WxConsts.XmlMsgType.NEWS

    @PostMapping("/callback")
    @Operation(summary = "微信消息回调地址")
    @PermitAll
    public String post(
            @RequestBody String requestBody,
            @RequestParam("msg_signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce) {

        logger.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, timestamp, nonce, requestBody);

        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, wxCpService.getWxCpConfigStorage(),
                timestamp, nonce, signature);
        logger.info("\n消息解密后内容为：\n{} ", JsonUtils.toJson(inMessage));
        
        // 文本消息处理        
        if(Objects.equals(inMessage.getMsgType(), WxConsts.XmlMsgType.TEXT)) {
            TextBuilder outMessage = WxCpXmlOutMessage.TEXT();
            outMessage.content("你好，目前我只会复述你的文字：" + inMessage.getContent()); // TODO 设置为具体的内容

            String out = outMessage.build().toEncryptedXml(wxCpService.getWxCpConfigStorage());
            logger.info("\n组装回复信息：{}", out);
            return out;
        }
        return null;
    }

    @PostMapping("/msg")
    @Operation(summary = "发送企业微信消息")
    @PermitAll
    public CommonResult post(@RequestParam("msg") String msg) {
        Long userId = getLoginUserId();
        assert userId != null;
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(u -> {
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCpService);
            WxCpMessage wxCpMessage = new WxCpMessage();
            wxCpMessage.setSafe("0");
            wxCpMessage.setMsgType("textcard");  // 设置消息形式
            wxCpMessage.setToUser("Chenkai"); // u.getWxCpId()
            wxCpMessage.setTitle("商机通知");
            wxCpMessage.setDescription("您的一份商机已经完成报价，请查看");
            wxCpMessage.setUrl("https://jl.viseem.com/projectDetail/493/%E8%A3%B8%E9%BC%A0%E6%88%90%E7%98%A4%E5%8A%A8%E7%89%A9%E5%AE%9E%E9%AA%8C");
            wxCpMessage.setBtnTxt("点击查看");
            try {
                wxCpMessageService.send(wxCpMessage);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        });


        return null;
    }

}