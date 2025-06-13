"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import { useCashiers } from "@/services/cashier";
import { useCourses } from "@/services/course";
import { useCourseOfferings } from "@/services/courseOffering";
import {
  useDeleteReceipt,
  useReceipts,
  useUpdateReceipt,
  useCreateReceipt,
} from "@/services/receipt";
import { useSemesters } from "@/services/semester";
import { useStudents } from "@/services/student";
import { ReceiptResponse } from "@/types/api";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography,
  Checkbox,
  ListItemText,
  Autocomplete,
  SelectChangeEvent,
} from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

function ReceiptPage() {
  const { data: receipts, isLoading, error } = useReceipts();
  const { data: students } = useStudents();
  const { data: semesters } = useSemesters();
  const { data: courseOfferings } = useCourseOfferings();
  const { data: cashiers } = useCashiers();
  const { mutate: updateReceipt } = useUpdateReceipt();
  const { mutate: createReceipt } = useCreateReceipt();
  const { mutate: deleteReceipt } = useDeleteReceipt();

  const [filteredData, setFilteredData] = useState<ReceiptResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedReceipt, setSelectedReceipt] =
    useState<ReceiptResponse | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [receiptToDelete, setReceiptToDelete] =
    useState<ReceiptResponse | null>(null);
  const [isCreating, setIsCreating] = useState(false);

  const columns: ColumnDef<ReceiptResponse>[] = [
    { accessorKey: "id", header: "ID" },
    { accessorKey: "studentName", header: "Tên học sinh" },
    {
      accessorKey: "totalAmount",
      header: "Tổng số tiền",
      cell: ({ row }) =>
        `${row.original.totalAmount.toLocaleString("vi-VN")} VND`,
    },
    {
      accessorKey: "status",
      header: "Trạng thái",
      cell: ({ row }) => (row.original.status ? "Đã đóng" : "Chưa đóng"),
    },
    { accessorKey: "description", header: "Mô tả" },
    { accessorKey: "semesterName", header: "Tên học kỳ" },
    {
      accessorKey: "paymentDate",
      header: "Ngày thanh toán",
      cell: ({ row }) => {
        const rawDate = row.original.paymentDate;

        if (!rawDate) return "Chưa thanh toán";

        try {
          const date = new Date(rawDate);
          if (!isNaN(date.getTime())) {
            return date.toLocaleDateString("vi-VN");
          }
        } catch (e) {}

        const dateStr = rawDate.toString();
        if (dateStr.length === 8 && /^\d+$/.test(dateStr)) {
          const year = dateStr.slice(0, 4);
          const month = dateStr.slice(4, 6);
          const day = dateStr.slice(6, 8);
          return `${day}/${month}/${year}`;
        }

        return rawDate;
      },
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
              setSelectedReceipt(row.original);
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
    setSelectedReceipt((prev) => ({
      ...prev!,
      [name]: name === "status" ? value === "true" : value,
    }));
  };

  const handleSelectChange = (e: SelectChangeEvent<string>) => {
    const { name, value } = e.target;
    setSelectedReceipt((prev) => ({
      ...prev!,
      [name]: name === "status" ? value === "true" : value,
    }));
  };

  const handleCourseSelection = (event: any, newValue: string[]) => {
    setSelectedReceipt((prev) => ({
      ...prev!,
      courseIds: newValue,
    }));
  };

  const handleSubmit = () => {
    if (selectedReceipt) {
      const operation = isCreating ? createReceipt : updateReceipt;
      operation(selectedReceipt, {
        onSuccess: () => {
          setIsEditModalOpen(false);
          setSelectedReceipt(null);
          setIsCreating(false);
        },
      });
    }
  };

  const handleDeleteClick = (receipt: ReceiptResponse) => {
    setReceiptToDelete(receipt);
    setIsDeleteModalOpen(true);
  };

  const confirmDelete = () => {
    if (receiptToDelete?.id) {
      deleteReceipt(receiptToDelete.id, {
        onSuccess: () => {
          setIsDeleteModalOpen(false);
          setReceiptToDelete(null);
        },
      });
    }
  };

  const cancelDelete = () => {
    setIsDeleteModalOpen(false);
    setReceiptToDelete(null);
  };

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      if (!searchTerm) {
        setFilteredData(receipts || []);
        return;
      }

      const filtered = (receipts || []).filter((item) =>
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
  if (error) return <div>Error loading receipts</div>;

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Quản lý biên lai
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm biên lai..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button
            variant="primary"
            className="whitespace-nowrap"
            onClick={() => {
              setSelectedReceipt({
                id: "",
                totalAmount: 0,
                status: false,
                description: "",
                paymentDate: null,
                studentId: "",
                semesterId: "",
                courseOfferingIds: [],
                studentName: "",
                studentClass: "",
                studentCode: "",
                semesterName: "",
                cashierId: "",
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
        data={filteredData.length > 0 ? filteredData : receipts || []}
      />

      {/* Edit/Create Modal */}
      <Dialog
        open={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>
          {isCreating ? "Thêm biên lai mới" : "Chỉnh sửa biên lai"}
        </DialogTitle>
        <DialogContent>
          {selectedReceipt && (
            <Stack spacing={3} sx={{ mt: 2 }}>
              <FormControl fullWidth>
                <InputLabel>Học sinh</InputLabel>
                <Select
                  name="studentId"
                  value={selectedReceipt.studentId}
                  onChange={handleSelectChange}
                  label="Học sinh"
                >
                  {students?.map((student) => (
                    <MenuItem key={student.id} value={student.id}>
                      {student.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <FormControl fullWidth>
                <InputLabel>Học kỳ</InputLabel>
                <Select
                  name="semesterId"
                  value={selectedReceipt.semesterId}
                  onChange={handleSelectChange}
                  label="Học kỳ"
                >
                  {semesters?.map((semester) => (
                    <MenuItem key={semester.id} value={semester.id}>
                      {semester.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <FormControl fullWidth>
                <InputLabel>Thu ngân</InputLabel>
                <Select
                  name="cashierId"
                  value={selectedReceipt.cashierId || ""}
                  onChange={handleSelectChange}
                  label="Thu ngân"
                >
                  {cashiers?.map((cashier) => (
                    <MenuItem key={cashier.id} value={cashier.id}>
                      {cashier.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <Autocomplete
                multiple
                options={
                  courseOfferings?.map((courseOffering) => courseOffering.id) ||
                  []
                }
                getOptionLabel={(option) =>
                  courseOfferings?.find((c) => c.id === option)?.courseName ||
                  ""
                }
                value={selectedReceipt.courseOfferingIds}
                onChange={handleCourseSelection}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Môn học"
                    placeholder="Chọn môn học"
                  />
                )}
                renderOption={(props, option) => {
                  const courseOffering = courseOfferings?.find(
                    (c) => c.id === option
                  );
                  return (
                    <li {...props}>
                      <Checkbox
                        checked={selectedReceipt.courseOfferingIds.includes(
                          option
                        )}
                      />
                      <ListItemText
                        primary={courseOffering?.courseId}
                        secondary={`${courseOffering?.credit} tín chỉ`}
                      />
                    </li>
                  );
                }}
              />

              <TextField
                fullWidth
                label="Tổng số tiền"
                name="totalAmount"
                type="number"
                value={selectedReceipt.totalAmount}
                onChange={handleInputChange}
              />

              <FormControl fullWidth>
                <InputLabel>Trạng thái</InputLabel>
                <Select
                  name="status"
                  value={selectedReceipt.status.toString()}
                  onChange={handleSelectChange}
                  label="Trạng thái"
                >
                  <MenuItem value="true">Đã đóng</MenuItem>
                  <MenuItem value="false">Chưa đóng</MenuItem>
                </Select>
              </FormControl>

              <TextField
                fullWidth
                label="Ngày thanh toán"
                name="paymentDate"
                type="date"
                InputLabelProps={{ shrink: true }}
                value={
                  selectedReceipt.paymentDate
                    ? new Date(selectedReceipt.paymentDate)
                        .toISOString()
                        .split("T")[0]
                    : ""
                }
                onChange={handleInputChange}
              />
              <TextField
                fullWidth
                label="Mô tả"
                name="description"
                multiline
                rows={3}
                value={selectedReceipt.description}
                onChange={handleInputChange}
              />

              <TextField
                fullWidth
                label="Tên sinh viên"
                name="studentName"
                rows={3}
                value={selectedReceipt.studentName}
                onChange={handleInputChange}
              />

              <TextField
                fullWidth
                label="Mã sinh viên"
                name="studentCode"
                rows={3}
                value={selectedReceipt.studentCode}
                onChange={handleInputChange}
              />

              <TextField
                fullWidth
                label="Lớp sinh viên"
                name="studentClass"
                rows={3}
                value={selectedReceipt.studentClass}
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
          {receiptToDelete && (
            <Typography>
              Bạn có chắc chắn muốn xóa biên lai của học sinh{" "}
              {receiptToDelete.studentName}?
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

export default ReceiptPage;
