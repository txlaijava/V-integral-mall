<template>
  <div class="page-content interface">
    <v-tip :status="true" className="ft14 mb20" v-show="isShowTip" @on-close="hideTip('isShowTip')">
      您的开发人员可以关注这里的异常订单情况，监控自己的接口状态是否健康。
    </v-tip>
    <div class="filter bottomline">
      <label class="padr20 ft14">选择日期：</label>
      <v-date-picker placeholder=""></v-date-picker>
      <label class="padlr20 ft14">异常原因：</label>
      <v-selection :selections="abnormalReason" style="width: 250px"></v-selection>
      <a href="javascript:void(0)" class="btn btn-default btn-lg ml25">筛选</a>
      <a href="javascript:void(0)" class="setmessage" @click="showDialog('isShowWarning')">
        <i class="iconfont icon-add"></i>设置短信提醒</a>
    </div>
    <v-noresult></v-noresult>
    <v-dialog className="messagesmodal" width="500px" showFooter="true" modalTitle="设置异常提醒" ref="dialog"
              :is-show="isShowWarning"
              @on-close="hideDialog('isShowWarning')">
      <div class="modal-body">
        <div class="bottomline"> 当天异常订单数到达10、50、100、500、1000条时，我们均会向您发送短信提醒。建议留下技术的手机号码，已方便实时监控。</div>
        <div class="form-group bottomline">
          <label class="control-label">联系电话：</label>
          <input type="text" class="form-control valid untouched pristine">
          <p class="input-desc"> 可设置最多3个联系电话，以“,”做间隔 </p>
        </div>
      </div>
    </v-dialog>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        isShowWarning: false,
        isShowTip: true,
        abnormalReason: [{
          label: '全选',
          value: 0
        }, {
          label: '扣积分失败，其它',
          value: 1
        }, {
          label: '扣积分失败，开发者订单号重复',
          value: 2
        }, {
          label: '扣积分失败，json解析失败',
          value: 3
        }]
      }
    },
    methods: {
      showDialog (param) {
        this[param] = true
        this.$refs.dialog.loadClient();
      },
      hideDialog (param) {
        this[param] = false
      },
      hideTip (param) {
        this[param] = false
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../../style/configure";

  .messagesmodal .bottomline {
    padding-bottom: 20px
  }

  .messagesmodal .form-group {
    padding-right: 0;
    margin-bottom: 0;
    margin-top: 20px
  }

  .messagesmodal .form-group .control-label {
    width: 100px;
    font-weight: 400
  }

  .messagesmodal .form-group .input-desc {
    color: #888
  }
</style>
