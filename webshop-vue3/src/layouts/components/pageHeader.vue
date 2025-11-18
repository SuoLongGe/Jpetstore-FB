<template>
    <div style="display: flex; align-items: center; position: absolute; top: 20px; left: 20px;">
      <router-link to="/" style="display: flex; background: none; border: none; padding: 0; cursor: pointer;">
        <img :src="logo" alt="Logo" style="width: 100px; height: auto;"/>
      </router-link>
      <h1 class="title" style="margin-left: 20px; margin-top: 0; margin-bottom: 0;">{{ title }}</h1>
    </div>
  </template>
  
  <script setup>
  defineProps({
    logo: {
      type: String,
      default: '/src/assets/Jiejie.jpg'
    },
    title: {
      type: String,
      required: true
    }
  })
  </script>
  
  <style scoped>
 
  .title {
    display: inline-block;
  
    margin-left: 20px;
    white-space: nowrap;
    color: rgb(255, 98, 0);
    font-size: 2.0em;
    font-weight: bold;
  }
  </style>