<!--template : 节点中间用于放置 html 代码；-->
<template>
  <!-- 设置语言为中文 -->
  <el-config-provider :locale="locale">
    <router-view></router-view>
  </el-config-provider>
</template>

<!--script : 节点中间用于放置 javascript 代码；-->
<script setup>
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
const locale = zhCn

</script>

<!-- style : 节点中间用于放置 css 样式代码； -->
<style scoped>
/* 自定义顶部加载 Loading 颜色 */
#nprogress .bar {
   background: #409eff!important;
}

</style>
