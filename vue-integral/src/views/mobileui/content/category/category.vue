<template>
    <div class="page-content" id="page-content">
        <div class="page-title">
            <h2>自定义分类</h2>
            <a href="javascript:void(0)" class="add-link" @click="showDialog('isShowCategoryEdit',false)">
                <i class="iconfont icon-add"></i>添加自定义分类</a>
        </div>
        <div class="page-content">
            <v-tip :status="true" className="ft14 mb20" v-show="isShowTip" @on-close="hideTip('isShowTip')">
                在<a href="#!/mobileui/content" class="v-link-active">页面内容设置</a>中编辑图标，可以将自定义分类添加到手机端首页的图标区。
            </v-tip>
            <div class="category-list">
                <table class="category-table">
                    <thead>
                    <tr class="tr-header">
                        <th>标题</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="tr-items" v-for="(item,index) in pageData.classify_list">
                        <td>
                            <img :src="imageWebServer + item.categoryIcon.path + item.categoryIcon.name" alt="" class="category-img">
                            <p class="category-title"><span>{{item.categoryName}}</span></p>
                        </td>
                        <td class="td-actions">
                            <div style="position: relative;">
                                <Tooltip content="编辑" placement="top">
                                    <span>
                                        <a href="javascript:void(0)" class="iconfont icon-edit"
                                           @click="showDialog('isShowCategoryEdit',true,index)">
                                        </a>
                                    </span>
                                </Tooltip>
                                <Tooltip content="删除" placement="top">
                                    <span>
                                        <a href="javascript:void(0)" class="iconfont icon-delete" @click="del_classify(item.id)"></a>
                                    </span>
                                </Tooltip>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <template v-if="pageData.classify_list.length > 0">
                    <div class="pagination">
                        <div class="total-count">共{{pageData.param.totalPages}}条</div>
                        <v-page :initCurrentPage="pageData.param.currentPage" :initTotalPages="pageData.param.totalPages" @click-page="get_classify"></v-page>
                    </div>
                </template>
                <v-noresult v-else></v-noresult>
            </div>
        </div>
        <v-dialog className="category-edit-modal" width="600px" modalTitle="添加自定义分类" ref="dialog" :showFooter="true"
                  :is-show="isShowCategoryEdit"
                  @on-close="hideDialog('isShowCategoryEdit')" @on-confirm="sub_editor">
            <div class="modal-body">
                <div class="form-group">
                    <label class="control-label label-required">分类标题：</label>
                    <input type="text" class="form-control invalid" maxlength="6" placeholder="分类标题"
                           v-model="editor_classify.categoryName"
                           :class="{'form-control-dirty' : errorData.categoryName.isError}">
                    <p class="invalid" v-if="errorData.categoryName.isError">
                        <i class="iconfont">&#xe629;</i>
                        {{errorData.categoryName.txt}}
                    </p>
                </div>
                <div class="form-group">
                    <label class="control-label label-required">分类图标：</label>
                    <div class="image-upload-container cf category-100-100 w-100-h-100">
                        <div class="img fl img-uploaded">
                            <p><template v-if="file.icon"><img :src="file.icon"></template></p>
                        </div>
                        <div class="upload-area">
                            <div class="upload-cell">
                                <p>尺寸为100*100px，格式为jpg、png、jpeg</p>
                                <div class="upload-btn">
                                    <div class="file-upload file-uploads file-uploads-html4">
                                        <input ref="pathClear" type="file" name="icon" id="icon"
                                               accept="image/jpg,image/jpeg,image/png"
                                               @change="getFile($event,{width:100,height:100})">
                                    </div>
                                    <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                                </div>
                            </div>
                        </div>
                        <p class="invalid">{{errorData.icon.txt}}</p>
                    </div>
                </div>
                <div class="form-group image-switch">
                    <label class="control-label wrap">是否添加分类页图片：</label>
                    <v-switch v-model="AddPictures"></v-switch>
                    <p class="switch-tip">
                        <span>展示在活动页奖品列表</span>
                    </p>
                </div>
                <div class="form-group classify-image" v-if="AddPictures">
                    <label class="control-label label-required">分类页图片：</label>
                    <div class="image-upload-container cf w-640-h-280">
                        <div class="img fl img-uploaded">
                            <p><template v-if="file.banner"><img :src="file.banner"></template></p>
                        </div>
                        <div class="upload-area">
                            <div class="upload-cell">
                                <div class="upload-btn">
                                    <div class="file-upload file-uploads file-uploads-html4">
                                        <input type="file" name="banner" id="banner"
                                               accept="image/jpg,image/jpeg,image/png"
                                               @change="getFile($event,{width:750,height:328})">
                                    </div>
                                    <a href="javascript:void(0)" class="btn btn-default">选择上传</a>
                                </div>
                            </div>
                        </div>
                        <p class="invalid">{{errorData.banner.txt}}</p>
                    </div>
                    <p>
                        尺寸为750*328px，格式为jpg、png、jpeg
                    </p>
                </div>
            </div>
        </v-dialog>

        <transition name="loading">
            <v-loading v-show="pageData.param.dataLoading"></v-loading>
        </transition>
    </div>
</template>

