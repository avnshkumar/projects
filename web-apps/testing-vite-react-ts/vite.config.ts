import { ConfigEnv, defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
// https://vite.dev/config/
export default defineConfig(({ command, mode }: ConfigEnv) => {
  console.log(`setting up vite config with command: ${command}, mode: ${mode}, envDir: ${process.cwd()} `);

  const env = loadEnv(mode, process.cwd(), "VITE_");
  return {
    server: {
      port: Number(env.PORT) || 3100,
    },
    plugins: [react(), tailwindcss()],
    resolve: {
      alias: {
        "src": "/src",
        "@": "/src",
        "@components": "/src/app/components",
        "@network": "/src/app/network",
      }
    }
  };
});
