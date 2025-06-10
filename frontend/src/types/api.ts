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
  roles: string;
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
  departmentId: string;
  courseIds: string[];
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
  departmentId: string;
  courseIds: string[];
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
  departmentId: string;
  courseIds: string[];
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
  birthDay?: Date;
  gender: boolean | null;
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
  birthDay?: Date;
  gender: boolean | null;
  loginName?: string;
  password?: string;
}

export interface UploadStudentRequest {
  id: string;
  studentCode: string;
  major?: string;
  gpa?: number;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  birthDay?: Date;
  gender: boolean | null;
  loginName?: string;
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
  paymentDate: Date | null;
  studentId: string;
  semesterName: string;
  studentName: string;
  studentClass: string;
  studentCode: string;
  semesterId: string;
  courseIds: string[];
  cashierId: string;
}

export interface UploadReceiptRequest {
  totalAmount: number;
  status: boolean;
  description: string;
  paymentDate: Date | null;
  studentId: string;
  studentName: string;
  studentClass: string;
  studentCode: string;
  semesterId: string;
  courseIds: string[];
  cashierId: string;
}

export interface UpdateReceiptRequest {
  id: string;
  totalAmount: number;
  status: boolean;
  description: string;
  paymentDate: Date | null;
  studentClass: string;
  studentName: string;
  studentCode: string;
  studentId: string;
  semesterId: string;
  courseIds: string[];
  cashierId: string;
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

export interface MenuItem {
  text: string;
  icon: React.ReactNode;
  link: string;
  type?: string;
}

export interface CashierResponse {
  id: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  loginName: string;
  birthDay: string;
  gender: boolean;
  salary: number;
  hireDate: string;
}

export interface UploadCashierRequest {
  id: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  loginName: string;
  birthDay: string;
  gender: boolean;
  salary: number;
  hireDate: string;
  password?: string;
}

export interface UpdateCashierRequest {
  id: string;
  name: string;
  email: string;
  phoneNumber: string;
  status: string;
  loginName: string;
  birthDay: string;
  gender: boolean;
  salary: number;
  hireDate: string;
  password?: string;
}

export interface CourseOfferingResponse {
  id: string;
  startDate: Date | null;
  endDate: Date | null;
  courseId: string;
  courseName: string;
  credit: number;
  baseFeeCredit: number;
  subjectType: boolean;
}

export interface UploadCourseOfferingRequest {
  id: string;
  startDate: Date | null;
  endDate: Date | null;
}

export interface UpdateCourseOfferingRequest {
  id: string;
  startDate: Date | null;
  endDate: Date | null;
}
