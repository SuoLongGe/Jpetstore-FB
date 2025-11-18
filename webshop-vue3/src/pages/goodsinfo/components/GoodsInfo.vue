<template>
  <div class="container-fluid p-4" style="height: auto;min-height: 50vh; width: auto;min-width: 70vw">
    <div class="row">
      <!-- 左侧部分：商品图片 -->
      <div class="col-md-6 d-flex align-items-center justify-content-center" style="border-right: 1px solid #e0e0e0;">
        <img :src="goodsPicture" :alt="goodsName" style="width: 60%; height: auto;">
      </div>

      <!-- 右侧部分：商品信息 -->
      <div class="col-md-6 d-flex flex-column justify-content-center">
        <!-- 商品名称 -->
        <h4>{{ displayedProductName }}</h4>
        <!-- 商品价格 -->
        <p class="text-secondary" style="font-size: 1.2em;">单价: {{ displayedProductPrice }}元</p>

        <!-- 规格选择器 -->
        <div class="mb-3" style="width: 50%">
          <label for="productSpec" class="form-label">选择规格/件:</label>
          <select id="productSpec" v-model="selectedSpec" class="form-select" @change="updateProductInfo">
            <option v-for="product in products" :key="product.id" :value="product">
              {{ product.name }} - {{ product.price }}元
            </option>
          </select>
        </div>

        <!-- 数量选择和加入购物车 -->
        <div class="container my-2 d-flex align-items-center">
          <div class="d-flex align-items-center me-3" style="width: 30%;">
            <button type="button" class="btn btn-light" @click="decreaseQuantity" style="width: 33%; height: 6.5vh; font-size: 1.1em;">-</button>
            <input type="number" v-model="quantity" class="form-control text-center" min="1" style="width: 34%; height: 6.5vh; font-size: 1.1em;">
            <button type="button" class="btn btn-light" @click="increaseQuantity" style="width: 33%; height: 6.5vh; font-size: 1.1em;">+</button>
          </div>
          <button type="button" class="btn btn-primary" @click="addToCart" style="width: 20%; height: 6.5vh; background-color: rgb(255, 98, 0); border: none; font-size: 1.1em;">
            加入购物车
          </button>
        </div>

        <!-- 成功或错误信息 -->
        <div class="container">
          <span v-if="message" :style="{color: messageType === 'error' ? 'red' : 'green'}">{{ message }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { GET, POST } from '@/api/tool'
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { showMessage } from '@/composables/util'

const route = useRoute()
const router = useRouter()

// 商品数据
const goodsId = ref('')
const goodsName = ref('')
const goodsPicture = ref('')
const products = ref([])
const selectedSpec = ref(null)
const displayedProductName = ref('')
const displayedProductPrice = ref('')
const ProductId = ref('')
const quantity = ref(1)
const message = ref('')
const messageType = ref('')

// 初始化验证URL参数
onMounted(() => {
  const { id, name, picture } = route.query
  if (!id || !name || !picture) {
    router.push('/')
    showMessage('商品不存在', 'error')
    return
  }
  
  goodsId.value = id
  goodsName.value = name
  goodsPicture.value = picture
  
  fetchProductSpecs()
})

const fetchProductSpecs = async () => {
  GET(`/products/goodsid/${goodsId.value}`,{}).then(res => {
    if(res.success == true){
      products.value = res.data.map(item => ({
        id: item.productid,
        name: item.productname,
        price: item.productprice
      }))
      selectedSpec.value = products.value[0]
      updateProductInfo()
    }else if(res.success == false){
      showMessage(res.message, 'error')
    }else{
      showMessage('连接服务器失败', 'error')
      

    }
  })
}

const updateProductInfo = () => {
  if (selectedSpec.value) {
    ProductId.value = selectedSpec.value.id
    displayedProductName.value = selectedSpec.value.name
    displayedProductPrice.value = selectedSpec.value.price
  }
}

const increaseQuantity = () => {
  quantity.value += 1
}

const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value -= 1
  }
}


  const addToCart = async () => {
  POST(`/c/shoppingcarts/productid/${ProductId.value}/quantity/${quantity.value}`,{}).then(res => {
    if(res.success == true){
      showMessage('加入购物车成功', 'success')
    }else if(res.success == false){
      showMessage(res.message, 'error')
    }else{
      showMessage('连接服务器失败', 'error')
    }
  })


}
</script>
