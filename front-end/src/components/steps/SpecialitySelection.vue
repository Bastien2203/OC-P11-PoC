<script setup lang="ts">
import { computed } from 'vue';
import { SpecialityGroup } from '../../types/SpecialityGroup';
import SpecialityListMenu from '../SpecialityListMenu.vue';
import Spinner from '../ui/Spinner.vue';
import Button from '../ui/Button.vue';

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
        props.nextStep()
    }
});
</script>


<template>

    <div class="p-6 sm:p-8 flex flex-col grow">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Choisissez une spécialité</h2>

        <div v-if="isLoading" class="flex items-center justify-center gap-3 text-gray-500 grow">
            <Spinner size="w-6 h-6" /> Chargement des spécialités...
        </div>

        <div v-else-if="error" class="text-danger-500 p-4 bg-red-50 rounded-md">
            Erreur: {{ error }}
        </div>

        <div v-else>
            <SpecialityListMenu :items="groups" v-model="localSpecialityId" childrenKey="specialities" />
        </div>
    </div>
</template>