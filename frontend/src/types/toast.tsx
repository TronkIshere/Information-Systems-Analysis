"use client";
import { Check, Info, Warning, Error, Close } from "@mui/icons-material";

export const enum ToastType {
  success = "success",
  error = "error",
  warning = "warning",
  info = "info",
}

export const VARIANTS: any = {
  info: {
    icon: <Info />,
    color: "info",
  },
  success: {
    icon: <Check />,
    color: "success",
  },
  warning: {
    icon: <Warning />,
    color: "warning",
  },
  error: {
    icon: <Error />,
    color: "error",
  },
};
export interface IToastContent {
  title?: string;
  message: string;
  variant: ToastType;
  onClose: () => void;
  autoHideDuration?: number;
}
