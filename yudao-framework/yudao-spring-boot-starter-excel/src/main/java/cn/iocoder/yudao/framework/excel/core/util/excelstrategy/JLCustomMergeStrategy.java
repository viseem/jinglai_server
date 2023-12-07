package cn.iocoder.yudao.framework.excel.core.util.excelstrategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Objects;

public class JLCustomMergeStrategy extends AbstractMergeStrategy {

    private Integer rowCount=0;
    private Integer supplyCount = 0;
    private Integer chargeCount = 0;

    public  JLCustomMergeStrategy(Integer supplyCount,Integer chargeCount){
        this.supplyCount = supplyCount;
        this.chargeCount = chargeCount;
        this.rowCount=supplyCount+chargeCount;
    }

    //跳过一行表头
    private Integer prevIndex = 1;
    private String prevName;

    private Integer mergeRowCount=0;

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int columnIndex = cell.getColumnIndex();



        if(columnIndex==0){
            String name = cell.getStringCellValue();

            if(name.equals(prevName)){
                mergeRowCount++;
            }else{
                if(mergeRowCount>0){
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(prevIndex, prevIndex+mergeRowCount, columnIndex, columnIndex));
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(prevIndex, prevIndex+mergeRowCount, 7, 7));

                }
                if(prevName!=null){
                    prevIndex=prevIndex+mergeRowCount+1;
                }
                prevName = name;
                mergeRowCount=0;
            }

            //处理最后一次合并
            if (relativeRowIndex == rowCount-1&&mergeRowCount>0) {
//                sheet.addMergedRegionUnsafe(new CellRangeAddress(prevIndex, prevIndex+mergeRowCount, columnIndex, columnIndex));
            }

            //注意不能单独判断行，还要同时判断column，这样不会重复合并
            if(Objects.equals(relativeRowIndex, supplyCount)){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(supplyCount+1, supplyCount+1, 0, 4));
            }

            if(Objects.equals(relativeRowIndex, rowCount+1)){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(rowCount+2, rowCount+2, 0, 4));
            }

            if(Objects.equals(relativeRowIndex, rowCount+2)){
                sheet.addMergedRegionUnsafe(new CellRangeAddress(rowCount+3, rowCount+3, 0, 4));
            }

        }


    }

}
