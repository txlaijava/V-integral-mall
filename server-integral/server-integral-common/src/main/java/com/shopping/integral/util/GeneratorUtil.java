package com.shopping.integral.util;

import com.shopping.base.foundation.util.DateUtils;
import com.shopping.base.utils.StringUtils;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.Date;

/**
 * Created by gfj on 2018/5/3.
 */
public class GeneratorUtil {
    /**
     * dao_vm : DAO模板路径
     */
    private static String daoVmPath = "/template/Dao.vm";
    /**
     * service_vm : Service模板路径
     */
    private static String serviceVmPath = "/template/Service.vm";
    /**
     * serviceImpl_vm : ServiceImpl模板路径
     */
    private static String serviceImplVmPath = "/template/ServiceImpl.vm";

    private static String basePath = System.getProperty("user.dir").replace("/target/classes/", "") + "/";

    private static String rootPath = "/src/main/java/";


    /**
     * 处理生成路径
     *
     * @param c           需要生成实现的Base
     * @param module      模块
     * @param packageName 包名
     */
    public static void generator(Class c, String module, String packageName) {

        // 需要剔除 -dao 文件夹的路径
        String targetProject = module + "/" + module + "-dao";
        String path = basePath.replace(targetProject, "") + module + "/" + module;
        String daoPath = path + "-dao" + rootPath + packageName.replaceAll("\\.", "/") + "/dao";

        String servicePath = path + "-service" + rootPath + packageName.replaceAll("\\.", "/") + "/service";

        String serviceImplPath = servicePath + "/impl";

        String dao = daoPath + "/" + c.getSimpleName() + "DAO.java",
                // 实体类的接口层
                service = servicePath + "/I" + c.getSimpleName() + "Service.java",
                // 实体类的接口实现成功
                serviceImpl = serviceImplPath + "/" + c.getSimpleName() + "ServiceImpl.java";
        try {
            // 生成类
            buildTpl(daoVmPath, c, packageName, module, dao);
            buildTpl(serviceVmPath, c, packageName, module, service);
            buildTpl(serviceImplVmPath, c, packageName, module, serviceImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成对应的文件
     *
     * @param tpl         模板路径
     * @param c           实体类
     * @param packageName 包名
     * @param module      模块名称
     * @param filePath    生成的类文件路径
     * @throws Exception
     */
    private static void buildTpl(String tpl, Class c, String packageName, String module, String filePath) throws Exception {
        // 类文件
        File classFile = new File(filePath);
        if (!classFile.exists()) {
            VelocityContext context = new VelocityContext();
            context.put("packageName", packageName);
            context.put("modelName", module);
            context.put("model", c.getSimpleName());
            context.put("modelRepository", StringUtils.toLowerCaseFirstOne(c.getSimpleName()));
            context.put("ctime", DateUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
            VelocityUtil.generate(tpl, filePath, context);
            System.out.println(filePath);
        }

    }
}
