<script setup lang="ts">
import { ref, computed } from 'vue';

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


const activeCategory = ref<Record<string,any> | null>(null);

const selectCategory = (category: Record<string,any>) => {
  activeCategory.value = category;
};

const goBack = () => {
  activeCategory.value = null;
};

const selectSubSpecialty = (id: string | number) => {
  emit('update:modelValue', id);
};


const initSelection = () => {
  if (props.modelValue) {
    const parent = props.items.find(cat => 
      cat[props.childrenKey]?.some((child: any) => child[props.idKey] === props.modelValue)
    );
    if (parent) {
      activeCategory.value = parent;
    }
  }
};
initSelection();
</script>

<template>
  <div class="w-full mx-auto bg-base-100 border border-base-300 rounded-box overflow-hidden min-h-[50vh]">
    <ul v-if="!activeCategory" class="menu bg-base-100 w-full text-base">
      <li v-for="category in items" :key="category[idKey]">
        <a @click="selectCategory(category)" class="flex justify-between py-4">
          <span>{{ category[labelKey] }}</span>
          <svg class="w-5 h-5 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" /></svg>
        </a>
      </li>
    </ul>

    <div v-else class="flex flex-col">
      <button @click="goBack" class="btn btn-ghost rounded-none border-b border-base-200 justify-start w-full text-primary h-14">
        <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" /></svg>
        {{ activeCategory[labelKey] }}
      </button>
      
      <ul class="menu bg-base-100 w-full text-base">
        <li v-for="child in activeCategory[childrenKey]" :key="child[idKey]">
          <a @click="selectSubSpecialty(child[idKey])" :class="{ 'active': modelValue === child[idKey] }" class="py-4 flex justify-between">
            <span>{{ child[labelKey] }}</span>
            <svg v-if="modelValue === child[idKey]" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" /></svg>
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>