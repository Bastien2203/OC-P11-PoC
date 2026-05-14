<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useSpecialityGroups } from './composables/useSpecialityGroups'
import { useHospital } from './composables/useHospital';
import { LocationState } from './types/Geocoding';
import SpecialitySelection from './components/steps/SpecialitySelection.vue';
import AddressSelection from './components/steps/AddressSelection.vue';
import HospitalResult from './components/steps/HospitalResult.vue';
import Layout from './components/ui/Layout.vue';
import ReservationSuccess from './components/steps/ReservationSuccess.vue';


const { groups, isLoading, error, fetchGroups } = useSpecialityGroups()
const { hospital, hospitalIsLoading, hospitalError, fetchHospital, bookHospitalBed } = useHospital()
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

const onBookingSuccess = () => {
  currentStep.value = 4;
};

onMounted(() => {
  fetchGroups()
})
</script>

<template>
  <Layout>
    <ul v-if="currentStep < 4" class="steps w-full md:w-1/2 mb-8">
      <li class="step" :class="{ 'step-primary': currentStep >= 1 }">Spécialité</li>
      <li class="step" :class="{ 'step-primary': currentStep >= 2 }">Localisation</li>
      <li class="step" :class="{ 'step-primary': currentStep >= 3 }">Résultats</li>
    </ul>


    <div class="w-full max-w-xl min-h-100 flex flex-col relative">
      <Transition name="fade" mode="out-in">
        <div v-if="currentStep === 1">
          <SpecialitySelection :isLoading="isLoading" :error="error" :groups="groups"
            v-model:selectedSpecialityId="selectedSpecialityId" :nextStep="nextStep" />
        </div>

        <div v-else-if="currentStep === 2">
          <AddressSelection :prevStep="prevStep" :submitSearch="submitSearch" v-model:locationState="locationState" />
        </div>

        <div v-else-if="currentStep === 3" class="p-6 sm:p-8 flex flex-col grow items-center text-center">
          <HospitalResult :hospitalIsLoading="hospitalIsLoading" :hospitalError="hospitalError" :hospital="hospital"
            @success="onBookingSuccess"
            :resetSearch="resetSearch" :bookHospitalBed="bookHospitalBed" />
        </div>

        <div v-else-if="currentStep === 4" class="p-6 sm:p-8 flex flex-col grow items-center text-center">
          <ReservationSuccess :resetSearch="resetSearch" :hospitalName="hospital?.name" />
        </div>

      </Transition>
    </div>
  </Layout>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.fade-enter-from {
  opacity: 0;
}

.fade-leave-to {
  opacity: 0;
}
</style>