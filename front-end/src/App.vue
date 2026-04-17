<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useSpecialityGroups } from './composables/useSpecialityGroups'
import Accordion from './components/Accordion.vue'
import { useHospital } from './composables/useHospital';
import { LocationState } from './types/Geocoding';
import AddressInput from './components/AddressInput.vue';
import Spinner from './components/Spinner.vue';


const { groups, isLoading, error, fetchGroups } = useSpecialityGroups()
const { hospital, hospitalIsLoading, hospitalError, fetchHospital } = useHospital()
const selectedSpecialityId = ref<number | null>(null);

const locationState = ref<LocationState>({
  address: '',
  longitude: null,
  latitude: null
});

onMounted(() => {
  fetchGroups()
})
</script>

<template>
  <div>
    <header class="flex h-16 border-b border-gray-300 w-full bg-gray-200">
      <img src="./assets/medhead.png" alt="medhead" />
      <h1 class="invisible">Medhead</h1>
    </header>


    <main class="min-h-screen p-8">

      <h2>1 - Choisir une spécialité</h2>

      <div v-if="isLoading" class="text-gray-500 flex gap-2 items-center">
        <Spinner size="w-5 h-5" />Chargement des spécialités...
      </div>

      <div v-else-if="error" class="text-red-500">
        Erreur: {{ error }}
      </div>

      <div v-else>
        <Accordion :items="groups" v-model="selectedSpecialityId" childrenKey="specialities" />
      </div>


      <h2>2 - Rentrer votre addresse</h2>

      <AddressInput v-model="locationState" />

      <div v-if="locationState.latitude != null && locationState.longitude != null && selectedSpecialityId != null" class="mt-5 flex flex-col gap-3">
        <p><strong>Adresse :</strong> {{ locationState.address }}</p>

        <button class="my-2 bg-blue-600 rounded text-white px-4 py-2 cursor-pointer"
          @click="() => fetchHospital(locationState.latitude!!, locationState.longitude!!, selectedSpecialityId!!)">
          Lancer la recherche
        </button>

      </div>



      <h3>3 - Résultats</h3>

      <div v-if="hospitalIsLoading">
        Recherche de l'hopital le plus proche
      </div>

      <div v-else-if="hospitalError" class="text-red-500">
        Erreur: {{ error }}
      </div>

      <div v-else-if="hospital != null">
        Hopital trouvé :

        <div class="flex flex-col w-fit p-2 border rounded">
          <span>{{ hospital.name }}</span>
          <span>Lits disponibles: {{ hospital.availableBeds }}</span>
        </div>

      </div>

      <div v-else>
        Pas d'hopital trouvé
      </div>
    </main>
  </div>
</template>