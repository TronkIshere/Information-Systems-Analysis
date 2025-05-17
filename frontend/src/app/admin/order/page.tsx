"use client";
import IconBin from "@/assets/icons/IconBin";
import IconEdit from "@/assets/icons/IconEdit";
import CustomTable from "@/components/ui/table/CustomTable";
import { SearchInput } from "@/components/ui/search/SearchInput";
import { Stack, Typography, Button } from "@mui/material";
import { ColumnDef } from "@tanstack/react-table";
import React, { useState } from "react";

const data = [
  {
    id: 1,
    id_order: "AHHEHSNNSN-123",
    id_vat: "98373722",
    name: "Nguyen van A",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 0,
  },
  {
    id: 2,
    id_order: "AHHEASDASDSN-123",
    id_vat: "4342424",
    name: "Nguyen van B",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 1,
  },
  {
    id: 3,
    id_order: "AHHEASDASDSN-123",
    id_vat: "4342424",
    name: "Nguyen van B",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 1,
  },
  {
    id: 4,
    id_order: "AHHEASDASDSN-123",
    id_vat: "4342424",
    name: "Nguyen van B",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 1,
  },
  {
    id: 5,
    id_order: "AHHEASDASDSN-123",
    id_vat: "4342424",
    name: "Nguyen van B",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 1,
  },
  {
    id: 6,
    id_order: "AHHEASDASDSN-123",
    id_vat: "4342424",
    name: "Nguyen van B",
    address: "Tan Binh, Ho Chi Minh",
    email: "nguyena@gmail.com",
    status: 1,
  },
];

const columns: ColumnDef<any, any>[] = [
  { accessorKey: "id_order", header: "Mã đơn hàng" },
  { accessorKey: "id_vat", header: "Mã số thuế", sortingFn: "basic" },
  { accessorKey: "name", header: "Tên khách hàng" },
  { accessorKey: "address", header: "Địa chỉ" },
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

function OrderPage() {
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

        <Stack  display="flex" flexDirection="row" className="mb-4" gap={2}>
          <SearchInput
            onSearch={handleSearch}
            placeholder="Tìm kiếm đơn hàng..."
            className="max-w-md"
            isLoading={isSearching}
          />
          <Button variant="primary" className="whitespace-nowrap">
            Tìm kiếm</Button>
        </Stack>
      </Stack>
      <CustomTable columns={columns} data={filteredData} />
    </>
  );
}

export default OrderPage;
