<script setup lang="ts">
import { computed } from 'vue';
import { LocationState } from '../../types/Geocoding';
import AddressInput from '../AddressInput.vue';

    const props = defineProps<{
        locationState: LocationState;
        prevStep: () => void;
        submitSearch: () => void;
    }>();

    const emit = defineEmits<{
    (e: 'update:locationState', value: LocationState): void;
    }>();

    const localLocationState = computed({
    get() {
        return props.locationState;
    },
    set(v: LocationState) {
        emit('update:locationState', v);
    }
});
</script>


<template>
    <div class="p-6 sm:p-8 flex flex-col grow">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Où vous trouvez-vous ?</h2>

        <div class="grow">
            <label class="block text-sm font-medium text-gray-700 mb-2">Saisissez votre adresse</label>
            <AddressInput v-model="localLocationState" />
        </div>

        <div class="mt-6 pt-4 border-t border-gray-100 flex justify-between items-center">
            <button @click="prevStep" class="text-gray-500 hover:text-gray-800 font-medium px-4 py-2">
                Retour
            </button>
            <button @click="submitSearch" :disabled="!localLocationState.latitude"
                class="px-6 py-2.5 rounded-lg text-white font-medium transition-colors"
                :class="localLocationState.latitude ? 'bg-green-600 hover:bg-green-700' : 'bg-gray-300 cursor-not-allowed'">
                Rechercher
            </button>
        </div>
    </div>
</template>