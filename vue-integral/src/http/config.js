import config from '../config/config';
export default {
  // 基础url前缀
  baseURL: config.baseURL,            //api请求的baseURL
  // 设置超时时间
  timeout: 0,
  withCredentials: true, // 允许跨域 cookie
  // 请求头信息
  headers: {'X-Requested-With': 'XMLHttpRequest','Content-Type':'application/x-www-form-urlencoded;charset=UTF-8','Token' : ''},
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
}




