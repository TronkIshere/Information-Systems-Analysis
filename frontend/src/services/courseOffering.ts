import {
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import { api } from "@/lib/api-client";
import {
  CourseOfferingResponse,
  UpdateCourseOfferingRequest,
  UploadCourseOfferingRequest,
} from "@/types/api";

export type CourseOfferingListResponse = CourseOfferingResponse[];

export const getAllCourseOfferings =
  async (): Promise<CourseOfferingListResponse> => {
    const response = await api.get<CourseOfferingListResponse>(
      "/courseOfferings/list"
    );
    return response || [];
  };

const courseOfferingQueryKey = ["courseOfferings"];

export const getCoursesOfferingQueryOptions = () => {
  return queryOptions({
    queryKey: courseOfferingQueryKey,
    queryFn: getAllCourseOfferings,
  });
};

export const useCourseOfferings = () => {
  return useQuery(getCoursesOfferingQueryOptions());
};

export const getAllOpenCourseOfferings =
  async (): Promise<CourseOfferingListResponse> => {
    const response = await api.get<CourseOfferingListResponse>(
      "/courseOfferings/open-list"
    );
    return response || [];
  };

const openCourseOfferingQueryKey = ["openCourseOfferings"];

export const getOpenCoursesOfferingQueryOptions = () => {
  return queryOptions({
    queryKey: openCourseOfferingQueryKey,
    queryFn: getAllOpenCourseOfferings,
  });
};

export const useOpenCourseOfferings = () => {
  return useQuery(getOpenCoursesOfferingQueryOptions());
};

export const useCreateCourseOffering = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (receipt: UploadCourseOfferingRequest) =>
      api.post<CourseOfferingResponse>("/courseOfferings", receipt),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseOfferingQueryKey });
    },
  });
};

export const useUpdateCourseOffering = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (course: UpdateCourseOfferingRequest) =>
      api.put<CourseOfferingResponse>("/courseOfferings", course),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseOfferingQueryKey });
    },
  });
};

export const useDeleteCourseOffering = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => api.delete(`/courseOfferings/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: courseOfferingQueryKey });
    },
  });
};
