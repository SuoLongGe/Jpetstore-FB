<template>
  <div class="container-fluid" style="min-height: 100vh;">
    <Header />
    <UserSettingHeader />
    
    <div class="d-flex justify-content-center">
      <div style="width: 30%;">
        <UserSettingForm 
          v-if="userInfo"
          :user-info="userInfo"
          :loading="loading"
          :error="error"
          @submit="handleSubmit"
          @gitee-bind="handleGiteeBind"
        />
        <div v-else class="text-center py-5">
          加载用户信息中...
        </div>
      </div>
    </div>
    
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserInfo } from '@/composables/cookie.js'
import Header from '@/layouts/components/header.vue'
import Footer from '@/layouts/components/footer.vue'
import UserSettingHeader from './components/UserSettingHeader.vue'
import UserSettingForm from './components/UserSettingForm.vue'
import { PATCH } from '@/api/tool'

const userInfo = ref(null)

onMounted(() => {
  // 从cookie获取用户信息
  userInfo.value = getUserInfo()
})

const loading = ref(false)
const error = ref('')

const handleGiteeBind = async () => {
  try {
    loading.value = true
    error.value = ''
    // 直接跳转到Gitee授权页面
    window.location.href = 'https://gitee.com/oauth/authorize?client_id=26c76ab8dd757ae7abf4f94405ab8389cf2c0967215de16fd453488052ba39ca&redirect_uri=http://localhost:5173/c/usersetting/gitee-callback&response_type=code'
  } catch (err) {
    error.value = err.message
    console.error('处理Gitee账户绑定失败:', err)
  } finally {
    loading.value = false
  }
}

const handleSubmit = async (formData) => {
  try {
    loading.value = true
    error.value = ''
    
    // 构造URL参数
    const params = new URLSearchParams()
    params.append('name', formData.name)
    params.append('email', formData.email)
    params.append('address', formData.address)
    
    // 发送请求
    const response = await PATCH(`/c/users?${params.toString()}`, {
      password: formData.password
    })
    
    if (response.success) {
      // 更新成功处理
      console.log('用户信息更新成功:', response.data)
      // 可以添加成功提示或跳转逻辑
      window.location.href = '/c/usersetting'
    } else {
      throw new Error(response.message || '更新用户信息失败')
    }
  } catch (err) {
    error.value = err.message
    console.error('更新用户信息失败:', err)
  } finally {
    loading.value = false
  }
}
</script>
