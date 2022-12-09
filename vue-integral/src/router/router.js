import App from '../App'
import Vue from 'vue';
import VueRouter from 'vue-router';

// 登录
// const Login = r => require.ensure([], () => r(require('@/views/login/login')), 'Login');
const Login = r => require.ensure([], () => r(require('@/views/login/login_2')), 'Login');
// 子账号登录
const ChildAcLogin = r => require.ensure([], () => r(require('@/views/login/child_ac_login_2')), 'ChildAcLogin');
// 找回密码
// const forgotPwd = r => require.ensure([], () => r(require('@/views/login/forgot_pwd')), 'forgotPwd');
const forgotPwd = r => require.ensure([], () => r(require('@/views/login/forgotpwd_2')), 'forgotPwd');
// 注册
// const Signup = r => require.ensure([], () => r(require('@/views/login/signup')), 'Signup');
const Signup = r => require.ensure([], () => r(require('@/views/login/signup_2')), 'Signup');

//商城概况
const BasicFacts = r => require.ensure([], () => r(require('@/views/basicFacts/basicFacts'), 'BasicFacts'));
//界面
const Index = r => require.ensure([], () => r(require('@/views/index'), 'Index'));
//界面皮肤配置
const Skin = r => require.ensure([], () => r(require('@/views/mobileui/skin'), 'Skin'));
//首页楼层配置
const Floor = r => require.ensure([], () => r(require('@/views/mobileui/floor'), 'Floor'));
//页面内容配置
const Content = r => require.ensure([], () => r(require('@/views/mobileui/content/content'), 'Content'));
//页面内容配置==手机首页
const HomePage = r => require.ensure([], () => r(require('@/views/mobileui/content/homePage/homePage'), 'HomePage'));
//页面内容配置==自定义分类
const Category = r => require.ensure([], () => r(require('@/views/mobileui/content/category/category'), 'Category'));
//我的商品
const Mygoods = r => require.ensure([], () => r(require('@/views/goods/mygoods'), 'Mygoods'));
//我的商品==添加优惠券
const Coupon = r => require.ensure([], () => r(require('@/views/goods/addgoods/coupon'), 'Coupon'));
//我的商品==编辑商品
const Object = r => require.ensure([], () => r(require('@/views/goods/addgoods/object'), 'Object'));
//我的商品==添加实物商品
const newObject = r => require.ensure([], () => r(require('@/views/goods/addgoods/newObject'), 'newObject'));
//我的商品==添加虚拟商品
const newVirtual = r => require.ensure([], () => r(require('@/views/goods/addgoods/newVirtual'), 'newVirtual'));
//我的商品==添加虚拟商品
const Virtual = r => require.ensure([], () => r(require('@/views/goods/addgoods/virtual'), 'Virtual'));

//订单查询
const Ordersearch = r => require.ensure([], () => r(require('@/views/orders/ordersearch'), 'Ordersearch'));
//待发货订单
const Orderwaitship = r => require.ensure([], () => r(require('@/views/orders/orderwaitship'), 'Orderwaitship'));
//异常订单
const Orderabnormal = r => require.ensure([], () => r(require('@/views/orders/orderabnormal'), 'Orderabnormal'));
//售后订单
const Orderaftersale = r => require.ensure([], () => r(require('@/views/orders/orderaftersale'), 'Orderaftersale'));
//待审核订单
const Orderwaitaudit = r => require.ensure([], () => r(require('@/views/orders/orderwaitaudit'), 'Orderwaitaudit'));

//数据中心
const Datacenter = r => require.ensure([], () => r(require('@/views/datacenter/datacenter'), 'Datacenter'));
//数据中心==流量数据
const Flowdata = r => require.ensure([], () => r(require('@/views/datacenter/flowdata/flowdata'), 'Flowdata'));
//数据中心==用户数据
const Userdata = r => require.ensure([], () => r(require('@/views/datacenter/userdata/userdata'), 'Userdata'));
//数据中心==活跃数据
const Activitydata = r => require.ensure([], () => r(require('@/views/datacenter/activitydata/activitydata'), 'Activitydata'));
//数据中心==内容数据
const Contentdata = r => require.ensure([], () => r(require('@/views/datacenter/contentdata/contentdata'), 'Contentdata'));
//数据中心==日常数据
const Dailydata = r => require.ensure([], () => r(require('@/views/datacenter/dailydata/dailydata'), 'Dailydata'));
//数据中心==收入账户明细
const Incomedata = r => require.ensure([], () => r(require('@/views/datacenter/incomedata/incomedata'), 'Incomedata'));

