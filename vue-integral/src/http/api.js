import config from './config';
import qs from 'qs';
import {getStorage, setStorage} from '../api/utils';
import router from '../router/router';

const http = require('axios').create(config);

// 发送请求之前做一些处理
http.interceptors.request.use(function (config) {
    // 设置头部 Token
    if (getStorage('Token')) {
        config.headers['Token'] = getStorage('Token');
        if (getStorage('loginInfo')) {
            const uid = JSON.parse(getStorage('loginInfo')).id;
            config.headers['Uid'] = uid;
        }
    }
    return config;
}, function (error) {
    // 当请求异常时做一些处理
    return Promise.reject(error);
});

// 响应时拦截
http.interceptors.response.use(response => {
    if (response.data.data && response.data && response.data.meta && !response.data.data.success && response.data.meta.code == 1005 && response.data.data.jwt) {
        setStorage('Token', response.data.data.jwt);
        const re_config = response.config;
        let backoff = new Promise(function (resolve) {
            resolve();
        });
        // Return the promise in which recalls axios to retry the request
        return backoff.then(function () {
            re_config.url = re_config.url.replace('/proxy', '');
            return anewRequest(re_config);
        });
    }

    if (400 == response.data.error || 420 == response.data.error || 410 == response.data.error) {
        const error = response.data.error_msg;
        router.push('/admin/login');
        return Promise.reject(error);
    }
    return response;
}, error => {
    // 当响应异常时做一些处理
    //router.push('/app/index/error');
    return Promise.reject(error);
});


// get
export const _get = (url, data) => {
    return http.get(url, {params: data})
};

// post
export const _post = (url, params) => {
    return http.post(url, qs.stringify(params))
};

// upload
export const _upload = (url, params) => {
    return http.post(url, params)
};

//patch
export const _put = (url, params) => {
    return http.put(url, qs.stringify(params));
};

//delete
export const _delete = (url, data) => {
    return http.delete(url, {params: data})
};

//post and no withCredentials
export const anewRequest = (req) => {
    return http(req)
};
