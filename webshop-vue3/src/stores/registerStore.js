import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useRegisterStore = defineStore('register', () => {
  // Token管理
  const token = ref(localStorage.getItem('token') || '')
  
  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }
  // 注册表单数据
  const registerForm = ref({
    username: '',
    password: '',
    name: '',
    email: '',
    address: '',
    captcha: '',
    agreement: false
  })
  
  // 登录表单数据
  const loginForm = ref({
    username: '',
    password: '',
    captcha: ''
  })
  
  // 错误信息
  const errors = ref({})
  
  // 更新模拟数据
  function updateMockData(newData, isLogin = false) {
    if (isLogin) {
      Object.assign(loginForm.value, newData)
    } else {
      Object.assign(registerForm.value, newData)
    }
  }
  
  // 模拟登录
  async function mockLogin() {
    return new Promise((resolve) => {
      setTimeout(() => {
        setToken('mock-token')
        resolve(true)
      }, 500)
    })
  }

  return { 
    registerForm, 
    loginForm, 
    errors, 
    updateMockData,
    mockLogin,
    token
  }
})