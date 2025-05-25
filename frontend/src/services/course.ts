import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  CourseResponse,
  UpdateCourseRequest,
  UploadCourseRequest,
} from "@/types/api";

export type CourseListResponse = CourseResponse[];

export const getAllCourses = async (): Promise<CourseListResponse> => {
  const response = await api.get<CourseListResponse>("/courses/list");
  return response || [];
};

const courseQueryKey = ["courses"];

export const getCoursesQueryOptions = () => {
  return queryOptions({
    queryKey: courseQueryKey,
    queryFn: getAllCourses,
  });
};

export const useCourses = () => {
  return useQuery(getCoursesQueryOptions());
};

export const useCreateCourse = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (receipt: UploadCourseRequest) =>
      api.post<CourseResponse>("/courses", receipt),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseQueryKey });
    },
  });
};

export const useUpdateCourse = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (course: UpdateCourseRequest) =>
      api.put<CourseResponse>("/courses", course),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseQueryKey });
    },
  });
};

export const useDeleteCourse = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/courses/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseQueryKey });
    },
  });
};
