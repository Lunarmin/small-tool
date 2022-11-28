package com.plf.tool.common.file;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.List;
import java.util.Map;

public class ExcelUtils {

    /**
     * 根据sheet读取Excel列表
     * @param filePath
     * @param sheet
     * @return
     */
    public static List<Map<String,Object>> readMap(String filePath,String sheet){
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath), sheet);
        return reader.readAll();
    }

    /**
     * 导出Excel文件
     * @param excelPath 输出地址
     * @param data 数据
     * @param alias 别名
     * @param title 标题行
     * @param mergeColumn 标题行需要合并的列数
     */
    public  static <T>  void  exportExcel(String excelPath,
                                          List<T> data,
                                          Map<String,String> alias,
                                          String title,
                                          int mergeColumn){
        ExcelWriter writer = ExcelUtil.getWriter(excelPath);
        writer.setOnlyAlias(true);

        if(CollectionUtil.isNotEmpty(alias)) {
            alias.forEach((k, v) -> {
                writer.addHeaderAlias(k, v);
            });
        }

        if(StrUtil.isNotEmpty(title)) {
            // 合并单元格后的标题行，使用默认标题样式
            writer.merge(mergeColumn, title);
        }

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(data, true);
        // 关闭writer，释放内存
        writer.close();
    }

}