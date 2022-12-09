<template>
  <div class="main">
    <div class="page-title">
      <h2>添加应用至</h2>
      <ul class="nav-tabs">
        <li>
          <a href="#!/addApp/mall" class="active">积分商城</a></li>
        <li>
          <a href="#!/addApp/activitytool">活动工具</a></li>
      </ul>
    </div>
    <div class="page-content">
      <div class="form-wrap bottomline mb20">
        <div class="form-group cf">
          <label class="control-label">应用图标：</label>
          <div class="applogo fl">
            <img class="img"  :src="logoIcon" alt="应用图标">
            <div class="file-upload file-uploads file-uploads-html4">
              <input type="file" name="file" id="file" accept="image/png"  @change="getFile($event)">
            </div>
          </div>
          <p class="appdesc fl">请上传应用图标，规格为100*100像素png图片</p>
          <p class="invalid error-tip" v-if="error.uploadCell.error"> <i class="iconfont">&#xe629;</i> {{error.uploadCell.info}} </p>
        </div>
        <div class="form-group">
          <label class="control-label">应用名称：</label>
          <input type="text" class="form-control invalid pristine touched" v-model="acApply.applyName" placeholder="请输入您的应用名称，建议四个字以内">
          <p class="invalid" v-if="error.nameError.error"><i class="iconfont">&#xe629;</i> {{error.nameError.info}} </p>
        </div>
        <div class="form-group">
          <label class="control-label">积分汇率：</label>
          <p class="input-group">
            <input type="text" class="form-control invalid pristine touched" v-model="acApply.integralExchangeRate" placeholder="您的应用内多少积分等于1元人民币">
            <span class="unit">积分=1元</span>
          </p>
          <p class="invalid" v-if="error.rateError.error"><i class="iconfont">&#xe629;</i> {{error.rateError.info}} </p>
        </div>
        <div class="form-group">
          <label class="control-label">积分名称：</label>
          <input type="text" class="form-control invalid untouched pristine" v-model="acApply.integralUnit" placeholder="您的应用内显示的积分单位名称，如：积分、金币、元宝">
          <p class="invalid" v-if="error.unitError.error"><i class="iconfont">&#xe629;</i> {{error.unitError.info}} </p>
        </div>
      </div>
      <div class="form-group">
        <a class="btn btn-green btn-lg" href="javascript:;" @click="createApply()">创建并进入我的应用</a>
      </div>
    </div>
  </div>
</template>

<script>
    import {uploadImage,saveAcApply} from '@/service/getData';
    import * as SYSTEM from '@/api/system';
    import config from '@/config/config';
    export default {
        data (){
            return {
                imageWebServer:config.ossImgPath,
                acApply:{icon:'',applyName:'',integralExchangeRate:'',integralUnit:''},
                error:{nameError:{error:false,info:''},rateError:{error:false,info:''},unitError:{error:false,info:''},uploadCell:{error:false,info:''}},
                logoIcon:'../../../static/default_appicon.png',
            }
        },
        methods: {
            createApply (){
                this.validation();
                if(!this.error.nameError.error && !this.error.rateError.error && !this.error.unitError.error){
                    saveAcApply(this.acApply).then(res =>{
                        const re = res.data.data;
                        if(SYSTEM.SUCCSSS == re.success){
                            location.reload();
                        }
                    })
                }
            },
            validation (){
                if(!this.acApply.applyName){
                    this.error.nameError = {error:true,info:'请填写应用名称'};
                }else{
                    this.error.nameError = {error:false,info:''};
                }
                if(!this.acApply.integralExchangeRate || this.acApply.integralExchangeRate <= 0){
                    this.error.rateError = {error:true,info:'请输入不小于0的金额'};
                }else{
                    this.error.rateError = {error:false,info:''};
                }

                if(!this.acApply.integralUnit){
                    this.error.unitError = {error:true,info:'不能为空格'};
                }else{
                    this.error.unitError = {error:false,info:''};
                }
            },
            getFile: function (event) {
                const file = event.target.files[0];
                let formData = new FormData();
                formData.append("file", file);
                formData.append("saveFilePathName", "upload/goods");
                var WHList = [{"width":100,"height":100}];
                formData.append("WHList", JSON.stringify(WHList));
                uploadImage(formData).then(res =>{
                    let re = res.data.data;
                    if(re.status==1001){
                        this.acApply.icon = re.id;
                        this.logoIcon = this.imageWebServer + re.address;
                        this.error.uploadCell = {error: false, info: ''};
                    }else if(re.status == 1002){
                        this.error.uploadCell = {error: true, info: '上传的图片尺寸不符合要求'};
                    }else{
                        this.error.uploadCell = {error: true, info: '上传图片失败'};
                    }
              });
            },
        },
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  .main {
    padding: 0 36px;
    position: relative;
    min-width: 960px;
    .applogo {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      background: #d4d4d4;
      margin-top: -10px;
      img {
        margin-top: 14px;
        margin-left: 10px;
      }
      .img {
        width: 50px;
        height: 50px;
        margin: 0;
        border-radius: 50%;
      }
    }
    .file-uploads {
      width: 50px;
      height: 50px;
      top: -53px;
    }
    .appdesc {
      font-size: 12px;
      margin-top: 10px;
      margin-left: 10px;
      color: #888;
    }
  }
  .error-tip {
    clear: both;
    margin-left: 59px!important;
  }
</style>
