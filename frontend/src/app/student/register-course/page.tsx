"use client";
import React, { useMemo, useState } from "react";
import { useCourses } from "@/services/course";
import { useSemesters } from "@/services/semester";
import { useCreateReceipt } from "@/services/receipt";
import ClientSession from "@/services/session/client.session";
import {
  Box,
  Button,
  Checkbox,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  SelectChangeEvent,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { SearchInput } from "@/components/ui/search/SearchInput";
import useAppRoute from "@/utils/route";
import UIHelper from "@/utils/ui.helper.util";
import { ToastType } from "@/types/toast";
import { UploadReceiptRequest } from "@/types/api";

function RegisterCoursePage() {
  const { replace } = useAppRoute();
  const { data: courses, isLoading, error } = useCourses();
  const { data: semesters } = useSemesters();
  const { mutate: createReceipt } = useCreateReceipt();

  const [newReceipt, setNewReceipt] = useState<UploadReceiptRequest>({
    studentId: ClientSession.getUserId() || "",
    semesterId: "",
    courseIds: [],
    studentName: "",
    studentClass: "",
    studentCode: "",
    status: false,
    totalAmount: 0,
    description: "Đăng ký môn học mới",
    paymentDate: null,
    cashierId: "",
  });

  const [searchTerm, setSearchTerm] = useState("");

  const totalAmount = useMemo(() => {
    if (!courses || newReceipt.courseIds.length === 0) return 0;

    return newReceipt.courseIds.reduce((sum, courseId) => {
      const course = courses.find((c) => c.id === courseId);
      return sum + (course ? course.credit * course.baseFeeCredit : 0);
    }, 0);
  }, [newReceipt.courseIds, courses]);

  const handleCourseSelect = (courseId: string) => {
    setNewReceipt((prev) => {
      const newCourseIds = prev.courseIds.includes(courseId)
        ? prev.courseIds.filter((id) => id !== courseId)
        : [...prev.courseIds, courseId];

      return { ...prev, courseIds: newCourseIds };
    });
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewReceipt((prev) => ({ ...prev, [name]: value }));
  };

  const handleSelectChange = (e: SelectChangeEvent<string>) => {
    const { name, value } = e.target;
    setNewReceipt((prev) => ({ ...prev, [name]: value }));
  };

  const handleExportReceipt = () => {
    UIHelper.showToast({
      message: "Chức năng xuất biên lai PDF sẽ được triển khai sau",
      type: ToastType.info,
    });
  };

  const handleSubmit = () => {
    if (
      !newReceipt.studentId ||
      !newReceipt.semesterId ||
      newReceipt.courseIds.length === 0
    ) {
      UIHelper.showToast({
        message: "Vui lòng chọn học kỳ và ít nhất một môn học",
        type: ToastType.error,
      });
      return;
    }

    createReceipt(
      { ...newReceipt, totalAmount },
      {
        onSuccess: () => {
          UIHelper.showToast({
            message: "Đăng ký thành công! Vui lòng thanh toán",
            type: ToastType.success,
          });
          setNewReceipt((prev) => ({
            ...prev,
            courseIds: [],
            semesterId: "",
          }));
        },
        onError: () => {
          UIHelper.showToast({
            message: "Đăng ký thất bại! Vui lòng thử lại",
            type: ToastType.error,
          });
        },
      }
    );
  };

  if (!newReceipt.studentId) {
    replace("/login");
    return null;
  }

  if (isLoading) return <div>Đang tải danh sách môn học...</div>;
  if (error) return <div>Lỗi khi tải danh sách môn học</div>;

  const filteredCourses =
    courses?.filter((course) =>
      course.name.toLowerCase().includes(searchTerm.toLowerCase())
    ) || [];

  return (
    <Stack spacing={4} className="p-8 max-w-4xl mx-auto">
      <Typography variant="h3" className="text-center">
        Đăng ký môn học mới
      </Typography>

      <Paper elevation={3} className="p-4">
        <Typography variant="h5" gutterBottom>
          Thông tin sinh viên
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Họ và tên"
              name="studentName"
              value={newReceipt.studentName}
              onChange={handleInputChange}
              required
            />
          </Grid>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Lớp"
              name="studentClass"
              value={newReceipt.studentClass}
              onChange={handleInputChange}
              required
            />
          </Grid>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Mã sinh viên"
              name="studentCode"
              value={newReceipt.studentCode}
              onChange={handleInputChange}
              required
            />
          </Grid>
        </Grid>
      </Paper>

      <FormControl fullWidth>
        <InputLabel>Chọn học kỳ</InputLabel>
        <Select
          name="semesterId"
          value={newReceipt.semesterId}
          onChange={handleSelectChange}
          label="Chọn học kỳ"
        >
          {semesters?.map((semester) => (
            <MenuItem key={semester.id} value={semester.id}>
              {semester.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <SearchInput onSearch={setSearchTerm} placeholder="Tìm kiếm môn học..." />

      <Stack spacing={2}>
        {filteredCourses.map((course) => (
          <Stack
            key={course.id}
            direction="row"
            alignItems="center"
            className="p-4 border rounded-lg hover:bg-gray-50"
          >
            <Checkbox
              checked={newReceipt.courseIds.includes(course.id)}
              onChange={() => handleCourseSelect(course.id)}
            />
            <Stack>
              <Typography variant="h6">{course.name}</Typography>
              <Typography variant="body2">
                {course.credit} tín chỉ - Môn học{" "}
                {course.subjectType ? "bắt buộc" : "tự chọn"}
              </Typography>
            </Stack>
          </Stack>
        ))}
      </Stack>

      <Grid container spacing={4}>
        <Grid item xs={12} md={5}>
          <Box display="flex" justifyContent="flex-end" gap={2} mt={2}></Box>
        </Grid>
        <Grid item xs={12} md={7}>
          <Paper elevation={3} className="p-4">
            <Typography variant="h6" gutterBottom>
              Tổng thanh toán
            </Typography>
            <Typography variant="h5" className="text-red-600 font-bold">
              {totalAmount.toLocaleString()} VND
            </Typography>
            <Box display="flex" gap={2} mt={2}>
              <Button
                color="primary"
                className="whitespace-nowrap"
                fullWidth
                onClick={handleSubmit}
                disabled={
                  !newReceipt.semesterId || newReceipt.courseIds.length === 0
                }
              >
                Xác nhận đăng ký
              </Button>
              <Button
                variant="primary"
                className="whitespace-nowrap"
                fullWidth
                onClick={handleExportReceipt}
                disabled={totalAmount === 0}
              >
                Xuất biên lai
              </Button>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Stack>
  );
}

export default RegisterCoursePage;
