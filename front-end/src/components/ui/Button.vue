<script setup lang="ts">
defineProps({
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'primary' }
});
</script>

<template>
    <button :disabled="disabled"
        class="btn"
        :class="[
            variant === 'secondary'
                    ? 'btn-secondary'
                    : 'btn-primary'
        ]">
        <slot />
    </button>
</template>