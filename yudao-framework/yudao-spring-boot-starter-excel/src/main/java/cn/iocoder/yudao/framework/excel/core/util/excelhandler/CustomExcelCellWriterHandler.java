package cn.iocoder.yudao.framework.excel.core.util.excelhandler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

public class CustomExcelCellWriterHandler  implements CellWriteHandler {
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead)
    {
        // 这里可以对cell进行任何操作
//        System.out.println("进入第" +  cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "列数据...");
        if (cell.getRowIndex() >= 1 && 5 == cell.getColumnIndex())
        {
            // 税价 = 含税单价 * 数量 * 税率
            // 以第4行数据为例：税价 = C5*D5*$C$1
            int actualCellRowNum = cell.getRowIndex() + 1;
            cell.setCellFormula("D" + actualCellRowNum +"*E" + actualCellRowNum);
            System.out.println("第" +  cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "税价写入公式完成");
        }

/*        if (cell.getRowIndex() >= 4 && 5 == cell.getColumnIndex())
        {
            // 总价 = 含税单价 * 数量
            // 以第4行数据为例：税价 = C5*D5
            int actualCellRowNum = cell.getRowIndex() + 1;
            cell.setCellFormula("C" + actualCellRowNum + "*D" + actualCellRowNum);
            System.out.println("第" +  cell.getRowIndex() + "行,第" + cell.getColumnIndex() + "总价写入公式完成");
        }*/
    }

}
