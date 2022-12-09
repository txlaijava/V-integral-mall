<template>
  <div class="main">
    <div class="page-title">
      <h2 class="has-sub-title"> 添加商品
        <i class="iconfont icon-arrowRight"></i>
        <span class="sub-title" v-if="this.$route.params.type==1">添加实物</span>
        <span class="sub-title" v-else-if="this.$route.params.type==2">添加虚拟商品</span>
      </h2>
    </div>
    <div class="page-content edit-goods-form">
      <div class="form-groups-chunk">
        <h4>展示信息：</h4>
        <div class="form-group">
          <label class="control-label label-required">商品标题：</label>
          <input type="text" class="form-control form-control-dirty untouched pristine" v-model="goods.goodsName" id="name"
                 placeholder="积分商城的商品展示名称，建议8个字以内">
          <p class="invalid" ></p>
          <p class="form-group-tip">前台展示给用户的主标题</p>
        </div>
        <div class="form-group">
          <label class="control-label">商品分类：</label>
          <Select v-model="goods.categoryId" clearable style="width:200px">
            <Option v-for="item in categoryList" :value="item.id" :key="item.id">{{ item.categoryName }}</Option>
          </Select>
        </div>
        <div class="form-group">
          <label class="control-label">商品编码：</label>
          <input type="text" class="form-control" v-model="goods.goodsCode" placeholder="选填。填写后，将通过用户积分扣除传给开发者，请求给开发者记录">
        </div>
        <div class="form-group">
          <label class="control-label">详情说明：</label>
          <div class="richeditor">
            <editor id='tinymce1' v-model='goods.goodsDetails' :init='init' ></editor>
            <p class="form-group-tip">此奖品的介绍文案以及说明文案</p>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label">待发货文案：</label>
          <div class="richeditor">
            <editor id='tinymce2' v-model='goods.stayDeliveryTxt' :init='init' ></editor>
            <p class="form-group-tip">请输入该商品待发货时的说明</p>
          </div>
        </div>
        <div class="form-group pending-success-text">
          <label class="control-label">兑换成功文案：</label>
          <div class="richeditor">
            <editor id='tinymce3' v-model='goods.exchangeSuccessTxt' :init='init' ></editor>
            <p class="form-group-tip">请输入该奖品兑换成功后的说明</p>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label label-required">商品图片</label>
          <div class="tabs-box">
            <ul class="header-tabs cf" role="tablist">
              <li @click="photoShow('detailsShow');"><a href="javascript:void(0)" :class="{'active':photo.detailsShow}" >
                商品详情图 <i class="iconfont icon-checked2" :class="{'active':goods.photoDetails!=undefined && goods.photoDetails.length>0}"></i> </a></li>

              <li @click="photoShow('miniShow');"><a href="javascript:void(0)" :class="{'active':photo.miniShow}">
                缩略图 <i class="iconfont icon-checked2" :class="{'active':goods.photoMini!=undefined && goods.photoMini.id !=undefined}"></i> </a></li>

              <li @click="photoShow('iconShow');"><a href="javascript:void(0)" :class="{'active':photo.iconShow}">
                图标 <i class="iconfont icon-checked2" :class="{'active':goods.photoIcon!=undefined && goods.photoIcon.id !=undefined}"></i> </a></li>

            </ul>
            <div class="tab-content">
              <!--商品详情图-->
              <div role="tabpanel" class="tab-pane active" v-show="photo.detailsShow">
                <div class="warn-tip">
                  <p>
                    <span class="c-orange">注：</span>
                    详情图为必填选项，可以上传单张多张。尺寸为640*300像素或750*350像素，格式为jpg、png、jpeg，图片大小不可大于1M。
                  </p>
                </div>
                <div class="multi-image-upload cf w-640-h-300">
                  <ul class="uploaded-images fl">

                    <li v-for="(item,index) in goods.photoDetails">
                      <img :src="imageWebServer + item.path + item.name"> <i class="iconfont remove-icon" @click="photoDel('photoDetails',index)">&#xe694;</i>
                    </li>
                  </ul>
                  <div class="add-image-container fl">
                    <div class="file-upload file-uploads file-uploads-html4">
                      <input type="file" name="file" accept="image/jpg,image/jpeg,image/png" ref="pathClear" @change="getFile($event,'photoDetails')">
                    </div>
                    <i class="iconfont add-image-btn icon-add2"></i>
                  </div>
                  <p class="invalid"></p>
                </div>
                <p class="invalid"></p>
              </div>
              <!--商品缩略图-->
              <div role="tabpanel" class="tab-pane active" v-show="photo.miniShow">
                <div class="warn-tip">
                  <p><span class="c-orange">注：</span>首页列表区使用的图片，格式为jpg、png，图片大小不可大于1M。</p>
                </div>
                <div class="image-upload-container cf w-225-h-140">
                  <div class="img fl" :class="{'img-uploaded':goods.photoMini!=undefined && goods.photoMini.id!=undefined}"><p><img :src="imageWebServer + goods.photoMini.path+goods.photoMini.name"></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>225px*140px或370*230px，建议png格式</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png" ref="pathClear" @change="getFile($event,'photoMini')">
                        </div>
                        <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                      </div>
                    </div>
                  </div>
                  <p class="invalid"></p>
                </div>
                <p class="invalid"></p>
              </div>
              <!--图标-->
              <div role="tabpanel" class="tab-pane active" v-show="photo.iconShow">
                <div class="warn-tip"><p><span class="c-orange">注：</span>用于呈现品牌信息的图标，兑换记录等页面</p></div>
                <div class="image-upload-container cf w-100-h-100">
                  <div class="img fl" :class="{'img-uploaded':goods.photoIcon!=undefined && goods.photoIcon.id!=undefined}"><p><img :src="imageWebServer + goods.photoIcon.path+goods.photoIcon.name"></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>尺寸为100*100像素，格式为jpg、png，图片大小不可大于1M。</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png" ref="pathClear" @change="getFile($event,'photoIcon')">
                        </div>
                        <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                      </div>
                    </div>
                  </div>
                  <p class="invalid"></p></div>
                <p class="invalid"></p></div>

            </div>
          </div>
        </div>
      </div>
      <div class="form-groups-chunk"><h4>兑换规则：</h4>
        <div class="form-group">
          <label class="control-label label-required">市面价值：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control invalid untouched pristine" id="goodsPrice" v-model.trim="goods.goodsPrice" placeholder="该商品的市场价格或原价，用作展示">
            <div class="input-group-addon select-addon">
              <i class="iconfont icon-dropDown"></i>
              <select class="select-control" v-model="goods.goodsPriceUnit">
                <option value="0">元</option>
                <option value="1">积分</option>
              </select></div>
          </div>
        </div>
        <div class="form-group" v-show="goods.goodsType==1">
          <label class="control-label">是否上门自提：</label>
          <div class="switch "  >
            <label class="switch-label" :class="[{'on':goods.isSend},{'off':!goods.isSend}]" @click="typeChange('isSend')">
              <div class="switch-inner"></div>
              <div class="switch-switch"></div>
            </label>
          </div>
          <p class="switch-tip">
            <span>开启后，允许用户上门自提商品</span>
          </p>
        </div>
        <div class="form-group" v-show="goods.goodsType==1 && goods.isSend">
          <label class="control-label label-required">自提地点：</label>
          <div class="input-with-addon w40">
            <input type="text" class="form-control invalid untouched pristine" id="takeSelfAddr" v-model.trim="goods.takeSelfAddr" placeholder="请输入自提地点">
          </div>
          <a href="javascript:void(0)" class="link-d ml15 ft14" @click="goAppInfo">配置应用自提地点</a>
        </div>
        <div class="form-group">
          <label class="control-label label-required">兑换价格：</label>
          <div class="radio-group bottomline-radio">
            <label class="radio-label active">
              <input type="radio" class="radio-control" name="exchangeType" value="1" v-model="goods.exchangePriceType"> 纯积分
            </label>
            <label class="radio-label" v-show="goods.goodsType ==1">
              <input type="radio" class="radio-control" name="exchangeType" value="2" v-model="goods.exchangePriceType"> 积分＋人民币
            </label>
          </div>
          <div class="exchange-amb mt20">
            <template v-if="goods.exchangePriceType == '1' || goods.exchangePriceType == ''">
              <input type="text" class="form-control invalid untouched pristine" placeholder="兑换该商品所需要的消耗积分" id="exchangeIntegralPrice" v-model.trim="goods.exchangeIntegralPrice">
            </template>
            <template v-else>
              <div class="credit-money-input">
                <div class="input-box multiple-input-box">
                  <div class="input-with-addon">
                    <input type="text" class="form-control invalid untouched pristine" placeholder="兑换将会消耗的积分" id="exchangeIntegralPrice" v-model.trim="goods.exchangeIntegralPrice">
                    <div class="input-group-addon">积分</div>
                  </div>
                </div>
                <i class="iconfont">&#xe6e8;</i>
                <div class="input-box">
                  <div class="input-with-addon">
                    <input type="text" class="form-control invalid untouched pristine" placeholder="兑换所需支付的人民币" v-model.trim="goods.exchangePrice">
                    <div class="input-group-addon">元</div>
                  </div>
                </div>
              </div>
            </template>

          </div>
        </div>
        <div class="form-group" v-show="goods.goodsType ==1">
          <label class="control-label label-required">运费：</label>
          <div class="exchange-amb">
            <div class="form-group">
              <label class="control-label">运费价格：</label>
              <div class="radio-group">
                <label class="radio-label">
                  <input type="radio" class="radio-control" name="expressType" value="0" v-model="goods.freightWay"> 包邮 </label>
                <div class="input-box">
                  <label class="radio-label">
                    <input type="radio" class="radio-control" name="expressType" value="1" v-model="goods.freightWay"> 统一运费 </label>
                  <div class="input-with-addon unity-input">
                    <input type="text" class="form-control" placeholder="运费" id="freightPrice" v-model="goods.freightPrice">
                    <div class="input-group-addon">元</div>
                  </div>
                </div>
                <!--暂时去除运费模板-->
                <!--<div class="input-box">
                  <label class="radio-label">
                    <input type="radio" class="radio-control" name="expressType" value="2" v-model="goods.freightWay"> 运费模板 </label>
                  <div class="select-group">
                    <div class="input-group-addon select-addon">
                      <i class="iconfont icon-dropDown"></i>
                      <select class="select-control" >
                        <option value="">请选择运费模板</option>
                      </select></div>
                  </div>
                </div>
                <a class="link" href="setting?appId=48019#more/expresstemplate">管理运费模板</a>-->
              </div>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label label-required">剩余库存：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control invalid untouched pristine" id="goodsInventory" v-model="goods.goodsInventory" placeholder="该商品库存数">
            <div class="input-group-addon">件</div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label">用户兑换限制：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control valid untouched pristine" v-model="goods.userExchangeLimit" placeholder="每个用户最多可兑换的次数，不填则不做限制">
            <div class="input-group-addon select-addon">
              <i class="iconfont icon-dropDown"></i>
              <select class="select-control" v-model="goods.userExchangeLimitUnit">
                <option value="0">永久</option>
                <option value="1">每天</option>
                <option value="2">天</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label">自动下架时间：</label>
          <div class="datepicker">
            <v-date-picker placeholder="选择时间" name="autoLowerShelvesTime"  :initValue="goods.autoLowerShelvesTime" @on-change="updateDataVal('autoLowerShelvesTime',$event)" readonly></v-date-picker>
            <i class="iconfont icon-calendar"></i>
          </div>
          <a href="javascript:void(0)" class="link-d ml15 ft14" @click="cleanTime('autoLowerShelvesTime')">清除时间</a></div>
        <div class="form-group">
          <label class="control-label">开启审核：</label>
          <div class="switch "  >
            <label class="switch-label" :class="[{'on':goods.auditStatus},{'off':!goods.auditStatus}]" @click="typeChange('auditStatus')">
              <div class="switch-inner"></div>
              <div class="switch-switch"></div>
            </label>
          </div>
          <p class="switch-tip">
            <span>开启后，您的用户兑换这个商品时，需要进行审核步骤。您可以在待审核订单栏目里进行通过或者拒绝的操作。</span>
          </p>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">额外兑换限制：</label>
          <div class="switch-box">
            <div class="switch "  >
              <label class="switch-label" :class="[{'on':goods.additionalExchangeLimit},{'off':!goods.additionalExchangeLimit}]" @click="typeChange('additionalExchangeLimit')">
                <div class="switch-inner"></div>
                <div class="switch-switch"></div>
              </label>
            </div>
            <p class="switch-tip">
              <span>涉及商品每日限额、可兑换时间设置</span>
            </p>
          </div>
          <div class="exchange-limit mt20" v-show="goods.additionalExchangeLimit">
            <div class="form-group">
              <label class="control-label">每日兑换限制：</label>
              <div class="input-with-addon">
                <input type="text" class="form-control valid untouched pristine" v-model="goods.dailyExchangeLimit" placeholder="每天最多可被兑换的次数，不填则不做限制">
                <div class="input-group-addon">次</div>
              </div>
              <p class="form-group-tip">您是否希望每天限额发放此商品</p>
            </div>
            <div class="form-group">
              <label class="control-label">兑换日期限制：</label>
              <div class="date-range">
                <div class="datepicker">
                  <v-date-picker placeholder="起始时间" className="noBorder" name="beginTime"  :initValue="goods.exchangeStartTime" @on-change="updateDataVal('exchangeStartTime',$event)" readonly></v-date-picker>
                  <i class="iconfont icon-calendar"></i>
                </div>
                <span class="range-split">-</span>
                <div class="datepicker">
                  <v-date-picker placeholder="结束时间" className="noBorder" name="endTime" :initValue="goods.exchangeEndTime" @on-change="updateDataVal('exchangeEndTime',$event)" readonly></v-date-picker>
                  <i class="iconfont icon-calendar"></i>
                </div>
                <p class="date-range-error"></p></div>
              <p class="form-group-tip">配置之后，此商品只在日期区间内可以兑换，不填则为每天均可兑换</p></div>
          </div>
        </div>
      </div>
      <div class="form-group" v-if="goods.goodsType == 2">
        <label class="control-label label-required">档位信息：</label>
        <div class="radio-group bottomline-radio">
          <label class="radio-label active">
            <input type="radio" class="radio-control" name="gearType" value="1" v-model="goods.gearType"> 此商品只有一个单一档位
          </label>
          <label class="radio-label">
            <input type="radio" class="radio-control" name="gearType" value="2" v-model="goods.gearType"> 此商品有多个档位
          </label>
        </div>
        <div class="single-gear mt20" v-if="goods.gearType == '1'">
          <div class="form-group">
            <label class="control-label">标识符：</label>
            <input type="text" class="form-control invalid untouched pristine" v-model.trim="goods.gearValue" placeholder="请输入此商品在你系统中的标识符">
            <p class="form-group-tip">用户中奖的时候，平台会将此标识符（通常为你的商品ID）通过接口传递给您的服务器，请自行识别后处理后续流程。具体请
              <a href="" target="_blank">查看文档</a>。此表示可向负责您APP对接的开发同事索要。
            </p>
          </div>
        </div>
        <div class="multi-gear mt20" v-else>
          <table class="multi-gear-table">
            <thead>
            <tr class="tr-header">
              <th class="th-title">标题</th>
              <th class="th-identifier">标识符</th>
              <th class="th-identifier">需要积分</th>
              <th class="th-operate">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr class="tr-items" v-for="(item,index) in goods.gearValueJson">
              <td><input type="text" class="form-control" v-model.trim="item.gearTitle" placeholder="该档位选项文档"></td>
              <td><input type="text" class="form-control" v-model.trim="item.gearVal" placeholder="该档位在您系统的标识符"></td>
              <td><input type="text" class="form-control" v-model.trim="item.gearPrice" placeholder="兑换所需积分"></td>
              <td>
                <div class="posr">
                  <span @mouseenter="enterActions('delete'+index)" @mouseleave="leave"> <a href="javascript:void(0)" class="iconfont delete icon-delete" @click="delGear(index)"></a> </span>
                  <div class="tooltip top delete" role="tooltip" v-show=" goods.gearDelShow=='delete'+index ? true : false">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner"> 删除</div>
                  </div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
          <a href="javascript:void(0)" class="add-gear-btn" @click="addGear">
            <i class="iconfont icon-add"></i> 添加档位
          </a>
          <p class="form-group-tip"> 用户申请兑换的时候，平台会将此标识符(通常为你的商品id)通过接口传递给您 的服务器，请自行识别后处理后续流程。具体请
            <a href="" class="c-green" target="_blank">查看文档</a>。
            此标识可向负责您APP 对接的开发同事索要。 </p>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">自定义角标：</label>
          <input type="text" class="form-control valid untouched pristine" maxlength="6" v-model="goods.cornerMarkTxt" placeholder="限6个字符之内，不填则不展示">
          <div class="form-group color-group">
            <label class="control-label">角标底色：</label>
            <input class="form-control" placeholder="请输入6位有效的色值，无需带#号" v-model="goods.cornerMarkBagColor" style="width:50%;display: inline-block;">
            <i class="color-input-pre">#</i>
            <i class="iconfont icon-arrowRight"></i>
            <div class="colorpicker-wrapper tag-colorpicker">
              <div class="colorpicker-trigger" @click="showColorBox()">
                <span class="color-preview" v-bind:style="{'background-color':'rgba('+ chooseColors.rgba.r +','+ chooseColors.rgba.g +','+ chooseColors.rgba.b +','+ chooseColors.rgba.a +')'}"></span>
                <i class="iconfont">&#xe744;</i>
              </div>

              <div class="color-box-wrapper" v-show="colorIsShow">
                <Sketch v-model="colors" :class="'vue-color__sketch'"></Sketch>
                <div class="color-options">
                  <a href="javascript:;" class="btn btn-default" @click="showColorBox()">取消</a>
                  <a href="javascript:;" class="btn btn-green" @click="chooseColor()">确定</a></div>
              </div>
            </div>
          </div>
        </div>
        <div class="form-group pl20" style="padding-left: 140px;">
          <label class="control-label" style="width: 150px;">自定义兑换按钮文案：</label>
          <input class="form-control" placeholder="限6个字符之内，不填则为默认文案 '马上兑换'" v-model="goods.exchangeButTxt" maxlength="6">
        </div>
      </div>
      <div class="form-group mt20">
        <div  class="btn btn-green btn-lg mr20" @click="submit('warehouse');" v-if="goods.id=='' || goods.id==undefined">放入仓库</div>
        <div  class="btn btn-green btn-lg" @click="submit('shelves');" v-if="goods.id=='' || goods.id==undefined">上架</div>
        <div  class="btn btn-green btn-lg" @click="submit('edit');" v-if="goods.id!='' && goods.id !=undefined">保存</div>
      </div>
    </div>
    <transition name="loading">
      <v-loading v-show="dataLoading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {uploadImage,getAcApply,saveObjectGoods,getObjectGoods,getCategoryList} from '@/service/getData';
  import tinymce from 'tinymce/tinymce'
  import 'tinymce/themes/modern/theme'
  import Editor from '@tinymce/tinymce-vue'
  import 'tinymce/plugins/link'
  import 'tinymce/plugins/lists'
  import 'tinymce/plugins/wordcount'
  import 'tinymce/plugins/colorpicker'
  import 'tinymce/plugins/textcolor'
  import { Sketch } from 'vue-color';
  import $ from "jquery";
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';
  export default{
    data(){
      return {
        imageWebServer:config.ossImgPath,
        isShow: false,
        file:"",
        dataLoading:false,              //加载中显示状态
        //编辑器初始化显示的插件内容
        init: {
          language_url: '/static/tinymce/zh_CN.js',
          language: 'zh_CN',
          skin_url: '/static/tinymce/skins/lightgray',
          height: 300,
          plugins: 'link lists colorpicker textcolor wordcount',
          toolbar:
            'bold underline | fontsizeselect | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist | undo redo | link unlink image | removeformat',
          branding: false
        },
        colorIsShow:false,
        colors: {
          hex: '',
          hsl: {h: 352.71844660194176, s: 0.980952380952381, l: 0.4117647058823529, a: 1},
          hsv: {h: 352.71844660194176, s: 0.9903846153846154, v: 0.8156862745098039, a: 1},
          rgba: {r: 208, g: 2, b: 27, a: 1},
          a: 1
        },
        chooseColors:{hex:'',rgba: {r: 208,g: 2,b: 27,a: 1}},
        goods:{
          id:'',                                      //商品id
          goodsType:this.$route.params.type,          //商品类型
          goodsState:null,                            //状态
          goodsName:'',                               //商品标题name
          categoryId:'',                              //商品分类
          goodsCode:'',                               //商品编码
          goodsDetails:"<h4 style=\"margin-bottom: 11px; margin-top: 0px; font-size: 15px; color: #333333; font-family: 'Hiragino Sans GB', 'Helvetica Neue', Helvetica, 'Microsoft Yahei', STHeiTi, Arial, sans-serif;\">活动规则</h4>"+
          "<p style=\"color: #333333; padding: 0px; margin: 0px 0px 5px; line-height: 20px; font-family: 'Hiragino Sans GB', 'Helvetica Neue', Helvetica, 'Microsoft Yahei', STHeiTi, Arial, sans-serif;\">这里加入实物的兑换规则等内容</p>"+
          "<h4 style=\"margin-bottom: 11px; margin-top: 10px; font-size: 15px; color: #333333; font-family: 'Hiragino Sans GB', 'Helvetica Neue', Helvetica, 'Microsoft Yahei', STHeiTi, Arial, sans-serif;\">详情说明</h4>"+
          "<p style=\"color: #333333; padding: 0px; margin: 0px 0px 5px; line-height: 20px; font-family: 'Hiragino Sans GB', 'Helvetica Neue', Helvetica, 'Microsoft Yahei', STHeiTi, Arial, sans-serif;\">这里填写你的实物说明文字</p>",                            //详情说明
          stayDeliveryTxt:'',                         //待发货文案
          exchangeSuccessTxt:'',                      //兑换成功文案
          isSend:false,                               //是否上门自提
          takeSelfAddr:"",                            //自提地点
          goodsPrice:'',                              //市面价值
          goodsPriceUnit:0,                           //市面价值单位
          exchangeIntegralPrice:'',                   //兑换价格（积分）
          exchangePrice:'',                           //兑换价格（现金）
          exchangePriceType:1,                        //兑换价格类型（1：纯积分，2：积分加现金）
          freightWay:0,                               //运费价格计算方式（0：包邮，1：固定运费，2：运费模板）
          freightPrice:'',                            //运费价格
          goodsInventory:'',                          //剩余库存
          userExchangeLimit:'',                       //用户兑换限制
          userExchangeLimitUnit:0,                    //用户兑换限制单位（0：永久，1：每天，2：天）
          autoLowerShelvesTime:'',                    //自动下架时间
          auditStatus:false,                          //审核开启状态
          additionalExchangeLimit:false,              //额外兑换限制开启状态
          dailyExchangeLimit:'',                      //每日兑换限制
          exchangeStartTime:'',                       //兑换日期限制，开始时间
          exchangeEndTime:'',                         //兑换日期限制，结束时间
          cornerMarkTxt:'',                           //角标文字
          cornerMarkBagColor:'',                      //角标底色
          exchangeButTxt:'',                          //自定义兑换按钮文案
          gearType:1,                                //档位类型
          gearValue:'',                               //单档位值
          gearValueJson:[                                 //多档位json值
            {gearTitle:"",gearVal:"",gearPrice:""},
            {gearTitle:"",gearVal:"",gearPrice:""},
          ],
          gearDelShow:false,
          photoDetails:[],
          photoMini:{},
          photoIcon:{}
        },
        //图片上传显示和规则
        photo:{
          detailsShow:true,
          miniShow:false,
          iconShow:false,
          detailsRules:[{"width":640,"height":300},{"width":750,"height":350}],
          miniRules:[{"width":225,"height":140},{"width":370,"height":230}],
          iconRules:[{"width":100,"height":100}]
        },
        submitStatus:true,
        acApplyAddr:"",                               //应用自提地点
        categoryList: [],                             //分类集合
      }
    },
    methods:{
      // 获取应用信息
      acApplyData (){
        this.loading = true;
        getAcApply().then(res =>{
          const re = res.data.data.data;
          this.goods.takeSelfAddr = re.takeSelfAddr;
          this.acApplyAddr = re.takeSelfAddr;
        })
      },
      getGoodsCategory(){
        getCategoryList().then(res =>{
          const re = res.data.data.data;
          this.categoryList = re.data;
        })
      },
      //图片上传方法
      getFile: function (event,param) {
        this.file = event.target.files[0];
        let formData = new FormData();
        formData.append("file", this.file);
        formData.append("saveFilePathName", "upload/goods");
        var WHList;
        switch (param){
          case "photoDetails":
            WHList = this.photo.detailsRules;
            break;
          case "photoMini":
            WHList = this.photo.miniRules;
            break;
          case "photoIcon":
            WHList = this.photo.iconRules;
            break;
        }
        formData.append("WHList", JSON.stringify(WHList));
        uploadImage(formData).then(res =>{
          let re = res.data.data;
          if(re.status==1001){
            event.target.files[0].name
            var data = {id:re.id,path:re.path,name:re.name};
            if(param=="photoDetails"){
              this.goods.photoDetails.push(data);
            }else{
              this.goods[param] = data;
            }
            this.$refs.pathClear.value = '';
          }else if(re.status == 1002){
            this.$Message.error("图片不满足规定宽高");
          }else{
            this.$Message.error("上传失败");
          }
        })
      },
      //通过下标删除图片
      photoDel:function(type,index){
        this.goods[type].splice(index,1);
      },
      //显示颜色选择器
      showColorBox(){
        this.colorIsShow = !this.colorIsShow;
      },
      //关闭颜色选择器
      chooseColor: function(){
        [this.chooseColors.hex,this.chooseColors.rgba] = [this.colors.hex,this.colors.rgba];
        this.showColorBox();
      },
      //上传图片显示的切换
      photoShow:function(param){
        this.photo.detailsShow = false;
        this.photo.miniShow = false;
        this.photo.iconShow = false;
        this.photo[param] = true;
      },
      //开关的切换
      typeChange:function(param){
        this.goods[param] = !this.goods[param];
        if(param == "isSend" && this.goods.takeSelfAddr == "" && this.goods.isSend == true){
          this.goods.takeSelfAddr = this.acApplyAddr;
        }else if(this.goods.isSend == false){
          this.goods.takeSelfAddr = "";
        }
      },
      //时间选中事件
      updateDataVal:function(name,value){
        this.goods[name] = value;
      },
      cleanTime:function(name){
        this.goods[name] = '';
      },
      //提交方法
      submit:function(type){
        if(!this.submitStatus){
          return
        }

        if(this.goods.goodsName == ""){
          $("#name").focus();
          this.$Message.error("请输入商品标题");
          return;
        }
        if(this.goods.photoDetails == undefined || this.goods.photoDetails.length <1){
          this.$Message.error("请上传商品详情图");
          return;
        }
        if(this.goods.photoMini == undefined || this.goods.photoMini.name == undefined){
          this.$Message.error("请上传商品缩略图");
          return;
        }
        if(this.goods.photoIcon == undefined || this.goods.photoIcon.name == undefined){
          this.$Message.error("请上传商品logo图");
          return;
        }
        if(this.goods.goodsPrice == "" || this.goods.goodsPrice<=0){
          $("#goodsPrice").focus();
          this.$Message.error("请输入正确的商品市面价值");
          return;
        }
        if(this.goods.exchangePriceType == "1" && (this.goods.exchangeIntegralPrice =="" || this.goods.exchangeIntegralPrice <= 0)){
          $("#exchangeIntegralPrice").focus();
          this.$Message.error("请输入正确的兑换积分");
          return;
        }
        if(this.goods.exchangePriceType == "2" && (this.goods.exchangeIntegralPrice=="" || this.goods.exchangePrice == "") ){
          $("#exchangeIntegralPrice").focus();
          this.$Message.error("请输入兑换积分和现金");
          return;
        }
        if(this.goods.exchangePriceType == "2" && (this.goods.exchangeIntegralPrice <=0 || this.goods.exchangePrice <=0) ){
          $("#exchangeIntegralPrice").focus();
          this.$Message.error("兑换积分和现金必须大于0");
          return;
        }
        if(this.goods.freightWay == "1" && this.goods.freightPrice == ""){
          $("#freightPrice").focus();
          this.$Message.error("请输入运费");
          return;
        }
        if(this.goods.goodsInventory ==""){
          $("#goodsInventory").focus();
          this.$Message.error("请输入库存");
          return;
        }
        if(this.goods.goodsInventory <=0){
          $("#goodsInventory").focus();
          this.$Message.error("库存必须大于0");
          return;
        }
        if(this.goods.isSend && this.goods.takeSelfAddr == ""){
          $("#takeSelfAddr").focus();
          this.$Message.error("请填写自提地点");
          return;
        }
        if(this.goods.goodsType == 2){
          if(this.goods.gearType==1 && this.goods.gearValue ==""){
            this.$Message.error("请填写档位标识符");
            return;
          }else if(this.goods.gearType==2){
            if(this.goods.gearValueJson.length<2){
              this.$Message.error("多档位填写有误");
              return;
            }
            function sortVal(a,b){
              return a.gearVal-b.gearVal
            }
            this.goods.gearValueJson.sort(sortVal);
            console.log(this.goods.gearValueJson);
            for(var i = 0;i<this.goods.gearValueJson.length;i++){
              if(this.goods.gearValueJson[i].gearTitle=="" || this.goods.gearValueJson[i].gearVal=="" || this.goods.gearValueJson[i].gearPrice == ""){
                this.$Message.error("档位信息不可为空");
                return;
              }
              if(i<this.goods.gearValueJson.length-1) {
                if (this.goods.gearValueJson[i].gearVal == this.goods.gearValueJson[i + 1].gearVal) {
                  this.$Message.error("档位标识符不可重复");
                  return;
                }
              }
              if(this.goods.gearValueJson[i].gearPrice<0){
                this.$Message.error("档位价格不能小于零");
                return;
              }
            }
          }
        }
        this.submitStatus = false;
        var goodsInfo = this.goods;
        goodsInfo.photoDetailsList = JSON.stringify(this.goods.photoDetails);
        goodsInfo.photoMiniList = JSON.stringify(this.goods.photoMini);
        goodsInfo.photoIconList = JSON.stringify(this.goods.photoIcon);
        goodsInfo.gearValueJsonList = JSON.stringify(this.goods.gearValueJson);
        goodsInfo.type = type;
        saveObjectGoods(goodsInfo).then(res =>{
          let re = res.data.data;
          this.submitStatus = true;
          if(re.status==1000){
            this.$Message.success("保存成功");
            this.$router.push({ name: "mygoods"});
          }else if(re.status==2001){
            this.$Message.error(re.message);
            this.$router.push({ name: "mygoods"});
          }
        })

      },
      //如果有id，就初始化加载商品数据
      dataInit:function(){
        var that = this;
        that.goods.id = this.$route.params.id;
        that.goods.goodsState = this.$route.params.goodsState;
        if(that.goods.id == "" || that.goods.id == undefined){
          return
        }
        this.dataLoading = true;
        getObjectGoods({id:that.goods.id,goodsState:that.goods.goodsState}).then(res =>{
          this.dataLoading = false;
          let re = res.data.data.data;
          that.goods.id = re.id;
          that.goods.goodsType = re.goodsType;
          that.goods.goodsName = re.goodsName;
          that.goods.goodsCode = re.goodsCode;
          that.goods.goodsDetails = re.goodsDetails;
          that.goods.stayDeliveryTxt = re.stayDeliveryTxt;
          that.goods.exchangeSuccessTxt = re.exchangeSuccessTxt;
          that.goods.isSend = re.isSend;
          that.goods.takeSelfAddr = re.takeSelfAddr;
          that.goods.goodsInventory = re.goodsInventory;
          that.goods.auditStatus = re.auditStatus;
          that.goods.cornerMarkTxt = re.cornerMarkTxt;
          that.goods.cornerMarkBagColor = re.cornerMarkBagColor;
          that.goods.exchangeButTxt = re.exchangeButTxt;
          that.goods.photoDetails = re.goodsPhotos;
          that.goods.photoIcon = re.goodsIconPhoto;
          that.goods.photoMini = re.goodsMainPhoto;

          that.goods.goodsPrice = re.exchangeRules.goodsPrice;
          that.goods.goodsPriceUnit = re.exchangeRules.goodsPriceUnit;
          that.goods.exchangeIntegralPrice = re.exchangeRules.exchangeIntegralPrice;
          that.goods.exchangePrice = re.exchangeRules.exchangePrice;
          that.goods.exchangePriceType = re.exchangeRules.exchangePriceType;
          that.goods.freightWay = re.exchangeRules.freightWay;
          that.goods.freightPrice = re.exchangeRules.freightPrice;
          that.goods.userExchangeLimit = re.exchangeRules.userExchangeLimit;
          that.goods.userExchangeLimitUnit = re.exchangeRules.userExchangeLimitUnit;
          that.goods.additionalExchangeLimit = re.exchangeRules.additionalExchangeLimit;
          that.goods.dailyExchangeLimit = re.exchangeRules.dailyExchangeLimit;

          that.goods.autoLowerShelvesTime = re.autoLowerShelvesTime;
          that.goods.exchangeStartTime = re.exchangeRules.exchangeStartTime;
          that.goods.exchangeEndTime = re.exchangeRules.exchangeEndTime;

          that.goods.categoryId = re.category == undefined?"":re.category.id;

          //档位信息
          that.goods.gearType = re.gearType;
          if(re.gearType==1){
            that.goods.gearValue = re.gearValue;
          }else if(re.gearType == 2){
            that.goods.gearValueJson = JSON.parse(re.gearValue);
          }
          console.log();
        })
      },
      goAppInfo:function(){
        this.$router.push({ name: "appinfo"});
      },
      delGear:function(index){
        if(this.goods.gearValueJson.length==2){
          this.$Message.error("多档位至少需要两个档位");
          return
        }
        this.goods.gearValueJson.splice(index,1);
      },
      addGear:function(){
        var obj = {gearTitle:"",gearVal:"",gearPrice:""};
        this.goods.gearValueJson.push(obj);
      },
      enterActions(actionsList){
        this.goods.gearDelShow = actionsList;
      },
      leave(){
        this.goods.gearDelShow = false;
      }
    },
    watch:{
      'chooseColors.hex':function(newVal,oldVal){
        this.goods.cornerMarkBagColor = newVal;
      }
    },
    created:function () {
      var that = this;
      this.$Message.config({
        top: 100
      });
      this.acApplyData();
      this.getGoodsCategory();
      that.dataInit();
      tinymce.init({})
    },
    components: {
      Editor,
      Sketch
    },
    directives: {
      focus: {
        // 指令的定义
        inserted: function (el) {
          el.focus()
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/addGoods.css";
  .color-group{margin-top: 20px;}
</style>
