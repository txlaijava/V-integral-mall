<template>
  <div class="express-template">
    <div class="template-list" v-if="isHideTemplate">
      <p class="cf">
        <a class="add-template-link fr" @click="addTemplate"><i class="iconfont icon-add"></i> 添加模板 </a>
      </p>
    </div>
    <div class="express-template" v-else>
      <div class="template-edit-form">
        <div class="form-groups-chunk">
          <div class="form-group">
            <label class="control-label">模板名称：</label>
            <input type="text" class="form-control invalid untouched pristine" placeholder="运费模板的名称，限16字"
                   maxlength="16">
            <table class="mt20">
              <thead>
              <tr class="tr-header">
                <th>
                  配送地区
                </th>
                <th class="th-price">
                  运费（元）
                </th>
              </tr>
              </thead>
              <tbody>
              <tr class="tr-items">
                <td>
                  默认运费
                </td>
                <td>
                  <input type="text" class="form-control price-control valid untouched pristine">
                </td>
              </tr>
              <tr class="tr-items">
                <td colspan="2">
                  <a href="javascript:void(0)" class="add-express" @click="showDialog('isShowRegio')">指定新的配送地区</a>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="form-group mt20">
          <a href="javascript:void(0)" class="btn btn-green btn-lg mr20">保存</a>
          <a class="btn btn-default btn-lg v-link-active" @click="defaultBtn">取消</a>
        </div>
        <v-dialog width="500px" modalTitle="指定新的配送区域" ref="dialog" :showFooter="true" :is-show="isShowRegio"
                  @on-close="hideDialog('isShowRegio')">
          <div class="modal-body">
            <div class="region-selector">
              <div class="form-group bottomline">
                <label class="control-label">请选择地域：</label>
                <v-distpicker hide-area :placeholders="placeholders" @selected="onSelected"></v-distpicker>
                <a class="btn btn-default btn-lg" @click="addArea">添加</a>
              </div>
              <div class="region-selected">
                <p class="error-region-tip c-orange" style="display: none;">
                  请选择省份
                </p>
                <p class="error-region-tip" style="display: none;">
                  已添加
                  <span class="c-orange"></span> 全省范围，无需添加 <span class="c-orange"></span>
                </p>
                <div class="area-tags" v-if="isShowAreaTags">
                  <a href="javascript:void(0)" class="area-tag" v-for="(item,index) in areaTags">{{item.areaTag}}
                    <i class="iconfont" @click="colseMyself(index)">&#xe694;</i>
                  </a>
                </div>
                <p class="empty-region-tip" v-else>
                  您还未选择地域
                </p>
              </div>
              <div role="dialog" class="modal fade" style="display: none;">
                <div class="modal-dialog" role="document" style="width: 300px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <i class="iconfont close icon-close"></i>
                      <h4 class="modal-title"> 添加undefined </h4>
                    </div>
                    <div class="modal-body">
                      <p>
                        已选列表存在属于
                        <span class="c-orange"></span> 的城市：
                      </p>
                      <p>
                      </p>
                      <p>
                        添加“全省范围”后将被全部清除。
                      </p>
                    </div>
                    <div class="modal-footer">
                      <a class="btn btn-green btn-pd-lg mr15">确定</a><a class="btn btn-default btn-pd-lg">取消</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <p class="invalid">
            </p>
          </div>
        </v-dialog>
      </div>
    </div>
  </div>
</template>

<script>
  import VDistpicker from 'v-distpicker'
  export default{
    data () {
      return {
        isHideTemplate: true,
        isShowRegio: false,
        placeholders: {
          province: '请选择省份',
          city: '请选择城市'
        },
        isShowAreaTags: false,
        areaTags: [{areaTag:''}],
        province:''
      }
    },
    methods: {
      addTemplate () {
        this.isHideTemplate = false
      },
      defaultBtn () {
        this.isHideTemplate = true
      },
      showDialog (param) {
        this[param] = true;
        this.$refs.dialog.loadClient();
      },
      hideDialog (param) {
        this[param] = false
      },
      onSelected(data) {
        this.placeholders.province = data.province.value;
        this.placeholders.city = data.city.value;
      },
      addArea () {
        var that= this
        if( this.areaTags[0].areaTag == ''){
          this.areaTags[0].areaTag = this.placeholders.city || this.placeholders.province
        }else {
          this.areaTags.push({
            areaTag: that.placeholders.city || this.placeholders.province
          })
        }
        this.isShowAreaTags = true
      },
      colseMyself(index) {
        this.areaTags.splice(index, 1);
      }
    },
    watch: {
      areaTags(newVal){
        if (newVal.length == 0) {
          this.isShowAreaTags = false
        }
      }
    },
    components: {
      'v-distpicker': VDistpicker
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../../../style/configure";

</style>
