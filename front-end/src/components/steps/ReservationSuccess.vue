<script setup lang="ts">
import Button from '../ui/Button.vue';

defineProps<{
    resetSearch: () => void;
    hospitalName?: string;
}>();
</script>

<template>
    <div class="flex flex-col items-center justify-center grow py-10 w-full">
        <div class="w-24 h-24 rounded-full bg-success/20 flex items-center justify-center mb-6 text-success">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
            </svg>
        </div>

        <h2 class="text-3xl font-bold text-base-content mb-4">Réservation confirmée</h2>
        
        <p class="text-base-content/70 text-lg mb-8 max-w-md">
            Un lit d'urgence a été bloqué avec succès pour vous à 
            <strong class="text-base-content">{{ hospitalName || "l'établissement sélectionné" }}</strong>.
        </p>

        <div class="alert alert-info shadow-sm bg-info/10 text-info-content border-none mb-8 w-full">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="stroke-current shrink-0 w-6 h-6"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
            <span>Veuillez vous présenter aux urgences de cet établissement dans les plus brefs délais.</span>
        </div>

        <Button @click="resetSearch" class="btn btn-primary btn-wide">
            Retour à l'accueil
        </Button>
    </div>
</template>