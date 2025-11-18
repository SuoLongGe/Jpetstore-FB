<template>
  <div class="row align-items-center mb-3" style="border-bottom: 1px solid #e0e0e0; padding: 10px 0;">
    <div class="col-1">
      <input 
        type="checkbox" 
        :checked="isSelected"
        @change="handleCheckboxChange"
        style="transform: scale(1.5);"
      >
    </div>
    <div class="col-2">
      <img :src="item.image" :alt="item.name" style="max-width: 60%; height: auto;">
    </div>
    <div class="col-5">
      <h5>{{ item.name }}</h5>
      <p>单价: <strong>￥{{ item.price }}</strong></p>
    </div>
    <div class="col-4 d-flex align-items-center">
      <label for="quantity" class="me-2">数量:</label>
      <input 
        type="number" 
        v-model.number="quantity" 
        min="1" 
        style="width: 60px;"
        @change="$emit('update-quantity', item.id, quantity)"
      >
      <button 
        class="btn btn-danger btn-sm ms-2" 
        @click="$emit('delete-item', item.id)"
      >
        删除
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  item: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['update-quantity', 'delete-item', 'toggle-select'])

const isSelected = ref(false)
const quantity = ref(props.item.quantity)

const handleCheckboxChange = (e) => {
  isSelected.value = e.target.checked
  console.log('Checkbox changed:', props.item.id, isSelected.value)
  emit('toggle-select', props.item.id, isSelected.value)
}

watch(() => props.item.quantity, (newVal) => {
  quantity.value = newVal
})
</script>
