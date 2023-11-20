package cn.iocoder.yudao.framework.excel.core.util.excelstrategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class JLCustomMergeStrategy extends AbstractMergeStrategy {
    private Map<String, Integer> nameStartIndexMap = new HashMap<>();
    private Map<String, Integer> nameCountMap = new HashMap<>();

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int columnIndex = cell.getColumnIndex();
        if(columnIndex==0){
            System.out.println("merge------------");
            String name = cell.getStringCellValue();
            Integer startIndex = nameStartIndexMap.get(name);
            Integer count = nameCountMap.get(name);

            if (startIndex == null) {
                // 这是一个新的name，开始一个新的合并
                nameStartIndexMap.put(name, relativeRowIndex);
                nameCountMap.put(name, 1);
            } else if (!name.equals(getName(sheet, cell))) {
                System.out.println("merge2------");
                // 这是一个不同的name，结束上一个name的合并
                if(count>0){
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(startIndex, startIndex + count - 1, cell.getColumnIndex(), cell.getColumnIndex()));
                }

                // 开始一个新的合并
                nameStartIndexMap.put(name, relativeRowIndex);
                nameCountMap.put(name, 1);
            } else {
                // 这是相同的name，继续合并
                nameCountMap.put(name, count + 1);
            }
        }
    }

    private String getName(Sheet sheet, Cell cell) {
        return sheet.getRow(cell.getRowIndex() - 1).getCell(cell.getColumnIndex()).getStringCellValue();
    }
}
