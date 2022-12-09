<template>
  <div class="vip-limit">
    <v-tip :status="false">
      <h3>会员等级功能可以实现你的不同会员兑换不同的兑换项</h3>
      <p>要开启此功能，你需要：</p>
      <p>1、配置你的会员等级和会员标识；</p>
      <p>2、在用户登录url中，通过参数告诉兑吧当前登录用户的会员等级。具体请
        <a href="" target="_blank">查看文档</a>；
      </p>
      <h3>注意事项：</h3>
      <p>如果您删除相关会员等级，当用户再次以删除等级id访问积分商城时，将以默认等级会员处理。</p>
    </v-tip>
    <div class="form-groups-chunk">
      <div class="form-group">
        <label class="control-label">开启会员等级：</label>
        <v-switch v-model="value"></v-switch>
      </div>
    </div>
    <div class="vip-limit-control" v-if="value">
      <div class="form-groups-chunk">
        <div class="form-group"><label class="control-label">会员等级配置：</label>
          <div class="vip-level-list">
            <table>
              <thead>
              <tr class="tr-header">
                <th>会员名称</th>
                <th>等级ID</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
              <tr class="tr-items">
                <td>
                  <input type="text" class="form-control untouched invalid dirty modified form-control-dirty" placeholder="该等级会员名称">
                </td>
                <td>0（数字越大，会员层级提升）</td>
                <td><!--<span><a class="iconfont icon-delete "></a></span>--></td>
              </tr>
              </tbody>
            </table>
          </div>
          <a href="javascript:void(0)" class="add-vip-limit" @click="addVipLimit"> <i class="iconfont icon-add"></i> 增加会员等级 </a>
          <p class="invalid fr mt20 ft14" style="display: none;">请填完所有的会员名称</p>
          <p class="invalid fr mt20 ft14" style="display: none;">至少需要两个会员等级</p></div>
        <div class="form-group"><label class="control-label">无法兑换处理：</label>
          <div class="radios">
            <label> <input type="radio" name="doMethod" value="0" v-model="doMethodValue"> 隐藏当前用户等级无法兑换的内容 </label>
            <label><input type="radio" name="doMethod" value="1" v-model="doMethodValue"> 告知用户如何获得兑换资格 </label>
          </div>
        </div>
      </div>
      <div class="form-groups-chunk" v-if="doMethodValue==1">
        <div class="form-group">
          <label class="control-label">规则页面指向：</label>
          <div class="radios">
            <label><input type="radio" name="rulePoint" value="1" v-model="rulePointValue"> 我自己有规则说明页面 </label>
            <label><input type="radio" name="rulePoint" value="0" v-model="rulePointValue"> 使用兑吧的页面 </label>
          </div>
        </div>
        <div class="form-group" v-if="rulePointValue == 1">
          <label class="control-label">页面规则链接：</label>
          <input type="text" class="form-control" placeholder="http://">
          <p class="form-group-tip">此链接告诉客户端如何跳转到你自己的规则页面，需要客户端做相应的拦截处理</p>
        </div>
        <div class="form-group" v-else-if="rulePointValue==0">
          <label class="control-label">页面规则内容：</label>
          <div class="richeditor">
            <div class="richeditor-toolbar" style="" id="rule-toolbar">
              <a data-wysihtml5-command="formatBlock" class="iconfont" data-wysihtml5-command-value="h4" href="javascript:;" unselectable="on">H1</a>
              <a data-wysihtml5-command="formatBlock" class="iconfont" data-wysihtml5-command-value="p" href="javascript:;" unselectable="on">A</a>
            </div>
            <textarea id="rule-editor" placeholder="这里请输入等级规则" style="display: none;"></textarea><input type="hidden" name="_wysihtml5_mode" value="1">
            <iframe class="wysihtml5-sandbox" security="restricted" allowtransparency="true" frameborder="0" width="0" height="0" marginwidth="0" marginheight="0" style="background-color: rgb(255, 255, 255); border-collapse: separate; border-color: rgb(0, 0, 0); border-style: none; border-width: 0px; clear: none; display: inline-block; float: none; margin: 0px; outline: rgb(0, 0, 0) none 0px; outline-offset: 0px; padding: 10px 15px; position: static; top: auto; left: auto; right: auto; bottom: auto; z-index: auto; vertical-align: baseline; text-align: start; box-sizing: border-box; box-shadow: none; border-radius: 0px; width: 838px; height: 160px;">
            </iframe>
          </div>
          <a href="javascript:void(0)" class="preview-rule"><i class="iconfont">&#xe772;</i> 预览 </a>

        </div>
      </div>
    </div>
    <div class="form-group mt20"><a href="javascript:void(0)" class="btn btn-green btn-lg">保存配置</a></div>
  </div>
</template>

<script>
  export default{
    data() {
      return {
        value: false,
        doMethodValue: 0,
        rulePointValue: 0
      }
    },
    methods: {
      addVipLimit () {
          this.vipLimitItems.push({

          })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../../style/configure";

  .vip-limit {
    max-width: 950px
  }

  .vip-limit .tip h3 {
    color: #177c77;
    font-size: 14px;
    margin: 5px 0
  }

  .vip-limit .tip h3:first-child {
    margin-top: 0
  }

  .vip-limit .tip p {
    line-height: 1.5
  }

  .vip-limit .richeditor textarea {
    height: 160px
  }

  .vip-limit .vip-level-list {
    max-height: 560px;
    overflow-y: scroll
  }

  .vip-limit .vip-level-list .form-control {
    width: 80%
  }

  .vip-limit .preview-rule {
    color: #444;
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 14px
  }

  .vip-limit .preview-rule .iconfont {
    color: #bbb
  }

  .vip-limit .add-vip-limit {
    color: #444;
    font-size: 14px;
    display: inline-block;
    margin-top: 20px
  }

  .vip-limit .add-vip-limit .iconfont {
    color: #29b6b0
  }

  .vip-limit .level-select {
    width: 195px;
    border: 1px solid #e9e9e9;
    border-radius: 5px;
    line-height: 32px;
    height: 32px;
    outline: 0
  }

  .vip-limit .level-select.select-conflict {
    border-color: #f66b4e
  }
</style>
