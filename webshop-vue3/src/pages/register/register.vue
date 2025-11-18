<template>
  <div class="container-fluid bg-light vh-100 d-flex justify-content-center align-items-center">
    <PageHeader
      logo="/src/assets/Jiejie.jpg"
      title="用户注册"
    />
    
    <div class="login-container">
      <div class="container-fluid vh-100 d-flex justify-content-center align-items-center">
        <RegisterForm />
      </div>
    </div>
  </div>
</template>

<script setup>
import PageHeader from '@/layouts/components/pageHeader.vue'
import RegisterForm from '@/layouts/components/registerForm.vue'
</script>

<style scoped>
/* 可以添加页面特定的样式 */
.login-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 2rem;
}
</style>
