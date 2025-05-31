"use client";
import React, { useMemo, useRef, useState } from "react";
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
import {
  CourseResponse,
  SemesterResponse,
  UploadReceiptRequest,
} from "@/types/api";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

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

  const receiptRef = useRef<HTMLDivElement>(null);

  const handleExportReceipt = async () => {
    if (!receiptRef.current) return;

    try {
      const canvas = await html2canvas(receiptRef.current, {
        scale: 2,
        useCORS: true,
      });

      const imgData = canvas.toDataURL("image/png");

      const pdf = new jsPDF("p", "mm", "a4");
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

      pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);

      pdf.save(`hoa-don-${new Date().toLocaleDateString("vi-VN")}.pdf`);
    } catch (error) {
      UIHelper.showToast({
        message: "Xuất PDF thất bại! Vui lòng thử lại",
        type: ToastType.error,
      });
    }
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

      <div style={{ position: "absolute", left: "-9999px" }}>
        <div
          ref={receiptRef}
          style={{ width: "210mm", padding: "20px", fontFamily: "Arial" }}
        >
          <ReceiptTemplate
            newReceipt={newReceipt}
            courses={courses}
            semesters={semesters}
            totalAmount={totalAmount}
          />
        </div>
      </div>
    </Stack>
  );
}

export default RegisterCoursePage;

function ReceiptTemplate({
  newReceipt,
  courses,
  semesters,
  totalAmount,
}: {
  newReceipt: UploadReceiptRequest;
  courses: CourseResponse[] | undefined;
  semesters: SemesterResponse[] | undefined;
  totalAmount: number;
}) {
  const semester = semesters?.find((s) => s.id === newReceipt.semesterId);
  const selectedCourses = courses?.filter((course) =>
    newReceipt.courseIds.includes(course.id)
  );

  const currentDate = new Date();
  const formattedDate = `${currentDate.getDate()}/${
    currentDate.getMonth() + 1
  }/${currentDate.getFullYear()}`;

  return (
    <>
      <Typography variant="h4" align="center" gutterBottom>
        HÓA ĐƠN ĐĂNG KÝ MÔN HỌC
      </Typography>

      <Grid container spacing={2} sx={{ mb: 3 }}>
        <Grid item xs={6}>
          <Typography>
            <strong>Họ tên:</strong> {newReceipt.studentName}
          </Typography>
          <Typography>
            <strong>Lớp:</strong> {newReceipt.studentClass}
          </Typography>
        </Grid>
        <Grid item xs={6} textAlign="right">
          <Typography>
            <strong>Mã SV:</strong> {newReceipt.studentCode}
          </Typography>
          <Typography>
            <strong>Ngày:</strong> {formattedDate}
          </Typography>
        </Grid>
      </Grid>

      <Typography variant="h6" gutterBottom>
        Học kỳ: {semester?.name || "N/A"}
      </Typography>

      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #000", padding: "8px" }}>STT</th>
            <th style={{ border: "1px solid #000", padding: "8px" }}>
              Môn học
            </th>
            <th style={{ border: "1px solid #000", padding: "8px" }}>
              Số tín chỉ
            </th>
            <th style={{ border: "1px solid #000", padding: "8px" }}>
              Đơn giá (VND)
            </th>
            <th style={{ border: "1px solid #000", padding: "8px" }}>
              Thành tiền (VND)
            </th>
          </tr>
        </thead>
        <tbody>
          {selectedCourses?.map((course, index) => (
            <tr key={course.id}>
              <td
                style={{
                  border: "1px solid #000",
                  padding: "8px",
                  textAlign: "center",
                }}
              >
                {index + 1}
              </td>
              <td style={{ border: "1px solid #000", padding: "8px" }}>
                {course.name}
              </td>
              <td
                style={{
                  border: "1px solid #000",
                  padding: "8px",
                  textAlign: "center",
                }}
              >
                {course.credit}
              </td>
              <td
                style={{
                  border: "1px solid #000",
                  padding: "8px",
                  textAlign: "right",
                }}
              >
                {course.baseFeeCredit.toLocaleString()}
              </td>
              <td
                style={{
                  border: "1px solid #000",
                  padding: "8px",
                  textAlign: "right",
                }}
              >
                {(course.credit * course.baseFeeCredit).toLocaleString()}
              </td>
            </tr>
          ))}
          <tr>
            <td
              colSpan={4}
              style={{
                border: "1px solid #000",
                padding: "8px",
                textAlign: "right",
                fontWeight: "bold",
              }}
            >
              TỔNG CỘNG:
            </td>
            <td
              style={{
                border: "1px solid #000",
                padding: "8px",
                textAlign: "right",
                fontWeight: "bold",
              }}
            >
              {totalAmount.toLocaleString()} VND
            </td>
          </tr>
        </tbody>
      </table>

      <Grid container justifyContent="flex-end" sx={{ mt: 6 }}>
        <Grid item xs={4} textAlign="center">
          <Typography fontStyle="italic">Ngày {formattedDate}</Typography>
          <Typography fontWeight="bold">Người lập phiếu</Typography>
          <Typography fontStyle="italic">(Ký và ghi rõ họ tên)</Typography>
        </Grid>
      </Grid>
    </>
  );
}
