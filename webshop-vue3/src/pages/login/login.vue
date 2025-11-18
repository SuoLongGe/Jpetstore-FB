<template>
  <div class="container-fluid bg-light vh-100 d-flex justify-content-center align-items-center">
    <PageHeader
      logo="/src/assets/Jiejie.jpg"
      title="用户登录"
    />
    
    <div class="login-container">
      <LoginForm />
    </div>
  </div>
</template>

<script setup>
import PageHeader from '@/layouts/components/pageHeader.vue'
import LoginForm from '@/layouts/components/loginForm.vue'
</script>

<style scoped>
.login-page {
  background-color: #f8f9fa;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 2rem;
}

.login-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 2rem;
}
</style>