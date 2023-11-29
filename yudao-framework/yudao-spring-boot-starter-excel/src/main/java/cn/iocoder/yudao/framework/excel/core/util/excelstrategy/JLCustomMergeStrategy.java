package cn.iocoder.yudao.framework.excel.core.util.excelstrategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class JLCustomMergeStrategy extends AbstractMergeStrategy {

    private Integer rowCount=0;

    public  JLCustomMergeStrategy(Integer rowCount){
        this.rowCount=rowCount;
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
                }
                if(prevName!=null){
                    prevIndex=prevIndex+mergeRowCount+1;
                }
                prevName = name;
                mergeRowCount=0;
            }

            System.out.println("rowCount:"+rowCount+"relativeRowIndex:"+relativeRowIndex);
            //处理最后一次合并
            if (relativeRowIndex == rowCount-1&&mergeRowCount>0) {
                sheet.addMergedRegionUnsafe(new CellRangeAddress(prevIndex, prevIndex+mergeRowCount, columnIndex, columnIndex));
            }

        }
    }

}
