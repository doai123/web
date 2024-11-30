import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Cấu hình Vite
export default defineConfig({
  plugins: [react()],
  server: {
    historyApiFallback: true, // Cấu hình này chuyển hướng các route về index.html
  },
});
