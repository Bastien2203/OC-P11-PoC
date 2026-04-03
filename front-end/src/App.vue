<script setup>
import { onMounted } from 'vue'
import { useSpecialityGroups } from './composables/useSpecialityGroups'

const { groups, isLoading, error, fetchGroups } = useSpecialityGroups()

onMounted(() => {
  fetchGroups()
})
</script>

<template>
  <div class="min-h-screen bg-gray-100 flex items-start justify-center p-8">
    <div>
      <h1>
        Medhead
      </h1>

      <div v-if="isLoading">
        Chargement des spécialités...
      </div>

      <div v-else-if="error">
        Erreur: {{ error }}
      </div>

      <div v-else>
        <div v-if="groups.length === 0">
          Aucune spécialité trouvée.
        </div>

        <div v-for="group in groups" :key="group.id">
          <h2>
            {{ group.name }}
          </h2>

          <ul class="list-disc my-4">
            <li v-for="speciality in group.specialities" :key="speciality.id" class="ml-5">
              {{ speciality.name }}
            </li>
          </ul>
        </div>
      </div>

    </div>
  </div>
</template>