package com.shopping.integral.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author txl
 */

@Controller
public class AppBaseController {

    @Value("${app.path}")
    private String appPath;

    protected void errorPage(HttpServletResponse response,String msg) throws Exception {
        response.sendRedirect(appPath + "/index/home/500?msg="+ URLEncoder.encode(msg, "UTF-8"));
        return;
    }
}
