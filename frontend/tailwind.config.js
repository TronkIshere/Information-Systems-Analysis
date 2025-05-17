/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: 'jit',
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],

  theme: {
    screens: {
      sm: "540px",
      md: "720px",
      lg: "960px",
      xl: "1108px",
      "1xl": "1285px",
      "2xl": "1466px",
      "3xl": "1760px",
      "4xl": "1920px",
    },
    borderRadius: {
      DEFAULT: "20px",
      sm: "8px",
      md: "16px",
      lg: "24px",
    },
    extend: {
      fontSize: {
        h1: "3.75rem",
        h2: "3rem",
        h3: "2.25rem",
        h4: "1.75rem",
        h5: "1.25rem",
        sh1: "clamp(1.5rem, 3vw, 1.875rem)",
        md: "1rem",
      },

      maxWidth: {
        sm: "540px",
        md: "720px",
        lg: "960px",
        xl: "1172px",
        "2xl": "1432px",
        "3xl": "1728px",
      },
      spacing: {
        xs: "0.25rem", // 4px
        sm: "0.5rem", // 8px
        md: "1rem", // 16px
        lg: "1.5rem", // 24px
        xl: "2rem", // 32px
        "1xl": "2.25rem", // 36px
        "2xl": "3rem", // 48px
        "3xl": "4rem", // 64px
        "4xl": "4.5rem", // 72px
        "5xl": "6rem", // 96px
      },
      backgroundImage: {
        "gradient-1":
          "linear-gradient(180deg, rgba(237, 246, 255, 0.60) 0%, rgba(250, 252, 255, 0.60) 100%)",
        "gradient-2":
          "radial-gradient(799.85% 132.3% at 5.49% 75%, #0065FD 0%, #46BCFE 100%)",
        "gradient-3":
          "linear-gradient(180deg, rgba(0, 0, 0, 0.00) -0.03%, rgba(0, 0, 0, 0.30) 59.99%, #000 100%))",
      },

      boxShadow: {
        DEFAULT: "0px 24px 45px 0px rgba(169, 186, 219, 0.25)",
        xs: "0px 1px 2px 0px rgba(16, 24, 40, 0.05)",
      },
      transitionProperty: {
        spacing: "margin, padding",
      },
    },
  },
  plugins: [],
};
