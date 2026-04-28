import { ref } from 'vue'
import { Hospital } from '../types/Hospital'


export function useHospital() {
  const baseUrl = (import.meta as any).env.VITE_API_BASE_URL
  const hospital = ref<Hospital | null>(null)
  const isLoading = ref<boolean>(false)
  const error = ref<string | null>(null)

  const bookHospitalBed = async (id: number) => {
    return new Promise<void>((resolve, reject) => {
      fetch(`${baseUrl}/hospitals/${id}/decrement`, { method: "PATCH" }).then(response => {
        if (response.status != 200) {
          reject(`Error while booking bed in hospital ${id}`)
        } else {
          resolve()
        }
      })
    })

  }

  const fetchHospital = async (lat: number, lon: number, specialityId: number) => {
    isLoading.value = true
    error.value = null

    try {
      const response = await fetch(`${baseUrl}/hospitals/findNearest?lat=${lat}&lon=${lon}&speciality_id=${specialityId}`)

      if (response.status == 404) {
        hospital.value = null
        isLoading.value = false
        return;
      }

      if (!response.ok) {
        throw new Error(`Server error (${response.status})`)
      }

      const data = await response.json() as Hospital
      console.log(data)
      hospital.value = data

    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Unknown error'
      hospital.value = null
    } finally {
      isLoading.value = false
    }
  }

  return {
    hospital,
    hospitalIsLoading: isLoading,
    hospitalError: error,
    fetchHospital,
    bookHospitalBed
  }
}