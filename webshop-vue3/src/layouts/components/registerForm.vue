<template>
  <div class="card border-0" style="width: 500px;min-height: 63.5%; background-color: white;">
    <form @submit.prevent="handleSubmit">
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <input 
            v-model="store.registerForm.username" 
            class="form-control" 
            placeholder="请输入账号" 
            style="width: 100%;"
          >
          <span v-if="store.errors.username" class="error-message">
            {{ store.errors.username }}
          </span>
        </div>
      </div>
      
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <input 
            v-model="store.registerForm.password" 
            type="password" 
            class="form-control" 
            placeholder="请输入密码" 
            style="width: 100%;"
          >
          <span v-if="store.errors.password" class="error-message">
            {{ store.errors.password }}
          </span>
        </div>
      </div>
      
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <input 
            v-model="store.registerForm.name" 
            class="form-control" 
            placeholder="请输入昵称" 
            style="width: 100%;"
          >
          <span v-if="store.errors.name" class="error-message">
            {{ store.errors.name }}
          </span>
        </div>
      </div>
      
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <input 
            v-model="store.registerForm.email" 
            class="form-control" 
            placeholder="请输入邮箱" 
            style="width: 100%;"
          >
          <span v-if="store.errors.email" class="error-message">
            {{ store.errors.email }}
          </span>
        </div>
      </div>
      
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <input 
            v-model="store.registerForm.address" 
            class="form-control" 
            placeholder="请输入地址" 
            style="width: 100%;"
          >
          <span v-if="store.errors.address" class="error-message">
            {{ store.errors.address }}
          </span>
        </div>
      </div>
      
      <div class="container-fluid">
        <div class="mb-3 mt-3">
          <CaptchaInput 
            :modelValue="store.registerForm.captcha" 
            :error="store.errors.captcha" 
            @update:modelValue="val => store.registerForm.captcha = val" 
          />
        </div>
      </div>
      
      <button type="submit" class="btn btn-primary my-5" style="width: 100%;height: 5.5vh; background-color: rgb(255, 197, 127); border: none;font-size: 1.1em;">
        注册
      </button>

      
      
      <div class="container-fluid mb-3 mt-3">
        <input 
          type="checkbox" 
          id="agreement" 
          v-model="store.registerForm.agreement" 
          required
        >
        <label for="agreement" style="font-size: 0.9em;">
          已阅读并同意以下协议
        </label>
        <router-link 
          to="/useragreement" 
          style="color: rgb(254, 187, 133); font-size: 0.9em;"
        >
          用户协议
        </router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRegisterStore } from '@/stores/registerStore'
import CaptchaInput from '@/layouts/components/captchaInput.vue'
import { showMessage } from '@/composables/util'
import { POST } from '@/api/tool'
import { useRouter } from 'vue-router'

const router = useRouter()


const store = useRegisterStore()

const validateForm = () => {
  let isValid = true
  const usernameRegex = /^[a-zA-Z0-9]+$/
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

  // 重置错误信息
  store.errors = {}

  if (!store.registerForm.username) {
    store.errors.username = '请输入账号'
    isValid = false
  } else if (!usernameRegex.test(store.registerForm.username)) {
    store.errors.username = '账号只能包含数字或英文'
    isValid = false
  }

  if (!store.registerForm.password) {
    store.errors.password = '密码不能为空'
    isValid = false
  }

  if (!store.registerForm.name) {
    store.errors.name = '昵称不能为空'
    isValid = false
  }

  if (!store.registerForm.email) {
    store.errors.email = '邮箱不能为空'
    isValid = false
  } else if (!emailRegex.test(store.registerForm.email)) {
    store.errors.email = '邮箱格式不正确'
    isValid = false
  }

  if (!store.registerForm.address) {
    store.errors.address = '地址不能为空'
    isValid = false
  }

  if (!store.registerForm.captcha || store.registerForm.captcha.trim() === '') {
    store.errors.captcha = '验证码不能为空'
    isValid = false
  }

  if (!store.registerForm.agreement) {
    showMessage('请阅读并同意用户协议', 'error')
    isValid = false
  }

  return isValid
}

const handleSubmit = async () => {
  if (!validateForm()) return

  try {
    const response = await POST(`/users/${store.registerForm.username}`, {
      "password": store.registerForm.password,
      "name": store.registerForm.name, 
      "email": store.registerForm.email,
      "address": store.registerForm.address,
      "captcha": store.registerForm.captcha,
    })
    if (response.success) {
      showMessage('注册成功', 'success')
      // 可以添加跳转登录页面的逻辑
      router.push('/login')
    } else {
      showMessage(response.message, 'error')
    }
  } catch (error) {
    showMessage('注册失败，请稍后再试', 'error')
    console.error('注册失败:', error)
  }
}

</script>

<style scoped>
.error-message {
  display: block;
  margin-top: 0.25rem;
  color: red;
  font-size: 1.0em;
}

input[type="password"] {
  font-family: inherit;
  letter-spacing: 0.1em;
}

input[type="password"]::-ms-reveal,
input[type="password"]::-ms-clear {
  display: none;
}

.form-control {
  font-size: 1em;
  padding: 0.5em;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control:focus {
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
</style>
