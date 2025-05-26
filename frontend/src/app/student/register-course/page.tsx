"use client";
import React, { useState } from "react";
import { useCourses } from "@/services/course";
import { useSemesters } from "@/services/semester";
import { useCreateReceipt } from "@/services/receipt";
import ClientSession from "@/services/session/client.session";
import {
  Button,
  Checkbox,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
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

  const [selectedSemester, setSelectedSemester] = useState("");
  const [selectedCourses, setSelectedCourses] = useState<string[]>([]);
  const [searchTerm, setSearchTerm] = useState("");

  const studentId = ClientSession.getUserId();

  const handleCourseSelect = (courseId: string) => {
    setSelectedCourses((prev) =>
      prev.includes(courseId)
        ? prev.filter((id) => id !== courseId)
        : [...prev, courseId]
    );
  };

  const handleSubmit = () => {
    if (!studentId || !selectedSemester || selectedCourses.length === 0) {
      UIHelper.showToast({
        message: "Vui lòng chọn học kỳ và ít nhất một môn học",
        type: ToastType.error,
      });
      return;
    }

    const newReceipt: UploadReceiptRequest = {
      studentId: studentId,
      semesterId: selectedSemester,
      courseIds: selectedCourses,
      status: false,
      totalAmount: 0,
      description: "Đăng ký môn học mới",
      paymentDate: null,
    };

    createReceipt(newReceipt, {
      onSuccess: () => {
        UIHelper.showToast({
          message: "Đăng ký thành công! Vui lòng thanh toán",
          type: ToastType.success,
        });
        setSelectedCourses([]);
        setSelectedSemester("");
      },
      onError: () => {
        UIHelper.showToast({
          message: "Đăng ký thất bại! Vui lòng thử lại",
          type: ToastType.error,
        });
      },
    });
  };

  if (!studentId) {
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

      <FormControl fullWidth>
        <InputLabel>Chọn học kỳ</InputLabel>
        <Select
          value={selectedSemester}
          onChange={(e) => setSelectedSemester(e.target.value)}
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
              checked={selectedCourses.includes(course.id)}
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

      <Button
        size="large"
        onClick={handleSubmit}
        disabled={!selectedSemester || selectedCourses.length === 0}
        className="self-center px-8 py-3 text-lg"
      >
        Xác nhận đăng ký
      </Button>
    </Stack>
  );
}

export default RegisterCoursePage;
