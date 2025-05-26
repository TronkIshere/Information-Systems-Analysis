"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import { useCourses } from "@/services/course";
import { useDepartments } from "@/services/department";
import {
  useDeleteLecturer,
  useLecturers,
  useUpdateLecturer,
  useCreateLecturer,
} from "@/services/lecturer";
import { LecturerResponse } from "@/types/api";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Stack,
  TextField,
  Typography,
  Autocomplete,
  Checkbox,
  ListItemText,
  InputLabel,
  MenuItem,
  Select,
  FormControl,
  SelectChangeEvent,
} from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

function LecturerPage() {
  const { data: lecturers, isLoading, error } = useLecturers();
  const { data: courses } = useCourses();
  const { data: departments } = useDepartments();
  const { mutate: updateLecturer } = useUpdateLecturer();
  const { mutate: deleteLecturer } = useDeleteLecturer();
  const { mutate: createLecturer } = useCreateLecturer();

  const [filteredData, setFilteredData] = useState<LecturerResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedLecturer, setSelectedLecturer] =
    useState<LecturerResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [lecturerToDelete, setLecturerToDelete] =
    useState<LecturerResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<LecturerResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "lecturerCode", header: "Mã giảng viên" },
    { accessorKey: "name", header: "Tên giảng viên" },
    { accessorKey: "phoneNumber", header: "Số điện thoại" },
    { accessorKey: "email", header: "Email" },
    { accessorKey: "status", header: "Trạng thái" },
    {
      accessorKey: "departmentId",
      header: "bộ môn",
      cell: ({ row }) => {
        const department = departments?.find(
          (d) => d.id === row.original.departmentId
        );
        return department?.name || "Chưa nằm trong bộ môn";
      },
    },
    {
      accessorKey: "courseIds",
      header: "Số môn dạy",
      cell: ({ row }) => row.original.courseIds?.length || 0,
    },
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
              setSelectedLecturer(row.original);
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
    setSelectedLecturer((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleSelectChange = (event: SelectChangeEvent<string>) => {
    const { name, value } = event.target;
    setSelectedLecturer((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    if (selectedLecturer) {
      const operation = isCreating ? createLecturer : updateLecturer;
      operation(selectedLecturer, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedLecturer(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (lecturer: LecturerResponse) => {
    setLecturerToDelete(lecturer);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (lecturerToDelete?.id) {
      deleteLecturer(lecturerToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setLecturerToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setLecturerToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(lecturers || []);
        return;
      }

      const filtered = (lecturers || []).filter((item) =>
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
  if (error) return <div>Error loading lecturers</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý giảng viên
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm giảng viên..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedLecturer({
                id: "",
                lecturerCode: "",
                name: "",
                phoneNumber: "",
                email: "",
                academicRank: "",
                salary: null,
                hireDate: null,
                researchField: "",
                birthDay: null,
                gender: null,
                status: "Hoạt động",
                password: "",
                departmentId: "",
                courseIds: [],
              });
              setIsCreating(true);
              setIsEditModalOpen(true);
            }}
          >
            Thêm mới
          </Button>
        </Stack>
      </Stack>
      <CustomTable
        columns={columns}
        data={filteredData.length > 0 ? filteredData : lecturers || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm giảng viên mới" : "Chỉnh sửa thông tin"}
        </DialogTitle>
        <DialogContent>
          {selectedLecturer && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <TextField
                fullWidth
                label="Mã giảng viên"
                name="lecturerCode"
                value={selectedLecturer.lecturerCode || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Tên giảng viên"
                name="name"
                value={selectedLecturer.name || ""}
                onChange={handleInputChange}
              />
              <FormControl fullWidth>
                <InputLabel>Phòng ban</InputLabel>
                <Select
                  name="departmentId"
                  value={selectedLecturer.departmentId || ""}
                  onChange={handleSelectChange}
                  label="Phòng ban"
                >
                  {departments?.map((department) => (
                    <MenuItem key={department.id} value={department.id}>
                      {department.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <TextField
                fullWidth
                label="Email"
                name="email"
                value={selectedLecturer.email || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Số điện thoại"
                name="phoneNumber"
                value={selectedLecturer.phoneNumber || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Học hàm"
                name="academicRank"
                value={selectedLecturer.academicRank || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Lĩnh vực nghiên cứu"
                name="researchField"
                value={selectedLecturer.researchField || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Lương cơ bản"
                type="number"
                name="salary"
                value={selectedLecturer.salary ?? ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Ngày tuyển dụng"
                type="date"
                name="hireDate"
                value={
                  selectedLecturer.hireDate
                    ? new Date(selectedLecturer.hireDate)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={handleInputChange}
                InputLabelProps={{ shrink: true }}
              />

              <TextField
                fullWidth
                label="Ngày sinh"
                type="date"
                name="birthDay"
                value={
                  selectedLecturer.birthDay
                    ? new Date(selectedLecturer.birthDay)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={handleInputChange}
                InputLabelProps={{ shrink: true }}
              />

              <TextField
                select
                fullWidth
                label="Giới tính"
                name="gender"
                value={
                  selectedLecturer.gender !== null &&
                  selectedLecturer.gender !== undefined
                    ? selectedLecturer.gender.toString()
                    : ""
                }
                onChange={handleInputChange}
                SelectProps={{ native: true }}
              >
                <option value="">-- Chọn giới tính --</option>
                <option value="true">Nam</option>
                <option value="false">Nữ</option>
              </TextField>

              <TextField
                select
                fullWidth
                label="Trạng thái"
                name="status"
                value={selectedLecturer.status || "ACTIVE"}
                onChange={handleInputChange}
                SelectProps={{ native: true }}
              >
                <option value="Hoạt động">Hoạt động</option>
                <option value="Ngừng hoạt động">Ngừng hoạt động</option>
              </TextField>
              <Autocomplete
                multiple
                options={courses?.map((course) => course.id) || []}
                getOptionLabel={(option) =>
                  courses?.find((c) => c.id === option)?.name || ""
                }
                value={selectedLecturer.courseIds || []}
                onChange={(event, newValue) => {
                  setSelectedLecturer((prev) => ({
                    ...prev!,
                    courseIds: newValue,
                  }));
                }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Môn học giảng dạy"
                    placeholder="Chọn môn học"
                  />
                )}
                renderOption={(props, option) => {
                  const course = courses?.find((c) => c.id === option);
                  return (
                    <li {...props}>
                      <Checkbox
                        checked={
                          selectedLecturer.courseIds?.includes(option) || false
                        }
                      />
                      <ListItemText
                        primary={course?.name}
                        secondary={`${course?.credit} tín chỉ`}
                      />
                    </li>
                  );
                }}
              />
              {isCreating && (
                <TextField
                  fullWidth
                  label="Mật khẩu"
                  name="password"
                  type="password"
                  value={selectedLecturer.password || ""}
                  onChange={handleInputChange}
                />
              )}
            </Stack>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setIsEditModalOpen(false)}>Hủy</Button>
          <Button onClick={handleSubmit}>
            {isCreating ? "Tạo mới" : "Lưu thay đổi"}
          </Button>
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
          {lecturerToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa giảng viên{" "}
              <strong>{lecturerToDelete.name}</strong> (Mã:{" "}
              {lecturerToDelete.lecturerCode}) không?
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

export default LecturerPage;
