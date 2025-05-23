"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import { SearchInput } from "@/components/ui/search/SearchInput";
import CustomTable from "@/components/ui/table/CustomTable";
import { Button, Stack, Typography } from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

const data = [
  {
    id: 1,
    studentName: "Nguyễn Văn A",
    totalAmount: 4500000,
    status: 1,
    description: "Thanh toán học phí Học kỳ 1",
    semesterName: "Học kỳ 1 - Năm học 2023-2024",
    paymentDate: "15/09/2023",
  },
  {
    id: 2,
    studentName: "Trần Thị B",
    totalAmount: 4800000,
    status: 1,
    description: "Thanh toán học phí và phí cơ sở vật chất",
    semesterName: "Học kỳ 1 - Năm học 2023-2024",
    paymentDate: "20/09/2023",
  },
  {
    id: 3,
    studentName: "Lê Văn C",
    totalAmount: 4200000,
    status: 0,
    description: "Học phí Học kỳ 2 chưa thanh toán",
    semesterName: "Học kỳ 2 - Năm học 2023-2024",
    paymentDate: "",
  },
  {
    id: 4,
    studentName: "Phạm Thị D",
    totalAmount: 5000000,
    status: 1,
    description: "Thanh toán đầy đủ học phí + phí đồ án",
    semesterName: "Học kỳ 2 - Năm học 2023-2024",
    paymentDate: "10/01/2024",
  },
  {
    id: 5,
    studentName: "Hoàng Văn E",
    totalAmount: 3800000,
    status: 0,
    description: "Nợ học phí Học kỳ hè",
    semesterName: "Học kỳ Hè - Năm học 2023-2024",
    paymentDate: "",
  },
  {
    id: 6,
    studentName: "Vũ Thị F",
    totalAmount: 4600000,
    status: 1,
    description: "Thanh toán học phí đúng hạn",
    semesterName: "Học kỳ 1 - Năm học 2024-2025",
    paymentDate: "05/09/2024",
  },
];

const columns: ColumnDef<any, any>[] = [
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
    cell: ({ row }) => (row.original.status === 1 ? "Đã đóng" : "Chưa đóng"),
  },
  { accessorKey: "description", header: "Mô tả" },
  { accessorKey: "semesterName", header: "Tên học kỳ" },
  {
    accessorKey: "paymentDate",
    header: "Ngày thanh toán",
    cell: ({ row }) => row.original.paymentDate || "Chưa thanh toán",
  },
  {
    accessorKey: "actions",
    header: "Hành động",
    cell: ({ row }) => {
      return (
        <Stack
          direction="row"
          alignItems="center"
          gap="16px"
          justifyContent="space-between"
          className="border border-solid border-[#D5D5D5] rounded-lg py-2 px-4 cursor-pointer"
        >
          <IconEdit />
          <IconBin />
        </Stack>
      );
    },
  },
];

function ReceiptPage() {
  const [filteredData, setFilteredData] = useState(data);
  const [isSearching, setIsSearching] = useState(false);

  const handleSearch = async (searchTerm: string) => {
    setIsSearching(true);
    try {
      // Simulate API call or heavy computation
      if (!searchTerm) {
        setFilteredData(data);
        return;
      }

      const filtered = data.filter((item) =>
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

  return (
    <>
      <Stack display="flex" flexDirection="row" justifyContent="space-between">
        <Typography variant="display-small" className="mb-4">
          Sản phẩm
        </Typography>

        <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm biên lai..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button variant="primary" className="whitespace-nowrap">
            Tìm kiếm
          </Button>
        </Stack>
      </Stack>
      <CustomTable columns={columns} data={filteredData} />
    </>
  );
}

export default ReceiptPage;
