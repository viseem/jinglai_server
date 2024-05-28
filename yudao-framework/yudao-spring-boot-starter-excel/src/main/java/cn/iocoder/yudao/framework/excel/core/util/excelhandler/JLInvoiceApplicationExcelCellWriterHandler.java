package cn.iocoder.yudao.framework.excel.core.util.excelhandler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;

import java.util.List;

public class JLInvoiceApplicationExcelCellWriterHandler implements CellWriteHandler {


    public JLInvoiceApplicationExcelCellWriterHandler() {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        // 设置默认行高
        cell.getRow().setHeightInPoints(24);

        if(cell.getRowIndex()==0){
            // 设置标题行的行高
            cell.getRow().setHeightInPoints(40);

        }

/*        if(cell.getRowIndex()==0){
            Font font = writeSheetHolder.getSheet().getWorkbook().createFont();
            font.setFontHeightInPoints((short) 30); // 设置字体大小为16
            font.setBold(true); // 设置字体加粗
            Cell cell1 = cell.getRow().getCell(0);
            System.out.println(cell1+"--"+cell1.getColumnIndex()+cell1.getAddress());
            cell1.getCellStyle().setFont(font);
        }*/

        if (cell.getRowIndex() == 2) {
            //设置该行的行高
            cell.getRow().setHeightInPoints(150);
        }


        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 10 * 512);
        writeSheetHolder.getSheet().setColumnWidth(0, 13 * 512);


    }

}
