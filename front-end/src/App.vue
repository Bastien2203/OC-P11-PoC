<script setup>
import { ref, onMounted } from 'vue';
import { useSpecialityGroups } from './composables/useSpecialityGroups'
import Accordion from './components/Accordion.vue'
import { useHospital } from './composables/useHospital';


const { groups, isLoading, error, fetchGroups } = useSpecialityGroups()
const { hospital, hospitalIsLoading, hospitalError, fetchHospital } = useHospital()
const selectedSpecialityId = ref(null);

const coords = ref({
  lat: 46.1364497,
  lon: -1.1515422
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


    <main class="min-h-screen flex items-start p-8">
      <div class="border-r w-1/2 flex overflow-scroll">
        <div v-if="isLoading" class="text-gray-500">
          Chargement des spécialités...
        </div>

        <div v-else-if="error" class="text-red-500">
          Erreur: {{ error }}
        </div>

        <div v-else>
          <Accordion :items="groups" v-model="selectedSpecialityId" childrenKey="specialities" />
        </div>
      </div>

      <div class="w-1/2 p-5">
        <div class="mt-4 text-sm text-gray-600">

          Coordonnées :
          <div class="flex flex-col w-1/3 gap-4">
            <span>lat :
              <input id="coord-lat" type="number" v-model.number="coords.lat" />
            </span>
            <span>lon :
              <input id="coord-lon" type="number" v-model.number="coords.lon" />
            </span>
          </div>

          <div class="mt-4 text-sm text-gray-500">
            Coordonnées actuelles : <strong>({{ coords.lat }}, {{ coords.lon }})</strong>
          </div>

          <div v-if="coords.lat != null && coords.lon != null && selectedSpecialityId != null">
            <button class="my-2 bg-blue-600 rounded text-white px-4 py-2 cursor-pointer"
              @click="() => fetchHospital(coords.lat, coords.lon, selectedSpecialityId)">
              Rechercher hopital
            </button>
          </div>

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

        </div>

      </div>
    </main>
  </div>
</template>