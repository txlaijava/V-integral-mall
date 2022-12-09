package com.shopping.integral.controller;

import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.model.IntegralUser;
import com.shopping.integral.queryform.IntegralAddressForm;
import com.shopping.integral.service.IIntegralAddressService;
import com.shopping.integral.token.authorization.annotation.CacheUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 类描述：收货地址操作
 *
 * @author：GuoFuJun
 * @date：2018年7月10日 上午15:34:46
 */
@Log4j2
@Api(value = "收货地址操作", tags = {"address"})
@RestController
@RequestMapping("/app/address")
public class AddressController {

    @Autowired
    IIntegralAddressService integralAddressService;

    @ApiOperation(value = "获取收货地址", tags = {"address"}, notes = "")
    @GetMapping(value = "/get_address")
    public ActionResult<BaseResult> getAddress(@CacheUser IntegralUser integralUser, Long addressId) {
        try {
            if (Utils.isNotEmpty(integralUser)) {
                BaseResult baseResult = integralAddressService.getIntegralAddress(integralUser.getId(), addressId);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("获取收货地址接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "添加收货地址", tags = {"address"}, notes = "")
    @PostMapping(value = "/save_address")
    public ActionResult<BaseResult> saveAddress(@CacheUser IntegralUser integralUser,@Valid IntegralAddressForm integralAddressForm) {
        try {
            if (Utils.isNotEmpty(integralUser)) {
                BaseResult baseResult = integralAddressService.saveIntegralAddress(integralUser.getId(), null, integralAddressForm);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("添加收货地址接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "修改收货地址", tags = {"address"}, notes = "")
    @PutMapping(value = "/put_address/{addressId}")
    public ActionResult<BaseResult> putAddress(@CacheUser IntegralUser integralUser, @PathVariable("addressId") Long addressId,
                                               IntegralAddressForm integralAddressForm) {
        try {
            if (Utils.isNotEmpty(integralUser)) {
                BaseResult baseResult = integralAddressService.saveIntegralAddress(integralUser.getId(), addressId, integralAddressForm);
                return ActionResult.ok(baseResult);
            }
        } catch (Exception e) {
            log.error("修改收货地址接口异常！", e);
        }
        return ActionResult.error("服务器异常");
    }
}
