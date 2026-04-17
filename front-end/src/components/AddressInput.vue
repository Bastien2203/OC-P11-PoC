<script setup lang="ts">
import { ref, watch, onMounted, onUnmounted } from 'vue';
import { useGeocoding } from '../composables/useGeocoding';
import { LocationState, GeocodingFeature } from '../types/Geocoding';
import Spinner from './Spinner.vue';


const props = defineProps<{
  modelValue: LocationState;
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: LocationState): void;
}>();

const { results, isLoading, searchAddress, clearResults } = useGeocoding();

const searchQuery = ref(props.modelValue.address || '');
const isOpen = ref(false);


const onInput = () => {
  isOpen.value = true;
  searchAddress(searchQuery.value);
  
  emit('update:modelValue', {
    address: searchQuery.value,
    longitude: null,
    latitude: null,
  });
};

const selectAddress = (feature: GeocodingFeature) => {
  const label = feature.properties.label;
  const [longitude, latitude] = feature.geometry.coordinates;

  searchQuery.value = label;
  isOpen.value = false;
  clearResults();

  emit('update:modelValue', {
    address: label,
    longitude,
    latitude,
  });
};

const closeDropdown = (e: MouseEvent) => {
  const target = e.target as HTMLElement;
  if (!target.closest('.address-input-wrapper')) {
    isOpen.value = false;
  }
};

onMounted(() => {
  document.addEventListener('click', closeDropdown);
});

onUnmounted(() => {
  document.removeEventListener('click', closeDropdown);
});

watch(() => props.modelValue.address, (newAddress) => {
  if (newAddress !== searchQuery.value) {
    searchQuery.value = newAddress;
  }
});
</script>

<template>
  <div class="relative w-full">
    
    <div class="relative">
      <input
        type="text"
        v-model="searchQuery"
        @input="onInput"
        @focus="isOpen = true"
        placeholder="Commencez à taper une adresse..."
        class="w-full px-4 py-2 text-sm text-gray-800 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
        autocomplete="off"
      />
      <span v-if="isLoading" class="absolute right-3 top-1/2 -translate-y-1/2 text-sm text-gray-500"><Spinner size="w-5 h-5" /></span>
    </div>

    <ul 
      v-if="isOpen && results.length > 0" 
      class="absolute z-10 w-full mt-1 overflow-y-auto bg-white border border-gray-200 rounded-md shadow-lg max-h-60"
    >
      <li
        v-for="feature in results"
        :key="feature.properties.id"
        @click="selectAddress(feature)"
        class="flex flex-col px-4 py-2 border-b border-gray-100 cursor-pointer hover:bg-blue-50 last:border-none transition-colors"
      >
        <span class="text-sm font-medium text-gray-800">{{ feature.properties.label }}</span>
        <span class="text-xs text-gray-500 mt-0.5">{{ feature.properties.context }}</span>
      </li>
    </ul>

    <div 
      v-if="isOpen && searchQuery.length >= 3 && results.length === 0 && !isLoading" 
      class="absolute z-10 w-full mt-1 p-4 text-sm text-center text-gray-500 bg-white border border-gray-200 rounded-md shadow-lg"
    >
      Aucune adresse trouvée.
    </div>
  </div>
</template>