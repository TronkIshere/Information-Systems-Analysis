import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  SemesterResponse,
  UpdateSemesterRequest,
  UploadSemesterRequest,
} from "@/types/api";

export type SemesterListResponse = SemesterResponse[];

export const getAllSemesters = async (): Promise<SemesterListResponse> => {
  const response = await api.get<SemesterListResponse>("/semesters/list");
  return response || [];
};

const semesterQueryKey = ["semesters"];

export const getSemestersQueryOptions = () => {
  return queryOptions({
    queryKey: semesterQueryKey,
    queryFn: getAllSemesters,
  });
};

export const useSemesters = () => {
  return useQuery(getSemestersQueryOptions());
};

export const useCreateSemester = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (semester: UploadSemesterRequest) =>
      api.post<SemesterResponse>("/semesters", semester),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: semesterQueryKey });
    },
  });
};

export const useUpdateSemester = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (semester: UpdateSemesterRequest) =>
      api.put<SemesterResponse>("/semesters", semester),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: semesterQueryKey });
    },
  });
};

export const useDeleteSemester = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/semesters/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: semesterQueryKey });
    },
  });
};
