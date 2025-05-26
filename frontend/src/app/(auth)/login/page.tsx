"use client";
import { LoginForm } from "@/features/auth/components/LoginForm";
import ClientSession from "@/services/session/client.session";
import { AuthResponse } from "@/types/api";
import { ADMIN_URL, STUDENT_URL } from "@/types/app.url.type";
import { ToastType } from "@/types/toast";
import useAppRoute from "@/utils/route";
import UIHelper from "@/utils/ui.helper.util";
import { useState } from "react";

export default function LoginPage() {
  const [accessToken, setAccessToken] = useState("");
  const { replace } = useAppRoute();

  const onSucessLogin = (data: AuthResponse) => {
    ClientSession.setAuthToken(data.accessToken);
    ClientSession.setUserId(data.userId);
    ClientSession.setRoles(data.roles.split(",").map((role) => role.trim()));

    UIHelper.showToast({
      message: "Đăng nhập thành công",
      type: ToastType.success,
    });

    const roles = data.roles.split(",").map((role) => role.trim());
    let redirectUrl: string = ADMIN_URL.HOME;

    if (roles.includes("ROLE_ADMIN")) {
      redirectUrl = ADMIN_URL.HOME;
    } else if (roles.includes("ROLE_STUDENT")) {
      redirectUrl = STUDENT_URL.HOME;
    }

    setTimeout(() => {
      window.location.href = redirectUrl;
    }, 1000);
  };

  return (
    <main className="h-full bg-[var(--color-primary)] shape-login">
      <LoginForm onSuccess={(data) => onSucessLogin(data)} />
    </main>
  );
}
