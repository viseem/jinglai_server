package cn.iocoder.yudao.framework.excel.core.util.excelhandler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Map;

public class CustomExcelCellSizeWriterHandler implements CellWriteHandler {

    private Map<Integer, Integer> columnWidthMap;

    public CustomExcelCellSizeWriterHandler(Map<Integer, Integer> columnWidthMap) {
        this.columnWidthMap = columnWidthMap;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead)
    {

        /*
        * 设置列宽
        * */

/*        if (cell.getColumnIndex() == 0 ||cell.getColumnIndex() == 2)
        {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 23 * 256);
        }*/

        if (columnWidthMap != null && columnWidthMap.containsKey(cell.getColumnIndex())) {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidthMap.get(cell.getColumnIndex()) * 256);
        }


        cell.getRow().setHeightInPoints(24);


    }

}
