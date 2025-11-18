<template>
    <div class="card" style="width: 500px;min-height: 50%; background-color: white;">
      <div class="card-body">
        <div class="container-fluid my-4">
            <p class="d-flex justify-content-center" style="color: rgb(255, 98, 0);font-size: 2.0em;font-weight: bold;">密码登录</p>
        </div>
        <form @submit.prevent="handleSubmit">
            <div class="container-fluid my-2">
                <div class="mb-3 mt-3">
                    <label class="form-label" style="font-size: 1.2em;">账号：</label>
                    <input
                        v-model="store.loginForm.username"
                        class="form-control my-2"
                        placeholder="请输入账号"
                        style="width: 100%;"
                    />
                    <span v-if="store.errors.username" class="error-message" style="color: red;font-size: 1.0em">
                    {{ store.errors.username }}
                    </span>
                </div>  
            </div>
  
            <div class="container-fluid my-2">
                <div class="mb-3 mt-3">
                    <label class="form-label" style="font-size: 1.2em;">密码：</label>
                    <input
                        v-model="store.loginForm.password"
                        type="password"
                        class="form-control my-2"
                        placeholder="请输入密码"
                        style="width: 100%"
                    />
                    <span v-if="store.errors.password" class="error-message" style="color: red;font-size: 1.0em">
                        {{ store.errors.password }}
                    </span>
                </div>
            </div>

            <div class="container-fluid my-2">
                <div class="mb-3 mt-3">
                    <CaptchaInput 
                        :modelValue="store.loginForm.captcha" 
                        :error="store.errors.captcha" 
                        @update:modelValue="val => store.loginForm.captcha = val" 
                    />
  
                </div>
            </div>
            <div class="container-fluid my-2">
                <button type="submit" class="submit-btn" style="width: 100%;height: 6.5vh; background-color: rgb(255, 98, 0); border: none;font-size: 1.1em;">登录</button>
            </div>

            <div class="container-fluid my-2">
                <button @click="handleGiteeLogin" type="button" class="submit-btn" style="width: 100%;height: 6.5vh; background-color: #c71d23; border: none;font-size: 1.1em;color: white; display: flex; align-items: center; justify-content: center;">
                    <img src="https://gitee.com/favicon.ico" alt="Gitee" style="width: 20px; height: 20px; margin-right: 8px;">
                    使用Gitee登录
                </button>
            </div>
          
        </form>
  
        <div class="container-fluid my-1">
            <router-link to="/register" style="background: none; border: none; padding: 0; cursor: pointer; font-size: 0.8em; text-align: left;">
                没有账号?点击注册
            </router-link>
        </div>
        
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRegisterStore } from '@/stores/registerStore'
  import CaptchaInput from '@/layouts/components/captchaInput.vue'
  import { useRouter } from 'vue-router'
import { GET, POST } from '@/api/tool'
import { showMessage } from '@/composables/util'
import { setToken } from '@/composables/cookie'
  
  const router = useRouter()
  const store = useRegisterStore()
  
  const validateForm = () => {
    let isValid = true
    const usernameRegex = /^[a-zA-Z0-9]+$/
  
    // 重置错误信息
    store.errors = {}
  
    if (!store.loginForm.username) {
      store.errors.username = '请输入账号'
      isValid = false
    } else if (!usernameRegex.test(store.loginForm.username)) {
      store.errors.username = '账号只能包含数字或英文'
      isValid = false
    }
  
    if (!store.loginForm.password) {
      store.errors.password = '密码不能为空'
      isValid = false
    }
  
    if (!store.loginForm.captcha || store.loginForm.captcha.trim() === '') {
      store.errors.captcha = '验证码不能为空'
      isValid = false
    } else {
      store.errors.captcha = ''
    }
  
    return isValid
  }
  
  const handleGiteeLogin = () => {
      window.open(
          'https://gitee.com/oauth/authorize?client_id=26c76ab8dd757ae7abf4f94405ab8389cf2c0967215de16fd453488052ba39ca&redirect_uri=http://localhost:5173/oauth/callback&response_type=code',
          'GiteeLogin'
      )
  }

  const handleSubmit = async () => {
    if (!validateForm()) return
  
    console.log("handleSubmit")

    POST('/tokens', {
      "username": store.loginForm.username,
      "password": store.loginForm.password,
      "captcha": store.loginForm.captcha
    }).then((response) => {
        if (response.success == true) {
          setToken(response.data.token)
          router.push('/')
        } else if(response.success == false) {
          showMessage(response.message, 'error')
        }else {
          console.log("error")
        }
    }) 
  }
  </script>
  
<style scoped>
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

.error-message {
  display: block;
  margin-top: 0.25rem;
}
</style>
