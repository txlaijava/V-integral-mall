import config from '../config/config';
import qs from 'qs';
import {getStorage} from '../api/utils';
import router from '../router/router';
import * as SYSTEM from '../api/system';

const http = require('axios').create({
    // 基础url前缀
    baseURL: config.appBaseURL,            //api请求的baseURL
    // 设置超时时间
    timeout: 0,
    withCredentials: true, // 允许跨域 cookie
    // 请求头信息
    headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
        'Token': ''
    },
    maxContentLength: 2000,
    // `transformResponse`允许在 then / catch之前对响应数据进行更改
    transformResponse: [function (data) {
        try {
            data = JSON.parse(data);
        } catch (e) {
            data = {};
        }
        return data;
    }]
});

// 请求时的拦截
http.interceptors.request.use(function (config) {
    // 发送请求之前做一些处理
    // 设置头部 Token
    if (getStorage('Token')) {
        config.headers['Token'] = getStorage('Token');
    }
    return config;
}, function (error) {
    // 当请求异常时做一些处理
    return Promise.reject(error);
});

// 响应时拦截
http.interceptors.response.use(response => {

    let error = false;
    let errorMsg = '';

    if (400 == response.data.error) {
        errorMsg = response.data.error_msg;
        error = true
    }
    if (SYSTEM.CODE_IS_ERROR == response.data.code) {
        errorMsg = response.data.msg;
        error = true
    }
    if (error) {
        console.log(errorMsg);
        router.push({name: '500', params: {message: errorMsg}});
        return Promise.reject(errorMsg);
    }

    return response;
}, error => {
    // 当响应异常时做一些处理
    router.push({name: '500', params: {message:error}});
    return Promise.reject(error);
});


// get
export const _get = (url, data) => {
    return http.get(url, {params: data})
}

// post
export const _post = (url, params) => {
    return http.post(url, qs.stringify(params))
}

// upload
export const _upload = (url, params) => {
    return http.post(url, params)
}

//patch
export const _put = (url, params) => {
    return http.put(url, qs.stringify(params));
}

//delete
export const _delete = (url, data) => {
    return http.delete(url, {params: data})
}

//post and no withCredentials
export const _postNoWithCredentials = (req) => {
    return axios({method: 'post', url: `/${req.url}`, data: req.data, withCredentials: false})
};
