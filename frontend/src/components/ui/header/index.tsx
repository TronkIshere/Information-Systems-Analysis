"use client";
import MenuIcon from "@mui/icons-material/Menu";
import { Input } from "@/components/ui/form/input";
import ProfileHeader from "@/components/ui/ProfileHeader";
import Box from "@mui/material/Box";
import {
  Button,
  IconButton,
  Menu,
  MenuItem,
  Badge,
  Typography,
} from "@mui/material";
import Link from "next/link";
import { ADMIN_URL } from "@/types/app.url.type";
import NotificationsIcon from "@mui/icons-material/Notifications";
import SettingsIcon from "@mui/icons-material/Settings";
import EventIcon from "@mui/icons-material/Event";
import PersonIcon from "@mui/icons-material/Person";
import ErrorIcon from "@mui/icons-material/Error";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useState } from "react";

const languages = [
  { code: "en", name: "Tiếng Anh", flag: "🇬🇧" },
  { code: "vn", name: "Tiếng Việt", flag: "🇻🇳" },
];

const notifications = [
  {
    id: 1,
    title: "Cài đặt",
    description: "Cập nhật bảng điều khiển",
    icon: <SettingsIcon sx={{ color: "#4379EE" }} />,
  },
  {
    id: 2,
    title: "Cập nhật sự kiện",
    description: "Cập nhật lại ngày sự kiện",
    icon: <EventIcon sx={{ color: "#E83E8C" }} />,
  },
  {
    id: 3,
    title: "Hồ sơ",
    description: "Cập nhật hồ sơ của bạn",
    icon: <PersonIcon sx={{ color: "#6F42C1" }} />,
  },
  {
    id: 4,
    title: "Lỗi ứng dụng",
    description: "Kiểm tra ứng dụng đang chạy",
    icon: <ErrorIcon sx={{ color: "#DC3545" }} />,
  },
];

export default function Header() {
  const [notifyAnchorEl, setNotifyAnchorEl] = useState<null | HTMLElement>(
    null
  );
  const [langAnchorEl, setLangAnchorEl] = useState<null | HTMLElement>(null);
  const [currentLang, setCurrentLang] = useState(languages[1]); // Default to Vietnamese

  const handleNotifyClick = (event: React.MouseEvent<HTMLElement>) => {
    setNotifyAnchorEl(event.currentTarget);
  };

  const handleLangClick = (event: React.MouseEvent<HTMLElement>) => {
    setLangAnchorEl(event.currentTarget);
  };

  const handleNotifyClose = () => {
    setNotifyAnchorEl(null);
  };

  const handleLangClose = () => {
    setLangAnchorEl(null);
  };

  const handleLanguageSelect = (lang: (typeof languages)[0]) => {
    setCurrentLang(lang);
    handleLangClose();
  };

  return (
    <header className="flex items-center py-2 px-[30px] justify-between bg-white w-full">
      <Box className="flex items-center" gap="24px">
        <MenuIcon />
        <Input
          type="text"
          placeholder="Tìm kiếm"
          className="w-[368px] rounded-4xl bg-dashboard"
        />
      </Box>
      <Box className="flex items-center gap-4">
        <Button variant="primary">
          <Link href={ADMIN_URL.COMPLETED_ORDER}>Xuất hoá đơn online</Link>
        </Button>

        {/* Notification Button */}
        <IconButton onClick={handleNotifyClick}>
          <Badge badgeContent={6} color="error">
            <NotificationsIcon sx={{ color: "#4379EE" }} />
          </Badge>
        </IconButton>

        {/* Language Selector */}
        <Button
          onClick={handleLangClick}
          endIcon={<KeyboardArrowDownIcon />}
          sx={{
            textTransform: "none",
            color: "text.primary",
            "&:hover": { backgroundColor: "transparent" },
            background: "#fff",
          }}
        >
          <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
            <span style={{ fontSize: "27px" }}>{currentLang.flag}</span>
            {currentLang.name}
          </Box>
        </Button>

        <ProfileHeader />
      </Box>

      {/* Notification Menu */}
      <Menu
        anchorEl={notifyAnchorEl}
        open={Boolean(notifyAnchorEl)}
        onClose={handleNotifyClose}
        PaperProps={{
          sx: {
            width: 320,
            maxWidth: "100%",
            mt: 1.5,
            "& .MuiMenuItem-root": {
              px: 2,
              py: 1,
              borderBottom: "1px solid",
              borderColor: "divider",
            },
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        <Typography variant="subtitle1" sx={{ p: 2, fontWeight: 600 }}>
          Thông báo
        </Typography>
        {notifications.map((notification) => (
          <MenuItem key={notification.id} onClick={handleNotifyClose}>
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              <Box
                sx={{
                  width: 40,
                  height: 40,
                  borderRadius: 1,
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  bgcolor: "action.hover",
                }}
              >
                {notification.icon}
              </Box>
              <Box>
                <Typography variant="subtitle2">
                  {notification.title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {notification.description}
                </Typography>
              </Box>
            </Box>
          </MenuItem>
        ))}
        <Box sx={{ p: 2, borderTop: "1px solid", borderColor: "divider" }}>
          <Typography
            component={Link}
            href="#"
            variant="subtitle2"
            sx={{
              color: "primary.main",
              textDecoration: "none",
              "&:hover": { textDecoration: "underline" },
            }}
          >
            Xem tất cả thông báo
          </Typography>
        </Box>
      </Menu>

      {/* Language Menu */}
      <Menu
        anchorEl={langAnchorEl}
        open={Boolean(langAnchorEl)}
        onClose={handleLangClose}
        PaperProps={{
          sx: {
            width: 200,
            mt: 1.5,
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        <Typography variant="subtitle1" sx={{ p: 2, fontWeight: 600 }}>
          Chọn ngôn ngữ
        </Typography>
        {languages.map((language) => (
          <MenuItem
            key={language.code}
            selected={language.code === currentLang.code}
            onClick={() => handleLanguageSelect(language)}
          >
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              <span style={{ fontSize: "1.2rem" }}>{language.flag}</span>
              {language.name}
            </Box>
          </MenuItem>
        ))}
      </Menu>
    </header>
  );
}
