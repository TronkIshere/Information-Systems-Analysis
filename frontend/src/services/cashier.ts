import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  CashierResponse,
  UpdateCashierRequest,
  UploadCashierRequest,
} from "@/types/api";

export type CashierListResponse = CashierResponse[];

export const getAllCashiers = async (): Promise<CashierListResponse> => {
  const response = await api.get<CashierListResponse>("/cashiers/list");
  return response || [];
};

const cashierQueryKey = ["cashiers"];

export const getCashiersQueryOptions = () => {
  return queryOptions({
    queryKey: cashierQueryKey,
    queryFn: getAllCashiers,
  });
};

export const useCashiers = () => {
  return useQuery(getCashiersQueryOptions());
};

export const useCreateCashier = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (cashier: UploadCashierRequest) =>
      api.post<CashierResponse>("/cashiers", cashier),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: cashierQueryKey });
    },
  });
};

export const useUpdateCashier = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (cashier: UpdateCashierRequest) =>
      api.put<CashierResponse>("/cashiers", cashier),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: cashierQueryKey });
    },
  });
};

export const useDeleteCashier = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/cashiers/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: cashierQueryKey });
    },
  });
};
