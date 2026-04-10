import { Speciality } from "./Speciality";


export interface Hospital {
    availableBeds: number,
    id: number,
    latitude: number,
    longitude: number,
    name: string,
    specialities: Speciality[]
}