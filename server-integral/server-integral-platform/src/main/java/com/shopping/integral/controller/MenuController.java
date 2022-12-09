package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：菜单操作接口
 *
 * @author：GuoFuJun
 * @date：2018年1月26日 上午11:13:46
 */
@Log4j2
@Api(value = "菜单操作", tags = {"menu"})
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "获取菜单", tags = {"menu"}, notes = "")
    @ResponseBody
    @GetMapping(value = "/getMenuList")
    public ActionResult<BaseResult> getMenuList(HttpServletRequest request) {
        try {
            BaseResult baseResult = menuService.getMenuList();
            return ActionResult.ok(baseResult);
        } catch (Exception e) {
            log.error("注册账户异常！", e);
        }
        return ActionResult.error("服务器异常");
    }
}
