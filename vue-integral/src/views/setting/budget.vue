<template>
  <div class="main">
    <div class="page-title">
      <h2> 预算控制 </h2>
    </div>
    <div class="page-content setting-budget">
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">每日支出上限：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control valid untouched pristine" placeholder="应用每天最高可消耗的金额，不填为不限制">
            <div class="input-group-addon">元</div>
          </div>
          <p class="form-group-tip">应用每天可消耗的最高金额，不填则不做限制</p>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">每月支出上限：</label>
          <div class="input-with-addon">
            <input type="text" class="form-control valid untouched pristine" placeholder="应用每个自然月最高可消耗的金额，不填为不限制">
            <div class="input-group-addon">元</div>
          </div>
          <p class="form-group-tip">应用每个自然月最高可消耗的金额，不填为不限制</p>
        </div>
      </div>
      <div class="form-groups-chunk">
        <div class="form-group">
          <label class="control-label">单笔交易审核下限：</label>
          <div class="input-with-addon">
            <input type="text"  class="form-control valid untouched dirty modified form-control-dirty" placeholder="单笔交易达到多少金额需要审核">
            <div class="input-group-addon">元</div>
          </div>
          <p class="form-group-tip">每笔交易的消费金额超过多少需要审核，0为每笔需要扣费的都审核</p></div>
      </div>
      <div class="form-group mt20"><a href="javascript:void(0)" class="btn btn-green btn-lg">保存配置</a>
      </div>
    </div>
  </div>
</template>

<script>

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "../../style/configure";
</style>
