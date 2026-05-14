<script setup lang="ts">
import { ref } from 'vue';
import { Hospital } from '../../types/Hospital';
import Button from '../ui/Button.vue';

const props = defineProps<{
    hospitalIsLoading: boolean;
    hospitalError: string | null;
    hospital: Hospital | null;
    resetSearch: () => void;
    bookHospitalBed: (id: number) => Promise<void>;
}>();

const emit = defineEmits<{
    (e: 'success'): void;
}>();


const bookBed = () => {
    if (props.hospital == null) throw Error("Cannot book a bed, cause hospital is null ")
    props.bookHospitalBed(props.hospital.id)
        .then(() => {
            emit('success');
        })
        .catch(e => {
            console.error(e);
        });
}
</script>

<template>
    <div v-if="hospitalIsLoading" class="flex flex-col items-center justify-center grow py-12">
        <Spinner size="w-12 h-12" color="text-primary-600" />
        <p class="mt-4 text-gray-600 font-medium">Recherche de l'hôpital le plus proche...</p>
    </div>

    <div v-else-if="hospitalError" class="alert alert-error shadow-sm w-full">
        <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <span>{{ hospitalError }}</span>
    </div>

    <div v-else-if="hospital != null" class="flex flex-col items-center grow w-full pt-4">
        <h2 class="card-title text-2xl text-success">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 mr-2" fill="none" viewBox="0 0 24 24"
                stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
            Hôpital trouvé !
        </h2>
        <div class="stat bg-base-100 mt-4 rounded-box">
            <div class="stat-title whitespace-normal">{{ hospital.name }}</div>
            <div class="stat-value text-primary">{{ hospital.availableBeds }}</div>
            <div class="stat-desc">Lits disponibles</div>
        </div>
    </div>

    <div v-else class="flex flex-col items-center grow py-12">
        <h2 class="text-xl font-bold text-gray-800 mb-2">Aucun établissement</h2>
        <p class="text-gray-500">Nous n'avons pas trouvé d'hôpital correspondant à votre recherche.</p>
        <Button @click="resetSearch" variant="primary" class="w-full sm:w-auto mt-5">
            Nouvelle recherche
        </Button>
    </div>


    <div class="mt-8 pt-4 border-t border-gray-100 w-full">
        <div v-if="hospital != null" class="flex justify-center gap-4">
            <Button @click="resetSearch" variant="secondary" class="w-1/2 sm:w-auto">
                Nouvelle recherche
            </Button>
            <Button @click="bookBed" class="w-1/2 sm:w-auto">
                Réserver un lit
            </Button>
        </div>
    </div>
</template>