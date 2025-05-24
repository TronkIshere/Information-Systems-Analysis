"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useDeleteStudent,
  useStudents,
  useUpdateStudent,
} from "@/services/student";
import { StudentResponse } from "@/types/api";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
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

  const [filteredData, setFilteredData] = useState<StudentResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedStudent, setSelectedStudent] =
    useState<StudentResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [studentToDelete, setStudentToDelete] =
    useState<StudentResponse | null>(null);

  const columns: ColumnDef<StudentResponse>[] = [
    { accessorKey: "id", header: "ID" },
    {
      accessorKey: "studentCode",
      header: "Mã sinh viên",
      sortingFn: "basic",
    },
    { accessorKey: "name", header: "Tên sinh viên" },
    { accessorKey: "phoneNumber", header: "Số điện thoại" },
    { accessorKey: "email", header: "Email" },
    { accessorKey: "status", header: "Trạng thái" },
    { accessorKey: "major", header: "Chuyên ngành" },
    { accessorKey: "gpa", header: "GPA" },
    {
      accessorKey: "actions",
      header: "Hành động",
      cell: ({ row }) => (
        <Stack
          direction="row"
          alignItems="center"
          gap="16px"
          justifyContent="space-between"
          className="border border-solid border-[#D5D5D5] rounded-lg py-2 px-4"
        >
          <IconEdit
            onClick={(e) => {
              e.stopPropagation();
              setSelectedStudent(row.original);
              setIsEditModalOpen(true);
            }}
          />
          <IconBin
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
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    if (selectedStudent) {
      updateStudent(selectedStudent, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedStudent(null);
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
          setIsEditModalOpen(false);
          setStudentToDelete(null);
          setSelectedStudent(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setStudentToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(students || []);
        return;
      }

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
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý sinh viên
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm sinh viên..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button variant="primary" className="whitespace-nowrap">
            Tìm kiếm
          </Button>
        </Stack>
      </Stack>
      <CustomTable
        columns={columns}
        data={filteredData.length > 0 ? filteredData : students || []}
      />

      {/* Edit Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {selectedStudent ? "Chỉnh sửa thông tin" : "Thêm sinh viên mới"}
        </DialogTitle>
        <DialogContent>
          {selectedStudent && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <TextField
                fullWidth
                label="Mã sinh viên"
                name="studentCode"
                value={selectedStudent.studentCode || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Tên sinh viên"
                name="name"
                value={selectedStudent.name || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Email"
                name="email"
                value={selectedStudent.email || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Số điện thoại"
                name="phoneNumber"
                value={selectedStudent.phoneNumber || ""}
                onChange={handleInputChange}
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
                name="gpa"
                type="number"
                value={selectedStudent.gpa || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Ngày sinh"
                name="birthDay"
                type="date"
                InputLabelProps={{ shrink: true }}
                value={selectedStudent.birthDay || ""}
                onChange={handleInputChange}
              />
              <TextField
                select
                fullWidth
                label="Giới tính"
                name="gender"
                value={selectedStudent.gender ? "true" : "false"}
                onChange={handleInputChange}
                SelectProps={{
                  native: true,
                }}
              >
                <option value="true">Nam</option>
                <option value="false">Nữ</option>
              </TextField>
            </Stack>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setIsEditModalOpen(false)}>Hủy</Button>
          <Button onClick={handleUpdate}>Lưu thay đổi</Button>
          {selectedStudent?.id && (
            <Button
              color="error"
              onClick={() => handleDeleteClick(selectedStudent)}
            >
              Xóa sinh viên
            </Button>
          )}
        </DialogActions>
      </Dialog>

      {/* Delete Confirmation Dialog */}
      <Dialog
        open={isDeleteModalOpen}
        onClose={cancelDelete}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Xác nhận xóa</DialogTitle>
        <DialogContent>
          {studentToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa sinh viên{" "}
              <strong>{studentToDelete.name}</strong> (Mã:{" "}
              {studentToDelete.studentCode}) không?
            </Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={cancelDelete}>Hủy</Button>
          <Button color="error" onClick={confirmDelete}>
            Xóa
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default StudentPage;
