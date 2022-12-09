<template>
  <div class="main">
    <div class="page-title">
      <h2 class="has-sub-title"> 添加商品
        <i class="iconfont icon-arrowRight"></i>
        <span class="sub-title">添加虚拟商品</span>
      </h2>
    </div>
    <div class="page-content edit-goods-form">
      <p class="no-virtual-url">
        <span class="c-red">尚未配置虚拟接口，虚拟商品不可添加！</span>
        <a class="c-green" href="setting?appId=46346#!/interface">&gt;&gt;配置虚拟商品充值接口</a>
      </p>
      <v-tip :status="true" className="form-tip" v-show="isShowTip" @on-close="hideTip('isShowTip')">
        虚拟商品可以实现用户中奖之后，直接在用户账号内到账相应的服务，例如你系统的会员身份、虚拟货币、游戏礼包等等，实现虚拟商品兑换需要服务端接口配合。具体请
        <a href="" class="c-green" target="_blank">查看文档</a>。
      </v-tip>
      <!--<div class="tip form-tip">-->
      <!---->
      <!--</div>-->
      <div class="form-groups-chunk">
        <h4>展示信息：</h4>
        <div class="form-group">
          <label class="control-label label-required">商品标题：</label>
          <input type="text" class="form-control invalid untouched pristine" placeholder="积分商城的商品展示名称，建议8个字以内">
          <p class="form-group-tip">前台展示给用户的主标题</p>
        </div>
        <div class="form-group">
          <label class="control-label">商品编码：</label>
          <input type="text" class="form-control" placeholder="选填。填写后，将通过用户积分扣除传给开发者，请求给开发者记录">
        </div>
        <div class="form-group">
          <label class="control-label">详情说明：</label>
          <div class="richeditor">
            <div class="richeditor-toolbar" style="" id="desc-toolbar">
              <a data-wysihtml5-command="formatBlock" class="iconfont" data-wysihtml5-command-value="h4"
                 href="javascript:;" unselectable="on">H1</a>
              <a data-wysihtml5-command="fontSize" class="iconfont" data-wysihtml5-command-value="large"
                 href="javascript:;" unselectable="on">T</a>
              <a data-wysihtml5-command="bold" class="iconfont" title="CTRL+B" href="javascript:;"
                 unselectable="on">B</a>
              <a data-wysihtml5-command="foreColor" class="iconfont foreColor" data-wysihtml5-command-value="red"
                 href="javascript:;" unselectable="on">A</a>
              <a data-wysihtml5-command="createLink" class="iconfont create-link icon-link" href="javascript:;"
                 unselectable="on"></a>
              <a data-wysihtml5-command="insertUnorderedList" class="iconfont unordered-list icon-unorderedList"
                 href="javascript:;"
                 unselectable="on"></a>
              <a data-wysihtml5-command="insertOrderedList" class="iconfont ordered-list icon-orderedList"
                 href="javascript:;"
                 unselectable="on"></a>
              <a data-wysihtml5-command="insertImage" class="iconfont insert-image icon-insertImage" href="javascript:;"
                 unselectable="on"></a>
              <div class="wysihtml5-link-modal" data-wysihtml5-dialog="createLink" style="display: none">
                <div class="form-group">
                  <label> 地址： </label>
                  <input data-wysihtml5-dialog-field="href" value="http://" class="form-control">
                </div>
                <div class="link-btns">
                  <a data-wysihtml5-dialog-action="cancel" class="btn btn-default mr20">取消</a>
                  <a data-wysihtml5-dialog-action="save" class="btn btn-green">确认</a>
                </div>
              </div>
              <div class="wysihtml5-image-modal" data-wysihtml5-dialog="insertImage" style="display: none">
                <div class="modal in richeditor-img-modal" style="display: block">
                  <div class="modal-dialog" role="document" style="width: 570px">
                    <div class="modal-content">
                      <div class="modal-header">
                        <i class="iconfont close" data-wysihtml5-dialog-action="cancel"></i>
                        <h4 class="modal-title"> 添加图片 </h4>
                      </div>
                      <div class="modal-body">
                        <div class="image-upload-container cf w-640-h-300 w-640-h-null">
                          <div class="img fl">
                            <p><img alt="" src=""></p>
                          </div>
                          <div class="upload-area">
                            <div class="upload-cell">
                              <p>图片宽为640px格式为jpg、jpeg、png</p>
                              <div class="upload-btn">
                                <div class="file-upload file-uploads file-uploads-html4">
                                  <input type="file" name="file" accept="image/jpg,image/jpeg,image/png">
                                </div>
                                <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                              </div>
                            </div>
                          </div>
                          <p class="invalid"></p>
                        </div>
                        <input type="hidden" data-wysihtml5-dialog-field="src" value="">
                      </div>
                      <div class="modal-footer">
                        <a href="javascript:void(0)" class="btn btn-green btn-pd-lg fr disabled">添加</a>
                        <a href="javascript:void(0)" class="btn btn-default btn-pd-lg fr mr20"
                           data-wysihtml5-dialog-action="cancel">取消</a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <input type="hidden" name="_wysihtml5_mode" value="1">
            <iframe class="wysihtml5-sandbox" security="restricted" allowtransparency="true" frameborder="0" width="0"
                    height="0" marginwidth="0" marginheight="0"
                    style="background-color: rgb(255, 255, 255); border-collapse: separate; border-color: rgb(0, 0, 0); border-style: none; border-width: 0px; clear: none; display: inline-block; float: none; margin: 0px; outline: rgb(0, 0, 0) none 0px; outline-offset: 0px; padding: 10px 15px; position: static; z-index: auto; vertical-align: baseline; text-align: start; box-sizing: border-box; box-shadow: none; border-radius: 0px; width: 818px; height: 300px; top: auto; left: auto; right: auto; bottom: auto;"></iframe>
          </div>
        </div>
        <div class="form-group pending-success-text">
          <label class="control-label">兑换成功文案：</label>
          <div class="richeditor">
            <div class="richeditor-toolbar" style="" id="success-toolbar">
              <a data-wysihtml5-command="formatBlock" class="iconfont " data-wysihtml5-command-value="h4"
                 href="javascript:;" unselectable="on">H1</a>
              <a data-wysihtml5-command="createLink" class="iconfont create-link icon-link" href="javascript:;"
                 unselectable="on"></a>
              <div class="wysihtml5-link-modal" data-wysihtml5-dialog="createLink" style="display: none">
                <div class="form-group">
                  <label> 地址： </label>
                  <input data-wysihtml5-dialog-field="href" value="http://" class="form-control">
                </div>
                <div class="link-btns">
                  <a data-wysihtml5-dialog-action="cancel" class="btn btn-default mr20">取消</a>
                  <a data-wysihtml5-dialog-action="save" class="btn btn-green">确认</a>
                </div>
              </div>
            </div>
            <textarea placeholder="请输入该商品兑换成功后的说明" style="display: none;">  </textarea>
            <input type="hidden" name="_wysihtml5_mode" value="1">
            <iframe class="wysihtml5-sandbox" security="restricted" allowtransparency="true" frameborder="0" width="0"
                    height="0" marginwidth="0" marginheight="0"
                    style="background-color: rgb(255, 255, 255); border-collapse: separate; border-color: rgb(0, 0, 0); border-style: none; border-width: 0px; clear: none; display: inline-block; float: none; margin: 0px; outline: rgb(0, 0, 0) none 0px; outline-offset: 0px; padding: 10px 15px; position: static; z-index: auto; vertical-align: baseline; text-align: start; box-sizing: border-box; box-shadow: none; border-radius: 0px; width: 818px; height: 120px; top: auto; left: auto; right: auto; bottom: auto;"></iframe>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label label-required">商品图片</label>
          <div class="tabs-box">
            <ul class="header-tabs cf" role="tablist">
              <li @click="photoShow('detailsShow');"><a href="javascript:void(0)" :class="{'active':photo.detailsShow}">
                商品详情图 <i class="iconfont icon-checked2"></i> </a></li>
              <li @click="photoShow('miniShow');"><a href="javascript:void(0)" :class="{'active':photo.miniShow}"> 缩略图
                <i class="iconfont icon-checked2"></i> </a></li>
              <li @click="photoShow('iconShow');"><a href="javascript:void(0)" :class="{'active':photo.iconShow}"> 图标 <i
                class="iconfont icon-checked2"></i> </a></li>
              <li @click="photoShow('bannerShow');"><a href="javascript:void(0)" :class="{'active':photo.bannerShow}">
                首页Banner <i class="iconfont icon-checked2"></i> </a></li>
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
                    <li><img src="//yun.duiba.com.cn/images/201807/4wi6c459fv.png"> <i class="iconfont remove-icon">
                      &#xe694;</i></li>
                  </ul>
                  <div class="add-image-container fl">
                    <div class="file-upload file-uploads file-uploads-html4">
                      <input type="file" name="file" accept="image/jpg,image/jpeg,image/png" @change="getFile($event)">
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
                  <div class="img fl"><p><img alt="" src=""></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>225px*140px或370*230px，建议png格式</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png">
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
                  <div class="img fl"><p><img alt="" src=""></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>尺寸为100*100像素，格式为jpg、png，图片大小不可大于1M。</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png">
                        </div>
                        <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                      </div>
                    </div>
                  </div>
                  <p class="invalid"></p></div>
                <p class="invalid"></p></div>
              <!--首页Banner-->
              <div role="tabpanel" class="tab-pane active" v-show="photo.bannerShow">
                <div class="warn-tip"><p><span class="c-orange">注：</span>展示在首页Banner区的图片，格式为jpg、png、jpeg，图片大小不可大于1M。</p>
                </div>
                <div class="image-upload-container cf w-750-h-220">
                  <div class="img fl"><p><img alt="" src=""></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>新版：750px*220px</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png">
                        </div>
                        <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                      </div>
                    </div>
                  </div>
                  <p class="invalid"></p></div>
                <div class="image-upload-container cf w-640-h-280">
                  <div class="img fl"><p><img alt="" src=""></p></div>
                  <div class="upload-area">
                    <div class="upload-cell"><p>旧版：640px*280px</p>
                      <div class="upload-btn">
                        <div class="file-upload file-uploads file-uploads-html4">
                          <input type="file" name="file" accept="image/jpg,image/jpeg,image/png">
                        </div>
                        <a href="javascript:void(0)" class="btn btn-default">选择上传</a></div>
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
            <input type="text" class="form-control invalid untouched pristine" placeholder="该商品的市场价格或原价，用作展示">
            <div class="input-group-addon select-addon">
              <i class="iconfont icon-dropDown"></i>
              <select class="select-control">
                <option value="0">元</option>
                <option value="1">测试积分</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label label-required">兑换价格：</label>
          <div class="radio-group bottomline-radio">
            <label class="radio-label active">
              <input type="radio" class="radio-control" name="exchangeType" value="credit" v-model="exchangeType"> 纯积分
            </label>
            <label class="radio-label">
              <input type="radio" class="radio-control" name="exchangeType" value="amb" v-model="exchangeType"> 积分＋人民币
            </label>
          </div>
          <div class="exchange-credit mt20">
            <template v-if="exchangeType == 'credit'">
              <input type="text" class="form-control invalid untouched pristine" placeholder="兑换该商品所需要的消耗积分">
            </template>
            <template v-else>

              <div class="credit-money-input">
                <div class="input-box multiple-input-box">
                  <div class="input-with-addon">
                    <input type="text" class="form-control invalid untouched pristine" placeholder="兑换将会消耗的积分">
                    <div class="input-group-addon">积分</div>
                  </div>
                </div>
                <i class="iconfont">&#xe6e8;</i>
                <div class="input-box">
                  <div class="input-with-addon">
                    <input type="text" class="form-control invalid untouched pristine" placeholder="兑换所需支付的人民币">
                    <div class="input-group-addon">元</div>
                  </div>
                </div>
              </div>
            </template>

          </div>

        </div>
        <div class="form-group">
          <label class="control-label label-required">剩余库存：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control invalid untouched pristine" placeholder="该商品库存数">
            <div class="input-group-addon">件</div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label">用户兑换限制：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control valid untouched pristine" placeholder="每个用户最多可兑换的次数，不填则不做限制">
            <div class="input-group-addon select-addon">
              <i class="iconfont icon-dropDown"></i>
              <select class="select-control">
                <option value="forever">永久</option>
                <option value="everyday">每天</option>
                <option value="DAY_">天</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label">自动下架时间：</label>
          <v-date-picker placeholder="请选择时间"></v-date-picker>
          <a href="javascript:void(0)" class="link-d ml15 ft14">清除时间</a></div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">额外兑换限制：</label>
          <div class="switch-box">
            <v-switch v-model="extraExchange"></v-switch>
            <p class="switch-tip">
              <span>涉及商品每日限额、可兑换时间设置</span>
            </p>
          </div>
          <div class="exchange-limit mt20" v-if="extraExchange">
            <div class="form-group">
              <label class="control-label">每日兑换限制：</label>
              <div class="input-with-addon">
                <input type="text" class="form-control valid untouched pristine" placeholder="每天最多可被兑换的次数，不填则不做限制">
                <div class="input-group-addon">次</div>
              </div>
              <p class="form-group-tip">您是否希望每天限额发放此商品</p>
            </div>
            <div class="form-group">
              <label class="control-label">兑换日期限制：</label>
              <div class="date-range">
                <v-date-picker placeholder="起始时间" className="noBorder"></v-date-picker>
                <span class="range-split">-</span>
                <v-date-picker placeholder="结束时间" className="noBorder"></v-date-picker>
                <p class="date-range-error"></p></div>
              <p class="form-group-tip">配置之后，此商品只在日期区间内可以兑换，不填则为每天均可兑换</p></div>
            <div class="form-group">
              <label class="control-label">地区兑换限制：</label>
              <div class="switch-box">
                <v-switch v-model="areaExchange"></v-switch>
                <p class="switch-tip"><span>您可以设置此商品地域兑换限制</span></p>
              </div>
              <div class="area-limit" v-if="areaExchange">
                <div class="radio-group bottomline-radio mb20">
                  <label class="radio-label active">
                    <input type="radio" class="radio-control" name="arealimitType" value="whitelist"
                           v-model="arealimitType">白名单，以下地域可以兑换
                  </label>
                  <label class="radio-label">
                    <input type="radio" class="radio-control" name="arealimitType" value="blacklist"
                           v-model="arealimitType">黑名单，以下地域不可兑换
                  </label>
                </div>
                <div class="region-selector">
                  <div class="form-group bottomline">
                    <label class="control-label">请选择地域：</label>
                    <v-distpicker hide-area :placeholders="placeholders" @selected="onSelected"></v-distpicker>
                    <a class="btn btn-default btn-lg" @click="addArea">添加</a></div>
                  <div class="region-selected">
                    <p class="error-region-tip c-orange" v-show="isShow">请选择省份</p>
                    <p class="error-region-tip" v-show="isShow"> 已添加 <span class="c-orange"></span></p>
                    <p class="error-region-tip" v-show="isShow"> 已添加 <span class="c-orange"></span> 全省范围，无需添加<span
                      class="c-orange"></span></p>
                    <div class="area-tags" v-if="isShowAreaTags">
                      <a href="javascript:void(0)" class="area-tag" v-for="(item,index) in areaTags">{{item.areaTag}}
                        <i class="iconfont" @click="colseMyself(index)">&#xe694;</i>
                      </a>
                    </div>
                    <p class="empty-region-tip" v-else>您还未选择地域</p>
                  </div>
                  <div role="dialog" class="modal fade" style="display: none">
                    <div class="modal-dialog" role="document" style="width: 300px;">
                      <div class="modal-content">
                        <div class="modal-header"><i class="iconfont close"></i> <h4 class="modal-title">
                          添加undefined </h4></div>
                        <div class="modal-body"><p>已选列表存在属于 <span class="c-orange"></span> 的城市：</p>
                          <p></p>
                          <p>添加“全省范围”后将被全部清除。</p></div>
                        <div class="modal-footer"><a class="btn btn-green btn-pd-lg mr15">确定</a> <a
                          class="btn btn-default btn-pd-lg">取消</a></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label">兑换需输入账号：</label>
              <v-switch v-model="exchangeNum"></v-switch>
              <p class="switch-tip">
                <span>开启后，用户在兑换时还需要输入账号，兑吧通过接口参数account传递给您的服务器，具体请参阅文档。</span>
              </p>
            </div>
            <div class="form-group">
              <label class="control-label">提示文案：</label>
              <input type="text" class="form-control valid untouched pristine"
                     placeholder="限12个字符，当需要输入账号时的提示文案，不填默认显示“请输入您的账号”">
            </div>
          </div>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">转为秒杀商品：</label>
          <div class="switch-box">
            <v-switch v-model="secKill"></v-switch>
            <p class="switch-tip">
              <span>开启后此商品将会作为秒杀商品显示在秒杀楼层</span>
            </p>
          </div>
          <div class="exchange-limit mt20" v-if="secKill">
            <div class="form-group time-limit">
              <label class="control-label">秒杀时间：</label>
              <v-selection :selections="staringTimeH" @selectCallBack="setSecKillTimeSH"></v-selection>
              <span class="select-split">:</span>
              <v-selection :selections="startingTimeS" @selectCallBack="setSecKillTimeSS"></v-selection>
              <span class="select-split">至</span>
              <v-selection :selections="endingTimeH" @selectCallBack="setSecKillTimeEH"></v-selection>
              <span class="select-split">:</span>
              <v-selection :selections="endingTimeS" @selectCallBack="setSecKillTimeES"></v-selection>
              <p class="invalid" v-if="isShowWraing"><i class="iconfont">&#xe770;</i> 结束时间必须大于开始时间 </p>
              <div class="time-setting-tip">
                <p class="form-group-tip">
                  <span style="color:red">注：</span>1.设置的开始时间，必须与当前的自然时间间隔在
                  20 分钟以上，否则无法保存提交。
                </p>
                <p class="form-group-tip tip2-time">2.当前时间距离上次设置秒杀商品的开始时间只剩 20 分钟时,不支持再修改时间。</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label">档位信息：</label>
        <div class="radio-group bottomline-radio">
          <label class="radio-label active">
            <input type="radio" class="radio-control" name="gearType" value="singleGear" v-model="gearType"> 此商品只有一个单一档位
          </label>
          <label class="radio-label">
            <input type="radio" class="radio-control" name="gearType" value="multiGear" v-model="gearType"> 此商品有多个档位
          </label>
        </div>
        <div class="single-gear mt20" v-if="gearType == 'singleGear'">
          <div class="form-group">
            <label class="control-label">标识符：</label>
            <input type="text" class="form-control invalid untouched pristine" placeholder="请输入此商品在你系统中的标识符">
            <p class="form-group-tip">用户中奖的时候，兑吧会将此标识符（通常为你的商品ID）通过接口传递给您的服务器，请自行识别后处理后续流程。具体请
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
            <tr class="tr-items">
              <td><input type="text" class="form-control" placeholder="该档位选项文档"></td>
              <td><input type="text" class="form-control" placeholder="该档位在您系统的标识符"></td>
              <td><input type="text" class="form-control" placeholder="兑换所需积分"></td>
              <td><p class="posr"><span> <a href="javascript:void(0)" class="iconfont delete icon-delete"></a> </span>
                <div class="tooltip top delete" role="tooltip" style="display: none;">
                  <div class="tooltip-arrow"></div>
                  <div class="tooltip-inner"> 删除</div>
                </div>
                </p> </td>
            </tr>
            <tr class="tr-items">
              <td><input type="text" class="form-control" placeholder="该档位选项文档"></td>
              <td><input type="text" class="form-control" placeholder="该档位在您系统的标识符"></td>
              <td><input type="text" class="form-control" placeholder="兑换所需积分"></td>
              <td><p class="posr"><span> <a href="javascript:void(0)" class="iconfont delete icon-delete"></a> </span>
                <div class="tooltip top delete" role="tooltip" style="display: none;">
                  <div class="tooltip-arrow"></div>
                  <div class="tooltip-inner"> 删除</div>
                </div>
                </p> </td>
            </tr>
            </tbody>
          </table>
          <input type="hidden" value="[{},{}]" class="invalid untouched pristine">
          <a href="javascript:void(0)" class="add-gear-btn">
            <i class="iconfont icon-add"></i> 添加档位
          </a>
          <p class="form-group-tip"> 用户申请兑换的时候，兑吧会将此标识符(通常为你的商品id)通过接口传递给您 的服务器，请自行识别后处理后续流程。具体请
            <a href="" class="c-green" target="_blank">查看文档</a>。
            此标识可向负责您APP 对接的开发同事索要。 </p>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">自定义角标：</label>
          <input type="text" class="form-control valid untouched pristine" maxlength="6" placeholder="限6个字符之内，不填则不展示">
          <div class="form-group color-group">
            <label class="control-label">角标底色：</label>
            <input class="form-control" placeholder="请输入6位有效的色值，无需带#号" :value="chooseColors.hex">
            <i class="color-input-pre">#</i>
            <i class="iconfont icon-arrowRight"></i>
            <div class="colorpicker-wrapper tag-colorpicker">
              <div class="colorpicker-trigger" @click="showColorBox()">
              <span class="color-preview"
                    v-bind:style="{'background-color':
                    'rgba('+ chooseColors.rgba.r +','+ chooseColors.rgba.g +','+ chooseColors.rgba.b +','+ chooseColors.rgba.a +')'}"></span>
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
        <div class="form-group pl20">
          <label class="label-style">自定义兑换按钮文案：</label>
          <input class="form-control" placeholder="限6个字符之内，不填则为默认文案 '马上兑换'" maxlength="6">
        </div>
      </div>
      <div class="form-group mt20">
        <a href="javascript:void(0)" class="btn btn-green btn-lg mr20 disabled">放入仓库</a>
        <a href="javascript:void(0)" class="btn btn-green btn-lg disabled">上架</a>
      </div>
    </div>
  </div>
