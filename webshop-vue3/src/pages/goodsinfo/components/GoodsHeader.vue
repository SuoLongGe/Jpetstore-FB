<template>
  <div class="container-fluid" style="height: 11vh; background-color: rgb(243, 246, 248);">
    <PageHeader title="商品详情" />
    <!-- 搜索表单 -->
    <div style="position: absolute; top: 8vh; right: 20vw; width: 40vw;">
      <form class="d-flex justify-content-center" @submit.prevent="handleSearch">
        <input v-model="searchKeyword" class="form-control me-2" type="text" placeholder="请输入搜索内容..." style="width: 70%; height: 5vh; font-size: 1.5vh;">
        <button class="btn btn-primary" type="submit" style="height: 5vh; background-color: rgb(255, 98, 0); border: none; font-size: 1.5vh;">
          搜索
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/layouts/components/pageHeader.vue'

const router = useRouter()
const searchKeyword = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/',
      query: { search: searchKeyword.value.trim() }
    })
  }
}
</script>
