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
    name: "Học kỳ 1 - Năm học 2023-2024",
    startDate: "05/09/2023",
    endDate: "15/12/2023",
  },
  {
    id: 2,
    name: "Học kỳ 2 - Năm học 2023-2024",
    startDate: "08/01/2024",
    endDate: "20/05/2024",
  },
  {
    id: 3,
    name: "Học kỳ Hè - Năm học 2023-2024",
    startDate: "10/06/2024",
    endDate: "30/07/2024",
  },
  {
    id: 4,
    name: "Học kỳ 1 - Năm học 2024-2025",
    startDate: "03/09/2024",
    endDate: "20/12/2024",
  },
  {
    id: 5,
    name: "Học kỳ 2 - Năm học 2024-2025",
    startDate: "06/01/2025",
    endDate: "25/05/2025",
  },
  {
    id: 6,
    name: "Học kỳ Hè - Năm học 2024-2025",
    startDate: "09/06/2025",
    endDate: "01/08/2025",
  },
];

const columns: ColumnDef<any, any>[] = [
  { accessorKey: "id", header: "ID" },
  { accessorKey: "name", header: "Tên học kỳ" },
  { accessorKey: "startDate", header: "Ngày bắt đầu" },
  { accessorKey: "endDate", header: "Ngày kết thúc" },
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

function SemesterPage() {
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
            placeholder="Tìm kiếm học kì..."
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

export default SemesterPage;
