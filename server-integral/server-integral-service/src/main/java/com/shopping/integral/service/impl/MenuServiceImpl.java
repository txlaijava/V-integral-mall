package com.shopping.integral.service.impl;

import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.Utils;
import com.shopping.integral.base.BaseResult;
import com.shopping.integral.dao.IntegralMenuDAO;
import com.shopping.integral.enums.ResultErrorCodeEnum;
import com.shopping.integral.service.IMenuService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类描述：MenuServiceImpl 接口
 * 菜单处理
 * 类编号: Code 201
 *
 * @author：GuoFuJun
 * @date：2018年06月06日 10:50:30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j
public class MenuServiceImpl implements IMenuService{

    @Autowired
    private IntegralMenuDAO integralMenuDAO;

    @Override
    public BaseResult getMenuList() {
        try{
            StringBuffer sql = new StringBuffer("SELECT a.id , a.`name` , a.menu_id , a.URL , a.sorting , a.grandpa_id , a.head_logo");
            sql.append(" FROM integral_menu a WHERE a.menu_type = :menuType AND a.delete_status = 0");
            //所有菜单
            Map params = new HashedMap();
            params.put("menuType",2);
            List<Map<String,Object>> menuList = integralMenuDAO.queryByNativeSQL(sql.toString(),params);
            //一级菜单
            List<Map<String,Object>> navList = new ArrayList<>();
            Map<String,Object> navObj = new HashedMap();
            menuList.forEach(itemOe ->{
                String idKey = "id", menuIdKey = "menu_id",grandpaIdKey = "grandpa_id";
                Boolean eqKeyNuLL = Utils.isEmpty(itemOe.get(menuIdKey)) && Utils.isEmpty(itemOe.get(grandpaIdKey));
                Map<String,Object> menuTow = new HashedMap();
                Map<String,Object> menuThree = new HashedMap();
                if(eqKeyNuLL){
                    // 二级菜单
                    List<Map<String,Object>> childList = new ArrayList<>();
                    menuList.forEach(itemTwo ->{
                        Boolean twoMenuIdKeyNotNull = Utils.isNotEmpty(itemTwo.get(menuIdKey));
                        if (twoMenuIdKeyNotNull){
                            //判断是否为当前一级菜单的二级菜单
                            Boolean equal = CommUtils.null2String(itemOe.get(idKey)).equals(CommUtils.null2String(itemTwo.get(menuIdKey)));
                            if (equal) {
                                // 三级菜单
                                List<Map<String,Object>> childSubList = new ArrayList<>();
                                menuList.forEach(itemThree ->{
                                    if(Utils.isNotEmpty(itemThree.get(grandpaIdKey))) {
                                        //判断是否为当前二级菜单的三级菜单
                                        if (CommUtils.null2String(itemThree.get(grandpaIdKey)).equals(CommUtils.null2String(itemTwo.get(idKey)))) {
                                            childSubList.add(itemThree);
                                        }
                                    }
                                });
                                // 排序代码如下
                                List<Map<String, Object>> collect = childSubList.stream().sorted(Comparator.comparing(MenuServiceImpl::comparingByDesc)
                                        .thenComparing(Comparator.comparing(MenuServiceImpl::comparingByDesc).reversed()))
                                        .collect(Collectors.toList());
                                menuThree.put(itemTwo.get("id").toString(),collect);
                                navObj.put("menuThree",menuThree);
                            }
                            // 二级菜单集合
                            childList.add(itemTwo);
                        }
                    });
                    // 保存一级菜单
                    navList.add(itemOe);
                    navObj.put("menuOn",navList);
                    // 保存二级菜单
                    List<Map<String, Object>> child = childList.stream().sorted(Comparator.comparing(MenuServiceImpl::comparingByDesc)
                            .thenComparing(Comparator.comparing(MenuServiceImpl::comparingByDesc).reversed()))
                            .collect(Collectors.toList());
                    menuTow.put(itemOe.get("id").toString(),child);
                    navObj.put("menuTow",menuTow);
                }
            });
            return BaseResult.SUCCESS("获取菜单成功",navObj);
        }catch (Exception e){
            log.error("获取菜单异常！{}",e);
        }
        return BaseResult.ERROR(ResultErrorCodeEnum.UNKNOWN_ERROR);
    }

    private static Integer comparingByDesc(Map<String, Object> map){
        return (Integer) map.get("sorting");
    }
}
