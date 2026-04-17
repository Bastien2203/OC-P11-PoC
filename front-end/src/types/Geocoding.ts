export interface LocationState {
  address: string;
  longitude: number | null;
  latitude: number | null;
}


export interface GeocodingFeature {
  type: string;
  geometry: {
    type: string;
    coordinates: [number, number]; // [longitude, latitude]
  };
  properties: {
    label: string;
    score: number;
    housenumber?: string;
    id: string;
    type: string;
    name: string;
    postcode: string;
    citycode: string;
    x: number;
    y: number;
    city: string;
    context: string;
    importance: number;
    street?: string;
  };
}

export interface GeocodingResponse {
  type: string;
  version: string;
  features: GeocodingFeature[];
  attribution: string;
  licence: string;
  query: string;
  limit: number;
}