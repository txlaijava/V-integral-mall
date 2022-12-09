package com.shopping.integral.job.jobhandler;

import com.shopping.integral.dao.model.IntegralGoods;
import com.shopping.integral.service.IIntegralGoodsService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品自动下架定时器
 */
@Log4j
@Component
public class GoodsAutoLowerShelvesJobHandler {

    @Autowired
    private IIntegralGoodsService goodsService;

	@XxlJob("goodsAutoLowerShelvesJobHandler")
	public ReturnT<String> execute(String param) throws Exception {

		Map pa = new HashMap<>();
		//查询自动下架时间为当日的所有商品
		String sql = "select * from integral_goods as g where g.goods_state = 1 and" +
                " date_format(g.auto_lower_shelves_time,'%Y-%m-%d') <= date_format(now(),'%Y-%m-%d') ";
        List<IntegralGoods> goodsList = this.goodsService.queryBySql(sql,null);
        if(goodsList!=null && goodsList.size()>0){
            for (IntegralGoods goods:goodsList) {
                try{
                    IntegralGoods integralgoods = this.goodsService.getObjById(goods.getId());
                    integralgoods.setGoodsState(3);             //下架
                    this.goodsService.update(integralgoods);
                    log.info("执行成功,商品id："+ goods.getId());
                }catch (Exception e){
                    log.error("执行错误,商品id："+ goods.getId(),e);
                }
            }
        }else{
            log.info("执行成功,今日无自动下架商品");
        }
		return ReturnT.SUCCESS;
	}

}
