"use client";
import ClientSession from "@/services/session/client.session";
import { ADMIN_URL } from "@/types/app.url.type";
import IconAccount from "@/assets/icons/IconAccount";
import IconKey from "@/assets/icons/IconKey";
import IconSetting from "@/assets/icons/IconSetting";
import IconLogout from "@/assets/icons/IconLogout";

class SettingAction {
  static logout() {
    ClientSession.removeAuthToken();
    window.location.replace(ADMIN_URL.LOGIN);
  }
}



export const USER_LIST_SETTING = [
  {
    title: "Quản lý tài khoản",
    icon: <IconAccount />,
    link: "",
  },
  {
    title: "Quên mật khẩu",
    icon: <IconKey />,
    link: "",
  },
  {
    title: "Thiết lâp bảo mật",
    icon: <IconSetting />,
    link: "",
  },
  {
    title: "Đăng xuất",
    icon: <IconLogout />,
    link: "",
    action: SettingAction.logout,
  },
];
