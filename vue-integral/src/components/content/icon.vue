<template>
  <div>
    <div class="editor-wrapper icon">
      <header>编辑图标区
        <a href="javascript:;" v-if="selectIcon.length > 0" class="btn btn-green btn-lg btn-add" @click="picker()">添加图标</a>
      </header>
      <div class="editor-content">
        <p class="ft-desc top-desc">左边预览区仅显示前四个图标</p>
        <div class="editor-full">
          <div class="editor-list-wrapper">
            <div class="s-item" v-for="(item,index) in selectIcon">
              <div class="s-con">
                <span class="s-index">{{(index + 1)}}</span>
                <img alt="" class="iconImg" :src="imageWebServer + item.itemDataImg" lazy="loaded">
                <div class="s-text">
                  <p>文案：{{item.itemDataName}}</p>
                  <p>指向：{{item.itemDataName}}</p>
                </div>
                <div class="s-options">
                  <span>
                    <a href="javascript:void(0)" class="iconfont" @click="showEditorIcon(index)">&#xe753;</a>
                  </span>
                  <div class="tooltip top" role="tooltip" style="top: -8px; left: 285px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">编辑</div>
                  </div>
                  <span @click="delIconItem(index,item.id)">
                    <a href="javascript:void(0)" class="iconfont delete">&#xe752;</a>
                  </span>
                  <div class="tooltip top delete" role="tooltip" style="top: -8px; left: 315px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">删除</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="editor-empty" v-if="selectIcon.length < 1">
          <i class="iconfont">&#xe656;</i>
          <p>图标区内容为空时，首页会隐藏此区域。</p>
          <a href="javascript:;" class="btn btn-green btn-lg" @click="picker()">
            <i class="iconfont">&#xe6e8;</i>添加图标</a>
        </div>
        <div class="s-item s-empty" style="display: none;"></div>
      </div>
      <div class="editor-bar" @click="picker()">
        <i class="iconfont">&#xe647;</i>
      </div>
    </div>
    <!-- 图标 -->
    <div class="picker-wrapper icon">
      <header>你可以挑选的图标
        <ul class="picker-menu">
          <li :class="{'active':pickerMenusType == 'itemList'}" @click="pickerMenusTab('itemList')">指向列表</li>
          <li :class="{'active':pickerMenusType == 'goods'}" @click="pickerMenusTab('goods')">指向商品</li>
        </ul>
      </header>
      <div class="picker-content" style="width: 812px;">
        <div class="icon-search">
          <div class="filter-form">
            <div class="filter-input" v-if="pickerMenusType == 'goods'">
              <input type="text" v-model="queryName" placeholder="请输入关键词"> <i class="iconfont">&#xe62c;</i>
            </div>
            <span class="ft-desc">最多可添加
                  <label class="fw-b">8</label>个,还可添加
                  <label class="fw-b">4</label>个
            </span>
          </div>
          <div class="icon-tips" v-if="pickerMenusType == 'goods'">
            已上架的商品才可以放入图标区，没找到你想要的内容，请先去
            <a class="link-green" href="javascript:void(0);">上架商品</a>
          </div>
          <div class="result-list">
            <div class="result-item" v-for="(item,index) in iconItem" v-if="'itemList' == pickerMenusType">
              <div class="item-content">
                <img :src="imageWebServer + item.iconImg" lazy="loaded">
                <p>指向：{{item.iconTitle}}</p>
              </div>
              <div class="cover" @click="addIconItem(index,item.iconImg,item.iconType,item.iconTitle,item.iconData)">
                <span><i class="iconfont">&#xe766;</i></span>
              </div>
            </div>

            <div class="result-item" v-for="(item,index) in goodsItem" v-if="'goods' == pickerMenusType">
              <div class="item-content">
                <img :src="imageWebServer + item.goodsMainPhoto.path + item.goodsMainPhoto.name" lazy="loaded">
                <p>指向：{{item.goodsName}}</p>
              </div>
              <div class="cover" @click="addIconItem(index,item.goodsMainPhoto.path + item.goodsMainPhoto.name,'goods',item.goodsName,item.id,item.id)">
                <span><i class="iconfont">&#xe766;</i></span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <a href="javascript:;" class="close iconfont" @click="picker()">&#xe695;</a>
      <div class="picker-bar" @click="picker()">
        <i class="iconfont">&#xe647;</i>
      </div>
    </div>

    <!-- 编辑图标 -->
    <div role="dialog" class="modal fade" :class="{'in':editorModalDialog}" v-show="editorModalDialog">
      <div class="modal-dialog" role="document" style="width: 550px; top: 251.5px; left: 565px;">
        <div class="modal-content">
          <div class="modal-header">
            <i class="iconfont close" @click="showEditorIcon()">&#xe695;</i>
            <h4 class="modal-title">编辑图标</h4>
          </div>
          <div class="modal-body modify-banner">
            <div class="form-group" style="margin-top: 15px">
              <label class="control-label">图标图片:</label>
              <div>
                <img style="width: 100px" :src="imageWebServer + editorModalData.itemDataImg" v-if="editorModalData.itemDataImg">
                <div class="img-desc">
                  <p class="ft-desc">支持尺寸为100*100像素的jpg/png图片</p>
                  <span class="upload-wrapper">
                    <div class="file-upload file-uploads file-uploads-html4">
                      <input ref="pathClear" type="file" name="file" id="file" accept="image/jpg,image/jpeg,image/png,image/gif" @change="getFile($event,true)">
                    </div>
                    <a href="javascript:;" class="btn btn-default">重新上传</a>
                  </span>
                </div>
              </div>
              <p class="invalid" v-if="editorInputError.uploadCell.error"><i class="iconfont">&#xe629;</i> {{editorInputError.uploadCell.errorInfo}}</p>
            </div>
            <div class="form-group">
              <label class="control-label">显示文案:</label>
              <div>
                <input type="text" class="form-control form-control-dirty" v-model="editorModalData.itemDataName" placeholder="请输入显示文案，建议5个字以内" maxlength="5">
              </div>
              <p class="invalid" v-if="editorInputError.title.isError"> <i class="iconfont">&#xe629;</i> {{editorInputError.title.txt}} </p>
            </div>
          </div>
          <div class="modal-footer modify-banner">
            <a href="javascript:;" class="btn btn-default" @click="showEditorIcon()">取消</a>
            <a href="javascript:;" :disabled="!isEditor" class="btn btn-green" @click="editorBannerSub()">保存</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    import {getApplyThemeItemData,uploadImage,saveApplyThemeItemData,delApplyThemeItemData,getGoodsList,getCategoryList} from '@/service/getData';
    import config from '@/config/config';

    export default {
        data (){
            return {
                imageWebServer:config.ossImgPath,
                iconItem:[
                    {id:"-1",iconImg:'upload/goods/classify/icon/i6pouxupj0.png',iconType:'all',iconTitle:'所有商品',iconData:'所有商品'},
                    {id:"-2",iconImg:'upload/goods/classify/icon/7fen28siy4.png',iconType:'coupons',iconTitle:'优惠券',iconData:'3'},
                    {id:"-3",iconImg:'upload/goods/classify/icon/fgzpqk6zzo.png',iconType:'entity',iconTitle:'实物',iconData:'1'},
                    {id:"-4",iconImg:'//yun.duiba.com.cn/duibaManagerWeb/y9gvhl083e.png',iconType:'virtual',iconTitle:'虚拟商品',iconData:'2'}
                ],
                goodsItem:[],
                selectIcon:[],
                queryName:'',
                isPicker: false,
                editorModalDialog:false,
                editorModalData:{id:'',itemDataGoods:null,itemDataType:'',itemDataName:'',itemDataLike:'',itemDataImg:'',itemDataValue:"",sequence:0},
                isEditor:true,
                editorInputError:{title:{isError:false,isSuccess:true,txt:''},uploadCell:{error:false,isSuccess:true,errorInfo:''}},
                pickerMenusType:''
            }
        },
        props: ['themeItem','showLoading'],
        methods: {
            picker:function(){
                this.isPicker = !this.isPicker;
                this.$emit('picker', this.isPicker);
                if(this.isPicker){
                    this.pickerMenusTab('itemList');
                }
            },
            // 切换菜单
            pickerMenusTab:function(v){
                this.pickerMenusType = v;
                if('goods' == this.pickerMenusType){
                    const params = {goodsName:this.queryName};
                    getGoodsList(params).then(res =>{
                        const re = res.data.data.data.data;
                        re.forEach(item =>{
                            const index = this.selectIcon.findIndex(obj => obj.itemDataGoodsId == item.id);
                            const goods_index = this.goodsItem.findIndex(obj => obj.id == item.id);
                            if(index < 0 && goods_index < 0){
                                this.goodsItem.push(item);
                            }
                        });
                    });
                }else{
                    getCategoryList({pageSize:-1}).then(res =>{
                        if(res.data.data.success){
                            const re = res.data.data.data.data;
                            re.forEach(item =>{
                                const index = this.selectIcon.findIndex(obj => obj.itemDataValue == item.id);
                                const icon_index = this.iconItem.findIndex(obj => obj.id == item.id);
                                if(index < 0 && icon_index < 0){
                                    this.iconItem.push({id:item.id,iconImg:item.categoryIcon.path + item.categoryIcon.name,iconType:'category',iconTitle: item.categoryName,iconData:item.id});
                                }
                            });

                        }
                    })
                }
            },
            // 添加图标楼层数据
            addIconItem:function(index,img,type,title,data,goodsId){
                console.log(goodsId);
                /**
                 * 类型为 goods : url 为商品的编号
                 */
                if('goods' == this.pickerMenusType){
                    this.goodsItem.splice(index,1);
                }else{
                    this.iconItem.splice(index,1);
                }
                const icon = {
                    themeItemId:this.themeItem.theme_item_id,itemDataType:type,itemDataGoodsId:goodsId,
                    itemDataName:title,itemDataLike:'',itemDataImg:img,itemDataValue:data
                };
                saveApplyThemeItemData(icon).then(res =>{
                    icon.id = res.data.data.data;
                    this.selectIcon.unshift(icon);
                })
            },
            // 删除图标楼层数据
            delIconItem:function(index,id){
                delApplyThemeItemData(id).then(res =>{
                    if(res.data.data.success){
                        this.selectIcon.splice(index,1);
                    }
                })
            },
            // 获取楼层数据
            getThemeItemData:function(){
                getApplyThemeItemData(this.themeItem.theme_item_id).then(res =>{
                    const re = res.data.data;
                    this.selectIcon = re.data;
                    this.$emit('load',false);
                })
            },
            // 图片上传方法
            getFile  (event) {
                this.file = event.target.files[0];
                let formData = new FormData();
                formData.append("file", this.file);
                formData.append("saveFilePathName", "upload/goods/classify");
                var WHList = [{"width":100,"height":100}];
                formData.append("WHList", JSON.stringify(WHList));
                uploadImage(formData).then(res =>{
                    let re = res.data.data;
                    if(re.status==1001){
                        this.editorModalData.itemDataImg = re.address;
                        this.editorInputError.uploadCell = {error: false,isSuccess:true, errorInfo: ''};
                        this.isEditor = true;
                        this.$refs.pathClear.value = '';
                    }else if(re.status == 1002){
                        this.editorInputError.uploadCell = {error: true, errorInfo: '上传的图片尺寸不符合要求'};
                        this.isEditor = false;
                    }else{
                        this.editorInputError.uploadCell = {error: true, errorInfo: '上传图片失败'};
                        this.isEditor = false;
                    }
                });
            },
            // 显示编辑弹出层
            showEditorIcon (index){
                this.editorModalDialog = !this.editorModalDialog;
                if(this.editorModalDialog){
                    const singleObj = this.selectIcon[index];
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
                  const i = this.selectIcon.findIndex(item => item.id === this.editorModalData.id);
                  this.selectIcon[i] = this.editorModalData;
                  this.showEditorIcon();
              });
            },
        },
        created() {
            this.getThemeItemData();
        },
        watch: {
            selectIcon: function(val,oldVal){
                this.$emit('select-data',val);
            },
            'editorModalData.itemDataName' (val, oldVal){
                if(val == '') {
                    this.editorInputError.title = {isError: true, txt: '请填写广告标题'}
                    this.isEditor = false;
                }else{
                    this.editorInputError.title = {isError: false,isSuccess:true,txt: ''};
                    this.isEditor = true;
                }
            }
        }
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  @import "../../style/interface.css";
  .icon .s-item{background-color:#fff;}
  .s-item .s-con{
    height: 68px;
    box-sizing: border-box;
    padding: 3px 5px;
    background-color: #f6f6f6;
    border-top-left-radius: 34px;
    border-bottom-left-radius: 34px
  }
  .s-item .s-con .s-options {
    padding: 10px 0 10px 10px;
    font-size: 14px;
    color: #444;
    float: right;
    margin-top: 13px;
    border-left-width: 1px;
    border-left-style: solid;
    border-left-color: hsla(0,0%,59%,.1);
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
