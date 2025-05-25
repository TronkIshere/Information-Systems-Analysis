import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  ReceiptResponse,
  UpdateReceiptRequest,
  UploadReceiptRequest,
} from "@/types/api";

export type ReceiptListResponse = ReceiptResponse[];

export const getAllReceipts = async (): Promise<ReceiptListResponse> => {
  const response = await api.get<ReceiptListResponse>("/receipts/list");
  console.log(response);
  return response || [];
};

const receiptQueryKey = ["receipts"];

export const getReceiptsQueryOptions = () => {
  return queryOptions({
    queryKey: receiptQueryKey,
    queryFn: getAllReceipts,
  });
};

export const useReceipts = () => {
  return useQuery(getReceiptsQueryOptions());
};

export const useCreateReceipt = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (receipt: UploadReceiptRequest) =>
      api.post<ReceiptResponse>("/receipts", receipt),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: receiptQueryKey });
    },
  });
};

export const useUpdateReceipt = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (receipt: UpdateReceiptRequest) =>
      api.put<ReceiptResponse>("/receipts", receipt),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: receiptQueryKey });
    },
  });
};

export const useDeleteReceipt = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/receipts/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: receiptQueryKey });
    },
  });
};
