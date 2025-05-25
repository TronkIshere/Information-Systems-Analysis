"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import {
  useDeleteDepartment,
  useDepartments,
  useUpdateDepartment,
  useCreateDepartment,
} from "@/services/department";
import { DepartmentResponse } from "@/types/api";
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

function DepartmentPage() {
  const { data: departments, isLoading, error } = useDepartments();
  const { mutate: createDepartment } = useCreateDepartment();
  const { mutate: updateDepartment } = useUpdateDepartment();
  const { mutate: deleteDepartment } = useDeleteDepartment();

  const [filteredData, setFilteredData] = useState<DepartmentResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedDepartment, setSelectedDepartment] =
    useState<DepartmentResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [departmentToDelete, setDepartmentToDelete] =
    useState<DepartmentResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<DepartmentResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "name", header: "Tên chuyên ngành" },
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
              setSelectedDepartment(row.original);
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
    setSelectedDepartment((prev) => ({
      ...prev!,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    if (selectedDepartment) {
      const operation = isCreating ? createDepartment : updateDepartment;
      operation(selectedDepartment, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedDepartment(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (department: DepartmentResponse) => {
    setDepartmentToDelete(department);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (departmentToDelete?.id) {
      deleteDepartment(departmentToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setDepartmentToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setDepartmentToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(departments || []);
        return;
      }

      const filtered = (departments || []).filter((item) =>
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
  if (error) return <div>Error loading departments</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý chuyên ngành
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm chuyên ngành..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedDepartment({
                id: "",
                name: "",
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
        data={filteredData.length > 0 ? filteredData : departments || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm chuyên ngành mới" : "Chỉnh sửa chuyên ngành"}
        </DialogTitle>
        <DialogContent>
          {selectedDepartment && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <TextField
                fullWidth
                label="Tên chuyên ngành"
                name="name"
                value={selectedDepartment.name}
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
          {!isCreating && selectedDepartment?.id && (
            <Button
              color="error"
              onClick={() => handleDeleteClick(selectedDepartment)}
            >
              Xóa chuyên ngành
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
          {departmentToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa chuyên ngành{" "}
              <strong>{departmentToDelete.name}</strong>?
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

export default DepartmentPage;
