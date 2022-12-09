<img src="https://qiniu.rblcmall.com/uPic/xkhcbQ.png"/>

# V-integral-mall
开源的积分商城 - 仅需1天即让你的系统可拥有一套会员积分商城
        
## 简介
无侵入式接入任何平台的用户积分体系，搭建一套移动端积分商城（微信小程序、APP）,前端使用微信小程序,后端服务使用SpringBoot,管理平台使用VUE构建。

## 接口调用流程图
<img src="https://qiniu.rblcmall.com/uPic/iNnAvF.png"/>

## 功能
* 积分商城微信小程序端
* 积分商城商城H5端
* 积分商城商品兑换
* 积分商城后台 - 积分商城装修
* 积分商城后台 - 商品上架
* 积分商城后台 - 报表
* 积分商城后台 - 配置

## 界面
### 小程序界面
<img src="https://qiniu.rblcmall.com/uPic/sRDCA2.jpg" width="30%" style="padding-left:2%">
<img src="https://qiniu.rblcmall.com/uPic/ND7JyI.jpg" width="30%" style="padding-left:2%">

### 管理后台界面
#### 登录界面
<img src="https://qiniu.rblcmall.com/uPic/v54mts.png"/>

#### 主界面
<img src="https://qiniu.rblcmall.com/uPic/QorGUZ.png" />

#### 装修配置
<img src="https://qiniu.rblcmall.com/uPic/DC4MvA.png" />

#### 商品上架页面
<img src="https://qiniu.rblcmall.com/uPic/iShot_2022-12-08_23.43.54.gif" />

#### 报表
<img src="https://qiniu.rblcmall.com/uPic/iShot_2022-12-08_23.46.17.gif" />

## 代码结构
* server-integral - 服务端代码，现在架构考虑到SaaS模式，有需要也可以直接连我的服务进行使用。
* 小程序端 - 满200 Start开源。
* vue-integral - H5以及后台管理端代码


## 开发对接

# 免登录URL生成

#### 输入参数\(Get请求方式传参\) {#输入参数get请求方式传参}

| 参数 | 是否必须 | 参数类型 | 限制长度 | 参数说明 |
| :--- | :--- | :--- | :--- | :--- |
| uid | yes | string | 255 | 用户唯一性标识，对应唯一一个用户且不可变 （用not\_login作为uid标记游客用户） |
| credits | yes | long | 20 | 用户积分余额 |
| appKey | yes | string | 255 | 接口appKey，应用的唯一标识 |
| timestamp | yes | String | 20 | 1970-01-01开始的时间戳，毫秒为单位。 |
| **redirect** | **no** | string | 255 | 登录成功后的重定向地址，可以直达积分商城内的任意页面,如果不带redirect参数，默认跳转到积分商城首页 |
| sign | yes | string | 255 | MD5签名 |

### 注意事项 {#注意事项}

1. 为了确保客户端每次请求到都是最新的免登录url，客户端每次向服务器发的请求不能是固定的，以避免请求被缓存。

2. 免登录url经过签名，**该url地址5分钟失效，请务必在生成地址后立即使用**，使用后页面会重定向进入积分商城，登录状态24小时有效。

3. 开发者服务器端需要开发一个支持重定向的接口实现动态生成免登录url地址，该接口地址配置在客户端，用户通过点击该地址访问积分商城。

### 代码示例 {#代码示例}

java

```java
CreditTool tool=new CreditTool(appKey, appSecret);
Map params=new HashMap();
params.put("uid","userId001");
params.put("credits","100");
if(redirect!=null){
 // redirect是目标页面地址，如果要跳转到积分商城指定页面，redirect地址就是目标页面地址
 // 此处请设置成一个外部传进来的参数，方便运营灵活配置
 params.put("redirect", redirect);
}
String url=tool.buildUrlWithSign("https://www.yummall.cn/autoLogin/autologin?",params);
//此url即为免登录url
```

php

```php
$url=buildRedirectAutoLoginRequest($appKey,$appSecret,$uid,$credits,$redirect)
```

.net

```java
string url = "https://www.yummall.cn/autoLogin/autologin";
string uid = "userId001";
int credits = 9999;

Hashtable hshTable = new Hashtable();
hshTable.Add("uid", uid);
hshTable.Add("credits", credits);
if(redirect!=null){
 //redirect为商城直达的目标页面地址，如果要跳转的目标页面不是积分商城首页，改地址必须配置
 hshTable.Add("redirect", redirect);
}
url = yummall.BuildUrlWithSign(url, hshTable, appKey, appSecret);
```

## 用户积分扣除接口 {#用户积分扣除接口}

#### 输入参数（Get请求方式传参）

