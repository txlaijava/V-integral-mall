<template>
  <div class="dialog dialog--confirm" v-if="isShow">
    <div class="msk"></div>
    <div class="dialog__container">
      <div class="dialog__hd">
        <slot name="hd">empty</slot>
      </div>
      <div class="dialog__bd t-333">
        <slot name="bd">empty</slot>
      </div>
      <template v-if="footerType == 0">
        <div class="dialog__ft">
          <div class="dialog__cell">
            <a class="dialog__btn dialog__cancel-btn" @click="closeMyself">取消</a>
            <a class="dialog__btn dialog__confirm-btn t-red" @click="handleConfirm ">确定</a>
          </div>
        </div>
      </template>
      <template v-else-if="footerType == 1">
        <i class="iconfont icon-close close" v-if="!isShowFt" @click="closeMyself"></i>
      </template>
      <template v-else>
        <div class="iconfont close2" @click="closeMyself">&#xe68f;</div>
      </template>
    </div>
  </div>
</template>

<script>
    export default{
        props: ["isShow","isShowFt","footerType"],
        methods: {
            closeMyself() {
                this.$emit('on-close')
            },
            handleConfirm() {
                this.$emit('on-confirm')
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .dialog--confirm {
        width: 100vw;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 99;
        .dialog__container {
            background: #FFF;
            width: 6rem;
            position: absolute;
            left: 0.75rem;
            top: 47%;
            -webkit-border-radius: 0.1rem;
            border-radius: 0.1rem;
            overflow: hidden;
            -moz-animation: .5s fadeInUp forwards;
            -ms-animation: .5s fadeInUp forwards;
            -o-animation: .5s fadeInUp forwards;
            -webkit-animation: .5s fadeInUp forwards;
            animation: .5s fadeInUp forwards;
            .dialog__hd {
                padding: 0.48rem 0.46rem 0;
                text-align: center;
                .close{
                    position: absolute;
                    right: 0.3rem;
                    top: 0.26rem;
                    font-size: 0.34rem;
                }
                strong {
                    font-size: 0.34rem;
                }
            }
            .dialog__bd {
                padding: 0.36rem 0.46rem 0.46rem;
                font-size: 0.3rem;
                text-align: center;
                .object-exchange-dialog__bd {
                    text-align: left;
                    p {
                        font-size: 0.3rem;
                        margin-bottom: 0.24rem;
                        align-items: flex-start;
                        label{
                            width: 1.42rem;
                            white-space: nowrap;
                        }
                        .flex__item{

                        }
                    }
                }

            }
            .dialog__ft {
                border-top: solid #e5e5e5;
                border-width: .5px;
                color: #666;
                .dialog__cell {
                    display: table;
                    width: 100%;
                    height: 1rem;
                    .dialog__btn {
                        display: table-cell;
                        text-align: center;
                        font-size: 0.36rem;
                        vertical-align: middle;
                        & + .dialog__btn {
                            border-left: solid #EFEFEF;
                            border-width: 0.5px;
                        }
                        &:active {
                            background-color: #EEE;
                        }
                    }
                }
            }
        }
    }
    @-webkit-keyframes fadeInUp{from{opacity:0;-webkit-transform:translateY(100%);transform:translateY(100%);}
        to{opacity:1;-webkit-transform:translateY(-50%);transform:translateY(-50%);}
    }
    @keyframes fadeInUp{from{opacity:0;-webkit-transform:translateY(100%);transform:translateY(100%);}
        to{opacity:1;-webkit-transform:translateY(-50%);transform:translateY(-50%);}
    }
</style>
