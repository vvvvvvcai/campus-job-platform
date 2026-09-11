/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#0037b0',
          container: '#1d4ed8',
          fixed: '#dce1ff',
          'fixed-dim': '#b7c4ff',
        },
        secondary: {
          DEFAULT: '#006591',
          container: '#39b8fd',
          fixed: '#c9e6ff',
          'fixed-dim': '#89ceff',
        },
        tertiary: {
          DEFAULT: '#623c00',
          container: '#825100',
          fixed: '#ffddb8',
        },
        surface: {
          DEFAULT: '#faf8ff',
          dim: '#d2d9f4',
          bright: '#faf8ff',
          container: {
            DEFAULT: '#eaedff',
            low: '#f2f3ff',
            high: '#e2e7ff',
            highest: '#dae2fd',
            lowest: '#ffffff',
          },
        },
        'on-surface': {
          DEFAULT: '#131b2e',
          variant: '#434655',
        },
      },
      fontFamily: {
        sans: ['Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', 'Arial', 'sans-serif'],
      },
      borderRadius: {
        'sm': '0.25rem',
        'DEFAULT': '0.5rem',
        'md': '0.75rem',
        'lg': '1rem',
        'xl': '1.5rem',
      },
      spacing: {
        'space-2xs': '0.25rem',
        'space-xs': '0.5rem',
        'space-sm': '0.75rem',
        'space-md': '1rem',
        'space-lg': '1.5rem',
        'space-xl': '2rem',
        'space-2xl': '3rem',
        'space-3xl': '4rem',
        'margin-mobile': '1rem',
        'margin-tablet': '1.5rem',
        'margin-desktop': '2.5rem',
      },
    },
  },
  plugins: [],
}
