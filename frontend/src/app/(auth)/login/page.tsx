"use client";
import { LoginForm } from "@/features/auth/components/LoginForm";
import ClientSession from "@/services/session/client.session";
import { AuthResponse } from "@/types/api";
import { ADMIN_URL } from "@/types/app.url.type";
import { ToastType } from "@/types/toast";
import useAppRoute from "@/utils/route";
import UIHelper from "@/utils/ui.helper.util";
import { useState } from "react";
export default function LoginPage() {
  const [accessToken, setAccessToken] = useState("");
  const { replace } = useAppRoute();

  const onSucessLogin = (data: AuthResponse) => {
    // Set token to cookie when success login
    ClientSession.setAuthToken(data.accessToken);
    window.location.href = ADMIN_URL.HOME;
    UIHelper.showToast({
      message: "Đăng nhập thành công",
      type: ToastType.success,
    });
  };

  return (
    <main className="h-full bg-[var(--color-primary)] shape-login">
      <LoginForm onSuccess={(data) => onSucessLogin(data)} />
    </main>
  );
}
