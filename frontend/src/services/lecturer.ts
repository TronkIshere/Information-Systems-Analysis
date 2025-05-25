import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { z } from "zod";

import {
  LecturerResponse,
  ResponseAPI,
  UpdateLecturerRequest,
  UploadLecturerRequest,
} from "@/types/api";
import { api } from "@/lib/api-client";

export type LecturerListResponse = LecturerResponse[];

export const getAllLecturers = async (): Promise<LecturerListResponse> => {
  const response = await api.get<LecturerListResponse>("/lecturers/list");
  console.log(response);
  return response || [];
};

const lecturerQueryKey = ["lecturers"];

export const getLecturersQueryOptions = () => {
  return queryOptions({
    queryKey: lecturerQueryKey,
    queryFn: getAllLecturers,
  });
};

export const useLecturers = () => {
  return useQuery(getLecturersQueryOptions());
};

export const useCreateLecturer = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (department: UploadLecturerRequest) =>
      api.post<LecturerResponse>("/lecturers", department),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: lecturerQueryKey });
    },
  });
};

export const useUpdateLecturer = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (lecturer: UpdateLecturerRequest) =>
      api.put<LecturerResponse>("/lecturers", lecturer),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: lecturerQueryKey });
    },
  });
};

export const useDeleteLecturer = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/lecturers/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: lecturerQueryKey });
    },
  });
};
