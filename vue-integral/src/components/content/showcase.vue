<template>
  <div class="editor-wrapper showcase-edit">
    <header>编辑橱窗区</header>
    <div class="editor-content">
      <section class="editor-section">
        <h3>位置1</h3>
        <div class="section-content cf" :class="{'active':sectionContentType == 'place1'}" @click="sectionContent('place1')" v-if="!selectData.place1">
          <a href="javascript:void(0)" class="empty-wrapper">
            <img src="http://yun.dui88.com/developer_new/images/showcase_empty.png" width="115" height="132" alt="" class="showcase-empty-img">
            <div class="showcase-empty-info">
              <h4 class="showcase-title">未配置</h4>
              <p class="showcase-title-desc">此位置内容为空时，橱窗区域关闭。</p>
              <span class="btn btn-green btn-lg"><i class="iconfont" style="margin-right: 5px;">&#xe6e8;</i>去添加</span>
            </div>
          </a>
        </div>

        <div class="section-content place1 cf has-content" :class="{'active':sectionContentType == 'place1'}" v-else>
          <img alt="" class="showcase-img" :src="selectData.place1.data.itemDataImg">
          <div class="showcase-info">
            <div class="showcase-info-wrapper">
              <div class="showcase-info-content" v-if="'goods' != selectData.place1.data.itemDataType">
                <h4 class="showcase-title ellipsis">{{selectData.place1.data.itemDataLike}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">链接</span>
                </p>
              </div>

              <div class="showcase-info-content" v-else>
                <h4 class="showcase-title ellipsis">{{selectData.place1.data.itemDataName}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">商品</span>
                </p>
              </div>

              <div class="showcase-info-edit">
                <div class="posr">
                  <span><a href="javascript:void(0)" class="iconfont preview-icon">&#xe772;</a></span>
                  <div class="tooltip left" role="tooltip" style="top: -24.5px; left: -181px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">
                      <p class="posr">
                        <img class="preview-img" width="189" height="63" :src="selectData.place1.data.itemDataImg">
                      </p>
                    </div>
                  </div>
                  <span>
                      <a href="javascript:void(0)" @click="sectionContent('place1')" class="iconfont">&#xe753;</a>
                  </span>
                  <div class="tooltip top" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">编辑</div>
                  </div>
                  <span>
                     <a href="javascript:void(0)" class="iconfont delete" @click="delIconItem('place1',selectData.place1.data.id)">&#xe752;</a>
                  </span>
                  <div class="tooltip top delete" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">删除</div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </section>
      <section class="editor-section">
        <h3>位置2</h3>
        <div class="section-content place2 cf" :class="{'active':sectionContentType == 'place2'}" @click="sectionContent('place2')" v-if="!selectData.place2">
          <a href="javascript:void(0)" class="empty-wrapper">
            <img src="http://yun.dui88.com/developer_new/images/showcase_empty.png" width="115" height="132" alt="" class="showcase-empty-img">
            <div class="showcase-empty-info">
              <h4 class="showcase-title">未配置</h4>
              <p class="showcase-title-desc">此位置内容为空时，橱窗区域关闭。</p>
              <span class="btn btn-green btn-lg"><i class="iconfont" style="margin-right: 5px;">&#xe6e8;</i>去添加</span>
            </div>
          </a>
        </div>

        <div class="section-content place2 cf has-content" :class="{'active':sectionContentType == 'place2'}" v-else>
          <img alt="" class="showcase-img" :src="selectData.place2.data.itemDataImg">
          <div class="showcase-info">
            <div class="showcase-info-wrapper">
              <div class="showcase-info-content" v-if="'goods' != selectData.place2.data.itemDataType">
                <h4 class="showcase-title ellipsis">{{selectData.place2.data.itemDataLike}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">链接</span>
                </p>
              </div>

              <div class="showcase-info-content" v-else>
                <h4 class="showcase-title ellipsis">{{selectData.place2.data.itemDataName}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">商品</span>
                </p>
              </div>
              <div class="showcase-info-edit">
                <div class="posr">
                  <span><a href="javascript:void(0)" class="iconfont preview-icon">&#xe772;</a></span>
                  <div class="tooltip left" role="tooltip" style="top: -24.5px; left: -181px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">
                      <p class="posr">
                        <img class="preview-img" width="189" height="63" :src="selectData.place2.data.itemDataImg">
                      </p>
                    </div>
                  </div>
                  <span>
                      <a href="javascript:void(0)" class="iconfont" @click="sectionContent('place2')">&#xe753;</a>
                  </span>
                  <div class="tooltip top" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">编辑</div>
                  </div>
                  <span>
                    <a href="javascript:void(0)" class="iconfont delete" @click="delIconItem('place2',selectData.place2.data.id)">&#xe752;</a>
                  </span>
                  <div class="tooltip top delete" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">删除</div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </section>
      <section class="editor-section">
        <h3>位置3</h3>
        <div class="section-content place3 cf" :class="{'active':sectionContentType == 'place3'}" @click="sectionContent('place3')" v-if="!selectData.place3">
          <a href="javascript:void(0)" class="empty-wrapper">
            <img src="http://yun.dui88.com/developer_new/images/showcase_empty.png" width="115" height="132" alt="" class="showcase-empty-img">
            <div class="showcase-empty-info">
              <h4 class="showcase-title">未配置</h4>
              <p class="showcase-title-desc">此位置内容为空时，橱窗区域关闭。</p>
              <span class="btn btn-green btn-lg">
                <i class="iconfont" style="margin-right: 5px;">&#xe6e8;</i>去添加
              </span>
            </div>
          </a>
        </div>

        <div class="section-content place3 cf has-content" :class="{'active':sectionContentType == 'place3'}" v-else>
          <img alt="" class="showcase-img" :src="selectData.place3.data.itemDataImg">
          <div class="showcase-info">
            <div class="showcase-info-wrapper">
              <div class="showcase-info-content" v-if="'goods' != selectData.place3.data.itemDataType">
                <h4 class="showcase-title ellipsis">{{selectData.place3.data.itemDataLike}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">链接</span>
                </p>
              </div>

              <div class="showcase-info-content" v-else>
                <h4 class="showcase-title ellipsis">{{selectData.place3.data.itemDataName}}</h4>
                <p class="showcase-title-desc">
                  <span class="tag">商品</span>
                </p>
              </div>

              <div class="showcase-info-edit">
                <div class="posr">
                  <span><a href="javascript:void(0)" class="iconfont preview-icon">&#xe772;</a></span>
                  <div class="tooltip left" role="tooltip" style="top: -24.5px; left: -181px; display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">
                      <p class="posr">
                        <img class="preview-img" width="189" height="63" :src="selectData.place3.data.itemDataImg">
                      </p>
                    </div>
                  </div>
                  <span>
                      <a href="javascript:void(0)" @click="sectionContent('place3')" class="iconfont">&#xe753;</a>
                  </span>
                  <div class="tooltip top" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">编辑</div>
                  </div>
                  <span>
                    <a href="javascript:void(0)" class="iconfont delete" @click="delIconItem('place3',selectData.place3.data.id)">&#xe752;</a>
                  </span>
                  <div class="tooltip top delete" role="tooltip" style="display: none;">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner">删除</div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </section>
    </div>

    <!-- 编辑区域 -->
    <div class="picker-wrapper">
      <header>配置位置1内容</header>
      <a href="javascript:;" class="close iconfont" @click="closePicker()">&#xe695;</a>
      <div class="picker-bar" @click="closePicker()">
        <i class="iconfont">&#xe647;</i>
      </div>
      <div class="picker-content" style="width: 812px;">
        <div class="showcase-config-wrapper">
          <section class="config-section">
            <h4>选择指向内容</h4>
            <div class="content-info content-empty" v-if="!modalSelectData">
              <p>未指定</p>
              <a href="javascript:void(0)" class="btn btn-default" @click="showSelectModal()">选择指向内容</a>
              <input type="hidden" class="invalid untouched pristine">
            </div>

            <div class="content-info content-activity" v-else-if="'goods' == dataType">
              <img alt="" :src="modalSelectData.img">
              <div class="content-activity-desc">
                <h4 class="ellipsis">{{modalSelectData.name}}</h4>
                <p><span class="tag tag-goods"> 商品 </span></p>
              </div>
              <a href="javascript:void(0)" class="iconfont" @click="showSelectModal()">&#xe753;</a>
            </div>

            <div class="content-info content-link" v-else-if="'link' == dataType">
              <p>{{modalSelectData.link}}</p>
              <span class="link-icon">链接</span>
              <a href="javascript:void(0)" class="iconfont" @click="showSelectModal()">&#xe753;</a>
            </div>

          </section>
          <section class="config-section" v-if="isPicker">
            <h4>橱窗展示图</h4>
            <div class="image-upload-container cf " :class="[uploadCell[sectionContentType].class]">
              <div class="img fl img-uploaded">
                <p>
                  <img alt="" :src="uploadCell[sectionContentType].domeImg">
                </p>
              </div>
              <div class="upload-area">
                <div class="upload-cell">
                    <p>{{uploadCell[sectionContentType].describe}}</p>
                    <p>具体图片内容可参考示例</p>

                  <div class="upload-btn">
                    <div class="file-upload file-uploads file-uploads-html4">
                      <input ref="pathClear" type="file" name="file" id="file" accept="image/jpg,image/jpeg,image/png"  @change="getFile($event)">
                    </div>
                    <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                    <a :href="uploadCell[sectionContentType].downloadDome" class="example-link">示例下载</a></div>
                </div>
              </div>
              <p class="invalid" v-if="uploadCell.invalid.error">{{uploadCell.invalid.errorInfo}}</p>
            </div>
          </section>
          <section class="config-section" v-if="'place1' == sectionContentType">
            <h4>橱窗倒计时</h4>
            <div class="switch off">
              <label class="switch-label">
                <div class="switch-inner"></div>
                <div class="switch-switch"></div>
              </label>
            </div>
          </section>
          <div class="save-btn-wrapper">
            <a href="javascript:void(0)" class="btn btn-green btn-lg save-btn" @click="saveBut()">保存</a>
          </div>
        </div>
      </div>
    </div>

    <!-- 弹出层 -->
    <div role="dialog" class="showcase-select-modal modal fade in" v-show="selectModal" :class="{'in' : selectModal}">
      <div class="modal-dialog" role="document" style="width: 580px; top: 243.5px; left: 550px;">
        <div class="modal-content">
          <div class="modal-header">
            <i class="iconfont close" @click="showSelectModal()">&#xe695;</i>
            <h4 class="modal-title">选择指向内容</h4>
            <ul class="content-type">
              <li>
                <a href="javascript:void(0)" class="type-item " @click="modalMenu('goods')" :class="{'active':'goods' == modalMenusType}">商品</a></li>
              <li>
                <a href="javascript:void(0)" class="type-item">活动</a></li>
              <li>
                <a href="javascript:void(0)" class="type-item" @click="modalMenu('link')" :class="{'active':'link' == modalMenusType}">链接</a></li>
            </ul>
          </div>
          <div class="modal-body">
            <div class="goods-activity-content" v-if="'goods' == modalMenusType">
              <div class="content-search">
                <div class="search-group">
                  <input type="text" class="search-control" placeholder="请输入关键词">
                  <i class="iconfont">&#xe647;</i>
                </div>
                <p class="search-tip">橱窗队列执行前请保证此商品/活动已上架</p>
              </div>
              <div class="small-loading-elephant" v-show="modalDialog"></div>
              <div class="goods-list" v-show="!modalDialog">
                <div class="goods-item" v-for="(item,index) in goodsItem" @click="active(index)" :class="{'active' : modalDataIndex == index}">
                  <img :src="imageWebServer + item.goodsMainPhoto.path + item.goodsMainPhoto.name" :alt="item.goodsName">
                  <div class="goods-info">
                    <h5 class="goods-title fl">{{item.goodsName}}</h5>
                    <span class="goods-credits fr">{{item.exchangeRules.exchangeIntegralPrice}}积分</span>
                  </div>
                  <i class="iconfont" v-show="modalDataIndex == item.id">&#xe766;</i>
                </div>
              </div>
            </div>

            <div class="link-content" v-else-if="'link' == modalMenusType">
              <h5>链接地址</h5>
              <textarea class="showcase-link" placeholder="https://" v-model="link"></textarea>
              <p class="error-tip" style="display: none;">
                <i class="iconhandle"></i>此链接格式异常，请确认其正确性。
              </p>
            </div>

          </div>
          <div class="modal-footer">
            <div class="footer-btns">
              <a class="btn btn-default btn-lg mr15" @click="showSelectModal()">取消</a>
              <a class="btn btn-green btn-lg"  @click="selectGoods()"  >确定</a>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
    import {getGoodsList,saveApplyThemeItemData,getApplyThemeItemData,delApplyThemeItemData,uploadImage} from '@/service/getData';
    import config from '@/config/config';
    export default {
        data (){
            return {
                imageWebServer:config.ossImgPath,
                uploadCell:{
                    place1:{
                        'class':'showcase-upload-big w-376-h-250',
                        'describe':'支持尺寸为376*250像素的jpg/png图片',
                        'domeImg':'//yun.duiba.com.cn/developer_new/images/mobileui/showcase_376_250.png',
                        'downloadDome':'https://yun.duiba.com.cn/developer/img/showcase_example_1.zip',
                        'rules':[{"width":376,"height":250}]
                    },
                    place2:{
                        'class':'showcase-upload-small w-374-h-125',
                        'describe':'支持尺寸为374*125像素的jpg/png图片',
                        'domeImg':'//yun.duiba.com.cn/developer_new/images/mobileui/showcase_374_124_new.png',
                        'downloadDome':'https://yun.duiba.com.cn/developer/img/showcase_example_1.zip',
                        'rules':[{"width":374,"height":125}]
                    },
                    place3:{
                        'class':'showcase-upload-small w-374-h-125',
                        'describe':'支持尺寸为374*125像素的jpg/png图片',
                        'domeImg':'//yun.duiba.com.cn/developer_new/images/mobileui/showcase_374_124_new.png',
                        'downloadDome':'https://yun.duiba.com.cn/developer/img/showcase_example_1.zip',
                        'rules':[{"width":374,"height":125}]
                    },
                    invalid:{error:false,errorInfo:''}
                },
                sectionContentType: '',
                isPicker: false,
                selectModal: false,
                goodsItem:[],
                modalDialog:true,
                modalDataIndex:null,
                modalSelectData:null,
                modalMenusType:'goods',
                dataType:'goods',
                link:'',
                showCaseImg:'',
                selectData:{place1:null,place2:null,place3:null},
                file:null
            }
        },
        props: ['themeItem','showLoading'],
        methods: {
            sectionContent:function(type){
                this.sectionContentType = type;
                this.isPicker = true;
                this.$emit('picker', this.isPicker);

                if(this.selectData[this.sectionContentType]){
                    const selectData = this.selectData[this.sectionContentType];
                    this.modalMenusType = selectData.type;
                    this.modalSelectData = {
                        id:selectData.data.itemDataGoodsId,
                        name:selectData.data.itemDataName,
                        img:selectData.data.itemDataImg,
                        link:selectData.data.itemDataLike
                    };
                    this.uploadCell[this.sectionContentType].domeImg = selectData.data.itemDataImg;
                    this.showCaseImg = selectData.data.itemDataImg;
                    if(selectData.data.id){
                        this.modalSelectData.themeItemDataId = selectData.data.id;
                    }
                    this.dataType = selectData.type;
                    this.link = selectData.data.itemDataLike;
                }else{
                    this.modalSelectData = null;
                    this.link = '';
                    this.showCaseImg = this.uploadCell[this.sectionContentType].domeImg;
                }
            },
            closePicker:function(){
                this.sectionContentType = '';
                this.isPicker = false;
                this.$emit('picker', this.isPicker);
            },
            showSelectModal:function(){
                this.selectModal = !this.selectModal;
                if(this.selectModal){
                    getGoodsList().then(res =>{
                        const re = res.data.data.data.data;
                        this.goodsItem = re;
                        this.modalDialog = false;
                    })
                }
            },
            selectGoods: function(){
                this.dataType = this.modalMenusType;
                let themeItemDataId = '';
                if(this.modalSelectData && this.modalSelectData.themeItemDataId){
                    themeItemDataId = this.modalSelectData.themeItemDataId;
                }
                if('link' == this.modalMenusType){
                    this.modalSelectData = {id:'',name:'',img:'',link:this.link,themeItemDataId:themeItemDataId};
                }else{
                    const goodsInfo = this.goodsItem[this.modalDataIndex];
                     this.modalSelectData = {id:goodsInfo.id,name:goodsInfo.goodsName,img:goodsInfo.goodsMainPhoto.path + goodsInfo.goodsMainPhoto.name,link:'',themeItemDataId:themeItemDataId};
                }

                this.showSelectModal();
            },
            active: function(index){
                this.modalDataIndex = index;
            },
            modalMenu:function(type){
                this.modalMenusType = type;
            },
            saveBut:function(){
                if(this.modalSelectData){

                  let params = {
                      id:'',themeItemId:this.themeItem.theme_item_id,itemDataType:this.dataType,
                      itemDataName:'',itemDataLike:'',itemDataImg:this.showCaseImg,itemDataValue:this.sectionContentType,
                      itemDataGoodsId:'',
                  };

                  if('goods' == this.dataType){
                      params.itemDataName = this.modalSelectData.name;
                      params.itemDataGoodsId = this.modalSelectData.id;
                  }else{
                      params.itemDataLike = this.modalSelectData.link;
                  }

                  if(this.modalSelectData.themeItemDataId){
                      params.id = this.modalSelectData.themeItemDataId;
                  }
                  saveApplyThemeItemData(params).then(res =>{
                      const themeItemDataId =  res.data.data.data;
                      params.id = themeItemDataId;
                      this.selectData[this.sectionContentType] = {type:this.dataType,data:params};
                      this.closePicker();
                  })
                }
            },
            getThemeItemData:function(){
              getApplyThemeItemData(this.themeItem.theme_item_id).then(res =>{
                const re = res.data.data;
                re.data.forEach((v)=>{
                    if(v.itemDataValue){
                        this.selectData[v.itemDataValue] =  {type:v.itemDataType,data:v};
                    }
                });
                this.$emit('load',false);
              })
            },
            delIconItem:function(index,id){
              delApplyThemeItemData(id).then(res =>{
                  this.selectData[index] = null;
              })
            },
            //图片上传方法
            getFile: function (event) {
                this.file = event.target.files[0];
                let formData = new FormData();
                formData.append("file", this.file);
                formData.append("saveFilePathName", "upload/goods");
                var WHList = this.uploadCell[this.sectionContentType].rules;
                formData.append("WHList", JSON.stringify(WHList));
                uploadImage(formData).then(res =>{
                    let re = res.data.data;
                    if(re.status==1001){
                        this.uploadCell[this.sectionContentType].domeImg = this.imageWebServer + re.address;
                        this.showCaseImg = this.imageWebServer + re.address;
                        this.uploadCell.invalid.errorInfo = '';
                        this.uploadCell.invalid.error = false;
                        this.$refs.pathClear.value = '';
                    }else if(re.status == 1002){
                        this.uploadCell.invalid.errorInfo = '上传的图片尺寸不符合要求';
                        this.uploadCell.invalid.error = true;
                    }else{
                        this.uploadCell.invalid.errorInfo = '上传图片失败！';
                        this.uploadCell.invalid.error = true;
                    }
                });
            },
        },
        created() {
            this.getThemeItemData()
        },
    }
</script>

<!-- 限定作用域"scoped" 不要误写成scope -->
<style lang="scss" scoped>
  @import "../../style/interface.css";
  .s-item .s-con{
    height: 68px;
    box-sizing: border-box;
    padding: 3px 5px;
    background-color: #f6f6f6;
    border-top-left-radius: 34px;
    border-bottom-left-radius: 34px
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
</style>
