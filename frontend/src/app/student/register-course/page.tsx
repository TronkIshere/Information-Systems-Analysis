"use client";
import React, { useMemo, useRef, useState } from "react";
import { useSemesters } from "@/services/semester";
import { useCreateReceipt } from "@/services/receipt";
import ClientSession from "@/services/session/client.session";
import {
  Box,
  Button,
  Checkbox,
  Grid,
  Paper,
  SelectChangeEvent,
  Stack,
  TextField,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { SearchInput } from "@/components/ui/search/SearchInput";
import useAppRoute from "@/utils/route";
import UIHelper from "@/utils/ui.helper.util";
import { ToastType } from "@/types/toast";
import {
  CourseOfferingResponse,
  SemesterResponse,
  UploadReceiptRequest,
} from "@/types/api";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import { useOpenCourseOfferings } from "@/services/courseOffering";

function RegisterCoursePage() {
  const { replace } = useAppRoute();
  const { data: courseOfferings, isLoading, error } = useOpenCourseOfferings();
  const { data: semesters } = useSemesters();
  const { mutate: createReceipt } = useCreateReceipt();

  const [newReceipt, setNewReceipt] = useState<UploadReceiptRequest>({
    studentId: ClientSession.getUserId() || "",
    semesterId: "",
    courseOfferingIds: [],
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
    if (!courseOfferings || newReceipt.courseOfferingIds.length === 0) return 0;

    return newReceipt.courseOfferingIds.reduce((sum, courseId) => {
      const course = courseOfferings.find((c) => c.id === courseId);
      return sum + (course ? course.credit * course.baseFeeCredit : 0);
    }, 0);
  }, [newReceipt.courseOfferingIds, courseOfferings]);

  const handleCourseSelect = (courseId: string) => {
    setNewReceipt((prev) => {
      const newCourseOfferingIds = prev.courseOfferingIds.includes(courseId)
        ? prev.courseOfferingIds.filter((id) => id !== courseId)
        : [...prev.courseOfferingIds, courseId];

      return { ...prev, courseOfferingIds: newCourseOfferingIds };
    });
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewReceipt((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = () => {
    if (!newReceipt.studentId || newReceipt.courseOfferingIds.length === 0) {
      UIHelper.showToast({
        message: "Vui lòng chọn học kỳ và ít nhất một môn học",
        type: ToastType.error,
      });
      return;
    }

    console.log("Dữ liệu gửi lên backend:", { ...newReceipt, totalAmount });

    createReceipt(
      { ...newReceipt, totalAmount },
      {
        onSuccess: () => {
          console.log("Đăng ký thành công, chuẩn bị hiển thị toast");
          UIHelper.showToast({
            message: "Đăng ký thành công! Vui lòng thanh toán",
            type: ToastType.success,
          });

          // Reset form sau khi đăng ký thành công
          setNewReceipt((prev) => ({
            ...prev,
            courseOfferingIds: [],
            semesterId: "",
          }));
        },
        onError: (error) => {
          const err = error as any;
          // Kiểm tra cả response.data (nếu có) vì bạn đã gói error trong fetchApi
          const errorData = err?.response?.data || err;
          const errorCode = errorData.code;
          const errorMessage = errorData.message || "Đăng ký thất bại!";

          console.error("Lỗi khi đăng ký:", err, errorCode, errorMessage);

          UIHelper.showToast({
            message: errorMessage,
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

  const filteredCourseOfferings =
    courseOfferings?.filter((courseOffering) =>
      courseOffering.courseName.toLowerCase().includes(searchTerm.toLowerCase())
    ) || [];

  return (
    <Stack spacing={4} className="p-8 max-w-4xl mx-auto">
      <Typography variant="h4" className="text-center">
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

      <SearchInput onSearch={setSearchTerm} placeholder="Tìm kiếm môn học..." />

      <Paper elevation={3} className="p-4">
        <Typography variant="h6" gutterBottom>
          Danh sách môn học
        </Typography>
        <TableContainer sx={{ maxHeight: 400 }}>
          <Table stickyHeader size="small">
            <TableHead>
              <TableRow>
                <TableCell padding="checkbox"></TableCell>
                <TableCell>Tên môn</TableCell>
                <TableCell align="center">Số TC</TableCell>
                <TableCell align="center">Loại môn</TableCell>
                <TableCell align="right">Đơn giá (VND)</TableCell>
                <TableCell align="right">Thành tiền (VND)</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredCourseOfferings.map((courseOffering) => {
                const courseAmount =
                  courseOffering.credit * courseOffering.baseFeeCredit;
                return (
                  <TableRow
                    key={courseOffering.id}
                    hover
                    selected={newReceipt.courseOfferingIds.includes(
                      courseOffering.id
                    )}
                    onClick={() => handleCourseSelect(courseOffering.id)}
                  >
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={newReceipt.courseOfferingIds.includes(
                          courseOffering.id
                        )}
                      />
                    </TableCell>
                    <TableCell>{courseOffering.courseName}</TableCell>
                    <TableCell align="center">
                      {courseOffering.credit}
                    </TableCell>
                    <TableCell align="center">
                      {courseOffering.subjectType ? "Thực hành" : "Lý thuyết"}
                    </TableCell>
                    <TableCell align="right">
                      {courseOffering.baseFeeCredit.toLocaleString()}
                    </TableCell>
                    <TableCell align="right">
                      {courseAmount.toLocaleString()}
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>

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
                disabled={newReceipt.courseOfferingIds.length === 0}
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
            courseOfferings={courseOfferings}
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
  courseOfferings,
  semesters,
  totalAmount,
}: {
  newReceipt: UploadReceiptRequest;
  courseOfferings: CourseOfferingResponse[] | undefined;
  semesters: SemesterResponse[] | undefined;
  totalAmount: number;
}) {
  const semester = semesters?.find((s) => s.id === newReceipt.semesterId);
  const selectedCourseOfferings = courseOfferings?.filter((courseOffering) =>
    newReceipt.courseOfferingIds.includes(courseOffering.id)
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
              Loại môn
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
          {selectedCourseOfferings?.map((course, index) => (
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
                {course.courseName}
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
                  textAlign: "center",
                }}
              >
                {course.subjectType ? "Thực hành" : "Lý thuyết"}
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
              colSpan={5}
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

      <Grid container justifyContent="space-between" sx={{ mt: 6 }}>
        <Grid item xs={4} textAlign="center">
          <Typography fontStyle="italic">Ngày {formattedDate}</Typography>
        </Grid>

        <Grid item xs={4} textAlign="center">
          <Typography fontWeight="bold">Người lập phiếu</Typography>
          <Typography fontStyle="italic">(Ký và ghi rõ họ tên)</Typography>
        </Grid>

        <Grid item xs={4} textAlign="center">
          <Typography fontWeight="bold">Thủ quỹ</Typography>
          <Typography fontStyle="italic">(Ký và ghi rõ họ tên)</Typography>
        </Grid>
      </Grid>
    </>
  );
}
