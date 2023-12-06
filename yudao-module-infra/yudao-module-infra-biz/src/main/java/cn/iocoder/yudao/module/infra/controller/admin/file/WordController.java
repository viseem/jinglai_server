package cn.iocoder.yudao.module.infra.controller.admin.file;

import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.word.Html2WordReqVO;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

@Tag(name = "管理后台 - word")
@RestController
@RequestMapping("/infra/word")
@Validated
@Slf4j
public class WordController {

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @GetMapping("/html2word")
    @Operation(summary = "html转word")
    @OperateLog(logArgs = false) // 上传文件，没有记录操作日志的必要
    public void html2Word(Html2WordReqVO reqVO, HttpServletResponse response) throws Exception {
        String outputFilePath = "output.docx";

        if(reqVO.getQuotationId()!=null){
            Optional<ProjectQuotation> byId = projectQuotationRepository.findById(reqVO.getQuotationId());
            reqVO.setHtml(byId.get().getPlanText());
        }
        System.out.println("-=-=-=-=-=-=");
        byte[] bytes = convertAndSave(reqVO.getHtml());
        // Set response headers
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(outputFilePath, "UTF-8"));

        // Write the document bytes to the response
        response.getOutputStream().write(bytes);
        response.flushBuffer();
//        ServletUtils.writeAttachment(response, "output.docx", bytes);
    }

    private byte[] convertAndSave(String htmlContent) {
        try {

            StringBuffer sbf = new StringBuffer();
            sbf.append("<html " +
                    "xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\"" + //将版式从web版式改成页面试图
                    ">");//缺失的首标签
            sbf.append("<head>" +
                    "<!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]-->" +
                    "</head>");//将版式从web版式改成页面试图
            sbf.append("<body>");//缺失的首标签
            sbf.append(htmlContent);//富文本内容
            sbf.append("</body></html>");//缺失的尾标签

            // 创建一个新的Word文档
            XWPFDocument document = new XWPFDocument();
            // 添加内容
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(htmlContent);

            // 设置每页的页脚
            setFooter(document, "Page ");

            // 保存文档到字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            byte[] documentBytes = outputStream.toByteArray();

            System.out.println("Word文档生成成功！");

            return documentBytes;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void setFooter(XWPFDocument document, String footerText) {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        // 创建一个新的页脚
        XWPFFooter footer = policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

        // 添加段落和文本到页脚
        XWPFParagraph paragraph = footer.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(footerText);

        // 设置页脚对齐方式，可以根据需要进行调整
        paragraph.setAlignment(ParagraphAlignment.CENTER);
    }
}
