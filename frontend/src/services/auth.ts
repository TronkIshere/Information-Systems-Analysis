import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { z } from "zod";

import { AuthResponse, ErrorResponse, User } from "@/types/api";

import { api } from "@/lib/api-client";

// api call definitions for auth (types, schemas, requests):
// these are not part of features as this is a module shared across features

export const getUser = async (): Promise<User> => {
  const response = (await api.get("/auth/me")) as { data: User };

  return response.data;
};

const userQueryKey = ["user"];

export const getUserQueryOptions = () => {
  return queryOptions({
    queryKey: userQueryKey,
    queryFn: getUser,
  });
};

export const useUser = () => useQuery(getUserQueryOptions());

export const useLogin = ({
  onSuccess,
  onError,
}: {
  onSuccess?: (data: AuthResponse) => void;
  onError?: (error: ErrorResponse) => void;
}) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: loginWithEmailAndPassword,
    onError: (err: ErrorResponse) => {
      onError?.(err);
    },
    onSuccess: (data) => {
      queryClient.setQueryData(userQueryKey, data);
      onSuccess?.(data);
    },
  });
};

export const useRegister = ({ onSuccess }: { onSuccess?: () => void }) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: registerWithEmailAndPassword,
    onSuccess: (data) => {
      // queryClient.setQueryData(userQueryKey, data);
      onSuccess?.();
    },
  });
};

export const useLogout = ({ onSuccess }: { onSuccess?: () => void }) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: logout,
    onSuccess: () => {
      queryClient.removeQueries({ queryKey: userQueryKey });
      onSuccess?.();
    },
  });
};

const logout = (): Promise<void> => {
  return api.post("/auth/logout");
};

export const loginInputSchema = z.object({
  loginName: z.string().min(5, "loginName không hợp lệ"),
  password: z.string().min(5, "Vui lòng nhập mật khẩu"),
});

export type LoginInput = z.infer<typeof loginInputSchema>;
const loginWithEmailAndPassword = (data: LoginInput): Promise<AuthResponse> => {
  return api.post("/auth/login", data);
};

export const registerInputSchema = z.object({
  loginName: z.string().min(1, "Required"),
  name: z.string().min(1, "Required"),
  // password: z.string().min(5, "Required"),
  password: z.string().min(1, "Required"),
  phoneNumber: z.string(),
});

export type RegisterInput = z.infer<typeof registerInputSchema>;

const registerWithEmailAndPassword = (
  data: RegisterInput
): Promise<AuthResponse> => {
  return api.post("/auth/register", data);
};
