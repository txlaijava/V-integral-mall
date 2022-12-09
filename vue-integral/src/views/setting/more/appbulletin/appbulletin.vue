<template>
  <div class="app-bulletin">
    <v-tip :status="true" v-show="isShowTip" @on-close="hideTip('isShowTip')">
      公告设置后，你的用户可以在访问积分商城首页时看到公告信息，此功能方便你对特殊事项进行通知和提醒。
    </v-tip>
    <div class="form-groups-chunk">
      <div class="form-group">
        <label class="control-label">开启公告：</label>
        <v-switch v-model="value"></v-switch>
      </div>
    </div>
    <div v-if="value">
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">公告内容：</label>
          <input type="text" class="form-control invalid untouched pristine" placeholder="积分商城首页将展示这条公告" maxlength="20">
        </div>
        <div class="form-group color-group">
          <label class="control-label">公告背景色：</label>
          <input class="form-control valid untouched dirty modified form-control-dirty invalid" placeholder="请输入6位有效的色值，无需带#号"
                 maxlength="6">
          <p class="invalid"> <i class="iconfont icon-danger"></i> 公告背景色不能为空 </p>
          <i class="color-input-pre">#</i>
        </div>
        <div class="form-group">
          <label class="control-label">公告详情页：</label>
          <div class="radios">
            <label><input type="radio" name="hasDetailPage" value="1" v-model="hasDetailPageValue"> 有 </label>
            <label><input type="radio" name="hasDetailPage" value="0" v-model="hasDetailPageValue"> 无 </label>
          </div>
        </div>
      </div>
      <div class="form-groups-chunk" v-if="hasDetailPageValue == 1">
        <div class="form-group">
          <label class="control-label">公告详情指向：</label>
          <div class="radios">
            <label><input type="radio" name="useDuibaPage" value="1" v-model="useDuibaPageValue"> 我自己有公告详情页面 </label>
            <label><input type="radio" name="useDuibaPage" value="2" v-model="useDuibaPageValue"> 使用兑吧的页面 </label>
          </div>
        </div>
        <div class="form-group" v-if="useDuibaPageValue == 1">
          <label class="control-label">公告详情链接：</label>
          <input type="text" class="form-control invalid untouched pristine" placeholder="请输入公告详情链接">
        </div>
        <div class="form-group detail-content" v-else-if="useDuibaPageValue == 2">
          <label class="control-label">公告详情内容：</label>
          <div class="richeditor">
            <div class="richeditor-toolbar" style="" id="detail-toolbar">
              <a data-wysihtml5-command="formatBlock" class="iconfont" data-wysihtml5-command-value="h4" href="javascript:;" unselectable="on">H1</a>
              <a data-wysihtml5-command="formatBlock" class="iconfont" data-wysihtml5-command-value="p" href="javascript:;" unselectable="on">A</a>
            </div>
            <textarea id="detail-editor" placeholder="请输入该公告的详细内容" style="display: none;"></textarea>
            <input type="hidden" name="_wysihtml5_mode" value="1">
            <iframe class="wysihtml5-sandbox" security="restricted" allowtransparency="true" frameborder="0" width="0" height="0" marginwidth="0" marginheight="0" style="background-color: rgb(255, 255, 255); border-collapse: separate; border-color: rgb(0, 0, 0); border-style: none; border-width: 0px; clear: none; display: inline-block; float: none; margin: 0px; outline: rgb(0, 0, 0) none 0px; outline-offset: 0px; padding: 10px 15px; position: static; top: auto; left: auto; right: auto; bottom: auto; z-index: auto; vertical-align: baseline; text-align: start; box-sizing: border-box; box-shadow: none; border-radius: 0px; width: 100%; height: 160px;">
            </iframe>
          </div>
        </div>
      </div>
    </div>
    <div class="form-group mt20">
      <a href="javascript:void(0)" class="btn btn-green btn-lg">保存配置</a>
    </div>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        isShowTip: true,
        value: false,
        hasDetailPageValue: 0,
        useDuibaPageValue: 1
      }
    },
    methods: {
      hideTip (param) {
        this[param] = false
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../../style/configure";

</style>