<script>
    import page from '@/components/base/page';
    import {uploadImage,setCategory,getCategoryList,delCategory} from '@/service/getData';
    import config from '@/config/config';

    export default {
        data() {
            return {
                imageWebServer: config.ossImgPath,
                isShowTip: true,                    // 是否显示提示
                isShowCategoryEdit: false,          // 是否显示添加分类弹窗
                AddPictures: false,                 // 是否添加分类页图片
                file: {icon: '', banner: ''},
                errorData: {
                    icon: {isError: false, isSuccess: true, txt: ''},
                    banner: {isError: false, isSuccess: true, txt: ''},
                    categoryName: {isError: false, isSuccess: true, txt: ''},
                },
                editor_classify: {
                    id: '',
                    acApplyId: '',
                    categoryName: '',
                    categoryIconId: '',
                    categoryBannerShow: '',
                    categoryBannerId: ''
                },
                classify_list: [],
                //分页信息
                pageData:{
                    param:{     //参数
                        currentPage:1,
                        totalPages: 1,
                        dataLoading:true
                    },
                    classify_list: {}       //数据容器
                },
            }
        },
        methods: {
            // 关闭提示
            hideTip(param) {
                this[param] = false
            },
            // 显示弹窗
            showDialog(param,editor,index) {
                this[param] = true;
                if(editor){
                    const classify = this.pageData.classify_list[index];
                    this.editor_classify = {
                        id: classify.id,
                        acApplyId: classify.acApply.id,
                        categoryName: classify.categoryName,
                        categoryIconId: classify.categoryIcon.id,
                        categoryBannerShow: classify.categoryBannerShow,
                        categoryBannerId: classify.categoryBanner ? classify.categoryBanner.id : ''
                    };
                    this.AddPictures = this.editor_classify.categoryBannerShow;
                    this.file.icon = this.imageWebServer + classify.categoryIcon.path + classify.categoryIcon.name;
                    this.file.banner = (this.imageWebServer + classify.categoryIcon.path + classify.categoryIcon.name);
                    [this.file.icon,this.file.banner] =
                        [this.imageWebServer + (classify.categoryIcon.path + classify.categoryIcon.name),classify.categoryBanner ?
                            (this.imageWebServer + classify.categoryBanner.path + classify.categoryBanner.name) : ''];
                }else{
                    this.editor_classify = {
                        id: '',
                        acApplyId: '',
                        categoryName: '',
                        categoryIconId: '',
                        categoryBannerShow: '',
                        categoryBannerId: ''
                    };
                    [this.file.icon,this.file.banner] = ['',''];
                }
            },
            // 关闭弹窗
            hideDialog(param) {
                this[param] = false;
            },
            // 图片上传方法
            getFile(event, WHList) {
                let formData = new FormData();
                formData.append("file", event.target.files[0]);
                formData.append("saveFilePathName", "upload/goods/classify");
                formData.append("WHList", JSON.stringify([WHList]));
                uploadImage(formData).then(res => {
                    let re = res.data.data;
                    if (re.status == 1001) {
                        if ('icon' == this.$refs.pathClear.name) {
                            this.file.icon = this.imageWebServer + re.address;
                            this.errorData.icon = {isError: false, isSuccess: true, txt: ''};
                            this.editor_classify.categoryIconId = re.id;
                        } else {
                            this.file.banner = this.imageWebServer + re.address;
                            this.errorData.banner = {isError: false, isSuccess: true, txt: ''};
                            this.editor_classify.categoryBannerId = re.id;
                        }
                    } else {
                        if ('icon' == this.$refs.pathClear.name) {
                            this.errorData.icon = {
                                isError: true, isSuccess: false, txt: re.status == 1002 ? '上传的图片尺寸不符合要求' : '上传图片失败'
                            };
                        } else {
                            this.errorData.banner = {
                                isError: true, isSuccess: false, txt: re.status == 1002 ? '上传的图片尺寸不符合要求' : '上传图片失败'
                            };
                        }

                    }
                    this.$refs.pathClear.value = '';
                });
            },
            // 类别信息提交操作
            sub_editor (){
                if(!this.editor_classify.categoryIconId){
                    this.errorData.icon = { isError: true, isSuccess: false, txt: '请上传分类图标' };
                    return;
                }
                if(!this.editor_classify.categoryBannerId && this.AddPictures){
                    this.errorData.banner = { isError: true, isSuccess: false, txt: '请上传分类页图片' };
                    return;
                }
                if(this.errorData.categoryName.isError){
                    return
                }
                setCategory(this.editor_classify).then(res =>{
                    if(res.data.data.success){
                        this.hideDialog('isShowCategoryEdit');
                        this.$Message.success('编辑成功');
                        this.get_classify(this.pageData.param.currentPage);
                    }else{
                        this.$Message.error(res.data.data.message);
                    }
                })
            },
            // 获取类别列表
            get_classify (currentPage){
                this.pageData.param.currentPage = currentPage;
                getCategoryList(this.pageData.param).then(res =>{
                    if(res.data.data.success){
                        const re = res.data.data.data;
                        this.pageData.param.dataLoading = false;
                        this.pageData.classify_list = re.data;
                        this.pageData.param.currentPage = re.currentPage;
                        this.pageData.param.totalPages = re.pages;
                    }
                })
            },
            // 删除类别
            del_classify (id){
                delCategory(id).then(res =>{
                    if(res.data.data.success){
                        this.$Message.success('编辑成功');
                        this.get_classify(this.pageData.param.currentPage);
                    }else{
                        this.$Message.error(res.data.data.message);
                    }
                })
            }
        },
        mounted (){
            this.get_classify(1)
        },
        components: {
            'v-page': page
        },
        watch: {
            'editor_classify.categoryName'(val, oldVal) {
                if (val.length <= 0) {
                    this.errorData.categoryName = {isError: true, isSuccess: false, txt: '标题不能为空'};
                } else {
                    this.errorData.categoryName = {isError: false, isSuccess: true, txt: ''};
                }
            },
            AddPictures (val, oldVal) {
                this.editor_classify.categoryBannerShow = val;
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

    .loading-enter-active, .loading-leave-active {
        transition: opacity .3s
    }
    .loading-enter, .loading-leave-active {
        opacity: 0
    }
    .control-label {
        &.wrap {
            white-space: normal;
            line-height: 1.3;
        }
    }

</style>
