<template>
  <div class="d-flex align-items-center p-2">
    <RouterLink to="/">
      <img :src="logo" alt="Logo" style="width: 100px; height: auto; margin-right: 20px;">
    </RouterLink>
    <h2 class="title">{{ title }}</h2>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router'

defineProps({
  logo: {
    type: String,
    default: '/src/assets/Jiejie.jpg'
  },
  title: {
    type: String,
    required: true
  }
})
</script>

<style scoped>
.title {
  margin: 0;
  color: rgb(255, 98, 0);
  font-size: 2.0em;
  font-weight: bold;
}
</style>
