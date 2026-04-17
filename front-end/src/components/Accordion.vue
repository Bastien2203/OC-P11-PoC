<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
  items: {
    type: Array<Record<string,any>>,
    required: true,
    default: () => []
  },
  modelValue: {
    type: [String, Number, null],
    default: null
  },
  idKey: { type: String, default: 'id' },
  labelKey: { type: String, default: 'name' },
  childrenKey: { type: String, default: 'items' } 
});

const emit = defineEmits(['update:modelValue']);

const openCategoryId = ref<string|null>(null);

const toggleCategory = (id: string) => {
  openCategoryId.value = openCategoryId.value === id ? null : id;
};

const selectItem = (id: string) => {
  emit('update:modelValue', id);
};
</script>

<template>
  <div class="w-full max-w-md mx-auto bg-white border border-gray-200 rounded-lg shadow-sm overflow-hidden">
    
    <div 
      v-for="category in items" 
      :key="category[idKey]" 
      class="border-b border-gray-200 last:border-b-0"
    >
      <button 
        @click="toggleCategory(category[idKey])"
        class="w-full flex justify-between items-center px-4 py-3 bg-gray-50 hover:bg-gray-100 transition-colors focus:outline-none"
      >
        <span class="font-medium text-gray-700 text-left">{{ category[labelKey] }}</span>

        <svg 
          class="w-5 h-5 text-gray-500 transition-transform duration-200"
          :class="{ 'rotate-180': openCategoryId === category[idKey] }" 
          fill="none" 
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
        </svg>
      </button>

      <div v-show="openCategoryId === category[idKey]" class="bg-white">
        <ul class="flex flex-col py-2">
          <li v-for="child in category[childrenKey]" :key="child[idKey]">
            <button 
              @click="selectItem(child[idKey])" 
              class="w-full text-left px-8 py-2 text-sm transition-colors cursor-pointer" 
              :class="[
                modelValue === child[idKey]
                  ? 'bg-blue-50 text-blue-700 font-medium border-l-4 border-blue-500 pl-7'
                  : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900 border-l-4 border-transparent pl-7'
              ]"
            >
              {{ child[labelKey] }}
            </button>
          </li>
        </ul>
      </div>
    </div>

  </div>
</template>