|  | 是否必须 | 参数类型 | 限制长度 | 参数说明 |
| :--- | :--- | :--- | :--- | :--- |
| uid | yes | string | 255 | 用户唯一性标识，唯一且不可变 |
| credits | yes | long | 20 | 本次兑换扣除的积分 |
| itemCode | no | string | 255 | 自有商品商品编码\(非必须字段\) |
| appKey | yes | string | 255 | 接口appKey，应用的唯一标识 |
| timestamp | yes | string | 20 | 1970-01-01开始的时间戳，毫秒为单位。 |
| description | yes | string | 255 | 本次积分消耗的描述\(带中文，请用utf-8进行url解码\) |
| orderNum | yes | string | 255 | 兑吧订单号\(请记录到数据库中\) |
| type | yes | string | 255 | 兑换类型：alipay\(支付宝\), qb\(Q币\), coupon\(优惠券\), object\(实物\), phonebill\(话费\), phoneflow\(流量\), virtual\(虚拟商品\),game\(游戏\), hdtool\(活动抽奖\),sign\(签到\)所有类型不区分大小写 |
| facePrice | no | int | 11 | 兑换商品的市场价值，单位是分，请自行转换单位 |
| actualPrice | yes | int | 11 | 此次兑换实际扣除开发者账户费用，单位为分 |
| ip | no | string | 255 | 用户ip，不保证获取到 |
| waitAudit | no | boolean |  | 是否需要审核\(如需在自身系统进行审核处理，请记录下此信息\) |
| params | no | string | 255 | 详情参数，不同的类型，返回不同的内容，中间用英文冒号分隔。\(支付宝类型带中文，请用utf-8进行解码\) 实物商品：返回收货信息\(姓名:手机号:省份:城市:区域:详细地址\)、支付宝：返回账号信息\(支付宝账号:实名\)、话费：返回手机号、QB：返回QQ号 |
| sign | yes | string | 255 | MD5签名，详见签名规则 |

#### 响应参数 {#响应参数}

| 参数 | 是否必须 | 参数类型 | 限制长度 | 参数说明 |
| :--- | :--- | :--- | :--- | :--- |
| status | yes | string | 255 | 扣积分结果状态，回复ok或者fail （不要使用0和1） |
| errorMessage | no | string | 255 | 出错原因 |
| bizId | yes | string | 255 | 开发者的订单号\(唯一且不重复，如果失败情况，该值可以不传\) |
| credits | yes | long | 20 | 用户积分余额 |

请按JSON格式返回结果。

#### 响应示例 {#响应示例}

```
成功：
{
    "status":"ok",
    "errorMessage":" ",
    'bizId':"20140730192133033",
    "credits":"100"
}
```

```
失败：
{
    "status":"fail",
    "errorMessage":"失败原因（显示给用户）",
    "credits":"0"
}
```

#### 代码示例 {#代码示例}

java

**点击下载（java开发包）**

```java
@RequestMapping("/hook")
@ResponseBody
 public String hook(HttpServletRequest request) {
     CreditTool tool = new CreditTool(appKey, appSecret);
     boolean success = false;
     String errorMessage = "";
     String bizId =null;
     Long credits=0L;
 try {
     CreditConsumeParams params = tool.parseCreditConsume(request);

     bizId = todo(); //开发者业务订单号，保证唯一不重复
     credits = getCredits(); // getCredits()是根据开发者自身业务，获取的用户最新剩余积分数。
     success = true;
 } catch (Exception e) {
     success = false;
     errorMessage = e.getMessage();
     e.printStackTrace();
 }
     CreditConsumeResult ccr = new CreditConsumeResult(success);
     ccr.setBizId(bizId);
     ccr.setErrorMessage(errorMessage);
     ccr.setCredits(credits);
     return ccr.toString();//返回扣积分结果json信息
 }
```

php

```php
/*
*  积分消耗请求的解析方法
*  当用户进行兑换时，兑吧会发起积分扣除请求，开发者收到请求后，可以通过此方法进行签名验证与解析，然后返回相应的格式
*/
function parseCreditConsume($appKey,$appSecret,$request_array){
    if($request_array["appKey"] != $appKey){
        throw new Exception("appKey not match");
    }
    if($request_array["timestamp"] == null ){
        throw new Exception("timestamp can't be null");
    }
    $verify=signVerify($appSecret,$request_array);
    if(!$verify){
        throw new Exception("sign verify fail");
    }
    $ret=array("appKey"=>$request_array["appKey"],"credits"=>$request_array["credits"],"timestamp"=>$request_array["timestamp"],
        "description"=>$request_array["description"],"orderNum"=>$request_array["orderNum"]);
    return $ret;
}
```

