import { defineConfig, loadEnv } from "vite";


// https://vitejs.dev/config/
export default defineConfig({
  // ...
  define: {
    'process.env': process.env
  }
})
