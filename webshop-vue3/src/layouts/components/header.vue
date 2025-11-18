<template>
    <nav class="navbar navbar-expand-sm bg-light no-padding" >
        <ul class="navbar-nav w-100 justify-content-between">
        <li class="nav-item">
            <span v-if="userInfo" class="user-info-name">
            欢迎, {{ userInfo.name }}
            </span>
            <button v-else @click="$router.push('/login')" class="button-link">
            登录
            </button>
        </li>
        <li v-if="userInfo" class="nav-item">
            <button @click="handleLogout" class="button-link">退出登录</button>
        </li>
        <li class="nav-item">
            <button @click="$router.push('/c/order')" class="button-link">我的订单</button>
        </li>
        <li class="nav-item">
            <button @click="$router.push('/c/shoppingcart')" class="button-link">购物车</button>
        </li>
        <li class="nav-item">
            <button @click="$router.push('/c/usersetting')" class="button-link">设置</button>
        </li>
        </ul>
    </nav>
      
</template>

<style scoped>
.navbar-expand-sm {
    padding: 0.0rem 0.1rem;
}
.button-link{
    font-size: 0.75em; 
    background-color: transparent;
    border: none;
    color: inherit; 
    cursor: pointer;
}
.user-info-name{
    font-size: 0.75em;
}
</style>

<script setup>
import { ref, onMounted } from 'vue'
import { GET } from '@/api/tool'
import { getToken, getUserInfo, setUserInfo, removeAuthData } from '@/composables/cookie'

const userInfo = ref(getUserInfo())

const fetchUserInfo = () => {
  const token = getToken()
  if (token) {
    GET('/c/users').then(res => {
      setUserInfo(res.data)
      userInfo.value = res.data
    }).catch(err => {
      console.error('获取用户信息失败:', err)
    })
  }
}

onMounted(() => {
  fetchUserInfo()
})

const handleLogout = () => {
  removeAuthData()
  userInfo.value = null
  
  window.location.reload()
}

</script>
