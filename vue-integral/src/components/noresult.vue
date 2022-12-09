<template>
  <div class="row-noresult"><i class="empty-elephant"></i>
    <p>没有符合条件的记录~</p>
  </div>
</template>

<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .row-noresult {
    background-color: #fff;
    text-align: center;
    padding: 103px 0 186px;
    .empty-elephant {
      width: 167px;
      height: 136px;
      display: inline-block;
      background: url('../../static/empty.png') no-repeat 50%;
      background-size: 100%;
      position: relative;
      /*left: 20px;*/
    }
    p {
      margin-top: 20px;
      font-size: 12px;
      color: #888;
    }
  }
</style>
