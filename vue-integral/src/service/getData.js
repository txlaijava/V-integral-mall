

import {_post,_get,_put,_delete,_upload} from '../http/api'

/**
 * 账号密码登录
 */
export const accountLogin = (params) => _get('/login',params);

/**
 * 创建账号
 */
export const singnupAc = (params) => _post('/register/setIntegralAc',params);

/**
 * 发送验证码
 */
export const getSMSCode = (params) => _get('/common/getSMSCode',params);

/**
 * 验证短信验证码
 */
export const getValidCode = (params) => _get('/common/getValidCode',params);

/**
 * 发送邮箱
 */
export const getSendMail = (params) => _get('/common/sendMail',params);

/**
 * 验证账号是否存在
 */
export const getValiAc = (params) => _get('/common/getValiAc',params);

/**
 * 修改账号密码
 */
export const setAcPwd = (params) => _post('/common/setAcPwd',params);

/**
 * 修改子账号密码
 */
export const setAcChildPwd = (params) => _post('/common/setAcChildPwd',params);

/**
 * 获取菜单
 */
export const getMenuList = (params) => _get('/menu/getMenuList',params);

/**
 * 当前登录用户信息
 * @param params 查询条件
 */
export const accountInfo = (params) => _get('',params);

/**
 * 获取 界面-界面皮肤 链接
 * @param params 查询条件
 */
export const getMobileuiSkin = (params) => _get('theme/getThemeList',params);

/**
 * 获取toolbar链接
 * @param params 查询条件
 */
export const getToolbar = (params) => _get('toolbar',params);

/**
 * 获取subnav链接
 * @param params 查询条件
 */
export const getSubnav = (params) => _get('subnav',params);

/**
 * 获取 界面-楼层配置 链接
 * @param params 查询条件
 */
export const getMobileuiFloor = (params) => _get('theme/getApplyThemeItem',params);

/**
 * 修改 界面-楼层配置 链接
 * @param params 查询条件
 */
export const putApplyThemeItem = (params) => _put('theme/putApplyThemeItem',params);

/**
 * 获取 商品-我的商品 链接
 * @param params 查询条件
 */
export const getGoods = (params) => _get('goods/get_goods_list_page',params);

/**
 * 获取 商品-我的商品 链接
 * @param params 查询条件
 */
export const getOrderLists = (params) => _get('orderLists',params);

/**
 * 修改账户信息
 * @param params 修改的值
 */
export const putAccountInfo = (params) =>_put('common/upAccountInfo/'+params.acId,params);

/**
 * 获取子账户列表
 * @param params 查询条件
 */
export const getSubAccountList = (params) =>_get('child/getSubAccountList',params);

/**
 * 获取对应子账号信息
 * @param params 查询条件
 */
export const getSubAccount = (params) =>_get('child/getSubAccount',params);

/**
 * 添加子账户
 * @param params 子账号信息
 */
export const createSubAccount = (params) =>_post('child/setIntegralChildAc',params);

/**
 * 添加子账户
 * @param params 子账号信息
 */
export const delSubAccount = (params) =>_delete('child/delIntegralChildAc',params);

/**
 * 获取账号应用
 */
export const getAcApplyList = () =>_get('ac_apply/get_ac_apply_list');

/**
 * 添加应用
 */
export const saveAcApply = (params) => _post('ac_apply/save_ac_apply',params);

/**
 * 切换应用
 */
export const switchApply = (applyId) => _post('ac/switch_apply/'+applyId);

/**
 * 修改应用
 * @param applyId 应用编号
 * @param params
 */
export const updateAcApply = (applyId,params) => _put('/ac_apply/update_ac_apply/'+applyId,params);

/**
 * 获取应用信息
 * @param applyId 应用编号
 * @param params
 */
export const getAcApply = (params) => _get('/ac_apply/get_ac_apply',params);


/**
 * 获取应用接口配置
 */
export const getInterfaceSetting = () => _get('/interface/get_interface_setting');

/**
 * 获取应用接口配置
 */
export const updateInterfaceSetting = (params) => _post('/interface/update_interface_setting',params);



// 界面管理

// ->>>>>>>>>>>>>>>>>> 页面内容配置 <<<<<<<<<<<<<<<<<<-
/**
 * 获取楼层对应的数据
 * @param params
 */
export const getApplyThemeItemData = (themeItemId) =>_get('theme/getApplyThemeItemData/'+themeItemId);

/**
 * 保存楼层对应的数据
 * @param params
 */
export const saveApplyThemeItemData = (params) =>_post('theme/saveApplyThemeItemData/'+params.themeItemId,params);

/**
 * 删除楼层对应的数据
 * @param params
 */
export const delApplyThemeItemData = (dataId) =>_delete('theme/delApplyThemeItemData/'+dataId);

/**
 * 图片上传
 * @param params
 */
export const uploadImage = (params) =>_upload('common/uploadImage',params);

// 商品管理

/**
 * 获取商品数据
 * @param params
 */
export const getGoodsList = (params) =>_get('goods/get_goods_list',params);

/**
 * 保存商品
 * @param params
 */
export const saveObjectGoods = (params) =>_post('goods/saveObjectGoods',params);

/**
 * 修改商品上架下架
 * @param params
 */
export const updateGoods = (params) =>_put('goods/update_goods',params);

/**
 * 获取单商品信息编辑
 * @param params
 */
export const getObjectGoods = (params) =>_get('goods/goods_info',params);

//订单管理

/**
 * 获取订单分页数据
 * @param params
 */
export const getOrderPage = (params) =>_get('orderManage/get_order_list_page',params);

/**
 * 修改订单物流信息
 * @param params
 */
export const updateOrderLogistics = (params) =>_post('orderManage/update_logistics',params);

/**
 * 取消订单
 * @param params
 */
export const cancelOrder = (params) =>_post('orderManage/cancel_order',params);

/**
 * 审核订单
 * @param params
 */
export const auditOrder = (params) =>_post('orderManage/audit_order',params);

/**
 * 获取数据中心数据
 */
export const getDatacenter = (params) => _get('datacenter',params);

/**
 * 内容数据
 */
export const getContentdata = (params) => _get('report/contentData',params);

/**
 * 流量数据
 * @param params
 */
export const getFlowdata = (params) => _get('report/flowData',params);

/**
 * 日常数据
 */
export const getDailydata = (params) => _get('report/dailyData',params);

/**
 * 用户数据-新增用户
 */
export const getAddUserData = (params) => _get('report/addUserData',params);

/**
 * 用户数据-访客占比
 */
export const getVisitorPerData = (params) => _get('report/visitorPerData',params);

/**
 * 用户数据-人均积分
 */
export const getAverageIntegralData = (params) => _get('report/averageIntegralData',params);

/**
 * 用户数据-积分分布
 */
export const getDisIntegralData = (params) => _get('report/disIntegralData',params);

/**
 * 收入账户明细
 */
export const getIncomeData = (params) => _get('report/get_income_list',params);

/**
 * 商城概况数据
 */
export const getBasicFacts = (params) => _get('report/basicFactsData',params);


export const setCategory = (params) => _post('category/set_category',params);

export const getCategoryList = (params) => _get('category/get_category_list',params);

export const delCategory = (category_id) => _delete('category/del_category'+category_id);

export const activityDataList = (params) => _get('report/activityDataList',params);

export const channelRatio = (params) => _get('report/channelRatio',params);
