<template>
  <div class="container-fluid d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="text-center">
      <div v-if="loading" class="spinner-border text-primary" role="status">
        <span class="visually-hidden">加载中...</span>
      </div>
      <div v-if="error" class="alert alert-danger mt-3">
        {{ error }}
      </div>
      <div v-if="success" class="alert alert-success mt-3">
        Gitee账户绑定成功！
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { GET } from '@/api/tool'

const route = useRoute()
const router = useRouter()
const loading = ref(true)
const error = ref('')
const success = ref(false)

onMounted(async () => {
  try {
    const code = route.query.code
    if (!code) {
      throw new Error('未获取到授权码')
    }

    const response = await GET(`/c/oauth/gitee/bind?code=${code}`,{})

    if (response.success) {
      success.value = true
      // 绑定成功后跳转回用户设置页
      setTimeout(() => {
        window.location.href = '/'
      }, 1000)
    } else {
      throw new Error(response.message || '绑定Gitee账户失败')
    }
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
})
</script>
