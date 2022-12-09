import {_post,_get,_put,_delete} from '../http/appApi'

/**
 * 获取当前登录对象
 */
export const getLoginInfo = () => _get('/autoLogin/login_user');

/**
 * APP 首页banner
 */
export const getApply = (params) => _get('/app/index/get_apply',params);

/**
 * APP 首页banner
 */
export const getApplyThemeItem = (params) => _get('/app/index/get_apply_theme_item',params);

/**
 * APP 首页banner
 */
export const getApplyThemeItemData = (params) => _get('/app/index/get_apply_theme_item_data/'+params.themeItemId,params);


/**
 * 商品详情
 */
export const getGoodsDetails = (params) => _get('/app/goods/get_goods_details/'+params.goodsId);

/**
 * 商品列表
 */
export const getGoodsList = (params) => _get('/app/goods/get_goods_list',params);

/**
 * APP子菜单
 */
export const getSubmenu = (params) => _get('app/submenu',params);

/**
 * APP九宫格商品列表
 */
export const getGridItemList = (params) => _get('app/gridItemList',params);


/**
 * 获取收货地址
 */
export const getAddress = (params) => _get("/app/address/get_address",params);

/**
 * 添加收货地址
 */
export const saveAddress = (params) => _post("/app/address/save_address",params);

/**
 * 添加收货地址
 */
export const updateAddress = (params) => _put("/app/address/put_address/"+params.id,params);

/**
 * 兑换商品下单
 */
export const saveOrder = (params) => _post('app/order/set_order',params);

/**
 * 兑换商品订单详情
 */
export const getOrderDetails = (params) => _get('app/order/get_order_details/'+params.orderId,);

/**
 * 兑换商品订单详情(核销码)
 */
export const getOrderDetailsByVerifyCode = (params) => _get('app/order/get_order_details_by_VerifyCode/'+params.acApplyId+"/"+params.verifyCode,);

/**
 * 兑换记录
 */
export const getRecords = (params) => _get('app/order/get_order_list',params);

/**
 * 兑换记录明细
 */
export const getRecordDetail = (params) => _get('app/recordDetail',params);


/**
 * 兑换记录明细
 */
export const getItemList = (params) => _get('',params);

/**
 * 兑换记录明细
 */
export const postCreatePay = (params) => _post('/app/pay/post_create_pay',params);

/**
 * 取消订单
 */
export const postCancelOrder = (orderId) => _post('/app/order/set_order_cancel/'+orderId);

/**
 * 核销订单
 */
export const postVerifyOrder = (orderId) => _post('/app/order/verify_order/'+orderId);

/**
 * 核销订单
 */
export const getCategoryDetails = (categoryId) => _get('/app/category/get_category_details/'+categoryId);
