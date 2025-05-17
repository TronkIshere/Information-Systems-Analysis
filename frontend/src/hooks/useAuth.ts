import { useAuthStore } from "@/stores/useAuthStore";

export const useAuth = () => {
  const { user, login, logout } = useAuthStore();
  return { user, login, logout };
};