.net

```java
public Hashtable parseCreditConsume(string appKey,string appSecret,HttpRequest request)
{
    if(!request.Params["appKey"].Equals(appKey)){
        throw new Exception("appKey not match");
    }
    if(request.Params["timestamp"] == null ){
        throw new Exception("timestamp can't be null");
    }
    Hashtable hshTable = yummall.GetUrlParams(request);

    bool verify=yummall.SignVerify(appSecret,hshTable);
    if(!verify){
        throw new Exception("sign verify fail");
    }
    return hshTable;
}
```

## 订单兑换成功/失败消息的接收接口 {#订单兑换成功失败消息的接收接口}

####  请求参数（Get请求方式传参）

| 参数 | 是否必须 | 参数类型 | 限制长度 | 参数说明 |
| :--- | :--- | :--- | :--- | :--- |
| appKey | yes | string | 255 | 接口appKey，应用的唯一标识码 |
| timestamp | yes | string | 20 | 1970-01-01开始的时间戳，毫秒。 |
| uid | yes | string | 255 | 用户唯一标识，唯一且不可变 |
| success | yes | boolean |  | 兑换是否成功，状态是true和false |
| errorMessage | no | string | 255 | 出错原因\(带中文，请用utf-8进行解码\) |
| orderNum | yes | string | 255 | 兑吧订单号 |
| bizId | no | string | 255 | 开发者的订单号 |
| sign | yes | string | 255 | 签名 |

#### 响应参数： {#响应参数：}

开发者服务器端收到通知并处理完成后，请返回纯文本的`ok`字符串，两边不带空格，忽略大小写，云猫验证到响应为 ok 后会标记通知成功。

如果响应为非 ok 字符串，兑吧会在**24小时内**最多重试**8**次通知。  
通知时间间隔为：2m、10m、10m、1h、2h、6h、15h。

注：出于网络异常的可能性，云猫平台可能会对开发者进行重复通知，开发者务必确保一笔订单不进行重复处理，**否则将产生严重bug**，详细原因参考下述：`重复通知处理`

#### 重复通知处理 {#重复通知处理}

> 由于网络具有不稳定的特性，当云猫平台向开发者服务器发送成功/失败通知时，有可能存在云猫平台发送了通知，开发者收到了通知并进行了处理。
>
> 若此时出现网络故障，开发者响应云猫平台失败，云猫平台没有收到开发者的响应，云猫平台会认为开发者没有收到通知，于是进行重复通知。
>
> 此时开发者收到通知后，务必先确认此订单是否已经处理过。如果已经处理过，则忽略此通知，并响应ok。 如果此时开发者忽略订单是否已经被处理过，而直接进行处理，将导致开发者反复向用户返还积分，导**致损失**！

#### 请勿限制通知等待时长 {#请勿限制通知等待时长}

> 有部分订单：如实物类订单，需等待发货之后才会确认订单成功，此类订单可能会隔比较长时间才发订单结果通知。如果限制了通知等待时长会导致部分订单接收不到结果。

#### 代码示例 {#代码示例}

java

```java

CreditTool tool=new CreditTool(appKey, appSecret);

try {
    CreditNotifyParams params= tool.parseCreditNotify(request);//利用tool来解析这个请求
    String orderNum=params.getOrderNum();
    if(params.isSuccess()){
        //兑换成功
    }else{
        //兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
    }
} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```

php

```php
function parseCreditNotify($appKey,$appSecret,$request_array){
    if($request_array["appKey"] != $appKey){
        throw new Exception("appKey not match");
    }
    if($request_array["timestamp"] == null ){
        throw new Exception("timestamp can't be null");
    }
    $verify=signVerify($appSecret,$request_array);
    if(!$verify){
        throw new Exception("sign verify fail");
    }
    $ret=array("success"=>$request_array["success"],"errorMessage"=>$request_array["errorMessage"],
        "uid"=>$request_array["uid"],"bizId"=>$request_array["bizId"]);
    return $ret;
}
```

.net

```java
function parseCreditNotify(string appKey,string appSecret,HttpRequest request)
{
    if(!request.Params["appKey"].Equals(appKey)){
        throw new Exception("appKey not match");
    }
    if(request.Params["timestamp"] == null ){
        throw new Exception("timestamp can't be null");
    }
    Hashtable hshTable = yummall.GetUrlParams(request);

    bool verify=yummall.SignVerify(appSecret,hshTable);
    if(!verify){
        throw new Exception("sign verify fail");
    }
    return hshTable;
}
```

  


 
## 交流

使用沟通交流可以加钉钉:
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/WechatIMG801.jpeg" width="200"/>