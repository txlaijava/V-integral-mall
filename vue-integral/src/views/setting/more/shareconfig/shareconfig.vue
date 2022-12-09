<template>
  <div class="share-config">
    <v-tip :status="true" v-show="isShowTip" @on-close="hideTip('isShowTip')">
      <h3>兑吧提供分享页面的功能支持</h3>
      <p>
        分享功能需要跟开发者的H5分享接口打通，兑吧支持在商品页面、活动页面上输出定制的分享信息。详情请查看
        <a href="//docs.duiba.com.cn/tech_doc_book/optional/share.html" target="_blank">在线文档</a>
      </p>
    </v-tip>
    <div class="form-groups-chunk">
      <div class="form-group">
        <label class="control-label">开启分享支持：</label>
        <v-switch v-model="value"></v-switch>
      </div>
    </div>
    <div class="form-groups-chunk">
      <div class="form-group">
        <label class="control-label">页面logo：</label>
        <div class="image-upload-container cf w-218-h-60">
          <div class="img fl">
            <p>
              <img alt="">
            </p>
          </div>
          <div class="upload-area">
            <div class="upload-cell">
              <p>
                分享页面的logo，可参考兑吧提供的范例尺寸为218px*60px图片大小不可大于1M
              </p>
              <div class="upload-btn">
                <div class="file-upload file-uploads file-uploads-html4">
                  <input type="file" name="file" id="file" accept="image/jpg,image/jpeg,image/png">
                </div>
                <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
              </div>
            </div>
          </div>
          <p class="invalid">
          </p>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label">按钮文案：</label>
        <input type="text" class="form-control invalid untouched pristine" placeholder="分享页面的按钮文案">
      </div>
      <div class="form-group">
        <label class="control-label">安卓下载链接：</label>
        <input type="text" class="form-control invalid untouched pristine" placeholder="http://">
      </div>
      <div class="form-group">
        <label class="control-label">ios下载链接：</label>
        <input type="text" class="form-control invalid untouched pristine" placeholder="http://">
      </div>
      <div class="form-group">
        <label class="control-label">打开应用链接：</label>
        <input type="text" class="form-control" placeholder="可以直接唤醒应用的链接协议，可不填">
        <p class="form-group-tip">
          如果你的应用支持网页唤醒，可在这里输入链接，例如微信的“weixin：//”
        </p>
      </div>
    </div>
    <div class="form-group mt20">
      <a href="javascript:void(0)" class="btn btn-green btn-lg">保存并预览</a>
    </div>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        isShowTip: true,
        value: false
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
