// ========================
// 📦 Base Types
// ========================

export type ResponseData<T> = {
  code: number;
  data: T | null;
  message: string;
};

export interface ResponseAPI<T> {
  code: number;
  message: string;
  data: T | null;
}

export type BaseEntity = {
  id: string;
  createdAt: number;
};

export type Entity<T> = {
  [K in keyof T]: T[K];
} & BaseEntity;

export type Meta = {
  page: number;
  total: number;
  totalPages: number;
};

export type ErrorResponse = {
  code: number;
  message: string;
};

// ========================
// 👤 User & Auth
// ========================

export type User = Entity<{
  firstName: string;
  lastName: string;
  email: string;
  role: "ADMIN" | "USER";
  teamId: string;
  bio: string;
}>;

export type AuthResponse = Entity<{
  accessToken: string;
  refreshToken: string;
  status: string;
  userType: "STUDENT" | "LECTURER" | "ADMIN";
  userId: string;
}>;

// ========================
// 🧑‍🏫 Lecturer
// ========================

export interface LecturerResponse {
  id: string;
  lecturerCode: string;
  academicRank: string;
  salary: number | null;
  hireDate: Date | null;
  researchField: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay: Date | null;
  gender: boolean | null;
  password?: string;
}

export type LecturerListResponse = LecturerResponse[];

export interface UploadLecturerRequest {
  lecturerCode: string;
  academicRank: string;
  salary: number | null;
  hireDate: Date | null;
  researchField: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay: Date | null;
  gender: boolean | null;
  password?: string;
}

export interface UpdateLecturerRequest {
  id: string;
  lecturerCode: string;
  academicRank: string;
  salary: number | null;
  hireDate: Date | null;
  researchField: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay: Date | null;
  gender: boolean | null;
}

// ========================
// 🎓 Student
// ========================

export interface StudentResponse {
  id: string;
  studentCode: string;
  major?: string;
  gpa?: number;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay?: string;
  gender?: boolean;
  loginName?: string;
}

export interface UpdateStudentRequest {
  id: string;
  studentCode: string;
  major?: string;
  gpa?: number;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay?: string;
  gender?: boolean;
  password?: string;
}

// ========================
// 📚 Course
// ========================

export interface CourseResponse {
  id: string;
  name: string;
  credit: number;
  baseFeeCredit: number;
  subjectType: boolean;
}

export interface UploadCourseRequest {
  name: string;
  credit: number;
  baseFeeCredit: number;
  subjectType: boolean;
}

export interface UpdateCourseRequest {
  id: string;
  name: string;
  credit: number;
  baseFeeCredit: number;
  subjectType: boolean;
}

export interface Course {
  id: string;
  name: string;
  credit: number;
}

// ========================
// 🏛️ Department
// ========================

export interface DepartmentResponse {
  id: string;
  name: string;
}

export interface UploadDepartmentRequest {
  name: string;
}

export interface UpdateDepartmentRequest {
  id: string;
  name: string;
}

// ========================
// 📅 Semester
// ========================

export interface SemesterResponse {
  id: string;
  name: string;
  startDate: Date | null;
  endDate: Date | null;
}

export interface UpdateSemesterRequest {
  id: string;
  name: string;
  startDate: Date | null;
  endDate: Date | null;
}

export interface UploadSemesterRequest {
  name: string;
  startDate: Date | null;
  endDate: Date | null;
}

// ========================
// 🧾 Receipt
// ========================

export interface ReceiptResponse {
  id: string;
  totalAmount: number;
  status: boolean;
  description: string;
  paymentDate: string;
  studentId: string;
  studentName: string;
  semesterId: string;
  semesterName: string;
  courseIds: string[];
}

export interface UploadReceiptRequest {
  totalAmount: number;
  status: boolean;
  description: string;
  paymentDate: string;
  studentId: string;
  semesterId: string;
  courseIds: string[];
}

export interface UpdateReceiptRequest extends UploadReceiptRequest {
  id: string;
}

// ========================
// 💬 Team, Discussion, Comment
// ========================

export type Team = Entity<{
  name: string;
  description: string;
}>;

export type Discussion = Entity<{
  title: string;
  body: string;
  teamId: string;
  author: User;
  public: boolean;
}>;

export type Comment = Entity<{
  body: string;
  discussionId: string;
  author: User;
}>;
