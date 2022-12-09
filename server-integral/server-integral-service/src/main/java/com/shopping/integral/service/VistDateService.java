package com.shopping.integral.service;

import com.alibaba.fastjson.JSONObject;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.integral.constant.RedisCons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class VistDateService {

    private static final Logger logger = LoggerFactory.getLogger(VistDateService.class);

    @Autowired
    RedisService redisService;

    /**
     * 访问用户数
     * @param appkey
     * @channel 渠道
     */
    public void VIST_UV(String appkey,String token,String channel){
        Object uv = redisService.hget(appkey, RedisCons.VIST_DATA_UV);
        HashSet set;
        if(uv instanceof HashSet){
            set = (HashSet)uv;
            set.add(token);
        }else{
            set = new HashSet();
            set.add(token);
        }
        redisService.hset(appkey, RedisCons.VIST_DATA_UV,set);
        switch (channel){
            //微信小程序
            case "wxApp":
                HashSet wxuv = (HashSet)redisService.hget(appkey, RedisCons.VIST_DATA_UV);
                wxuv.add(token);
                redisService.hset(appkey, RedisCons.VIST_DATA_WX_UV,wxuv);
                break;
            case "h5":

            default:
                break;

        }
    }

    public int GET_VIST_UV(String appkey){
        Object uv = redisService.hget(appkey, RedisCons.VIST_DATA_UV);
        HashSet set;
        if(uv instanceof HashSet){
            set = (HashSet)uv;
            return set!=null ? set.size() : 0;
        }else{
            return 0;
        }
    }

    public int GET_VIST_WX_UV(String appkey){
        Object uv = redisService.hget(appkey, RedisCons.VIST_DATA_WX_UV);
        HashSet set;
        if(uv instanceof HashSet){
            set = (HashSet)uv;
            return set!=null ? set.size() : 0;
        }else{
            return 0;
        }
    }

    /**
     * 访问页面数
     * @param appkey
     */
    public void VIST_PV(String appkey){
        int pv = CommUtils.null2Int(redisService.hget(appkey,RedisCons.VIST_DATA_PV));
        pv ++;
        redisService.hset(appkey, RedisCons.VIST_DATA_PV,pv);
    }

    public int GET_VIST_PV(String appkey){
        int pv = CommUtils.null2Int(redisService.hget(appkey,RedisCons.VIST_DATA_PV));
        return pv;
    }

    /**
     * 商品访问UV
     * @param appkey
     * @param goodsId
     * @param goodsName
     */
    public void VIST_GOODS_UV(String appkey,String goodsId,String goodsName,String token){
        Map<String,JSONObject> uv_map = (Map)(redisService.hget(appkey,RedisCons.VIST_DATA_GOODS_UV));
        if(uv_map==null){
            uv_map = new HashMap<>();
        }
        JSONObject obj = null;
        //如果存在这个商品的redis uv 信息 则只进行子女增
        if(uv_map.get(goodsId) != null){
            obj = uv_map.get(goodsId);
            //判断类型
            if(obj.get("uv") instanceof HashSet){
                HashSet set = (HashSet)obj.get("uv");
                set.add(token);
                obj.put("uv", set);
            }else{
                HashSet set = new HashSet();
                set.add(token);
                obj.put("uv",set);
            }
        }else{
            //没有存在这个商品的信息直接新增
            obj = new JSONObject();
            obj.put("goodsName", goodsName);
            HashSet set = new HashSet();
            set.add(token);
            obj.put("uv",set);
        }
        uv_map.put(goodsId,obj);
        redisService.hset(appkey, RedisCons.VIST_DATA_GOODS_UV,uv_map);
    }


    public Map<String,JSONObject> GET_VIST_GOODS_UV(String appkey){
        Map<String,JSONObject> uv_map = (Map)(redisService.hget(appkey,RedisCons.VIST_DATA_GOODS_UV));
        return uv_map;
    }

    /**
     * 商品访问PV
     * @param appkey
     * @param goodsId
     * @param goodsName
     */
    public void VIST_GOODS_PV(String appkey,String goodsId,String goodsName){
        Map<String,JSONObject> uv_map = (Map)(redisService.hget(appkey,RedisCons.VIST_DATA_GOODS_PV));
        if(uv_map==null){
            uv_map = new HashMap<>();
        }
        JSONObject obj = null;
        if(uv_map.get(goodsId)!=null){
            obj = uv_map.get(goodsId);
            int pv = CommUtils.null2Int(obj.get("pv"));
            pv ++;
            obj.put("pv",pv);
        }else{
            obj = new JSONObject();
            obj.put("goodsName",goodsName);
            obj.put("pv",1);
        }
        uv_map.put(goodsId,obj);
        redisService.hset(appkey, RedisCons.VIST_DATA_GOODS_PV,uv_map);
    }

    public Map<String,JSONObject> GET_VIST_GOODS_PV(String appkey){
        Map<String,JSONObject> uv_map = (Map)(redisService.hget(appkey,RedisCons.VIST_DATA_GOODS_PV));
        return uv_map;
    }

    /**
     * 走完定时器删除所有数据
     * @param appkey
     */
    public void clean_VSIT_DATA(String appkey){
        redisService.hdel(appkey,RedisCons.VIST_DATA_UV,RedisCons.VIST_DATA_PV,RedisCons.VIST_DATA_GOODS_UV,RedisCons.VIST_DATA_GOODS_PV,RedisCons.VIST_DATA_ACT_PV);
    }
}
