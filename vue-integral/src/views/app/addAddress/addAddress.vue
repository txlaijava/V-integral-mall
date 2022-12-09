<template>
  <div class="container">
    <div class="cells">
      <div class="cell">
        <div class="cell__hd">收货人</div>
        <div class="cell__bd">
          <input type="text" v-model="addrObj.trueName" placeholder="请输入收货人姓名">
        </div>
      </div>
      <div class="cell">
        <div class="cell__hd">手机号码</div>
        <div class="cell__bd">
          <input type="tel" name="telephone" v-model="addrObj.mobile" pattern="[0-9]" placeholder="收货人的电话，方便联系">
        </div>
      </div>
      <div class="cell cell_access">
        <div class="cell__hd">收货地址</div>
        <div class="cell__bd">
          <p :class="{ 't-333': isAddress  }" @click="showCitypick" v-text="addrObj.area"></p>
        </div>
        <div class="cell__ft"></div>
      </div>
      <div class="cell">
        <div class="cell__hd">详细地址</div>
        <div class="cell__bd">
          <textarea v-model="addrObj.areaInfo" placeholder="请输入街道、门牌等详细地址信息"></textarea>
        </div>
      </div>
    </div>
    <div class="address__btn-wrap flex">
      <div class="app_btn btn--default flex1" @click="goBack">取消</div>
      <div class="app_btn btn--primary flex1" @click="handleConfirm">保存地址</div>
    </div>
    <app-citypicker :isShowCitypick="showStatus" :cityPicker="addrObj.cityPicker" @onSelected="selected" @on-close="hideCitypick"></app-citypicker>
  </div>
</template>

<script>
  import citypicker from '@/components/app/citypicker';
  import {getAddress,saveAddress,updateAddress} from '@/service/getAppData';
  import * as SYSTEM from '@/api/system';
  import {openNewWindow} from '@/api/appApi';

  export default{
    data() {
      return {
        isAddress: false,
        showStatus: false,
        goodsId:this.$route.params.goodsId,
        orderStateType:this.$route.params.orderStateType,
        addrObj: {
            id:this.$route.params.addressId,
            mobile: "",
            area: "请选择收货地址",
            areaInfo: "",
            trueName: "",
            cityPicker:[],
        }
      }
    },
    methods: {
      showCitypick() {
          this.showStatus = true
      },
      selected(data){
          this.showStatus = false;
          this.isAddress = true;
          this.addrObj.area = data.province.value + ' ' + data.city.value + ' ' + data.area.value;
      },
      hideCitypick() {
        this.showStatus = false;
      },
      handleConfirm(){
        if (this.formValidate()) {

            if(this.addrObj.id){
                updateAddress(this.addrObj).then(res =>{
                    const re = res.data.data;
                    if (SYSTEM.SUCCSSS == re.success) {
                        this.goBack();
                    }
                })
            }else {
                saveAddress(this.addrObj).then(res => {
                    const re = res.data.data;
                    if (SYSTEM.SUCCSSS == re.success) {
                        this.goBack();
                    }
                });
            }
        }
      },
      formValidate (){
          var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
          if (!this.addrObj.trueName || "" == this.addrObj.trueName) {
              this.$Message.error("请输入姓名");
              return false;
          }else if (!this.addrObj.mobile || "" == this.addrObj.mobile || !myreg.test(this.addrObj.mobile)){
              this.$Message.error("请输入正确的手机号码")
              return false;
          }
          return true;
      },
      goBack (){
          if(this.goodsId!=undefined && this.goodsId!=""){
              this.$router.push({path: "/app/index/itemDetail/"+this.goodsId, query: {orderStateType: this.orderStateType}});
          }else{
              this.$router.go(-1);
          }

      },
      getAddrObj (){
          getAddress().then(res =>{
              const re = res.data.data;
              if(SYSTEM.SUCCSSS == re.success){
                  this.addrObj = re.data;
                  this.addrObj.cityPicker = re.data.area.split(' ');
                  this.isAddress = true;
              }
          })
      },

    },
    created () {
        if(this.addrObj.id){
            this.getAddrObj();
        }
    },
    components: {
      'app-citypicker': citypicker
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .container {
    .cells {
      margin: 0.3rem 0;
      .cell {
        &:last-child {
          align-items: flex-start;
        }
        .cell__hd {
          width: 1.3rem;
          margin-right: 0.2rem;
        }
        .cell__bd {
          p {
            font-size: 0.3rem;
            color: #ccc;
          }
          input {
            font-size: 0.3rem;
          }
          textarea {
            font-size: 0.3rem;
            height: 1rem;
          }
        }
        .cell__ft {
          .iconfont {
            font-size: 0.38rem;
            color: #ccc;
          }
        }
      }
    }
    .address__btn-wrap {
      margin-top: 0.3rem;
      padding: 0.3rem;
      .app_btn {
        height: 0.9rem;
        &:first-child {
          margin-right: 0.2rem;
        }
      }
    }
  }
</style>
