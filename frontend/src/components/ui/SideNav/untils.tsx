import { ADMIN_URL } from "@/types/app.url.type";
import IconDashboard from "@/assets/icons/IconDashboard";
import IconCustomer from "@/assets/icons/IconCustomer";
import IconReport from "@/assets/icons/IconReport";
import IconOrder from "@/assets/icons/IconOrder";
import IconCompletedOrder from "@/assets/icons/IconCompletedOrder";

export const getMenu = () => {
  return [
    {
      type: "single",
      text: "Tổng quan",
      icon: <IconDashboard />,
      link: ADMIN_URL.HOME,
    },
    {
      type: "single",
      text: "Khách hàng",
      icon: <IconCustomer />,
      link: ADMIN_URL.CUSTOMER,
    },
    {
      type: "single",
      text: "Báo cáo",
      icon: <IconReport />,
      link: ADMIN_URL.REPORT,
    },
    {
      type: "single",
      text: "Đơn hàng",
      icon: <IconOrder />,
      link: ADMIN_URL.ORDER,
    },
    {
      type: "single",
      text: "Đơn hoàn",
      icon: <IconCompletedOrder />,
      link: ADMIN_URL.COMPLETED_ORDER,
    },
  ];
};
