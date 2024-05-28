package cn.iocoder.yudao.framework.excel.core.util.excelstrategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Objects;

public class JLInvoiceApplicationExcelMergeStrategy extends AbstractMergeStrategy {


    int colCount = 7;


    public JLInvoiceApplicationExcelMergeStrategy(){

    }


    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {

        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();

        if(columnIndex==0){
            if(rowIndex==0){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, colCount-1));
            }
            if(rowIndex==1){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 3, colCount-1));
            }
            if(rowIndex==2){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 0, 2));
            }
        }

        if(columnIndex==1){

        }

        if(columnIndex==2){

        }

        if(columnIndex==3){
            if(rowIndex==2){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 3, colCount-1));
            }

        }

        if(columnIndex==4){

        }

        if(columnIndex==5){

        }

        if(columnIndex==6){

        }


    }

}
