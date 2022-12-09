import {postCreatePay,postCancelOrder} from '../service/getAppData';
import router from '../router/router';

var sUserAgent = navigator.userAgent.toLowerCase();
var isIphoneOs = sUserAgent.match(/iphone os/i);
var isPad = sUserAgent.match(/iPad/i);
var isAndroid = sUserAgent.match(/android/i);
var isApp = sUserAgent.match(/rblc/i);

/**
 * app接口
 */
function appPay(orderid, subject, channel) {
    postCreatePay({orderId:orderid,channel:channel,subject:subject}).then(res =>{
        if(res.data.data.code != 0){
            api.alert({msg: '支付请求失败,请重试'});
            return
        }
        var pingpp = api.require('pingpp');
        var params = {
            charge: res.data.data.data,
            scheme: "rblcmall"
        };
        pingpp.createPayment(params, function (ret, err) {
            if(ret.result =='cancel'){
                postCancelOrder(orderid).then(res =>{
                    if(res.data.data.code == 0){
                        api.alert({msg: '取消成功！'});
                        router.push({name: 'recordDetail', params: {order_id: orderid}});
                    }
                });
            }else{
                router.push({name: 'recordDetail', params: {order_id: orderid}});
            }
        });
    });
};

export {appPay,isApp,isAndroid,isPad,isIphoneOs,sUserAgent};
