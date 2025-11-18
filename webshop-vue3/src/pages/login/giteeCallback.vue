<template>
  <div class="container">
    <p>正在处理Gitee登录...</p>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

import { GET } from '@/api/tool'
import { setToken,getToken,setUserInfo } from '@/composables/cookie'
import { useRouter } from 'vue-router'


const router = useRouter()

console.log('Gitee回调页面接收到code:', route.query.code)

onMounted(async () => {
  console.log('Gitee回调页面已加载')
  console.log('完整URL:', window.location.href)
  const code = route.query.code
  console.log('从路由获取的code参数:', code)
  console.log('完整的route.query:', route.query)
  
  if (!code) {
    console.error('未获取到授权码')
    router.push('/login')
    return
  }
  if (getToken()) {
    console.log(getToken())
  }

  try {
    const response = await GET('/oauth/gitee?code=' + code)
    if (response.success) {
      const token = response.data.token
      setToken(token)
      console.log('Gitee登录成功:', token)
      console.log('Cookie设置后立即检查:', document.cookie)
      console.log('Token设置验证:', getToken() === token)

      // 强制刷新页面确保状态同步
      window.location.href = '/'
      
    } else {
      console.error('Gitee登录失败:', response.message)
      router.push('/login?error=' + encodeURIComponent(response.message))
    }
  } catch (error) {
    console.error('请求失败:', error)
    router.push('/login?error=' + encodeURIComponent(error.message))
  }
})
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
