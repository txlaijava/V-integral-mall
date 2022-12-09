<template>
  <div class="side-toolbar-wrapper">
    <ul class="tool-list">
      <li class="tool-item item-guide"><i class="iconfont icon-guide"></i>操作指南</li>
      <li class="tool-item item-schedule"><i class="iconfont icon-schedule"></i>计划任务</li>
      <li class="tool-item item-helper">
        <div class="btn-helper"><i class="iconfont icon-qq"></i>
          <div class="tip-wrap contact-qq-info sidetoolbar-fade-transition" style="display: none;"><h3>QQ咨询</h3>
            <div><a target="_blank" class="btn btn-default"
                    href="//wpa.qq.com/msgrd?v=3&amp;uin=2850393282&amp;site=qq&amp;menu=yes" alt="点击这里给我发消息"
                    title="点击这里给我发消息"> <i class="iconfont icon-tech"></i> 技术咨询 </a></div>
          </div>
        </div>
        <div class="btn-helper"><i class="iconfont icon-phone"></i>
          <div class="tip-wrap contact-phone-info sidetoolbar-fade-transition" style="display: none;"><h3>电话咨询</h3>
            <p>0571-28258687</p></div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .side-toolbar-wrapper {
    position: fixed;
    z-index: 101;
    right: 0;
    top: 0;
    bottom: 0;
    .tool-list {
      position: absolute;
      top: 50%;
      right: 0;
      margin-top: -101px;
      background-color: #fff;
      border: 1px solid #e9e9e9;
      text-align: center;
      font-size: 12px;
      transition: all .5s ease;
      .tool-item {
        font-size: 12px;
        width: 16px;
        padding: 20px 10px;
        border-bottom: 1px solid #e9e9e9;
        cursor: pointer;
        .iconfont {
          display: inline-block;
          &.icon-guide {
            margin: 0 0 6px 1px;
          }
          &.icon-schedule {
            margin: 0 0 6px 2px;
          }
          .tip-wrap {
            min-width: 80px;
            color: #fff;
            position: absolute;
            top: 0;
            left: -20px;
            background: #444;
            padding: 10px;
            border-radius: 4px;
            &.contact-qq-info {
              -webkit-transform: translateX(-100%) translateY(-44%);
              transform: translateX(-100%) translateY(-44%);
              width: 106px;
              padding-bottom: 15px;
              a {
                margin-top: 10px;
                &.btn-default {
                  background: #fff;
                  color: #444;
                  border: 1px solid #ccc;
                }
              }
            }
            .sidetoolbar-fade-transition {
              transition: all .3s ease;
              opacity: 1;
            }
            &.contact-phone-info {
              -webkit-transform: translateX(-100%) translateY(-35%);
              transform: translateX(-100%) translateY(-35%);
              width: 106px;
            }
          }
        }
        &.item-helper{
          .btn-helper {
            background: #fff;
            position: relative;
            margin-bottom: 20px;
            &:last-child {
              margin-bottom: 0;
            }
            .icon-phone {
              font-size: 14px;
            }
          }
        }
      }
    }
  }
</style>
