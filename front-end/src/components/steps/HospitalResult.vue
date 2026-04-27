<script setup lang="ts">
import { Hospital } from '../../types/Hospital';

defineProps<{
    hospitalIsLoading: boolean;
    hospitalError: string | null;
    hospital: Hospital | null;
    resetSearch: () => void;
}>();
</script>

<template>
    <div v-if="hospitalIsLoading" class="flex flex-col items-center justify-center grow py-12">
        <Spinner size="w-12 h-12" color="text-blue-600" />
        <p class="mt-4 text-gray-600 font-medium">Recherche de l'hôpital le plus proche...</p>
    </div>

    <div v-else-if="hospitalError" class="text-red-500 p-4 bg-red-50 rounded-md w-full">
        Erreur: {{ hospitalError }}
    </div>

    <div v-else-if="hospital != null" class="flex flex-col items-center grow w-full pt-4">
        <div class="w-16 h-16 bg-green-100 text-green-600 rounded-full flex items-center justify-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24"
                stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-800 mb-2">Hôpital trouvé !</h2>

        <div class="mt-6 p-6 border border-gray-200 rounded-xl bg-gray-50 w-full flex flex-col items-start text-left">
            <span class="text-lg font-bold text-blue-900">{{ hospital.name }}</span>
            <div class="mt-3 flex items-center gap-2 text-gray-700">
                <span class="inline-block w-3 h-3 rounded-full bg-green-500"></span>
                <span><strong>{{ hospital.availableBeds }}</strong> lits disponibles</span>
            </div>
        </div>
    </div>

    <div v-else class="flex flex-col items-center grow py-12">
        <h2 class="text-xl font-bold text-gray-800 mb-2">Aucun établissement</h2>
        <p class="text-gray-500">Nous n'avons pas trouvé d'hôpital correspondant à votre recherche.</p>
    </div>

    <div class="mt-8 pt-4 border-t border-gray-100 w-full flex justify-center">
        <button @click="resetSearch"
            class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors">
            Nouvelle recherche
        </button>
    </div>
</template>