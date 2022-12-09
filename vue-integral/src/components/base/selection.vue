<!--
  ----------select下拉组件----------
声明组件：<v-selection :selections="Array"  v-model="value" :name="name"></v-selection>
  参数:Array:[
            {label: '商品类型', value: ''},
          {label: '实物类', value: '1'},
          {label: '虚拟类', value: '2'},
          {label: '优惠券', value: '3'}]
       value:'2' 通过value的值进行默认选中，父组件通过value传值和取值，选中后会更新父组件传入的value
       name:''  隐藏域的name值
-->


<template>
  <div class="select-group" @mouseleave="toggleClean">
    <input type="hidden" :name="name" v-model="nowIndex">
    <a class="select-toggle"  @mouseenter="toggleDrop" >{{ selections[selectIndex].label }}<i class="iconfont icon-dropDown"></i></a>
    <ul class="select-menu" v-if="isDrop">
      <li v-for="(item, index) in selections" @click="chooseSelection(index)"  :class=" index==selectIndex ? 'active' : '' ">
        <a>
          <i class="iconfont icon_checked" v-if="index == selectIndex"></i>
          {{ item.label }}
        </a>
      </li>
    </ul>
  </div>

</template>

<script>
  export default{
    props: {
      selections: {
        type: Array,
        default: [{
          label: 'test',
          value: ''
        }]
      },
      name: {type: String, default: ''},    //隐藏域name
    },
    data () {
      return {
        isDrop: false,
        nowIndex: '',
        selectIndex:0
      }
    },
    methods: {
      toggleDrop () {
        this.isDrop = !this.isDrop
      },
      toggleClean(){
        this.isDrop = false;
      },
      chooseSelection (index) {
        this.selectIndex = index;
        this.isDrop = false;
        this.nowIndex = this.selections[this.selectIndex].value;
        this.$emit("selectCallBack",this.selections[this.selectIndex].value)
      }
    },mounted () {
        for(var i = 0;i<this.selections.length;i++){
            if(this.selections[i].value == this.nowIndex) {
              this.selectIndex = i;
            }
        }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  .form-filter-wrap{
    .form-combination{
      .select-group{
        .select-toggle{
          border-right: 0;
          border-top-right-radius: 0;
          border-bottom-right-radius: 0;
        }
      }
    }
  }
  .money-input-wrap{
    display: inline-block;
    .form-control {
      width: 100px!important;
    }
  }
</style>
