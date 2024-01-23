package cn.iocoder.yudao.framework.excel.core.util.excelhandler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class CustomExcelCellWriterHandler  implements CellWriteHandler {

    private  Integer supplyCount=0;
    private  Integer chargeCount=0;
    private  Integer rowCount=0;

    public CustomExcelCellWriterHandler(Integer supplyCount, Integer chargeCount){
        this.supplyCount=supplyCount;
        this.chargeCount=chargeCount;
        this.rowCount=supplyCount+chargeCount+3;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead)
    {

        /*
        * 设置列宽
        * */

        if (cell.getColumnIndex() == 4 ||cell.getColumnIndex() == 3)
        {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 10 * 256);
        }
        if (cell.getColumnIndex() == 5)
        {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 12 * 256);
        }
        //最后一列
        if (cell.getColumnIndex() == 7)
        {
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 10 * 256);
        }

        /*
        * 设置行高
        * */
        if (cell.getRowIndex() == 0)
        {
            cell.getRow().setHeightInPoints(26);
        }

        if (cell.getRowIndex() >= 1)
        {
            cell.getRow().setHeightInPoints(24);
        }

        int actualCellRowNum = cell.getRowIndex() + 1;
        if (cell.getRowIndex() >= 1 && 5 == cell.getColumnIndex())
        {
            cell.setCellFormula("D" + actualCellRowNum +"*E" + actualCellRowNum);
        }

        //实验材料合计
        if(cell.getRowIndex()==supplyCount+1 && 5 == cell.getColumnIndex()){
            cell.setCellFormula("SUM(F2:F" + cell.getRowIndex() +")");
            //合并
        }

        if(cell.getRowIndex()==supplyCount+chargeCount+2 && 5 == cell.getColumnIndex()){
            cell.setCellFormula("SUM(F"+(supplyCount+3)+":F" + cell.getRowIndex() +")");
        }
        if(cell.getRowIndex()==rowCount && 5 == cell.getColumnIndex()){
            cell.setCellFormula("SUM(F"+(supplyCount+2)+"+F" + cell.getRowIndex() +")");
        }
    }

}
