/**
* @name store.js
* @description 封装的vuex状态管理组件
* @creatDate 2018-5-9
*/
import Vue from 'vue'
import Vuex from 'vuex'

// 用户信息
import user from './modules/user'
import appUser from './modules/appUser'


Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        user,
        appUser
    }
})
