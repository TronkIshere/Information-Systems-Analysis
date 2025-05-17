import React from "react";
import {
  Snackbar,
  Alert,
  AlertTitle,
  Typography,
  IconButton,
} from "@mui/material";
import { Close } from "@mui/icons-material";
import { IToastContent, VARIANTS } from "@/types/toast";

const ToastContent = (props: IToastContent) => {
  const { title, message, variant, onClose, autoHideDuration = 6000 } = props;
  const { icon, color } = VARIANTS[variant] || VARIANTS.info;
  return (
    <Snackbar
      open={true}
      autoHideDuration={autoHideDuration}
      onClose={onClose}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
    >
      <Alert
        severity={color}
        icon={icon}
        action={
          <IconButton
            aria-label="close"
            color="inherit"
            size="small"
            onClick={onClose}
          >
            <Close fontSize="small" />
          </IconButton>
        }
        sx={{
          width: "100%",
          boxShadow: 2,
          alignItems: "flex-start",
        }}
      >
        {title && <AlertTitle sx={{ fontWeight: "bold" }}>{title}</AlertTitle>}
        <Typography variant="body2">{message}</Typography>
      </Alert>
    </Snackbar>
  );
}

export default ToastContent;