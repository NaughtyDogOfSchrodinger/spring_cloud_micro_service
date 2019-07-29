package com.jianghu.mscore.file.excel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.util.LinkedList;
import java.util.List;

public abstract class ExcelTemplate {

    private List<List<String>> dataList = new LinkedList<>();

    private String title = getTitle();

    //表头
    private String[] headers = getHeaders();

    //列标题
    private List<String> options = getOptions();

    protected abstract String[] getHeaders();

    protected abstract List<String> getOptions();

    protected abstract String getTitle();

    public List<List<String>> getDataList() {
        return dataList;
    }

    public ExcelTemplate setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
        return this;
    }

    //表头分组算法
    private int[] getTitleGroup() {
        int[] tempArray = new int[options.size()];
        for (int a = 0; a < tempArray.length; a++) {
            tempArray[a] = headers.length / options.size();
        }
        for (int i = tempArray.length - 1, j = 0; j < headers.length % options.size(); i--, j++) {
            tempArray[i] = headers.length / options.size() + 1;
        }
        return tempArray;
    }

    private void addData(HSSFSheet sheet, List<List<String>> result, int index) {
        // 遍历集合数据，产生数据行
        HSSFRow row;
        if (result != null) {
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str);
                    cellIndex++;
                }
                index++;
            }
        }
    }


    public void exportExcel(int sheetNum, List<List<String>> result, HSSFWorkbook hssfWorkbook) {
        // 生成一个表格
        HSSFSheet sheet = hssfWorkbook.createSheet();
        hssfWorkbook.setSheetName(sheetNum, title + "-" + (sheetNum + 1));
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = hssfWorkbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        //rowDataTop为空则从第二行开始
        boolean hasOptions = CollectionUtils.isNotEmpty(options);
        int rowNum = hasOptions ? 2 : 1;

        // 产生表格标题行
        HSSFRow row = sheet.createRow(rowNum);
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text.toString());
            }
        }

        HSSFRow row1 = sheet.createRow(0);
        // 在索引0的位置创建单元格（左上端）
        HSSFCell cell0 = row1.createCell(0);
        // 定义单元格为字符串类型
        cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell0.setCellStyle(style);
        // 在单元格中输入一些内容
        cell0.setCellValue(StringUtils.isEmpty(title) ? "导出表格" : title);

        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headers.length - 1);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);

        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(1, cra, sheet, hssfWorkbook); // 下边框
        RegionUtil.setBorderLeft(1, cra, sheet, hssfWorkbook); // 左边框
        RegionUtil.setBorderRight(1, cra, sheet, hssfWorkbook); // 有边框
        RegionUtil.setBorderTop(1, cra, sheet, hssfWorkbook); // 上边框

        if (CollectionUtils.isNotEmpty(options)) {
            row1 = sheet.createRow(rowNum - 1);
            int[] titleGroup = getTitleGroup();
            int lastIndex = 0;
            for (int i = 0; i < options.size(); i++) {
                // 第二行
                cell0 = row1.createCell(lastIndex);
                cell0.setCellStyle(style);
                cell0.setCellValue(options.get(i));
                CellRangeAddress cra1 = new CellRangeAddress(1, 1, lastIndex, lastIndex + titleGroup[i] - 1);
                lastIndex = lastIndex + titleGroup[i];
                //在sheet里增加合并单元格
                sheet.addMergedRegion(cra1);
                // 使用RegionUtil类为合并后的单元格添加边框
                RegionUtil.setBorderBottom(1, cra1, sheet, hssfWorkbook); // 下边框
                RegionUtil.setBorderLeft(1, cra1, sheet, hssfWorkbook); // 左边框
                RegionUtil.setBorderRight(1, cra1, sheet, hssfWorkbook); // 有边框
                RegionUtil.setBorderTop(1, cra1, sheet, hssfWorkbook); // 上边框
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            addData(sheet, result, hasOptions ? 3 : 2);
        }

    }
}

