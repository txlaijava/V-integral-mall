<template>
  <div role="dialog" class="modal fade in" :class="className" v-if="isShow">
    <div class="modal-dialog" role="document" ref="dialog" id="dialog" :style="{width:width,top:top,left:left}">
      <transition name="drop">
        <div class="modal-content" v-if="isShow">
          <div class="modal-header">
            <i class="iconfont icon-close close" @click="closeMyself"></i>
            <h4 class="modal-title"> {{modalTitle}} </h4>
          </div>
          <slot>empty</slot>
          <div class="modal-footer">
            <template v-if="showFooter">
              <a class="btn btn-green btn-pd-lg mr15" @click="confirmMyself">确定</a>
              <a class="btn btn-default btn-pd-lg" @click="closeMyself">取消</a>
            </template>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
  export default{
    props: {
      isShow: {
        type: Boolean,
        default: false
      },
      className: {
        type: String,
        default: ''
      },
      showFooter: {
        type: Boolean,
        default: ''
      },
      modalTitle: {
        type: String,
        default: ''
      },
      width: {
        type: String,
        default: '238px'
      }
    },
    data () {
      return {
        left: '',
        top: '',
        disable:false,
      }
    },
    methods: {
//      loadClient () {
//        var that = this;
//        setTimeout(function () {
//          that.left = (document.documentElement.clientWidth - that.$refs.dialog.clientWidth) / 2 + 'px';
//          that.top = (document.documentElement.clientHeight - that.$refs.dialog.clientHeight) / 2 + 'px';
//        }, 1)
//      },
      closeMyself () {
        this.$emit('on-close')
      },
      confirmMyself(){
          var that = this;
          if(this.disable){
              return
          }
          that.disable = true;
          setTimeout(function(){
            that.disable = false
          },10000)
          this.$emit("on-confirm");
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .modal{
    .modal-dialog{
      position: absolute;
      left: 50%;
      top: 50%;
      -webkit-animation: .5s fadeInUp forwards;
      animation: .5s fadeInUp forwards;
    }
  }

  .stock-modal-form {
    .form-group {
      padding-left: 200px;
      .control-label {
        width: 200px;
      }
    }
  }
</style>
