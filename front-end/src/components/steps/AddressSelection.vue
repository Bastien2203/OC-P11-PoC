<script setup lang="ts">
import { computed } from 'vue';
import { LocationState } from '../../types/Geocoding';
import AddressInput from '../AddressInput.vue';
import Button from '../ui/Button.vue';

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
            <div class="mt-2 py-2 px-3 bg-primary-50 border-l-primary border-l-2 rounded-l-none text-sm italic text-gray-500">
                Cette adresse n'est pas conservée. Elle sert exclusivement à cette recherche.
            </div>
        </div>

        <div class="mt-6 pt-4 border-t border-gray-100 flex justify-center gap-4">
            <Button @click="prevStep" variant="secondary" class="w-1/2 sm:w-auto">
                Retour
            </Button>
            <Button @click="submitSearch" :disabled="!localLocationState.latitude" class="w-1/2 sm:w-auto">
                Rechercher
            </Button>
        </div>
    </div>
</template>