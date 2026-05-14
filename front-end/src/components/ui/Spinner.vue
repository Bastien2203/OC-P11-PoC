<script setup lang="ts">
const props = withDefaults(defineProps<{
  size?: string;
  color?: string;
}>(), {
  size: 'loading-md',
  color: 'text-primary'
});
</script>

<template>
  <span :class="['loading loading-spinner', size, color]" aria-hidden="true"></span>
</template>