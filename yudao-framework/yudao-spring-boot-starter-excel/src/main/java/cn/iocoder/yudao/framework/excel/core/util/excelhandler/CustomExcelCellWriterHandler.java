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

        Workbook workbook = cell.getSheet().getWorkbook();

        // Create a cell style with centered alignment
        CellStyle centeredStyle = workbook.createCellStyle();
        centeredStyle.setAlignment(HorizontalAlignment.CENTER);
        centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置单元格居中
        cell.setCellStyle(centeredStyle);


//        System.out.println("进入第" +  cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");
        int actualCellRowNum = cell.getRowIndex() + 1;
        if (cell.getRowIndex() >= 1 && 5 == cell.getColumnIndex())
        {
            cell.setCellFormula("D" + actualCellRowNum +"*E" + actualCellRowNum);
//            System.out.println("第" +  cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "税价写入公式完成");
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
