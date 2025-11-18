<template>
  <div
    class="hover-window"
    :style="{ left: position.x + 'px', top: position.y + 'px' }"
  >
    <div v-for="(item, index) in productDetails" :key="index" class="product-info">
      <div class="product-name">商品名称：{{ item.productname.length > 10 ? item.productname.substring(0,10)+'...' : item.productname }}</div>
      <div class="product-price">价格：{{ item.productprice }}元</div>
    </div>
  </div>
</template>
  
<script setup>
defineProps({
  productDetails: {
    type: Array,
    required: true
  },
  position: {
    type: Object,
    required: true
  }
})
  </script>
  
  <style scoped>
  .hover-window {
    position: fixed;
    border: 1px solid #ccc;
    background-color: rgba(255, 255, 255, 0.9);
    padding: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    max-width: 250px;
    z-index: 1000;
  }
  
  .product-info {
    display: flex;
    flex-direction: column;
    gap: 4px;
    font-size: 14px;
    color: #333;
    padding: 8px 0;
    border-bottom: 1px solid #eee;
  }
  
  .product-info:last-child {
    border-bottom: none;
  }
  
  .product-name {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 200px;
  }

  .loading {
    padding: 8px;
    text-align: center;
    color: #666;
  }
  </style>
