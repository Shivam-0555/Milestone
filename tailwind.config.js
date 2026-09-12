// tailwind.config.js
module.exports = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
    "./src/**/*.{js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {
      colors: {
        primary: "#2563EB", // blue-600 as primary
        secondary: "#4F46E5", // indigo-600
        accent: "#EC4899" // rose-500
      },
      fontFamily: {
        sans: ["Inter", "sans-serif"],
        mono: [""],
      },
    },
  },
  plugins: [],
  darkMode: "class",
};
