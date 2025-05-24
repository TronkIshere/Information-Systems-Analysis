"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useDeleteLecturer,
  useLecturers,
  useUpdateLecturer,
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
} from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

function LecturerPage() {
  const { data: lecturers, isLoading, error } = useLecturers();
  const { mutate: updateLecturer } = useUpdateLecturer();
  const { mutate: deleteLecturer } = useDeleteLecturer();

  const [filteredData, setFilteredData] = useState<LecturerResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedLecturer, setSelectedLecturer] =
    useState<LecturerResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [lecturerToDelete, setLecturerToDelete] =
    useState<LecturerResponse | null>(null);

  const handleRowClick = (lecturer: LecturerResponse) => {
    setSelectedLecturer(lecturer);
    setIsEditModalOpen(true);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSelectedLecturer((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    if (selectedLecturer) {
      updateLecturer(selectedLecturer, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedLecturer(null);
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
          setIsEditModalOpen(false);
          setLecturerToDelete(null);
          setSelectedLecturer(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setLecturerToDelete(null);
  };

  const columns: ColumnDef<LecturerResponse>[] = [
    { accessorKey: "id", header: "ID" },
    {
      accessorKey: "lecturerCode",
      header: "Mã giảng viên",
      sortingFn: "basic",
    },
    { accessorKey: "name", header: "Tên giảng viên" },
    { accessorKey: "phoneNumber", header: "Số điện thoại" },
    { accessorKey: "email", header: "Email" },
    { accessorKey: "status", header: "Trạng thái" },
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
          Sản phẩm
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm giảng viên..."
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
        data={filteredData.length > 0 ? filteredData : lecturers || []}
        onRowClick={handleRowClick}
      />

      {/* Edit Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {selectedLecturer ? "Chỉnh sửa thông tin" : "Thêm giảng viên mới"}
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
                label="Trạng thái"
                name="status"
                value={selectedLecturer.status || ""}
                onChange={handleInputChange}
              />
            </Stack>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setIsEditModalOpen(false)}>Hủy</Button>
          <Button onClick={handleUpdate}>Lưu thay đổi</Button>
          {selectedLecturer?.id && (
            <Button
              color="error"
              onClick={() => handleDeleteClick(selectedLecturer)}
            >
              Xóa giảng viên
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
