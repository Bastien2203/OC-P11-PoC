import { ref } from 'vue'
import { Hospital } from '../types/Hospital'


export function useHospital() {
  const hospital = ref<Hospital|null>(null)
  const isLoading = ref<boolean>(false)
  const error = ref<string | null>(null)

  const fetchHospital = async (lat: number, lon: number, specialityId: number) => {
    isLoading.value = true
    error.value = null
    
    try {
      const baseUrl = (import.meta as any).env.VITE_API_BASE_URL
      const response = await fetch(`${baseUrl}/hospitals/findNearest?lat=${lat}&lon=${lon}&speciality_id=${specialityId}`)
      
      if (!response.ok) {
        throw new Error(`Erreur serveur (${response.status})`)
      }

      const data = await response.json() as Hospital
      console.log(data)
      hospital.value = data
      
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Une erreur inconnue est survenue'
      hospital.value = null
    } finally {
      isLoading.value = false
    }
  }

  return {
    hospital,
    hospitalIsLoading: isLoading,
    hospitalError: error,
    fetchHospital
  }
}