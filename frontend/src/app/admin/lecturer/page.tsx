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
    lecturerCode: "GV-001",
    name: "Nguyen Van A",
    phoneNumber: "0987654321",
    email: "nguyena@gmail.com",
    status: 0,
  },
  {
    id: 2,
    lecturerCode: "GV-002",
    name: "Nguyen Van B",
    phoneNumber: "0987654322",
    email: "nguyenb@gmail.com",
    status: 1,
  },
  {
    id: 3,
    lecturerCode: "GV-003",
    name: "Nguyen Van C",
    phoneNumber: "0987654323",
    email: "nguyenc@gmail.com",
    status: 1,
  },
  {
    id: 4,
    lecturerCode: "GV-004",
    name: "Nguyen Van D",
    phoneNumber: "0987654324",
    email: "nguyend@gmail.com",
    status: 1,
  },
  {
    id: 5,
    lecturerCode: "GV-005",
    name: "Nguyen Van E",
    phoneNumber: "0987654325",
    email: "nguyene@gmail.com",
    status: 1,
  },
  {
    id: 6,
    lecturerCode: "GV-006",
    name: "Nguyen Van F",
    phoneNumber: "0987654326",
    email: "nguyenf@gmail.com",
    status: 1,
  },
];

const columns: ColumnDef<any, any>[] = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "lecturerCode", header: "Mã giảng viên", sortingFn: "basic" },
  { accessorKey: "name", header: "Tên giảng viên" },
  { accessorKey: "phoneNumber", header: "số điện thoại" },
  { accessorKey: "email", header: "Email" },
  { accessorKey: "status", header: "Trạng thái" },
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

function LecturerPage() {
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
            placeholder="Tìm kiếm giảng viên..."
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

export default LecturerPage;
