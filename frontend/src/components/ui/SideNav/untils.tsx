import { ADMIN_URL, CASHIER_URL, STUDENT_URL } from "@/types/app.url.type";
import IconDashboard from "@/assets/icons/IconDashboard";
import IconCustomer from "@/assets/icons/IconCustomer";
import IconReport from "@/assets/icons/IconReport";
import IconOrder from "@/assets/icons/IconOrder";
import IconCompletedOrder from "@/assets/icons/IconCompletedOrder";
import IconAccount from "@/assets/icons/IconAccount";
import IconKey from "@/assets/icons/IconKey";
import { MenuItem } from "@/types/api";

export const getAdminMenu = () => {
  return [
    {
      type: "single",
      text: "Tổng quan",
      icon: <IconDashboard />,
      link: ADMIN_URL.HOME,
    },
    {
      type: "single",
      text: "Giảng viên",
      icon: <IconCustomer />,
      link: ADMIN_URL.LECTURER,
    },
    {
      type: "single",
      text: "Học sinh",
      icon: <IconReport />,
      link: ADMIN_URL.STUDENT,
    },
    {
      type: "single",
      text: "Môn học",
      icon: <IconKey />,
      link: ADMIN_URL.COURSE,
    },
    {
      type: "single",
      text: "Bộ môn",
      icon: <IconOrder />,
      link: ADMIN_URL.DEPARTMENT,
    },
    {
      type: "single",
      text: "Biên lai",
      icon: <IconCompletedOrder />,
      link: ADMIN_URL.RECEIPT,
    },
    {
      type: "single",
      text: "Học kì",
      icon: <IconDashboard />,
      link: ADMIN_URL.SEMESTER,
    },
    {
      type: "single",
      text: "Mở môn học",
      icon: <IconDashboard />,
      link: ADMIN_URL.COURSEOFFERING,
    },
  ];
};

export const getStudentMenu = () => [
  {
    text: "Tổng quan",
    icon: <IconDashboard />,
    link: STUDENT_URL.HOME,
    type: "single",
  },
  {
    text: "Đăng ký môn học",
    icon: <IconCompletedOrder />,
    link: STUDENT_URL.REGISTER_COURSE,
    type: "single",
  },
];

export const getCashierMenu = () => [
  {
    text: "Tổng quan",
    icon: <IconDashboard />,
    link: CASHIER_URL.HOME,
    type: "single",
  },
  {
    text: "Thanh toán",
    icon: <IconCompletedOrder />,
    link: CASHIER_URL.PAYMENT,
    type: "single",
  },
];
