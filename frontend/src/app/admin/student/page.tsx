"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useCreateStudent,
  useDeleteStudent,
  useStudents,
  useUpdateStudent,
} from "@/services/student";
import {
  StudentResponse,
  UpdateStudentRequest,
  UploadStudentRequest,
} from "@/types/api";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  MenuItem,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

function StudentPage() {
  const { data: students, isLoading, error } = useStudents();
  const { mutate: updateStudent } = useUpdateStudent();
  const { mutate: deleteStudent } = useDeleteStudent();
  const { mutate: createStudent } = useCreateStudent();

  const [filteredData, setFilteredData] = useState<StudentResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedStudent, setSelectedStudent] = useState<Partial<
    UpdateStudentRequest | UploadStudentRequest
  > | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [studentToDelete, setStudentToDelete] =
    useState<StudentResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<StudentResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "studentCode", header: "Mã SV", sortingFn: "basic" },
    { accessorKey: "name", header: "Tên sinh viên" },
    { accessorKey: "phoneNumber", header: "SĐT" },
    { accessorKey: "email", header: "Email" },
    {
      accessorKey: "status",
      header: "Trạng thái",
      cell: ({ row }) =>
        row.original.status === "ACTIVE" ? "Hoạt động" : "Khóa",
    },
    { accessorKey: "major", header: "Chuyên ngành" },
    {
      accessorKey: "gpa",
      header: "GPA",
      cell: ({ row }) => row.original.gpa?.toFixed(2) || "N/A",
    },
    {
      accessorKey: "actions",
      header: "Thao tác",
      cell: ({ row }) => (
        <Stack direction="row" gap={1}>
          <IconEdit
            className="cursor-pointer text-blue-500"
            onClick={(e) => {
              e.stopPropagation();
              setSelectedStudent(row.original);
              setIsEditModalOpen(true);
            }}
          />
          <IconBin
            className="cursor-pointer text-red-500"
            onClick={(e) => {
              e.stopPropagation();
              handleDeleteClick(row.original);
            }}
          />
        </Stack>
      ),
    },
  ];

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSelectedStudent((prev) => ({
      ...prev!,
      [name]: name === "gpa" ? Number(value) : value,
    }));
  };

  const handleDateChange = (name: string, value: string) => {
    setSelectedStudent((prev) => ({
      ...prev!,
      [name]: new Date(value).toISOString(),
    }));
  };

  const handleGenderChange = (value: string) => {
    setSelectedStudent((prev) => ({
      ...prev!,
      gender: value === "null" ? null : value === "true",
    }));
  };

  const handleSubmit = () => {
    if (selectedStudent) {
      const payload = {
        ...selectedStudent,
        birthDay: selectedStudent.birthDay
          ? new Date(selectedStudent.birthDay).toISOString()
          : undefined,
        gender: selectedStudent.gender ?? null,
      };

      const operation = isCreating ? createStudent : updateStudent;
      operation(payload as any, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedStudent(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (student: StudentResponse) => {
    setStudentToDelete(student);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (studentToDelete?.id) {
      deleteStudent(studentToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setStudentToDelete(null);
        },
      });
    }
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      const filtered = (students || []).filter((item) =>
        Object.values(item).some(
          (value) =>
            value &&
            value.toString().toLowerCase().includes(searchTerm.toLowerCase())
        )
      );
      setFilteredData(filtered);
    } finally {
      setIsSearching(false);
    }
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error loading students</div>;

  return (
    <>
      <div className="flex justify-between items-center mb-4">
        <Typography variant="h4">Quản lý sinh viên</Typography>
        <div className="flex gap-2">
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm sinh viên..."
            className="w-64"
          />
          <Button
            color="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedStudent({
                studentCode: "",
                name: "",
                phoneNumber: "",
                email: "",
                status: "ACTIVE",
                password: "",
                gender: null,
              });
              setIsCreating(true);
              setIsEditModalOpen(true);
            }}
          >
            Thêm mới
          </Button>
        </div>
      </div>

      <CustomTable
        columns={columns}
        data={filteredData.length > 0 ? filteredData : students || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm sinh viên mới" : "Cập nhật thông tin"}
        </DialogTitle>
        <DialogContent>
          {selectedStudent && (
            <div className="grid grid-cols-2 gap-4 mt-4">
              {isCreating && (
                <>
                  <TextField
                    fullWidth
                    label="Tên đăng nhập"
                    name="loginName"
                    value={
                      (selectedStudent as UploadStudentRequest).loginName || ""
                    }
                    onChange={handleInputChange}
                    required
                  />
                  <TextField
                    fullWidth
                    label="Mật khẩu"
                    type="password"
                    name="password"
                    value={selectedStudent.password || ""}
                    onChange={handleInputChange}
                    required
                  />
                </>
              )}

              <TextField
                fullWidth
                label="Mã sinh viên"
                name="studentCode"
                value={selectedStudent.studentCode || ""}
                onChange={handleInputChange}
                required
              />
              <TextField
                fullWidth
                label="Họ và tên"
                name="name"
                value={selectedStudent.name || ""}
                onChange={handleInputChange}
                required
              />
              <TextField
                fullWidth
                label="Email"
                type="email"
                name="email"
                value={selectedStudent.email || ""}
                onChange={handleInputChange}
                required
              />
              <TextField
                fullWidth
                label="Số điện thoại"
                name="phoneNumber"
                value={selectedStudent.phoneNumber || ""}
                onChange={handleInputChange}
                required
              />
              <TextField
                fullWidth
                label="Chuyên ngành"
                name="major"
                value={selectedStudent.major || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="GPA"
                type="number"
                name="gpa"
                value={selectedStudent.gpa || ""}
                onChange={handleInputChange}
                inputProps={{
                  step: "0.01",
                  min: "0",
                  max: "4",
                  pattern: "\\d+(\\.\\d{1,2})?",
                }}
              />
              <TextField
                fullWidth
                label="Ngày sinh"
                type="date"
                name="birthDay"
                value={
                  selectedStudent.birthDay
                    ? new Date(selectedStudent.birthDay)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={(e) => handleDateChange("birthDay", e.target.value)}
                InputLabelProps={{ shrink: true }}
              />
              <TextField
                select
                fullWidth
                label="Giới tính"
                value={
                  selectedStudent.gender === null
                    ? "null"
                    : String(selectedStudent.gender)
                }
                onChange={(e) => handleGenderChange(e.target.value)}
              >
                <MenuItem value="null">Chưa xác định</MenuItem>
                <MenuItem value="true">Nam</MenuItem>
                <MenuItem value="false">Nữ</MenuItem>
              </TextField>
              <TextField
                select
                fullWidth
                label="Trạng thái"
                name="status"
                value={selectedStudent.status || "Hoạt động"}
                onChange={handleInputChange}
              >
                <MenuItem value="Hoạt động">Hoạt động</MenuItem>
                <MenuItem value="Khóa">Khóa</MenuItem>
              </TextField>
            </div>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setIsEditModalOpen(false)}>Hủy</Button>
          <Button onClick={handleSubmit}>
            {isCreating ? "Tạo mới" : "Cập nhật"}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Delete Confirmation Dialog */}
      <Dialog
        open={isDeleteModalOpen}
        onClose={() => setIsDeleteModalOpen(false)}
      >
        <DialogTitle>Xác nhận xóa</DialogTitle>
        <DialogContent>
          {studentToDelete && (
            <Typography>
              Bạn có chắc muốn xóa sinh viên {studentToDelete.name}?
              <br />
              <small>(Mã SV: {studentToDelete.studentCode})</small>
            </Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setIsDeleteModalOpen(false)}>Hủy</Button>
          <Button onClick={confirmDelete} color="error">
            Xác nhận xóa
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default StudentPage;
