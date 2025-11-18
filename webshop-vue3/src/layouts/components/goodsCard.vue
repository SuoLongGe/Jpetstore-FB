<template>
    <div class="col-2_4 mb-4 text-center" @click="handleClick">
      <div class="card h-100" style="height: 50vh !important;" @mouseenter="showHover" @mouseleave="hideHover" @mousemove="updatePosition">
        <img :src="goods.image" class="card-img-top" alt="商品图片" style="width: 100%; height: auto;" />
        <div class="card-body">
          <h5 class="card-title">{{ truncatedName }}</h5>
          <p class="card-text">价格: {{ goods.price }}元</p>
        </div>
        <HoverWindow 
          v-if="showHoverWindow" 
          :productDetails="productDetails" 
          :position="position" 
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed, ref } from 'vue'
  import { useRouter } from 'vue-router'
  import HoverWindow from '@/layouts/components/hoverWindow.vue'
  import { GET } from '@/api/tool'

  const router = useRouter()
  
  const props = defineProps({
    goods: {
      type: Object,
      required: true
    }
  })
  
  const showHoverWindow = ref(false)
  const position = ref({ x: 0, y: 0 })
  
  const truncatedName = computed(() => 
    props.goods.name.length > 15 
      ? props.goods.name.substring(0, 15) + '...'
      : props.goods.name
  )
  
  const productDetails = ref([])
  const isLoading = ref(false)
  
  let abortController = null
  
  const showHover = async (event) => {
    // 取消之前的请求
    if (abortController) {
      abortController.abort()
    }
    abortController = new AbortController()
    
    try {
      isLoading.value = true
      showHoverWindow.value = true // 立即显示窗口
      updatePosition(event)
      
      const res = await GET(`/products/goodsid/${props.goods.id}`, {
        signal: abortController.signal
      })
      
      if (res.success && !abortController.signal.aborted) {
        productDetails.value = res.data
      }
    } catch (e) {
      if (e.name !== 'AbortError') {
        console.error('Fetch product details failed:', e)
      }
    } finally {
      if (!abortController.signal.aborted) {
        isLoading.value = false
      }
      abortController = null
    }
  }
  
  const hideHover = () => {
    if (abortController) {
      abortController.abort()
    }
    showHoverWindow.value = false
    productDetails.value = []
    isLoading.value = false
  }
  
  const handleClick = () => {
    router.push({
      path: '/goodsinfo',
      query: {
        id: props.goods.id,
        name: props.goods.name,
        picture: props.goods.image
      }
    })
  }

  const updatePosition = (event) => {
    const scrollX = window.scrollX || window.pageXOffset
    const scrollY = window.scrollY || window.pageYOffset
    position.value = {
      x: event.pageX - scrollX + 15,
      y: event.pageY - scrollY + 15
    }
  }
  </script>
  
  <style scoped>
  .col-2_4 {
    flex: 0 0 auto;
    width: 20%;
  }
  
  .card {
    cursor: pointer;
    height: 43vh;
  }
  </style>
