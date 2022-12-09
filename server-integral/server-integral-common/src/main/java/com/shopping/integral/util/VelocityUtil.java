package com.shopping.integral.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.StringWriter;

/**
 * Created by gfj on 2018/5/3.
 */
public class VelocityUtil {

    /**
     * 根据模板生成文件
     *
     * @param inputVmFilePath 模板路径
     * @param outputFilePath  输出文件路径
     * @param context
     * @throws Exception
     */
    public static void generate(String inputVmFilePath, String outputFilePath, VelocityContext context) throws Exception {
        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty("resource.loader", "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();

            File outputFile = new File(outputFilePath);

            Template template = ve.getTemplate(inputVmFilePath, "utf-8");
            StringWriter sw = new StringWriter();
            template.merge(context, sw);
            FileUtils.writeStringToFile(outputFile, sw.toString(), "UTF-8");
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 根据文件绝对路径获取目录
     *
     * @param filePath
     * @return
     */
    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    /**
     * 根据文件绝对路径获取文件
     *
     * @param filePath
     * @return
     */
    public static String getFile(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }
}
