// src/composables/useSpecialityGroups.ts
import { ref } from 'vue'
import { SpecialityGroup } from '../types/SpecialityGroup'


export function useSpecialityGroups() {
  const groups = ref<SpecialityGroup[]>([])
  const isLoading = ref<boolean>(false)
  const error = ref<string | null>(null)

  const fetchGroups = async () => {
    isLoading.value = true
    error.value = null
    
    try {
      const baseUrl = import.meta.env.VITE_API_BASE_URL
      const response = await fetch(`${baseUrl}/speciality-groups`)
      
      if (!response.ok) {
        throw new Error(`Erreur serveur (${response.status})`)
      }
      
      const data = await response.json() as SpecialityGroup[]
      groups.value = data
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Une erreur inconnue est survenue'
    } finally {
      isLoading.value = false
    }
  }

  return {
    groups,
    isLoading,
    error,
    fetchGroups
  }
}