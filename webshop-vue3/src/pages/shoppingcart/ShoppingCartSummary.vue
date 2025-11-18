<template>
  <div class="col-md-3 d-flex flex-column">
    <!-- 搜索部分 -->
    <div class="container mb-4">
      <form class="d-flex" @submit.prevent="handleSearch">
        <input 
          class="form-control me-2" 
          type="text" 
          v-model="searchKeyword"
          placeholder="请输入搜索内容..." 
          style="width: 70%;"
        >
        <button class="btn btn-primary" type="submit" style="background-color: rgb(255, 98, 0); border: none;">
          搜索
        </button>
      </form>
    </div>
    
    <!-- 结算明细部分 -->
    <div class="container mb-4">
      <h4>结算明细</h4>
      <p>商品总价: <strong id="totalPrice">{{ totalPrice.toFixed(2) }}</strong></p>
    </div>

    <div class="container mb-4">
      <button 
        class="btn btn-primary" 
        @click="handleCheckout"
        style="background-color: rgb(255, 98, 0); border: none;"
      >
        结算
      </button>
      <p v-if="errorMessage" style="color: red;font-size: 1.0em">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  selectedItems: {
    type: Array,
    default: () => []
  },
  totalPrice: {
    type: Number,
    default: 0
  }
})

const emit = defineEmits(['checkout'])

const router = useRouter()
const searchKeyword = ref('')
const errorMessage = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/',
      query: { search: searchKeyword.value.trim() }
    })
  }
}

const handleCheckout = () => {
  if (props.selectedItems.length === 0) {
    errorMessage.value = '请至少选择一件商品'
    return
  }
  emit('checkout', props.selectedItems)
}
</script>
