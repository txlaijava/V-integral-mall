<template>
  <div class="main">
    <div class="page-title"><h2> 应用信息 </h2></div>
    <div class="page-content appinfo">
      <div class="form-group">
        <label class="control-label">AppKey：</label>
        <p class="appkey">{{applyData.appKey}}</p>
      </div>
      <div class="form-group">
        <label class="control-label">App Secret：</label>
          <Button type="warning" @click.once="sendEmail()">点击发送</Button>
      </div>
      <div class="form-group cf">
        <label class="control-label">应用图标：</label>
        <div class="applogo fl">
          <img class="img" alt="应用图标" :src="imageWebServer + applyData.applyIconAcc.path + applyData.applyIconAcc.name" v-if="applyData.applyIconAcc">
          <img class="img" alt="应用图标" :src="imageWebServer + applyIconImg" v-else-if="applyIconId">
          <img class="img" alt="应用图标" src="../../../static/default_appicon.png" v-else>
          <div class="file-upload file-uploads file-uploads-html4">
            <input ref="pathClear" type="file" name="file" id="file" accept="image/png" @change="getFile($event)">
          </div>
        </div>
        <p class="appdesc fl">
          <span>注</span>应用图标为选填项，规格为100*100像素png图片
        </p>
        <p class="invalid error-tip" v-if="fault.uploadCell.error"> <i class="iconfont">&#xe629;</i> {{fault.uploadCell.errorInfo}} </p>
      </div>
      <div class="form-group">
        <label class="control-label">积分汇率：</label>
        <p class="input-group">
          <input type="text" class="form-control form-control-dirty" :class="{'invalid':fault.rate.error}"
                 placeholder="您的应用内多少积分等于1元人民币" v-model="applyData.integralExchangeRate">
          <span class="unit">积分=1元</span>
        </p>
        <p class="invalid" v-if="fault.rate.error"> <i class="iconfont">&#xe629;</i> {{fault.rate.errorInfo}} </p>
      </div>
      <div class="form-group">
        <label class="control-label">积分单位：</label>
        <input type="text" class="form-control form-control-dirty" :class="{'invalid':fault.unit.error}"
               placeholder="您的应用内显示的积分单位名称，如：积分、金币、元宝" v-model="applyData.integralUnit">
        <p class="invalid" v-if="fault.unit.error"> <i class="iconfont">&#xe629;</i> {{fault.unit.errorInfo}} </p>
      </div>
      <div class="form-group">
        <label class="control-label">应用名称：</label>
        <input type="text" class="form-control form-control-dirty" placeholder="请输入你的应用名称" v-model="applyData.applyName">
        <p class="invalid" v-if="fault.name.error"> <i class="iconfont">&#xe629;</i> {{fault.name.errorInfo}} </p>
      </div>
      <div class="form-group">
        <label class="control-label">积分明细链接：</label>
        <input type="text" class="form-control form-control-dirty" placeholder="配置链接后，商城首页积分值处可点击跳转至此链接页面。不填则无跳转。" v-model="applyData.integralDetailedLink">
      </div>
      <div class="form-group padr80">
        <label class="control-label">获取积分文案：</label>
        <input type="text" class="form-control  pristine" placeholder="用户积分不足的时，详情兑换按钮文案。不填则为默认的“去赚取更多积分”。限10字以内。"  v-model="applyData.integralInsufficientTxt">
      </div>
      <div class="form-group padr80">
        <label class="control-label">获取积分链接：</label>
        <input type="text" class="form-control" placeholder="用户积分不足时，可以跳转至此行链接页面。不填则不配置" v-model="applyData.integralInsufficientLink">
        <div class="btn btn-default btn-lg btn-right disabled">预览</div>
      </div>
      <div class="form-group">
        <label class="control-label">商城标题：</label>
        <input type="text" class="form-control valid untouched dirty modified form-control-dirty" placeholder="请输入你的商城标题" v-model="applyData.shoppingName">
      </div>
      <div class="form-group">
        <label class="control-label">商城自提地点：</label>
        <input type="text" class="form-control valid untouched dirty modified form-control-dirty" placeholder="请输入你的自提地点" v-model="applyData.takeSelfAddr">
      </div>
      <div class="form-group">
        <div class="btn btn-green btn-lg submit-btn" @click="upApply()" :disabled="!isSub" >保存配置</div>
      </div>
    </div>
    <transition name="loading">
      <v-loading v-show="loading"></v-loading>
    </transition>
  </div>
</template>

