// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import router from './router/router'
import store from './store/store'
import {removeSessionStorage,setStorage} from './api/utils';


// 引入全局组件
import topTool from '@/components/topTool';
import sidebar from '@/components/sidebar';
import hidebar from '@/components/hidebar';
import sideToolbar from '@/components/sideToolbar';
import loading from '@/components/loading';
import noresult from '@/components/noresult';
import selection from '@/components/base/selection';
import datepicker from '@/components/base/datepicker';
import dialog from '@/components/base/dialog';
import switchs from '@/components/base/switch';
import tip from '@/components/base/tip';
import iView from 'iview';
import { Lazyload,Actionsheet,Dialog,Swipe, SwipeItem ,Collapse, CollapseItem, Tab, Tabs } from 'vant';

Vue.config.debug = true;//开启错误提示

// 注册全局组件
Vue.component('v-topTool', topTool);          //顶部工具组件
Vue.component('v-sidebar', sidebar);          //左侧工具组件
Vue.component('v-hidebar', hidebar);          //隐藏工具组件
Vue.component('v-sideToolbar', sideToolbar);  //右侧工具组件
Vue.component('v-loading', loading);          //loading组件
Vue.component('v-noresult', noresult);        //没有结果组件
Vue.component('v-selection', selection);      //下拉选择组件
Vue.component('v-date-picker', datepicker);   //日历组件
Vue.component('v-dialog', dialog);            //遮罩层组件
Vue.component('v-switch', switchs);           //开关按钮组件
Vue.component('v-tip', tip);                  //提示组件


// 引入公共css
import '@/style/iconfont.scss'
import '@/style/common.scss'
import 'iview/dist/styles/iview.css';
import 'vant/lib/vant-css/index.css';


Vue.use(iView)
    .use(Lazyload)
    .use(Actionsheet)
    .use(Dialog)
    .use(Swipe)
    .use(SwipeItem)
    .use(Collapse)
    .use(CollapseItem)
    .use(Tab)
    .use(Tabs);


// 设置页面文档信息
function setDocumentTitle(option) {
  var defaults,                 // 默认配置
    setting;                  // 实际的配置
  // 默认配置
  defaults = {
    title: '云猫积分商城运营平台',
    meta: [
      {hid: 'description', name: 'description', content: '云猫帮您快速搭建移动APP + 微信公众号 + 小程序三平台积分商城'}
    ]
  };
  // 参数继承
  setting = Object.assign(defaults, option);

  document.title = setting.title
}

// 将文档标题赋给 Vue原型方法
Vue.prototype.docTitle = setDocumentTitle;

// 路由导航钩子beforeEach，在路由进入前调用
router.beforeEach((to, from, next) => {
  // 设置每个页面的文档信息
  if (to.meta.title != void 0) {
    var options = {
      title: to.meta.title,
    }
  }


  if(to.path.indexOf('/app/index/home') >= 0 && to.query.token){
      setStorage('Token',to.query.token)
  }

  setDocumentTitle(options);
  // 验证当前路由是否需要登录
  if (to.meta.requireAuth) {
    if (store.state.user.loginStatus) {  // 通过vuex state 获取当前的token是否存在
      next();   //通过
    } else {
      next({path: from ? from.path : '/login'});
      return;
    }
  }
  next();   //通过
});


Vue.config.productionTip = false;

var app = new Vue({
  router,
  store,
}).$mount('#app');
