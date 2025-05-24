"use client";
import React, { useState } from "react";
import {
  useReactTable,
  getCoreRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  ColumnDef,
  flexRender,
} from "@tanstack/react-table";
import {
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  TableContainer,
  TablePagination,
  TableSortLabel,
  Paper,
} from "@mui/material";

interface MuiTableProps<TData> {
  columns: ColumnDef<TData, any>[];
  data: TData[];
  onRowClick?: (rowData: TData) => void;
}

const CustomTable = <TData,>({
  columns,
  data,
  onRowClick,
}: MuiTableProps<TData>) => {
  const [sorting, setSorting] = useState<any>([]);
  const [pageIndex, setPageIndex] = useState(0);
  const [pageSize, setPageSize] = useState(10);

  const table = useReactTable({
    data,
    columns,
    state: { sorting, pagination: { pageIndex, pageSize } },
    onSortingChange: setSorting,
    onPaginationChange: (updater) => {
      setPageIndex((prevPageIndex) => {
        setPageSize((prevPageSize) => {
          const newPagination =
            typeof updater === "function"
              ? updater({ pageIndex: prevPageIndex, pageSize: prevPageSize })
              : updater;

          return newPagination?.pageSize ?? prevPageSize;
        });

        return typeof updater === "function"
          ? (updater({ pageIndex: prevPageIndex, pageSize }).pageIndex ??
              prevPageIndex)
          : (updater.pageIndex ?? prevPageIndex);
      });
    },
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  });

  return (
    <Paper sx={{ width: "100%", overflow: "hidden" }}>
      <TableContainer>
        <Table>
          <TableHead>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <TableCell key={header.id} sx={{ fontWeight: "bold" }}>
                    <TableSortLabel
                      active={!!header.column.getIsSorted()}
                      direction={
                        header.column.getIsSorted() === "desc" ? "desc" : "asc"
                      }
                      onClick={header.column.getToggleSortingHandler()}
                    >
                      {header.column.columnDef.header as string}
                    </TableSortLabel>
                  </TableCell>
                ))}
              </TableRow>
            ))}
          </TableHead>

          <TableBody>
            {table.getRowModel().rows.map((row) => (
              <TableRow
                key={row.id}
                hover // Thêm hiệu ứng hover
                onClick={() => onRowClick && onRowClick(row.original)}
                sx={{
                  cursor: onRowClick ? "pointer" : "default",
                  "&:hover": {
                    backgroundColor: onRowClick ? "action.hover" : "inherit",
                  },
                }}
              >
                {row.getVisibleCells().map((cell) => (
                  <TableCell key={cell.id}>
                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                  </TableCell>
                ))}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <TablePagination
        component="div"
        count={data.length}
        page={pageIndex}
        rowsPerPage={pageSize}
        labelRowsPerPage="Hiển thị"
        onPageChange={(_, newPage) => table.setPageIndex(newPage)}
        onRowsPerPageChange={(event) =>
          table.setPageSize(Number(event.target.value))
        }
      />
    </Paper>
  );
};

export default CustomTable;