<script>
  import {updateAcApply,getAcApply,uploadImage,getSendMail} from '@/service/getData';
  import * as SYSTEM from '@/api/system';
  import config from '@/config/config';

  export default{
    data(){
      return {
          imageWebServer:config.ossImgPath,
          applyData:{},
          acApplyId:'',
          applyIconId:'',
          applyIconImg:'',
          fault:{
              rate:{error:false,success:true,errorInfo:''},
              unit:{error:false,success:true,errorInfo:''},
              name:{error:false,success:true,errorInfo:''},
              uploadCell:{error:false,success:true,errorInfo:''}
          },
          isSub:true,
          file:'',
          loading:false,
      }
    },
    methods: {
        // 获取应用信息
        acApplyData (){
            this.loading = true;
            getAcApply().then(res =>{
                const re = res.data.data.data;
                this.acApplyId = re.id;
                this.applyData = re;
                this.loading = false;
            })
        },
        // 验证输入数据
        extend(obj,validator) {
            var reg = validator.reg;
            if(validator.rules.indexOf('required') >= 0 && (typeof(validator.value) == "undefined" || validator.value.toString().replace(/\s+/g,"").length <= 0)){
                [obj.error,obj.success,obj.errorInfo] = [true,false,validator.getMessage.required.msg];
                this.isSub = false;
                return obj;
            }

            if(validator.max != void 0 && validator.value.toString().replace(/\s+/g,"").length > validator.max){
                [obj.error,obj.success,obj.errorInfo] = [true,false,validator.getMessage.max.msg];
                this.isSub = false;
                return obj;
            }

            if((validator.reg != void 0) && '' != validator.reg && !reg.test(validator.value)){
                [obj.error,obj.success,obj.errorInfo] = [true,false,validator.getMessage.reg.msg];
                this.isSub = false;
                return obj;
            }

            [obj.error,obj.success,obj.errorInfo] = [false,true,''];
            this.isSub = true;
            return obj;
        },
        // 提交修改
        upApply () {
            if(!this.isSub || !this.fault.uploadCell.success){
                return;
            }
            this.applyData.editorType = 3;
            this.applyData.applyIconId = this.applyIconId;
            updateAcApply(this.acApplyId,this.applyData).then(res =>{
                if(SYSTEM.SUCCSSS == res.data.data.success){
                    this.$Message.success("修改成功");
                    location.reload();
                }
            })
        },
        sendEmail (){
            getSendMail().then(res =>{
                if(res.data.data.success){
                    this.$Message.success("发送成功！");
                }else{
                    this.$Message.error(res.data.data.message);
                }
            })
        },
        // 图片上传方法
        getFile: function (event) {
            this.file = null;
            this.file = event.target.files[0];
            let formData = new FormData();
            formData.append("file", this.file);
            formData.append("saveFilePathName", "upload/goods");
            var WHList = [{"width":100,"height":100}];
            formData.append("WHList", JSON.stringify(WHList));
            uploadImage(formData).then(res =>{
                let re = res.data.data;
                if(re.status==1001){
                    this.fault.uploadCell = {error: false,success:true, errorInfo: ''};
                    this.applyIconImg = re.address;
                    this.applyIconId = re.id;
                    this.$refs.pathClear.value = '';
                }else if(re.status == 1002){
                    this.fault.uploadCell = {error: true,success:false, errorInfo: '上传的图片尺寸不符合要求'};
                }else{
                    this.fault.uploadCell = {error: true,success:false, errorInfo: '上传图片失败'};
                }
            });
        },
    },
    mounted () {
        this.acApplyData();
    },
    watch:{
        'applyData.integralExchangeRate' (val, oldVal) {
            let validator = {
              reg: /^[1-9]\d*$/ ,
              rules: 'required',max: 6, value: val,
              getMessage: {
                  required:{msg: '请填写积分汇率'},
                  reg: {msg: '请输入正确整数金额'},
                  max: {msg: '最大6位数'}
              }
            };
            this.extend(this.fault.rate,validator,this.isSub);
        },
        'applyData.integralUnit' (val, oldVal) {
            let validator = {
                rules: 'required',max: 6, value: val,
                getMessage: {
                    required:{msg: '请填写积分单位'},
                    max: {msg: '最大6个字'}
                }
            };
            this.extend(this.fault.unit,validator,this.isSub);
        },
        'applyData.applyName' (val, oldVal) {
            let validator = {
                rules: 'required',max: 15, value: val,
                getMessage: {
                    required:{msg: '请填写应用名称'},
                    max: {msg: '最大为15字'}
                }
            };
            this.extend(this.fault.name,validator,this.isSub);
        }
    }
  }

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/configure";
</style>