//配置==应用信息
const Appinfo = r => require.ensure([], () => r(require('@/views/setting/appinfo'), 'Appinfo'));
//配置==接口配置
const Interface = r => require.ensure([], () => r(require('@/views/setting/interface/interface'), 'Interface'));
//配置==接口配置==接口配置
const Setting = r => require.ensure([], () => r(require('@/views/setting/interface/setting/setting'), 'Setting'));
//配置==接口配置==异常监控
const Exception = r => require.ensure([], () => r(require('@/views/setting/interface/exception/exception'), 'Exception'));
//配置==预算控制
const Budget = r => require.ensure([], () => r(require('@/views/setting/budget'), 'Budget'));
//配置==更多
const More = r => require.ensure([], () => r(require('@/views/setting/more/more'), 'More'));
//配置==更多==会员等级
const Level = r => require.ensure([], () => r(require('@/views/setting/more/level/level'), 'Level'));
//配置==更多==分享配置
const Shareconfig = r => require.ensure([], () => r(require('@/views/setting/more/shareconfig/shareconfig'), 'Shareconfig'));
//配置==更多==运费模版
const Expresstemplate = r => require.ensure([], () => r(require('@/views/setting/more/expresstemplate/expresstemplate'), 'Expresstemplate'));
//配置==更多==公告设置
const Appbulletin = r => require.ensure([], () => r(require('@/views/setting/more/appbulletin/appbulletin'), 'Appbulletin'));
//404
const PCFileNotFound = r => require.ensure([], () => r(require('@/views/fileNotFound/fileNotFound'), 'PCFileNotFound'));


const acInfo = r => require.ensure([], () => r(require('@/views/setting/account/ac_set_info'), 'acInfo'));
const acSubacount = r => require.ensure([], () => r(require('@/views/setting/account/ac_subacount'), 'acSubacount'));
const createSubacount = r => require.ensure([], () => r(require('@/views/setting/account/ac_create_subacount'), 'createSubacount'));
const editorSubAccount = r => require.ensure([], () => r(require('@/views/setting/account/ac_editor_subaccount'), 'editorSubAccount'));
const upPassword = r => require.ensure([], () => r(require('@/views/setting/account/ac_up_password'), 'upPassword'));

/*app*/
const appIndex = r => require.ensure([], () => r(require('@/views/app/index'), 'appIndex'));
//首页
const Home = r => require.ensure([], () => r(require('@/views/app/home/home'), 'Home'));
//兑换记录
const Record = r => require.ensure([], () => r(require('@/views/app/record/record'), 'Record'));
//分类
const Classify = r => require.ensure([], () => r(require('@/views/app/classify/classify'), 'Classify'));
//商品详情
const ItemDetail = r => require.ensure([], () => r(require('@/views/app/itemDetail/itemDetail'), 'ItemDetail'));
//订单详情
const RecordDetail = r => require.ensure([], () => r(require('@/views/app/recordDetail/recordDetail'), 'RecordDetail'));
//订单详情_新
const RecordDetailNew = r => require.ensure([], () => r(require('@/views/app/recordDetailNew/recordDetailNew'), 'recordDetailNew'));
//核销详情
const VerifyDetail = r => require.ensure([], () => r(require('@/views/app/verifyDetail/verifyDetail'), 'VerifyDetail'));
//地址
const AddAddress = r => require.ensure([], () => r(require('@/views/app/addAddress/addAddress'), 'AddAddress'));
//404
const FileNotFound = r => require.ensure([], () => r(require('@/views/app/fileNotFound/fileNotFound'), 'FileNotFound'));
//页面错误
const Error = r => require.ensure([], () => r(require('@/views/app/error/error'), 'Error'));
//支付页面
const AmbPay = r => require.ensure([], () => r(require('@/views/app/ambPay/ambPay'), 'AmbPay'));
//操作成功
const SuccessfulOperation = r => require.ensure([], () => r(require('@/views/app/successfulOperation/successfulOperation'), 'SuccessfulOperation'));

const addApply = r => require.ensure([], () => r(require('@/views/apply/add_apply'), 'addApply'));

