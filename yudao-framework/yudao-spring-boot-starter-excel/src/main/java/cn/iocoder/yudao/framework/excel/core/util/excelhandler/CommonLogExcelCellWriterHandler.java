package cn.iocoder.yudao.framework.excel.core.util.excelhandler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

public class CommonLogExcelCellWriterHandler implements CellWriteHandler {


    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead)
    {

        /*
        * 设置列宽
        * */

        if (cell.getColumnIndex() == 0 ||cell.getColumnIndex() == 2)
        {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 23 * 256);
        }

        /*
        * 设置行高
        * */
/*        if (cell.getRowIndex() == 0)
        {
            cell.getRow().setHeightInPoints(26);
        }*/
        cell.getRow().setHeightInPoints(24);


    }

}
