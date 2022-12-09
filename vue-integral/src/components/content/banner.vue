<template>

  <div>
    <div class="editor-wrapper banner ">
      <header>编辑Banner区
        <a href="javascript:;" v-if="selectBanner.length > 0" class="btn btn-green btn-lg btn-add" @click="picker()">添加Banner</a>
      </header>
      <div class="editor-content">
        <p class="ft-desc top-desc" v-show="selectBanner.length > 0">左边预览区仅显示第一张Banner</p>
        <div class="editor-full" v-if="selectBanner.length > 0">
          <div class="editor-list-wrapper">
            <div class="s-item" v-for="(item,index) in selectBanner">
              <div class="s-con">
                <div class="s-img" >
                  <img :src="imageWebServer + item.itemDataImg" lazy="loaded">
                  <span class="s-index">{{(index + 1)}}</span>
                </div>
                <div class="s-options">
                  <p>{{item.itemDataName}}</p>
                  <span>
                  <a href="javascript:void(0)" @click="showEditorBanner(index)" class="iconfont">&#xe753;</a>
                </span>
                  <div class="tooltip top" role="tooltip" style="top: 23px; left: 243px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">编辑</div>
                  </div>
                  <span>
                  <a href="javascript:void(0)" class="iconfont delete" @click="delBanner(index,item.id)">&#xe752;</a>
                </span>
                  <div class="tooltip top delete" role="tooltip" style="top: 23px; left: 279px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">删除</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="editor-empty" v-if="selectBanner.length < 1">
          <i class="iconfont">&#xe656;</i>
          <p>Banner区内容为空时，首页会隐藏此区域。</p>
          <a href="javascript:;" class="btn btn-green btn-lg" @click="picker()">
            <i class="iconfont">&#xe6e8;</i>添加Banner</a>
        </div>
        <div class="s-item s-empty" v-show="selectBanner.length < 1"></div>
      </div>
      <div class="editor-bar" @click="picker()">
        <i class="iconfont">&#xe647;</i>
      </div>
    </div>

    <!-- 广告 -->
    <div class="picker-wrapper banner">
      <header>挑选或上传Banner图
        <ul class="picker-menu">
          <li :class="{'active':pickerMenusType == 'activity'}" @click="pickerMenusTab('activity')">活动</li>
          <li :class="{'active':pickerMenusType == 'goods'}" @click="pickerMenusTab('goods')">商品</li>
          <li :class="{'active':pickerMenusType == 'upload'}" @click="pickerMenusTab('upload')">上传</li>
        </ul>
      </header>
      <div class="picker-content" style="width: 812px;">

        <!-- 图片上传 -->
        <div class="banner-upload" v-if="pickerMenusType == 'upload'">
          <p class="ft-desc desc-title">首页仅展示前6个Banner</p>
          <form class="upload-form">
            <div class="form-group">
              <label class="control-label">广告标题:</label>
              <input type="text" class="form-control pristine" :class="{'invalid':inputError.title.isError}"
                     v-model="upload.bannerTitle" maxlength="10" placeholder="请输入标题，建议8个字以内">
              <p class="invalid" v-if="inputError.title.isError"> <i class="iconfont">&#xe629;</i> {{inputError.title.txt}} </p>
            </div>
            <div class="form-group">
              <label class="control-label">链接地址:</label>
              <input type="text" class="form-control pristine" :class="{'invalid':inputError.url.isError}"
                     v-model="upload.url"
                     placeholder="请输入链接地址，限制255个字符">
              <p class="invalid" v-if="inputError.url.isError"> <i class="iconfont">&#xe629;</i> {{inputError.url.txt}} </p>
            </div>
            <div class="form-group">
              <label class="control-label">广告图片:</label>
              <div class="image-upload-container cf w-750-h-220">
                <div class="img fl" :class="{'img-uploaded':imageWebServer + upload.bannerImg}">
                  <p>
                    <img alt="" :src="imageWebServer + upload.bannerImg" v-if="upload.bannerImg"></p>
                </div>
                <div class="upload-area">
                  <div class="upload-cell">
                    <p>新版：尺寸为750*220像素的jpg/png图片</p>
                    <div class="upload-btn">
                      <div class="file-upload file-uploads file-uploads-html4">
                        <input ref="pathClear" type="file" name="file" id="file" accept="image/jpg,image/jpeg,image/png" @change="getFile($event,false)">
                      </div>
                      <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                    </div>
                  </div>
                </div>
                <p class="invalid" v-if="inputError.uploadCell.error">{{inputError.uploadCell.errorInfo}}</p>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label">&nbsp;</label>
              <a href="javascript:;" class="btn btn-green" :disabled="!isUpload" @click="addBannerItem(upload.bannerImg,upload.bannerTitle,upload.url)">添加至编辑区</a></div>
          </form>
        </div>

        <!-- 选择商品-->
        <div class="banner-search" v-else>
          <div class="filter-form">
            <div class="filter-input">
              <input type="text" name="" placeholder="请输入关键词">
              <i class="iconfont">&#xe62c;</i></div>
            <span class="ft-desc">首页仅展示前6个Banner</span>
          </div>

          <div class="result-list" v-if="goodsItem.length > 0">
            <div class="result-item" v-for="(item,index) in goodsItem">
              <div class="no-image"> <i class="iconfont">&#xe734;</i> </div>
              <p>{{item.goodsName}}</p>
              <div class="cover" @click="addGoodsBannerItem(item)">
                <span><i class="iconfont">&#xe766;</i></span>
              </div>
            </div>
          </div>

          <div class="picker-empty" v-else>
            <i class="iconfont">&#xe76c;</i>
            <p>呐尼，没有符合条件的商品，换个试试吧~</p>
          </div>
        </div>

      </div>
      <a href="javascript:;" class="close iconfont" @click="picker()">&#xe695;</a>
      <div class="picker-bar"  @click="picker()">
        <i class="iconfont">&#xe647;</i>
      </div>
    </div>

    <!-- 弹出层-->
    <div role="dialog" class="modal fade " :class="{'in':editorModalDialog}" v-show="editorModalDialog">
      <div class="modal-dialog" role="document" style="width: 600px; top: 154px; left: 540px;">
        <div class="modal-content">
          <div class="modal-header">
            <i class="iconfont close" @click="showEditorBanner()">&#xe695;</i>
            <h4 class="modal-title">编辑Banner</h4>
          </div>
          <div class="modal-body modify-banner">
            <div class="form-group" style="margin-top: 15px">
              <label class="control-label">图片:</label>
              <div class="image-upload-container cf w-750-h-220">
                <div class="img fl img-uploaded">
                  <p>
                    <img alt="" :src="imageWebServer + editorModalData.itemDataImg" v-if="editorModalData.itemDataImg">
                  </p>
                </div>
                <div class="upload-area">
                  <div class="upload-cell">
                    <p>新版：尺寸为750*220像素的jpg/png图片</p>
                    <div class="upload-btn">
                      <div class="file-upload file-uploads file-uploads-html4">
                        <input ref="pathClear" type="file" name="file" accept="image/jpg,image/jpeg,image/png" @change="getFile($event,true)">
                      </div>
                      <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                    </div>
                  </div>
                </div>
                <p class="invalid" v-if="editorInputError.uploadCell.error"><i class="iconfont">&#xe629;</i> {{editorInputError.uploadCell.errorInfo}}</p>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label">标题:</label>
              <div>
                <input type="text" class="form-control untouched valid dirty modified form-control-dirty" v-model="editorModalData.itemDataName" maxlength="10" placeholder="请输入标题，建议8个字以内">
              </div>
              <p class="invalid" v-if="editorInputError.title.isError"> <i class="iconfont">&#xe629;</i> {{editorInputError.title.txt}} </p>
            </div>
            <div class="form-group" v-if="'goods' != editorModalData.itemDataType">
              <label class="control-label">链接:</label>
              <div>
                <input type="text" class="form-control valid untouched pristine" v-model="editorModalData.itemDataLike" placeholder="请输入链接">
              </div>
              <p class="invalid" v-if="editorInputError.url.isError"> <i class="iconfont">&#xe629;</i> {{editorInputError.url.txt}} </p>
            </div>
          </div>
          <div class="modal-footer modify-banner">
            <a class="btn btn-default" @click="showEditorBanner()">取消</a>
            <a class="btn btn-green" :disabled="!isEditor" @click="editorBannerSub()">保存</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    import {getGoodsList,getApplyThemeItemData,saveApplyThemeItemData,uploadImage,delApplyThemeItemData} from '@/service/getData';
    import config from '@/config/config';

    export default {
        data (){
            return {
                imageWebServer:config.ossImgPath,
                upload:{bannerImg:'',bannerTitle:'',url:''},
                selectBanner:[],
                goodsItem:[],
                isPicker: false,
                pickerMenusType:'goods',
                isUpload:false,
                inputError:{title:{isError:false,isSuccess:false,txt:''},url:{isError:false,isSuccess:false,txt:''},uploadCell:{error:false,isSuccess:false,errorInfo:''}},
                editorInputError:{title:{isError:false,isSuccess:true,txt:''},url:{isError:false,isSuccess:true,txt:''},uploadCell:{error:false,isSuccess:true,errorInfo:''}},
                _thisEditorItemId:'',
                file:null,
                editorModalDialog:false,
                editorModalData:{id:'',itemDataGoods:null,itemDataType:'',itemDataName:'',itemDataLike:'',itemDataImg:'',itemDataValue:"",sequence:0},
                isEditor:true
            }
        },
        props: ['themeItem','showLoading'],
        methods: {
            picker:function(){
                this.isPicker = !this.isPicker;
                this.$emit('picker', this.isPicker);
            },
            // 菜单切换
            pickerMenusTab:function(v){
                this.pickerMenusType = v;
                if('goods' == v){
                    getGoodsList().then(res =>{
                        const re = res.data.data.data.data;
                        this.goodsItem = re;
                    })
                }
            },
            // 添加楼层数据
            addBannerItem:function(img,title,url){
                if(!this.isUpload){
                    return;
                }
                const banner = {
                    themeItemId:this.themeItem.theme_item_id,itemDataType:this.pickerMenusType,
                    itemDataName:title,itemDataLike:url,itemDataImg:img,itemDataValue:''
                };
                saveApplyThemeItemData(banner).then(res =>{
                    banner.id = res.data.data.data;
                    this.selectBanner.unshift(banner);
                    this.upload = {bannerImg:'',bannerTitle:'',url:''};
                })
            },
            // 删除楼层数据
            delBanner:function(index,id){
                delApplyThemeItemData(id).then(res =>{
                    this.selectBanner.splice(index,1);
                })
            },
            // 验证提交
            vali:function(editor){
                if(editor){
                  if(this.editorInputError.title.isSuccess && this.editorInputError.url.isSuccess && this.editorInputError.uploadCell.isSuccess){
                      this.isEditor = true;
                  }else{
                      this.isEditor = false;
                  }
                }else{
                    if(this.inputError.title.isSuccess && this.inputError.url.isSuccess && this.inputError.uploadCell.isSuccess){
                        this.isUpload = true;
                    }
                }
            },
            // 获取楼层的数据
            getThemeItemData:function(){
                getApplyThemeItemData(this.themeItem.theme_item_id).then(res =>{
                    const re = res.data.data;
                    this.selectBanner = re.data;
                    this.$emit('load',false);
                })
            },
            // 图片上传方法
            getFile: function (event,editor) {
                this.file = null;
                this.file = event.target.files[0];
                let formData = new FormData();
                formData.append("file", this.file);
                formData.append("saveFilePathName", "upload/goods");
                var WHList = [{"width":750,"height":220}];
                formData.append("WHList", JSON.stringify(WHList));
                uploadImage(formData).then(res =>{
                    let re = res.data.data;
                    if(re.status==1001){
                        if(editor){
                            this.editorModalData.itemDataImg = re.address;
                            this.editorInputError.uploadCell = {error: false,isSuccess:true, errorInfo: ''};
                        }else{
                            this.upload.bannerImg = re.address;
                            this.inputError.uploadCell = {error: false,isSuccess:true, errorInfo: ''};
                        }
                        this.$refs.pathClear.value = '';
                    }else if(re.status == 1002){
                        if(editor) {
                            this.editorInputError.uploadCell = {error: true,isSuccess:false, errorInfo: '上传的图片尺寸不符合要求'};
                        }else{
                            this.inputError.uploadCell = {error: true,isSuccess:false, errorInfo: '上传的图片尺寸不符合要求'};
                        }
                    }else{
                        if(editor) {
                            this.editorInputError.uploadCell = {error: true,isSuccess:false, errorInfo: '上传图片失败'};
                        }else {
                            this.inputError.uploadCell = {error: true,isSuccess:false, errorInfo: '上传图片失败'};
                        }
                    }
                    this.vali(editor);
                });
            },
            // 显示编辑弹出层
            showEditorBanner (index){
                this.editorInputError.uploadCell.isSuccess = true;
                this.isEditor = true;
                this.editorModalDialog = !this.editorModalDialog;
                if(this.editorModalDialog){
                    const singleObj = this.selectBanner[index];
                    this.editorModalData = {id:singleObj.id,itemDataGoods:singleObj.itemDataGoods,itemDataType:singleObj.itemDataType,
                      itemDataName:singleObj.itemDataName,itemDataLike:singleObj.itemDataLike,itemDataImg:singleObj.itemDataImg,itemDataValue:singleObj.itemDataValue,sequence:singleObj.sequence};
                }
            },
            // 编辑弹出层提交函数
            editorBannerSub (){
                if(!this.isEditor){
                    return
                }
                this.editorModalData.themeItemId = this.themeItem.theme_item_id;
                saveApplyThemeItemData(this.editorModalData).then(res =>{
                    const i = this.selectBanner.findIndex(item => item.id === this.editorModalData.id);
                    this.selectBanner[i] = this.editorModalData;
                    this.showEditorBanner();
                    if(!this.editorModalData.id){
                        this.selectBanner.unshift(this.editorModalData);
                    }
                });
            },
            // 选择商品显示编辑弹出层
            addGoodsBannerItem (goods){
                const goodsLike = '/app/index/itemDetail/'+goods.id;
                this.editorModalData = {itemDataGoods:goods.id,itemDataType:'goods',
                  itemDataName:goods.goodsName,itemDataLike:goodsLike,itemDataImg:'',itemDataValue:'',};
                this.editorModalDialog = true;
                this.editorInputError.uploadCell = {error:true,isSuccess:false,errorInfo:'请上传图片'};
                this.isEditor = false;
            }
        },
        created() {
            this.getThemeItemData();
            this.pickerMenusTab('goods');
        },
        watch: {
            selectBanner: function(val,oldVal){
                this.$emit('select-data',val);
            },
            'upload.bannerTitle': function(val, oldVal){
                if(val == '') {
                    this.inputError.title = {isError: true, txt: '请填写广告标题'}
                }else{
                    this.inputError.title = {isError: false,isSuccess:true,txt: ''};
                }
                this.vali(false);
            },
            'upload.url': function(val, oldVal){
                const reg = /^[a-zA-z]+:\/[^\s]*$/;
                if(val == '') {
                    this.inputError.url = {isError: true, txt: '请填写链接地址'}
                }else if(!reg.test(val)){
                    this.inputError.url = {isError: true, txt: '请输入正确的地址'}
                }else{
                    this.inputError.url = {isError: false,isSuccess:true,txt: ''}
                }
                this.vali(false);
            },
            'editorModalData.itemDataName': function(val, oldVal){
                if(val == '') {
                    this.editorInputError.title = {isError: true, txt: '请填写广告标题'}
                }else{
                    this.editorInputError.title = {isError: false,isSuccess:true,txt: ''};
                }
                this.vali(true);
            },
            'editorModalData.itemDataLike': function(val, oldVal){

                if('goods' == this.editorModalData.itemDataType){
                    return
                }

                const reg = /^[a-zA-z]+:\/[^\s]*$/;
                if(val == '') {
                    this.editorInputError.url = {isError: true, txt: '请填写链接地址'}
                }else if(!reg.test(val)){
                    this.editorInputError.url = {isError: true, txt: '请输入正确的地址'}
                }else{
                    this.editorInputError.url = {isError: false,isSuccess:true,txt: ''}
                }
                this.vali(true);
            }
        },
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  @import "../../style/interface.css";
  .s-item.s-empty{background-color: #f6f6f6;}
  .s-item .s-con > div {
    display: inline-block;
    vertical-align: top
  }
  .form-control.invalid{
    border: 1px solid #f66b4e;
  }
  .modal-content .close {
    position: absolute;
    right: 10px;
    top: 8px;
    cursor: pointer;
    font-size: 18px;
    font-weight: 900;
    color: #d8d8d8;
  }
  .modal-content .close:hover {
    color: #444;
  }
</style>
