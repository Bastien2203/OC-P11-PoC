import { Speciality } from "./Speciality";

export interface SpecialityGroup {
  id: number;
  name: string;
  specialities: Speciality[];
}