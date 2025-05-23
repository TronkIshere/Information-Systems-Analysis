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
    name: "Lập trình Cơ bản",
    credit: 3,
    baseFeePerCredit: 500000,
    subjectType: 0,
  },
  {
    id: 2,
    name: "Cấu trúc Dữ liệu và Giải thuật",
    credit: 4,
    baseFeePerCredit: 600000,
    subjectType: 0,
  },
  {
    id: 3,
    name: "Cơ sở Dữ liệu",
    credit: 3,
    baseFeePerCredit: 550000,
    subjectType: 1,
  },
  {
    id: 4,
    name: "Lập trình Hướng đối tượng",
    credit: 3,
    baseFeePerCredit: 600000,
    subjectType: 1,
  },
  {
    id: 5,
    name: "Mạng Máy tính",
    credit: 3,
    baseFeePerCredit: 500000,
    subjectType: 0,
  },
  {
    id: 6,
    name: "Thực tập Lập trình Web",
    credit: 2,
    baseFeePerCredit: 700000,
    subjectType: 1,
  },
  {
    id: 7,
    name: "Trí tuệ Nhân tạo",
    credit: 3,
    baseFeePerCredit: 650000,
    subjectType: 0,
  },
  {
    id: 8,
    name: "Đồ án Phần mềm",
    credit: 4,
    baseFeePerCredit: 800000,
    subjectType: 1,
  },
  {
    id: 9,
    name: "An toàn Thông tin",
    credit: 3,
    baseFeePerCredit: 600000,
    subjectType: 0,
  },
  {
    id: 10,
    name: "Phát triển Ứng dụng Di động",
    credit: 3,
    baseFeePerCredit: 750000,
    subjectType: 1,
  },
];

const columns: ColumnDef<any, any>[] = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "name", header: "Tên môn học" },
  { accessorKey: "credit", header: "số tín chỉ" },
  {
    accessorKey: "baseFeePerCredit",
    header: "Số tiền mỗi tín",
    cell: ({ row }) => {
      return `${row.original.baseFeePerCredit.toLocaleString()} VND`;
    },
  },
  { accessorKey: "subjectType", header: "Môn thực hành" },
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

function CoursePage() {
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
      <>
        <Stack
          display="flex"
          flexDirection="row"
          justifyContent="space-between"
        >
          <Typography variant="display-small" className="mb-4">
            Sản phẩm
          </Typography>

          <Stack display="flex" flexDirection="row" className="mb-4" gap={2}>
            <SearchInput
              onSearch={handleSearch}
              placeholder="Tìm kiếm môn học..."
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
    </>
  );
}

export default CoursePage;
