package cn.iocoder.yudao.module.jl.controller.admin.project;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.itextpdf.kernel.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 项目管理")
@RestController
@RequestMapping("/jl/project")
@Validated
public class ProjectTestController {

    @PostMapping("/test")
    @PermitAll
    @Operation(summary = "创建项目管理")
    public CommonResult<Long> createProject() {
        test();
        return success(1L);
    }

    public  void test() {
        String richText = "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<title>Red Circle</title>\n" +
                "<style>\n" +
                "    .circle {\n" +
                "        width: 100px;\n" +
                "        height: 100px;\n" +
                "        background-color: red;\n" +
                "        border-radius: 50%; /* 让元素呈现圆形 */\n" +
                "    }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"circle\"></div>\n" +
                "</body>\n" +
                "</html>";

        String outputPath = "./output.pdf";

        try {
            File file = new File(outputPath);
            file.getParentFile().mkdirs();

            FileOutputStream outputStream = new FileOutputStream(outputPath);
            PdfWriter writer = new PdfWriter(outputStream);
            ConverterProperties converterProperties = new ConverterProperties();

            HtmlConverter.convertToPdf(richText, outputStream, converterProperties);

            System.out.println("PDF 文件已成功生成：" + outputPath);
            /*File file = new File(outputPath);
            file.getParentFile().mkdirs();

            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 设置字体
            PdfFont font = PdfFontFactory.createFont();
            document.setFont(font);

            // 添加富文本内容
            Paragraph paragraph = new Paragraph().setFontSize(12);
            paragraph.add(richText);
            paragraph.setTextAlignment(TextAlignment.LEFT);
            document.add(paragraph);

            document.close();
            System.out.println("PDF 文件已成功生成：" + outputPath);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
