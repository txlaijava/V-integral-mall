<template>
  <div role="dialog" class="addapp-modal modal fade in">
    <div class="modal-dialog" role="document" style="width: 700px;">
      <div class="modal-content">
        <div class="modal-header">
          <i class="iconfont close" @click="closeModal()">&#xe695;</i>
          <h4 class="modal-title"></h4>
        </div>
        <div class="modal-body">
          <div class="row-items">
            <li>
              <div class="img1"></div>
              <h1>积分商城</h1>
              <p class="desc">完善的积分商城管理系统，让你的积分运营更佳轻松</p>
              <a href="javascript:void(0)" @click="choose('addapply')" class="btn btn-green">选择</a>
            </li>
            <li>
              <div class="img2"></div>
              <h1>活动工具</h1>
              <p class="desc">海量的活动工具模板，让你的活动运营更佳出彩</p>
              <a class="btn btn-green" href="javascript:void(0);">选择</a></li>
          </div>
        </div>
        <div class="modal-footer"></div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        data (){
            return {}
        },
        methods: {
            closeModal (){
                this.$emit('close-modal',false);
            },
            choose (url_name){
                this.closeModal();
                this.$router.push(url_name);
            }
        }

    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  @import "../../style/interface.css";
  .addapp-modal{
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      justify-content: center;
  }
  .modal-content .close {
    position: absolute;
    right: 10px;
    top: 8px;
    cursor: pointer;
    font-size: 18px;
    color: #d8d8d8;
    font-weight: 900;
  }
  .modal-content .close:hover {
    color: #444;
  }
  .addapp-modal .img1 {
    background-image: url(../../../static/addapp_mall.png);
  }
  .addapp-modal .img2 {
    background-image: url(../../../static/addapp_tool.png);
  }
</style>
