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
    name: "Khoa học Máy tính",
  },
  {
    id: 2,
    name: "Kỹ thuật Phần mềm",
  },
  {
    id: 3,
    name: "Trí tuệ Nhân tạo",
  },
  {
    id: 4,
    name: "An toàn Thông tin",
  },
  {
    id: 5,
    name: "Hệ thống Thông tin",
  },
  {
    id: 6,
    name: "Mạng và Truyền thông Máy tính",
  },
];

const columns: ColumnDef<any, any>[] = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "name", header: "Tên chuyên ngành" },
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

function DepartmentPage() {
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
            placeholder="Tìm kiếm chuyên ngành..."
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

export default DepartmentPage;
