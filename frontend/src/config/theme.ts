"use client";
import { createTheme, ThemeOptions } from "@mui/material/styles";

declare module "@mui/material/styles" {
  interface Palette {
    statCard: {
      users: string;
      orders: string;
      revenue: string;
      growth: string;
    };
    custom: {
      brown: string;
      dark: string;
      light: string;
      bgColor: string;
    };
    severity: {
      error: string;
      warning: string;
      info: string;
      success: string;
    };
  }

  interface PaletteOptions {
    statCard: {
      users: string;
      orders: string;
      revenue: string;
      growth: string;
    };
    custom: {
      brown: string;
      dark: string;
      light: string;
      bgColor: string;
    };
    severity: {
      error: string;
      warning: string;
      info: string;
      success: string;
    };
  }
}

const themeOptions = {
  palette: {
    mode: "light",
    primary: {
      main: "#1976d2",
    },
    secondary: {
      main: "#dc004e",
    },
    statCard: {
      users: "#8280FF",
      orders: "#FFF3D6",
      revenue: "#D9F7E8",
      growth: "#E0C3B8",
    },
    background: {
      default: "#ffffff",
      paper: "#ffffff",
    },
    text: {
      primary: "rgba(0, 0, 0, 0.87)",
      secondary: "rgba(0, 0, 0, 0.6)",
    },
    custom: {
      brown: "#795548",
      dark: "#424242",
      light: "#f5f5f5",
      bgColor: "#ffffff",
    },
    severity: {
      error: "#f44336",
      warning: "#ff9800",
      info: "#2196f3",
      success: "#4caf50",
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    "display-large": {
      fontSize: "3.5rem",
      fontWeight: 300,
      lineHeight: 1.2,
    },
    "display-medium": {
      fontSize: "2.8rem",
      fontWeight: 300,
      lineHeight: 1.2,
    },
    "display-small": {
      fontSize: "2.2rem",
      fontWeight: 400,
      lineHeight: 1.2,
    },
    "headline-large": {
      fontSize: "2rem",
      fontWeight: 400,
      lineHeight: 1.2,
    },
    "headline-medium": {
      fontSize: "1.75rem",
      fontWeight: 400,
      lineHeight: 1.2,
    },
    "headline-small": {
      fontSize: "1.5rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "title-large": {
      fontSize: "1.25rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "title-medium": {
      fontSize: "1.1rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "title-small": {
      fontSize: "1rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "body-large": {
      fontSize: "1rem",
      fontWeight: 400,
      lineHeight: 1.5,
    },
    "body-medium": {
      fontSize: "0.875rem",
      fontWeight: 400,
      lineHeight: 1.5,
    },
    "body-small": {
      fontSize: "0.75rem",
      fontWeight: 400,
      lineHeight: 1.5,
    },
    "body-regular": {
      fontSize: "1rem",
      fontWeight: 400,
      lineHeight: 1.5,
    },
    "body-light": {
      fontSize: "0.875rem",
      fontWeight: 300,
      lineHeight: 1.5,
    },
    "label-large": {
      fontSize: "0.875rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "label-medium": {
      fontSize: "0.75rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
    "label-small": {
      fontSize: "0.625rem",
      fontWeight: 500,
      lineHeight: 1.2,
    },
  },
  components: {
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
        },
      },
    },
    MuiButton: {
      defaultProps: {
        variant: "primary",
      },
      variants: [
        {
          props: { variant: "primary" },
          style: {
            background: "var(--color-button-primary)",
            color: "white",
          },
        },
        {
          props: { variant: "secondary" },
          style: {
            background: "linear-gradient(270deg, #EEEEEE 0%, #DEDEDE 100%)",
            color: "black",
          },
        },
        {
          props: { variant: "outlined" },
          style: {
            backgroundColor: "#FFFFFF0A",
            color: "white",
            border: "none",
          },
        },
      ],
      styleOverrides: {
        sizeLarge: {
          height: "52px",
        },
        sizeMedium: {
          height: "40px",
        },
        sizeSmall: {
          height: "32px",
        },
      },
    },
  },
} as ThemeOptions;

export const theme = createTheme(themeOptions);
