<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {useSpecialityGroups} from './composables/useSpecialityGroups'
import {useHospital} from './composables/useHospital';
import {LocationState} from './types/Geocoding';
import SpecialitySelection from './components/steps/SpecialitySelection.vue';
import AddressSelection from './components/steps/AddressSelection.vue';
import HospitalResult from './components/steps/HospitalResult.vue';


const { groups, isLoading, error, fetchGroups } = useSpecialityGroups()
const { hospital, hospitalIsLoading, hospitalError, fetchHospital } = useHospital()
const currentStep = ref<number>(1);
const selectedSpecialityId = ref<number | null>(null);

const locationState = ref<LocationState>({
  address: '',
  longitude: null,
  latitude: null
});

const nextStep = () => {
  if (currentStep.value < 3) currentStep.value++;
};

const prevStep = () => {
  if (currentStep.value > 1) currentStep.value--;
};

const submitSearch = () => {
  if (locationState.value.latitude && locationState.value.longitude && selectedSpecialityId.value) {
    nextStep();
    fetchHospital(locationState.value.latitude, locationState.value.longitude, selectedSpecialityId.value);
  }
};

const resetSearch = () => {
  currentStep.value = 1;
  selectedSpecialityId.value = null;
  locationState.value = { address: '', longitude: null, latitude: null };
};

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


    <main class="grow flex flex-col items-center p-4 sm:p-8">

      <div class="w-full max-w-xl mb-6 flex justify-between items-center px-4 text-sm font-medium text-gray-500">
        <span :class="{ 'text-blue-600': currentStep === 1 }">1. Spécialité</span>
        <span class="w-8 h-px bg-gray-300"></span>
        <span :class="{ 'text-blue-600': currentStep === 2 }">2. Localisation</span>
        <span class="w-8 h-px bg-gray-300"></span>
        <span :class="{ 'text-blue-600': currentStep === 3 }">3. Résultats</span>
        <span class="w-8 h-px bg-gray-300"></span>
        <span :class="{ 'text-blue-600': currentStep === 4 }">4. Demande</span>
      </div>

      <div
        class="w-full max-w-xl min-h-100 flex flex-col relative">

        <Transition name="fade" mode="out-in">

          <div v-if="currentStep === 1">
            <SpecialitySelection
              :isLoading="isLoading"
              :error="error"
              :groups="groups"
              v-model:selectedSpecialityId="selectedSpecialityId"
              :nextStep="nextStep"
            />
          </div>
          

          <div v-else-if="currentStep === 2">
            <AddressSelection
              :prevStep="prevStep"
              :submitSearch="submitSearch"
              v-model:locationState="locationState"
            />
          </div>

          <div v-else-if="currentStep === 3" class="p-6 sm:p-8 flex flex-col grow items-center text-center">
            <HospitalResult
              :hospitalIsLoading="hospitalIsLoading"
              :hospitalError="hospitalError"
              :hospital="hospital"
              :resetSearch="resetSearch"
            />
          </div>

        </Transition>
      </div>
    </main>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>