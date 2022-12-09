
/**
 * @description 用户信息状态管理
 * @creatDate 2017-6-15
 * @author Geek.Yu  email:1098654043@qq.com
 */
import * as types from '../types'
import {getStorage,setStorage} from '../../api/utils'

const state = {
  // 用户登录状态
  appLoginStatus: getStorage("appLoginStatus") || false,
  // 用户信息
  appUserData: getStorage("appUserData") || {},
}

const actions = {
  /**
   * 用户登录
   */
  setAppUserInfo({ commit }, re) {
      setStorage('appLoginStatus', true);
      commit(types.SET_APP_LOGIN_STATUS, true);
  },
  /**
   * 请求用户信息
   */
  getAppUserData({ commit },re) {
      setStorage('appUserData', re);
      commit(types.GET_APP_USER_DATA,re);
  }
};

const getters = {
  appUserData: state => state.appUserData,
  appLoginStatus: state => state.appLoginStatus,
}

const mutations = {
  [types.SET_APP_LOGIN_STATUS](state, status) {
    state.appLoginStatus = status
  },

  [types.GET_APP_USER_DATA](state, res) {
    state.appUserData = res
  }
}

export default {
  state,
  actions,
  getters,
  mutations
}
