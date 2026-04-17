import { ref } from 'vue';
import { GeocodingFeature, GeocodingResponse } from '../types/Geocoding';


export function useGeocoding() {
    const results = ref<GeocodingFeature[]>([])
    const isLoading = ref<boolean>(false)
    const error = ref<string | null>(null)

    let debounceTimeout: ReturnType<typeof setTimeout> | null = null

    const searchAddress = async (query: string, delay = 300) => {
        if (debounceTimeout) clearTimeout(debounceTimeout)

        if (!query || query.length < 3) {
            results.value = [];
            return;
        }

        debounceTimeout = setTimeout(async () => {
            isLoading.value = true
            error.value = null
            try {
                const baseUrl = (import.meta as any).env.VITE_API_GEOCODE_URL
                const response = await fetch(`${baseUrl}/search?q=${encodeURIComponent(query)}&limit=5`)


                if (!response.ok) {
                    console.error(`http error ${response.status} with body ${await response.json()}`)
                    throw new Error(`Http error : ${response.status}`)
                }



                const data: GeocodingResponse = await response.json()
                results.value = data.features
            } catch (err: any) {
                error.value = err.message || "Error while using geocoding service"
            } finally {
                isLoading.value = false
            }
        }, delay)
    }
    const clearResults = () => {
        results.value = [];
    };

    return {
        results,
        isLoading,
        error,
        searchAddress,
        clearResults
    };
}