import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  StudentResponse,
  UpdateStudentRequest,
  UploadStudentRequest,
} from "@/types/api";

export type StudentListResponse = StudentResponse[];

export const getAllStudents = async (): Promise<StudentListResponse> => {
  const response = await api.get<StudentListResponse>("/students/list");
  return response || [];
};

const studentQueryKey = ["students"];

export const getStudentsQueryOptions = () => {
  return queryOptions({
    queryKey: studentQueryKey,
    queryFn: getAllStudents,
  });
};

export const useStudents = () => {
  return useQuery(getStudentsQueryOptions());
};

export const useCreateStudent = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (student: UploadStudentRequest) =>
      api.post<StudentResponse>("/students", student),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: studentQueryKey });
    },
  });
};

export const useUpdateStudent = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (student: UpdateStudentRequest) =>
      api.put<StudentResponse>("/students", student),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: studentQueryKey });
    },
  });
};

export const useDeleteStudent = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/students/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: studentQueryKey });
    },
  });
};