/*网站*/

const WebsiteIndex = r => require.ensure([], () => r(require('@/views/website/index'), 'WebsiteIndex'));
// 首页
const WebsiteHome = r => require.ensure([], () => r(require('@/views/website/home/home'), 'WebsiteHome'));

// 定义路由
const routes = [
    {
        path: '/',
        component: App, // 顶层路由，对应index.html
        children: [{
            path: '/',
            redirect: '/index/home'
        }, {
            // 网站首页
            path: 'index',
            name: 'WebsiteIndex',
            component: WebsiteIndex,
            children: [{
                path: 'home',
                name: '首页',
                component: WebsiteHome
            }]
        }]
    },
    {
        path: '/admin',
        component: App, // 顶层路由，对应index.html
        children: [ // 二级路由，对应App.vue
            {
                // 页面为空时跳转至首页
                path: '/admin',
                redirect: '/admin/login' // 页面重定向
            },
            {
                // 登录
                path: 'login',
                name: 'login',
                component: Login,
                meta: {
                    title: '积分商城-登录', // 网页信息title
                    keepAlive: false
                }
            },
            {
                // 之账号登录
                path: 'subacountsigin',
                name: 'subacountsigin',
                component: ChildAcLogin,
                meta: {
                    title: '积分商城-登录', // 网页信息title
                    keepAlive: false
                }
            },
            {
                // 找回密码
                path: 'forgotpwd',
                name: 'forgotpwd',
                component: forgotPwd,
                meta: {
                    title: '积分商城-登录', // 网页信息title
                    keepAlive: false
                }
            },
            {
                //注册
                path: 'signup',
                name: 'signup',
                component: Signup,
                meta: {
                    title: '积分商城-注册', // 网页信息title
                    keepAlive: false
                }
            },
            {
                path: 'index',
                name: 'index',
                component: Index,
                children: [
                    {
                        path: 'basicFacts',
                        name: '商城概况',
                        component: BasicFacts
                    },
                    {
                        path: 'skin',
                        name: '界面皮肤配置',
                        component: Skin,
                        meta: {
                            title: '积分商城-界面皮肤配置', // 网页信息title
                            keepAlive: false
                        }
                    },
                    {
                        path: 'floor',
                        name: '首页楼层配置',
                        component: Floor,
                        meta: {
                            title: '积分商城-首页楼层配置', // 网页信息title
                            keepAlive: false
                        }
                    },
                    {

                        path: 'content',
                        name: '页面内容配置',
                        component: Content,
                        meta: {
                            title: '积分商城-页面内容配置', // 网页信息title
                            keepAlive: false
                        },
                        children: [{
                            path: 'homePage',
                            name: '手机首页',
                            component: HomePage,
                        }, {
                            path: 'category',
                            name: '自定义分类',
                            component: Category,
                        }]
                    },
                    {
                        path: 'mygoods',
                        name: 'mygoods',
                        component: Mygoods,
                        meta: {
                            title: '积分商城-商品管理', // 网页信息title
                            keepAlive: false
                        }
                    },
                    {
                        path: 'coupon',
                        name: 'coupon',
                        component: Coupon,
                    },
                    {
                        path: 'object',
                        name: 'object',
                        component: Object,
                    },
                    {
                        path: 'newObject',
                        name: 'newObject',
                        component: newObject,
                    },
                    {
                        path: 'newVirtual',
                        name: 'newVirtual',
                        component: newVirtual,
                    },
                    {
                        path: 'virtual',
                        name: 'virtual',
                        component: Virtual,
                    },
                    {
                        path: 'ordersearch',
                        name: 'ordersearch',
                        component: Ordersearch
                    },
                    {
                        path: 'orderwaitship',
                        name: 'orderwaitship',
                        component: Orderwaitship
                    },
                    {
                        path: 'orderabnormal',
                        name: '异常订单',
                        component: Orderabnormal
                    },
                    {
                        path: 'orderaftersale',
                        name: '售后订单',
                        component: Orderaftersale
                    },
                    {
                        path: 'orderwaitaudit',
                        name: 'orderwaitaudit',
                        component: Orderwaitaudit
                    }, {
                        path: 'datacenter',
                        component: Datacenter,
                        children: [{
                            path: 'flowdata',
                            name: '流量数据',
                            component: Flowdata
                        }, {
                            path: 'userdata',
                            name: '用户数据',
                            component: Userdata
                        }, {
                            path: 'activitydata',
                            name: '活跃数据',
                            component: Activitydata
                        }, {
                            path: 'contentdata',
                            name: '内容数据',
                            component: Contentdata
                        }, {
                            path: 'dailydata',
                            name: '日常数据',
                            component: Dailydata
                        },
                            {
                                path: 'incomedata',
                                name: 'incomedata',
                                component: Incomedata
                            }]
                    },
                    {
                        path: 'appinfo',
                        name: 'appinfo',
                        component: Appinfo
                    },
                    {
                        path: 'interface',
                        component: Interface,
                        children: [{
                            path: 'setting',
                            name: '接口配置',
                            component: Setting
                        }, {
                            path: 'exception',
                            name: '异常监控',
                            component: Exception
                        }]
                    },
                    {
                        path: 'budget',
                        name: '预算控制',
                        component: Budget
                    },
                    {
                        path: 'more',
                        component: More,
                        children: [{
                            path: 'level',
                            name: '会员等级',
                            component: Level
                        }, {
                            path: 'shareconfig',
                            name: '分享配置',
                            component: Shareconfig
                        }, {
                            path: 'expresstemplate',
                            name: '运费模版',
                            component: Expresstemplate
                        }, {
                            path: 'appbulletin',
                            name: '公告设置',
                            component: Appbulletin
                        }]
                    },
                    {
                        path: 'acinfo',
                        name: 'acinfo',
                        component: acInfo
                    },
                    {
                        path: 'acSubacount',
                        name: 'acSubacount',
                        component: acSubacount
                    },
                    {
                        path: 'createSubacount',
                        name: 'createSubacount',
                        component: createSubacount
                    },
                    {
                        path: 'editorSubAccount/:subId',
                        name: 'editor-sub-account',
                        component: editorSubAccount
                    },
                    {
                        path: 'upPassword',
                        name: 'upPassword',
                        component: upPassword
                    },
                    {
                        path: 'addapply',
                        name: 'addapply',
                        component: addApply,
                    },
                ]
            }, {
                path: 'fileNotFound',
                name: 'PC404',
                component: PCFileNotFound,
            }, {
                path: '*',
                redirect: {name: 'PC404'},
            }
        ]
    },
    {
        path: '/app',
        component: App, // 顶层路由，对应index.html
        children: [ // 二级路由，对应App.vue
            {
                // 页面为空时跳转至首页
                path: '/app',
                redirect: '/app/index' // 页面重定向
            },
            {
                // 登录
                path: 'index',
                name: 'appIndex',
                component: appIndex,
                meta: {
                    title: '积分商城-App主页', // 网页信息title
                },
                children: [{
                    path: 'home',
                    name: '首页',
                    component: Home,
                    meta: {
                        keepAlive: true
                    },
                }, {
                    path: 'record',
                    name: '兑换记录',
                    component: Record,
                }, {
                    path: 'classify',
                    name: 'classify',
                    component: Classify,
                }, {
                    path: 'itemDetail/:goodsId',
                    name: '商品详情',
                    component: ItemDetail
                }, {
                    path: 'addAddress',
                    name: 'addAddress',
                    component: AddAddress
                }, {
                    path: 'recordDetail/:order_id',
                    name: 'recordDetail',
                    component: RecordDetail
                }, {
                    path: 'recordDetailNew',
                    name: 'recordDetailNew',
                    component: RecordDetailNew
                }, {
                    path: 'verifyDetail',
                    name: 'verifyDetail',
                    component: VerifyDetail
                }, {
                    path: 'fileNotFound',
                    name: '404',
                    component: FileNotFound,
                }, {
                    path: 'error',
                    name: '500',
                    component: Error
                }, {
                    path: 'ambPay/:order_id',
                    name: 'ambPay',
                    component: AmbPay
                }, {
                    path: 'successfulOperation',
                    name: '操作成功',
                    component: SuccessfulOperation
                }, {
                    path: '*',
                    redirect: {name: '404'},
                }]
            },
        ],
    },
];

Vue.use(VueRouter);
//路由对象
const router = new VueRouter({
    routes,
    mode: 'hash',   // 路由前面需要添加 /#/ 才能访问
});
export default router;


