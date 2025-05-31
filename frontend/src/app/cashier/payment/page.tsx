"use client";
import React, { useState } from "react";
import { useReceipts } from "@/services/receipt";
import ClientSession from "@/services/session/client.session";
import { Button, Stack, Typography } from "@mui/material";
import CustomTable from "@/components/ui/table/CustomTable";
import { ColumnDef } from "@tanstack/react-table";
import { ReceiptResponse } from "@/types/api";
import { useUpdateReceipt } from "@/services/receipt";
import useAppRoute from "@/utils/route";
import UIHelper from "@/utils/ui.helper.util";
import { ToastType } from "@/types/toast";
import { SearchInput } from "@/components/ui/search/SearchInput";

function PaymentPage() {
  const { replace } = useAppRoute();
  const { data: receipts, isLoading, error, refetch } = useReceipts();
  const { mutate: updateReceipt } = useUpdateReceipt();

  const cashierId = ClientSession.getUserId();

  const [filteredData, setFilteredData] = useState<ReceiptResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);

  const handlePayment = (receipt: ReceiptResponse) => {
    const updatedReceipt = {
      ...receipt,
      status: true,
      paymentDate: new Date(),
      cashierId: cashierId,
    };

    updateReceipt(updatedReceipt, {
      onSuccess: () => {
        UIHelper.showToast({
          message: "Thanh toán thành công!",
          type: ToastType.success,
        });
        refetch();
      },
      onError: () => {
        UIHelper.showToast({
          message: "Thanh toán thất bại! Vui lòng thử lại.",
          type: ToastType.error,
        });
      },
    });
  };

  const columns: ColumnDef<ReceiptResponse>[] = [
    { accessorKey: "id", header: "Mã biên lai" },
    { accessorKey: "studentName", header: "Tên sinh viên" },
    { accessorKey: "studentCode", header: "Mã sinh viên" },
    { accessorKey: "studentClass", header: "Lớp" },
    { accessorKey: "semesterName", header: "Học kỳ" },
    {
      accessorKey: "totalAmount",
      header: "Tổng tiền",
      cell: ({ row }) => `${row.original.totalAmount.toLocaleString()} VND`,
    },
    {
      accessorKey: "status",
      header: "Trạng thái",
      cell: ({ row }) =>
        row.original.status ? "Đã thanh toán" : "Chưa thanh toán",
    },
    {
      accessorKey: "paymentDate",
      header: "Ngày thanh toán",
      cell: ({ row }) => {
        if (!row.original.paymentDate) return "Chưa thanh toán";
        const date = new Date(row.original.paymentDate);
        return date.toLocaleDateString("vi-VN");
      },
    },
    {
      id: "actions",
      header: "Hành động",
      cell: ({ row }) => (
        <Button
          variant="primary"
          className="whitespace-nowrap"
          onClick={() => handlePayment(row.original)}
          disabled={row.original.status}
        >
          {row.original.status ? "Đã thanh toán" : "Thanh toán"}
        </Button>
      ),
    },
  ];

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

  if (isLoading) return <div>Đang tải danh sách biên lai...</div>;
  if (error) return <div>Lỗi khi tải danh sách biên lai</div>;

  return (
    <Stack spacing={4} className="p-8 max-w-6xl mx-auto">
      <Typography variant="h3" className="text-center">
        Quản lý thanh toán
      </Typography>

      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <SearchInput
          onSearch={handleSearch}
          placeholder="Tìm kiếm biên lai..."
          className="max-w-md"
          isLoading={isSearching}
        />
      </Stack>

      <CustomTable
        columns={columns}
        data={filteredData.length > 0 ? filteredData : receipts || []}
      />
    </Stack>
  );
}

export default PaymentPage;
