"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useCreateSemester,
  useDeleteSemester,
  useSemesters,
  useUpdateSemester,
} from "@/services/semester";
import { SemesterResponse } from "@/types/api";
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

function SemesterPage() {
  const { data: semesters, isLoading, error } = useSemesters();
  const { mutate: updateSemester } = useUpdateSemester();
  const { mutate: deleteSemester } = useDeleteSemester();
  const { mutate: createSemester } = useCreateSemester();

  const [filteredData, setFilteredData] = useState<SemesterResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedSemester, setSelectedSemester] =
    useState<SemesterResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [semesterToDelete, setSemesterToDelete] =
    useState<SemesterResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<SemesterResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "name", header: "Tên học kỳ" },
    {
      accessorKey: "startDate",
      header: "Ngày bắt đầu",
      cell: ({ row }) =>
        row.original.startDate
          ? new Date(row.original.startDate).toLocaleDateString()
          : "",
    },
    {
      accessorKey: "endDate",
      header: "Ngày kết thúc",
      cell: ({ row }) =>
        row.original.endDate
          ? new Date(row.original.endDate).toLocaleDateString()
          : "",
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
              setSelectedSemester(row.original);
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
    setSelectedSemester((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    if (selectedSemester) {
      const operation = isCreating ? createSemester : updateSemester;
      operation(selectedSemester, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedSemester(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (semester: SemesterResponse) => {
    setSemesterToDelete(semester);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (semesterToDelete?.id) {
      deleteSemester(semesterToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setSemesterToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setSemesterToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(semesters || []);
        return;
      }

      const filtered = (semesters || []).filter((item) =>
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
  if (error) return <div>Error loading semesters</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý học kỳ
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm học kỳ..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedSemester({
                id: "",
                name: "",
                startDate: null,
                endDate: null,
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
        data={filteredData.length > 0 ? filteredData : semesters || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm học kỳ mới" : "Chỉnh sửa học kỳ"}
        </DialogTitle>
        <DialogContent>
          {selectedSemester && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <TextField
                fullWidth
                label="Tên học kỳ"
                name="name"
                value={selectedSemester.name || ""}
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Ngày bắt đầu"
                name="startDate"
                type="date"
                InputLabelProps={{ shrink: true }}
                value={
                  selectedSemester.startDate
                    ? new Date(selectedSemester.startDate)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Ngày kết thúc"
                name="endDate"
                type="date"
                InputLabelProps={{ shrink: true }}
                value={
                  selectedSemester.endDate
                    ? new Date(selectedSemester.endDate)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={handleInputChange}
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
          {semesterToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa học kỳ{" "}
              <strong>{semesterToDelete.name}</strong>?
              <br />
              (Từ{" "}
              {semesterToDelete.startDate
                ? new Date(semesterToDelete.startDate).toLocaleDateString()
                : "N/A"}{" "}
              đến{" "}
              {semesterToDelete.endDate
                ? new Date(semesterToDelete.endDate).toLocaleDateString()
                : "N/A"}
              )
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

export default SemesterPage;
