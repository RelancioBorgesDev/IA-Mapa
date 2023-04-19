import { coordenadasAfrica, coordenadasAmericano, coordenadasEuropeu } from "./coordenadas";

const paisesAmericanos = Object.keys(coordenadasAmericano).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});
const paisesAfricanos = Object.keys(coordenadasAfrica).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});
const paisesEuropeus = Object.keys(coordenadasEuropeu).map((key) => {
  return key.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/[^a-zA-Z\s]/g, "");
});

export const paisesAceitos = [...paisesAfricanos,...paisesAmericanos,...paisesEuropeus].sort();


