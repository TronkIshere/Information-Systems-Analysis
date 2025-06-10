"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import { useCourses } from "@/services/course";
import {
  useCreateCourseOffering,
  useDeleteCourseOffering,
  useCourseOfferings,
  useUpdateCourseOffering,
  useOpenCourseOfferings,
} from "@/services/courseOffering";
import { useSemesters } from "@/services/semester";
import {
  CourseOfferingResponse,
  UpdateCourseOfferingRequest,
  UploadCourseOfferingRequest,
} from "@/types/api";
import {
  Autocomplete,
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
import { format } from "date-fns";
import React, { useEffect, useState } from "react";

function CourseOfferingPage() {
  const { data: offerings, isLoading, error } = useOpenCourseOfferings();
  const { data: courses } = useCourses();
  const { data: semesters } = useSemesters();
  const { mutate: createCourseOffering } = useCreateCourseOffering();
  const { mutate: updateCourseOffering } = useUpdateCourseOffering();
  const { mutate: deleteCourseOffering } = useDeleteCourseOffering();

  const [filteredData, setFilteredData] = useState<CourseOfferingResponse[]>(
    []
  );
  const [isSearching, setIsSearching] = useState(false);
  const [selectedOffering, setSelectedOffering] =
    useState<CourseOfferingResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [offeringToDelete, setOfferingToDelete] =
    useState<CourseOfferingResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<CourseOfferingResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "courseName", header: "Tên môn học" },
    { accessorKey: "credit", header: "Số tín chỉ" },
    {
      accessorKey: "baseFeeCredit",
      header: "Học phí mỗi tín",
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
      accessorKey: "startDate",
      header: "Ngày bắt đầu",
      cell: ({ row }) => row.original.startDate,
    },
    {
      accessorKey: "endDate",
      header: "Ngày kết thúc",
      cell: ({ row }) => row.original.endDate,
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
              setSelectedOffering(row.original);
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
    setSelectedOffering((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleCourseChange = (courseId: string) => {
    const selectedCourse = courses?.find((c) => c.id === courseId);
    if (selectedCourse && selectedOffering) {
      setSelectedOffering({
        ...selectedOffering,
        courseId: selectedCourse.id,
        courseName: selectedCourse.name,
        credit: selectedCourse.credit,
        baseFeeCredit: selectedCourse.baseFeeCredit,
        subjectType: selectedCourse.subjectType,
      });
    }
  };

  const handleDateChange = (name: string, value: string) => {
    setSelectedOffering((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    if (selectedOffering) {
      const offeringData = {
        ...selectedOffering,
        courseId: selectedOffering.courseId,
        startDate: selectedOffering.startDate,
        endDate: selectedOffering.endDate,
      };

      const operation = isCreating
        ? createCourseOffering
        : updateCourseOffering;
      operation(offeringData as any, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedOffering(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (offering: CourseOfferingResponse) => {
    setOfferingToDelete(offering);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (offeringToDelete?.id) {
      deleteCourseOffering(offeringToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setOfferingToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setOfferingToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(offerings || []);
        return;
      }

      const filtered = (offerings || []).filter((item) =>
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

  const handleSemesterChange = (semesterId: string) => {
    const selectedSemester = semesters?.find((s) => s.id === semesterId);
    if (selectedSemester && selectedOffering) {
      setSelectedOffering({
        ...selectedOffering,
        semesterId: selectedSemester.id,
      });
    }
  };

  useEffect(() => {
    if (offerings) {
      setFilteredData(offerings);
    }
  }, [offerings]);

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error loading course offerings</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý Đợt mở môn học
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm đợt mở môn..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedOffering({
                id: "",
                startDate: null,
                endDate: null,
                courseId: "",
                courseName: "",
                credit: 0,
                baseFeeCredit: 0,
                subjectType: false,
                semesterId: "",
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
        data={filteredData.length > 0 ? filteredData : offerings || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm đợt mở môn mới" : "Chỉnh sửa đợt mở môn"}
        </DialogTitle>
        <DialogContent>
          <Stack spacing={3} sx={{ mt: 2 }}>
            <Autocomplete
              options={courses?.map((course) => course.id) || []}
              getOptionLabel={(option) => {
                const course = courses?.find((c) => c.id === option);
                return course ? course.name : "";
              }}
              value={selectedOffering?.courseId || ""}
              onChange={(event, newValue) => {
                if (newValue) handleCourseChange(newValue);
              }}
              renderInput={(params) => (
                <TextField {...params} label="Môn học" required />
              )}
              disableClearable
            />

            <TextField
              fullWidth
              label="Tên môn học"
              value={selectedOffering?.courseName || ""}
              disabled
            />

            <Stack direction="row" spacing={2}>
              <TextField
                fullWidth
                label="Số tín chỉ"
                value={selectedOffering?.credit || 0}
                disabled
              />
              <TextField
                fullWidth
                label="Học phí mỗi tín (VND)"
                value={selectedOffering?.baseFeeCredit || 0}
                disabled
              />
            </Stack>

            <TextField
              label="Loại môn học"
              value={selectedOffering?.subjectType ? "Thực hành" : "Lý thuyết"}
              disabled
            />

            <Stack direction="row" spacing={2}>
              <TextField
                fullWidth
                label="Ngày bắt đầu"
                type="date"
                value={
                  selectedOffering?.startDate
                    ? format(new Date(selectedOffering.startDate), "yyyy-MM-dd")
                    : ""
                }
                onChange={(e) => handleDateChange("startDate", e.target.value)}
                InputLabelProps={{
                  shrink: true,
                }}
              />
              <TextField
                fullWidth
                label="Ngày kết thúc"
                type="date"
                value={
                  selectedOffering?.endDate
                    ? format(new Date(selectedOffering.endDate), "yyyy-MM-dd")
                    : ""
                }
                onChange={(e) => handleDateChange("endDate", e.target.value)}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Stack>

            <Autocomplete
              options={semesters || []}
              getOptionLabel={(option) => option.name}
              value={
                semesters?.find((s) => s.id === selectedOffering?.semesterId) ||
                undefined
              }
              onChange={(event, newValue) => {
                if (newValue) handleSemesterChange(newValue.id);
              }}
              renderInput={(params) => (
                <TextField {...params} label="Học kỳ" required />
              )}
              disableClearable
            />
          </Stack>
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
          {offeringToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa đợt mở môn{" "}
              <strong>{offeringToDelete.courseName}</strong>?
              <br />
              (Từ {offeringToDelete.startDate?.toLocaleDateString()} đến{" "}
              {offeringToDelete.endDate?.toLocaleDateString()})
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

export default CourseOfferingPage;