</template>

<script>
  import VDistpicker from 'v-distpicker'
  import * as SYSTEM from '@/api/system';
  import {Sketch} from 'vue-color';

  export default{
    data(){
      return {
        isShow: false,
        photo: {                                           // 商品图片
          detailsShow: true,
          miniShow: false,
          iconShow: false,
          bannerShow: false
        },
        isShowTip: true,                                   // 提示
        exchangeType: 'credit',                            // 兑换价格
        extraExchange: false,                              // 额外兑换限制
        areaExchange: false,                               // 地区兑换限制
        arealimitType: 'whitelist',                        // 黑白名单
        placeholders: {                                    // 地区选择
          province: '请选择省份',
          city: '请选择城市'
        },
        areaTags: [{areaTag: ''}],
        isShowAreaTags: false,
        exchangeNum: false,                                // 兑换需输入账号
        secKill: false,                                    // 转为秒杀商品
        staringTimeH: [{label: '时', value: '时'}],        // 秒杀商品起始 时
        endingTimeH: [{label: '时', value: '时'}],         // 秒杀商品起始 分
        startingTimeS: [{label: '分', value: '分'}],       // 秒杀商品结束 时
        endingTimeS: [{label: '分', value: '分'}],         // 秒杀商品起结束 分
        secKillTime: {sh:0,eh:0,ss:0,es:0},  //分别对应 秒杀时间 的4个值
        isShowWraing: false,                               // 警告（结束时间必须大于开始时间）
        gearType: 'singleGear',                            // 档位信息
        colorIsShow: false,                                // 角标
        colors: {
          hex: '#D0021B',
          tone: 'D0021B',
          hsl: {
            h: 352.71844660194176,
            s: 0.980952380952381,
            l: 0.4117647058823529,
            a: 1
          },
          hsv: {
            h: 352.71844660194176,
            s: 0.9903846153846154,
            v: 0.8156862745098039,
            a: 1
          },
          rgba: {
            r: 208,
            g: 2,
            b: 27,
            a: 1
          },
          a: 1
        },
        chooseColors: {hex: '#D0021B', rgba: {r: 208, g: 2, b: 27, a: 1}},
      }
    },
    methods: {
      // 商品图片
      photoShow: function (param) {
        this.photo.detailsShow = false;
        this.photo.miniShow = false;
        this.photo.iconShow = false;
        this.photo.bannerShow = false;
        this.photo[param] = true;
      },
      // 关闭提示
      hideTip(param) {
        this[param] = false
      },
      // 地区选择
      onSelected(data) {
        this.placeholders.province = data.province.value;
        this.placeholders.city = data.city.value;
      },
      addArea () {
        var that = this
        if (this.areaTags[0].areaTag == '') {
          this.areaTags[0].areaTag = this.placeholders.city || this.placeholders.province
        } else {
          this.areaTags.push({
            areaTag: that.placeholders.city || this.placeholders.province
          })
        }
        this.isShowAreaTags = true
      },
      colseMyself(index) {
        this.areaTags.splice(index, 1);
      },
      // 秒杀
      getHour() {
        for (var h = 0; h <= 23; h++) {
          this.staringTimeH.push({
            label: h,
            value: h
          })
          this.endingTimeH.push({
            label: h,
            value: h
          })
        }
      },
      getMinute() {
        for (var m = 0; m <= 59; m++) {
          this.startingTimeS.push({
            label: m,
            value: m
          })
          this.endingTimeS.push({
            label: m,
            value: m
          })
        }
      },
      getsecKillTime() {
        //这里是获取不到值的 NaN this.endingTimeH  这些都是对象数组  不是值么
       // this.secKillTime =

      },
      setSecKillTimeSH(value) {
        this.secKillTime.sh = value;
      },
      setSecKillTimeSS(value) {
        this.secKillTime.ss = value;
      },
      setSecKillTimeEH(value) {
        this.secKillTime.eh = value;
      },
      setSecKillTimeES(value) {
        this.secKillTime.es = value;
      },
      // 自定义角标
      showColorBox(){
        this.colorIsShow = !this.colorIsShow;
      },
      chooseColor: function () {
        [this.chooseColors.hex, this.chooseColors.rgba] = [this.colors.hex, this.colors.rgba];
        this.showColorBox();
      }
    },
    watch: {
      // 监听区域
      areaTags(newVal){
        if (newVal.length == 0) {
          this.isShowAreaTags = false
        }
      },
      // 监听秒杀
      secKillTime: { //深度监听
        handler: function (newVal, oldVal) {
          console.info( (newVal.eh * 60 + newVal.es * 1) - (newVal.sh*60 + newVal.ss * 1))
          if ( (newVal.eh * 60 + newVal.es * 1) - (newVal.sh*60 + newVal.ss * 1) < 0) {
            this.isShowWraing = true
          }else{
            this.isShowWraing = false
          }
        },
        deep: true

      }
    },
    components: {
      'v-distpicker': VDistpicker,
      Sketch
    },
    created() {
      this.getHour()
      this.getMinute()
      this.getsecKillTime()
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../style/addGoods.css";

</style>
