
/**
* @description 用户信息状态管理
* @creatDate 2017-6-15
* @author Geek.Yu  email:1098654043@qq.com
*/
import {accountInfo} from '../../service/getData'
import * as types from '../types'
import * as SYSTEM from '../../api/system'
import {getStorage,setStorage,removeStorage} from '../../api/utils'

const state = {
    // 用户登录状态
    loginStatus: getStorage("loginStatus") || false,
    // 用户登录信息
    loginInfo: getStorage("loginInfo") || {},
    // 用户信息
    userData: {},
}

const actions = {
    /**
     * 用户登录
     */
    setUserInfo({ commit }, res) {
        setStorage('loginInfo', JSON.stringify(res));
        setStorage('loginStatus', true);
        commit(types.SET_LOGIN_INFO, res);
        commit(types.SET_LOGIN_STATUS, true);
    },

    /**
     * 登录注销
     */
    setSignOut({ commit }) {
        removeStorage('loginStatus');
        removeStorage('loginInfo');
        removeStorage('userData');
        commit(types.SET_LOGIN_STATUS, false);
        commit(types.SET_LOGIN_INFO, {});
        commit(types.GET_USER_DATA, {});
    },
    /**
     * 用户信息更新
     */
    upUserInfo({ commit }, res) {
        setStorage('loginInfo', JSON.stringify(res));
        commit(types.SET_LOGIN_INFO, res);
    },

    /**
     * 请求用户信息
     */
    getUserData({ commit }, params) {
        accountInfo().then(res => {
            if(SYSTEM.SUCCSSS != res.success && SYSTEM.CODE_IS_OK != res.code){
                setStorage('userData', res.data)
                commit(types.GET_USER_DATA, res.data)
            }else{
                if(params.callBack){
                  params.callBack()
                }
            }
        })
    }
}

const getters = {
    userData: state => state.userData,
    loginInfo: state => state.loginInfo,
    loginStatus: state => state.loginStatus,
}

const mutations = {
    [types.SET_LOGIN_INFO](state, res) {
        state.loginInfo = res
    },

    [types.SET_LOGIN_STATUS](state, status) {
        state.loginStatus = status
    },

    [types.GET_USER_DATA](state, res) {
        state.userData = res
    }
}

export default {
    state,
    actions,
    getters,
    mutations
}
