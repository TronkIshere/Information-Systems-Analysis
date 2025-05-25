import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  DepartmentResponse,
  UpdateDepartmentRequest,
  UploadDepartmentRequest,
} from "@/types/api";

export type DepartmentListResponse = DepartmentResponse[];

export const getAllDepartments = async (): Promise<DepartmentListResponse> => {
  const response = await api.get<DepartmentListResponse>("/departments/list");
  return response || [];
};

const departmentQueryKey = ["departments"];

export const getDepartmentsQueryOptions = () => {
  return queryOptions({
    queryKey: departmentQueryKey,
    queryFn: getAllDepartments,
  });
};

export const useDepartments = () => {
  return useQuery(getDepartmentsQueryOptions());
};

export const useCreateDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (receipt: UploadDepartmentRequest) =>
      api.post<DepartmentResponse>("/departments", receipt),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: departmentQueryKey });
    },
  });
};

export const useUpdateDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (department: UpdateDepartmentRequest) =>
      api.put<DepartmentResponse>("/departments", department),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: departmentQueryKey });
    },
  });
};

export const useDeleteDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/departments/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: departmentQueryKey });
    },
  });
};
