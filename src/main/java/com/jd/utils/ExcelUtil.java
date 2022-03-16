package com.jd.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @Author ext.jianghzhongren
 * @Date 2021-06-10
 * @Description Excel工具类
 **/
@Slf4j
@Component
public class ExcelUtil {

    private static final int maxRow = 65535;

    private static final String formatter = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private static ExecutorService executorService;

    /**
     * 生成Excel 输出到浏览器
     *
     * @param list     数据源
     * @param fieldMap 数据源中字段名对应的中文释义
     * @param response response
     * @param fileName Excel文件名
     * @param <T>      实体
     */
    public static <T> void generateExcel(List<T> list, Map<String, String> fieldMap, HttpServletResponse response, String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("文件名不能为空");
        }

        // 设置请求头
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 创建工作簿
            WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
            doGenerateExcel(list, fieldMap, workbook);
        } catch (Exception e) {
            log.error("导出异常", e);
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

        }
    }

    /**
     * 生成Excel 输出到文件
     *
     * @param list     数据源
     * @param fieldMap 数据源中字段名对应的中文释义
     * @param file     目标Excel文件
     * @param <T>      实体
     * @throws Exception 异常抛出去
     */
    public static <T> void generateExcel(List<T> list, HashMap<String, String> fieldMap, File file) throws Exception {
        // 创建工作簿
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file);
            doGenerateExcel(list, fieldMap, workbook);
        } catch (Exception e) {
            log.error("导出异常", e);
            throw e;
        }
    }

    /**
     * 解析Excel 转换成List
     *
     * @param in          excel输入流
     * @param entityClass 转换的实体
     * @param fieldMap    Excel中的数据与实体的对应关系
     * @param <T>         实体
     * @return 列表
     * @throws Exception 抛出异常
     */
    public static <T> List<T> parseExcel(InputStream in, Class<T> entityClass, LinkedHashMap<String, String> fieldMap) throws Exception {
        List<T> resultList = new ArrayList<>();

        try {
            // 读取Excel
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(0);

            // 解析Excel
            parseExcelContent(sheet, fieldMap, entityClass, resultList);

        } catch (Exception e) {
            log.error("解析Excel报错", e);
            throw e;
        }

        return resultList;
    }

    /**
     * 解析Excel具体内容
     *
     * @param sheet       sheet表
     * @param fieldMap    Excel中的数据与实体的对应关系
     * @param entityClass 实体class
     * @param resultList  解析的结果集
     * @param <T>         实体
     * @throws Exception 异常抛出
     */
    private static <T> void parseExcelContent(Sheet sheet, HashMap<String, String> fieldMap, Class<T> entityClass, List<T> resultList) throws Exception {
        if (sheet.getRows() < 2) {
            return;
        }
        // 第一行为标题
        Cell[] firstCellArray = sheet.getRow(0);
        Map<String, Integer> colNameMap = new HashMap<>();

        // 获取列名及其序号对应关系
        for (int i = 0; i < firstCellArray.length; i++) {
            colNameMap.put(firstCellArray[i].getContents(), i);
        }

        // 读取Excel正文
        for (int i = 1; i < sheet.getRows(); i++) {
            // 待转换的Map
            Map<String, String> entityValueMap = new HashMap<>();

            for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                String enName = entry.getKey();
                String cnName = entry.getValue();

                // 获取每个单元格的值 获取不到说明Excel格式不正确
                Integer col = colNameMap.get(cnName);
                if (col == null) {
                    log.error("Excel中字段【{}】错误", cnName);
                    throw new Exception("Excel中字段【" + cnName + "】错误");
                }
                String content = sheet.getCell(col, i).getContents();

                if (StringUtils.isEmpty(content)) {
                    continue;
                }

                entityValueMap.put(enName, content);
            }

            // 将Map转换为实体
            T entity = entityClass.newInstance();
            try {
                map2Object(entityValueMap, entity);
            } catch (Exception exception) {
                String errorEnName = StringUtils.substringAfter(exception.getMessage(), ":");
                String errorCnName = fieldMap.get(errorEnName);
                throw new Exception("第" + (i + 1) + "行字段【" + errorCnName + "】的值【" + entityValueMap.get(errorEnName) + "】类型不正确");
            }

            resultList.add(entity);
        }
    }

    /**
     * 生成Excel
     *
     * @param list     待生成的数据源
     * @param fieldMap 数据源中字段名对应的中文释义
     * @param workbook excel work表
     * @param <T>      实体
     * @throws Exception 异常抛出
     */
    private static <T> void doGenerateExcel(List<T> list, Map<String, String> fieldMap, WritableWorkbook workbook) throws Exception {
        if (fieldMap == null || fieldMap.size() == 0) {
            throw new Exception("数据源中文释义不能为空");
        }

        try {
            // excel2003最大行为65536，去掉表头为65535，超过65535行则分为多个sheet
            int sheetNum = list.size() / maxRow + 1;
            for (int i = 0; i < sheetNum; i++) {
                WritableSheet sheet = workbook.createSheet("Sheet" + i, i);
                int firstIndex = maxRow * i;
                int lastIndex = Math.min(list.size(), maxRow * (i + 1));
                // 构建具体单元格
                buildSheet(sheet, list, fieldMap, firstIndex, lastIndex);
            }

            workbook.write();
        } catch (IOException e) {
            log.error("生成Excel异常", e);
            throw e;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private static <T> void buildSheet(WritableSheet sheet, List<T> list, Map<String, String> fieldMap, int firstIndex, int lastIndex) throws Exception {

        //构建标题
        Label la = new Label(0, 0, "我爱中国");
        sheet.addCell(la);
        sheet.mergeCells(0, 0, 4, 0);
        // 构建表头
        int colNum = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            Label label = new Label(colNum, 1, entry.getValue());
            sheet.addCell(label);
            colNum++;
        }

        // 构建内容
        int rowNum = 2;
        for (int i = firstIndex; i < lastIndex; i++) {
            T item = list.get(i);
            Map<String, String> itemMap = object2Map(item);

            colNum = 0;
            for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                String value = itemMap.get(entry.getKey()) == null ? "" : itemMap.get(entry.getKey());
                Label label = new Label(colNum, rowNum, value);
                sheet.addCell(label);
                colNum++;
            }

            rowNum++;
        }
    }

    private static <T> void map2Object(Map<String, String> entityValueMap, T obj) throws Exception {
        Class<?> clazz = obj.getClass();

        Field[] declaredFieldArray = clazz.getDeclaredFields();
        for (Field field : declaredFieldArray) {
            field.setAccessible(Boolean.TRUE);

            String value = entityValueMap.get(field.getName());
            if (StringUtils.isEmpty(value)) {
                continue;
            }

            Class<?> fieldType = field.getType();
            try {
                // 根据字段类型赋值
                if (String.class == fieldType) {
                    field.set(obj, value);
                } else if (Integer.class == fieldType) {
                    field.set(obj, Integer.parseInt(value));
                } else if (Long.class == fieldType) {
                    field.set(obj, Long.parseLong(value));
                } else if (BigDecimal.class == fieldType) {
                    field.set(obj, new BigDecimal(value));
                } else if (Date.class == fieldType) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(value);
                    field.set(obj, date);
                } else {
                    field.set(obj, value);
                }
            } catch (NumberFormatException | ParseException exception) {
                log.error("类型转换异常", exception);
                throw new Exception("字段类型不正确:" + field.getName());
            }
        }

    }

    private static Map<String, String> object2Map(Object obj) throws IllegalAccessException {
        if (obj instanceof Map) {
            return (Map) obj;
        } else {
            Map<String, String> map = new HashMap<>();

            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);

                    Object fieldValueObj = field.get(obj);
                    String fieldValue;
                    if (fieldValueObj != null && field.getType() == Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
                        fieldValue = sdf.format(fieldValueObj);
                    } else {
                        fieldValue = fieldValueObj == null ? "" : String.valueOf(fieldValueObj);
                    }

                    map.put(field.getName(), fieldValue);
                }
            } catch (IllegalAccessException e) {
                log.error("Class转Map异常", e);
                throw e;
            }
            return map;
        }
    }
}