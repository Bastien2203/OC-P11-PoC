<script setup lang="ts">
import { computed } from 'vue';
import { SpecialityGroup } from '../../types/SpecialityGroup';
import Accordion from '../Accordion.vue';
import Spinner from '../Spinner.vue';

const props = defineProps<{
    isLoading: boolean;
    error: string | null;
    groups: SpecialityGroup[];
    selectedSpecialityId: number | null
    nextStep: () => void
}>();

const emit = defineEmits<{
    (e: 'update:selectedSpecialityId', value: number | null): void;
}>();

const localSpecialityId = computed({
    get() {
        return props.selectedSpecialityId;
    },
    set(v: number | null) {
        emit('update:selectedSpecialityId', v);
    }
});
</script>


<template>

    <div class="p-6 sm:p-8 flex flex-col grow">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Choisissez une spécialité</h2>

        <div v-if="isLoading" class="flex items-center justify-center gap-3 text-gray-500 grow">
            <Spinner size="w-6 h-6" /> Chargement des spécialités...
        </div>

        <div v-else-if="error" class="text-red-500 p-4 bg-red-50 rounded-md">
            Erreur: {{ error }}
        </div>

        <div v-else>
            <Accordion :items="groups" v-model="localSpecialityId" childrenKey="specialities" />
        </div>

        <div class="mt-6 pt-4 border-t border-gray-100 flex justify-end">
            <button @click="nextStep" :disabled="!selectedSpecialityId"
                class="px-6 py-2.5 rounded-lg text-white font-medium transition-colors fixed bottom-0 right-0 mr-15 mb-5"
                :class="selectedSpecialityId ? 'bg-blue-600 hover:bg-blue-700' : 'bg-gray-300 cursor-not-allowed'">
                Suivant
            </button>
        </div>
    </div>
</template>