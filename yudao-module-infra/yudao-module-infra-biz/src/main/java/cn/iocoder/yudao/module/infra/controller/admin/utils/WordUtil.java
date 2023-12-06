package cn.iocoder.yudao.module.infra.controller.admin.utils;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

public class WordUtil {
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String content, String fileName) throws Exception {

        byte[] b = content.getBytes("GBK"); //这里是必须要设置编码的，不然导出中文就会乱码。
        ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry documentEntry = directory.createDocument("WordDocument", bais); //该步骤不可省略，否则会出现乱码。
        //输出文件
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");//导出word格式
        response.addHeader("Content-Disposition", "attachment;filename=" +
                new String(fileName.getBytes("GB2312"),"iso8859-1") + ".doc");
        ServletOutputStream ostream = response.getOutputStream();
        poifs.writeFilesystem(ostream);
        bais.close();
        ostream.close();
        poifs.close();
    }

    public static void downloadWord( byte[] b, OutputStream out)
            throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中
        try {

            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            poifs.writeFilesystem(out);
            bais.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(bais!=null){bais.close();}
            if(out!=null) {out.flush();out.close();}
        }
    }
}