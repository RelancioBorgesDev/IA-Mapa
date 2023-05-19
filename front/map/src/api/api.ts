import axios from 'axios'

export const apiAlgoritimos = axios.create({
    baseURL: "http://localhost:8080/api/algoritimos/"
})

export const apiPaises = axios.create({
    baseURL: "http://localhost:8080/api/paises/"
})

export const apiMetodos = axios.create({
    baseURL: "http://localhost:8080/api/metodos/"
})