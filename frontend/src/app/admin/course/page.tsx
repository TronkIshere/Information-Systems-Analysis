"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useDeleteCourse,
  useCourses,
  useUpdateCourse,
  useCreateCourse,
} from "@/services/course";
import { CourseResponse } from "@/types/api";
import {
  Autocomplete,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  ListItemText,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

function CoursePage() {
  const { data: courses, isLoading, error } = useCourses();
  const { mutate: createCourse } = useCreateCourse();
  const { mutate: updateCourse } = useUpdateCourse();
  const { mutate: deleteCourse } = useDeleteCourse();

  const [filteredData, setFilteredData] = useState<CourseResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedCourse, setSelectedCourse] = useState<CourseResponse | null>(
    null
  );
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [courseToDelete, setCourseToDelete] = useState<CourseResponse | null>(
    null
  );
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<CourseResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "name", header: "Tên môn học" },
    { accessorKey: "credit", header: "Số tín chỉ" },
    {
      accessorKey: "baseFeeCredit",
      header: "Số tiền mỗi tín",
      cell: ({ row }) => {
        const value = row.original.baseFeeCredit;
        return value != null ? `${value.toLocaleString("vi-VN")} VND` : "N/A";
      },
    },
    {
      accessorKey: "subjectType",
      header: "Môn thực hành",
      cell: ({ row }) => (row.original.subjectType ? "Có" : "Không"),
    },
    {
      accessorKey: "prerequisiteCount",
      header: "Số môn tiên quyết",
      cell: ({ row }) => row.original.prerequisiteIds?.length || 0,
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
              setSelectedCourse(row.original);
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
    setSelectedCourse((prev) => ({
      ...prev!,
      [name]:
        name === "subjectType"
          ? value === "true"
          : name === "credit" || name === "baseFeeCredit"
            ? Number(value)
            : value,
    }));
  };

  const handleSubmit = () => {
    if (selectedCourse) {
      const courseData = {
        ...selectedCourse,
        prerequisiteIds: selectedCourse.prerequisiteIds || [],
      };

      const operation = isCreating ? createCourse : updateCourse;
      operation(courseData, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedCourse(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (course: CourseResponse) => {
    setCourseToDelete(course);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (courseToDelete?.id) {
      deleteCourse(courseToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setCourseToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setCourseToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(courses || []);
        return;
      }

      const filtered = (courses || []).filter((item) =>
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
  if (error) return <div>Error loading courses</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý môn học
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm môn học..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedCourse({
                id: "",
                name: "",
                credit: 0,
                baseFeeCredit: 0,
                subjectType: false,
                prerequisiteIds: [],
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
        data={filteredData.length > 0 ? filteredData : courses || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm môn học mới" : "Chỉnh sửa môn học"}
        </DialogTitle>
        <DialogContent>
          {selectedCourse && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <TextField
                fullWidth
                label="Tên môn học"
                name="name"
                value={selectedCourse.name}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Số tín chỉ"
                name="credit"
                type="number"
                value={selectedCourse.credit}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Học phí mỗi tín (VND)"
                name="baseFeeCredit"
                type="number"
                value={selectedCourse.baseFeeCredit}
                onChange={handleInputChange}
              />
              <TextField
                select
                fullWidth
                label="Loại môn học"
                name="subjectType"
                value={selectedCourse.subjectType.toString()}
                onChange={handleInputChange}
                SelectProps={{
                  native: true,
                }}
              >
                <option value="true">Thực hành</option>
                <option value="false">Lý thuyết</option>
              </TextField>

              <Autocomplete
                multiple
                options={
                  courses
                    ?.filter((course) => course.id !== selectedCourse.id)
                    .map((course) => course.id) || []
                }
                getOptionLabel={(option) => {
                  const course = courses?.find((c) => c.id === option);
                  return course ? course.name : "";
                }}
                value={selectedCourse.prerequisiteIds || []}
                onChange={(event, newValue) => {
                  setSelectedCourse((prev) => ({
                    ...prev!,
                    prerequisiteIds: newValue,
                  }));
                }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Môn tiên quyết"
                    placeholder="Chọn môn học"
                  />
                )}
                renderOption={(props, option) => {
                  const course = courses?.find((c) => c.id === option);
                  return (
                    <li {...props} key={option}>
                      <Checkbox
                        checked={
                          selectedCourse.prerequisiteIds?.includes(option) ||
                          false
                        }
                      />
                      <ListItemText
                        primary={course?.name}
                        secondary={`${course?.credit} tín chỉ`}
                      />
                    </li>
                  );
                }}
                filterSelectedOptions
              />
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
          {courseToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa môn học{" "}
              <strong>{courseToDelete.name}</strong>?
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

export default CoursePage;
