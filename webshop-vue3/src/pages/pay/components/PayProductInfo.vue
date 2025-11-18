<template>
  <div>
    <div class="mb-3">
      <label class="form-label">商品:</label>
      <div class="form-control" style="height: auto; min-height: 150px;">
        <div 
          v-for="(item, index) in products" 
          :key="index"
          class="mb-2"
        >
          {{ item.name }} × {{ item.quantity }}
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-around mt-4 mb-3">
      <button 
        type="button" 
        class="btn btn-secondary" 
        @click="$emit('prev')"
      >
        上一步
      </button>
      <button 
        type="button" 
        class="btn btn-secondary" 
        @click="$emit('next')"
      >
        下一步
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  products: {
    type: Array,
    required: true,
    default: () => []
  }
})

defineEmits(['prev', 'next'])
</script>
