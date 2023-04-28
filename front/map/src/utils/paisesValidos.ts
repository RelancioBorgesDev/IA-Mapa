import { coordenadasAfrica, coordenadasAmericano, coordenadasEuropeu } from "./coordenadas";

export const paisesAmericanos = Object.keys(coordenadasAmericano).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});
export const paisesAfricanos = Object.keys(coordenadasAfrica).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});
export const paisesEuropeus = Object.keys(coordenadasEuropeu).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});

export const paisesAceitos = [...paisesAfricanos,...paisesAmericanos,...paisesEuropeus].sort();


