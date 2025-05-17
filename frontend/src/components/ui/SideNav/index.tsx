"use client";
import Image from "next/image";
import NextLink from "next/link";
import { useRouter } from "next/navigation";

import Logo from "@/assets/images/logo-side-bar.svg";
import {
  Box,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  styled,
} from "@mui/material";
import { getMenu } from "./untils";
import useAppRoute from "@/utils/route";

export default function SideNav() {
  const { pathName } = useAppRoute();
  const menu = getMenu();
  const ListItemStyled = styled(List)(({ theme }) => ({
    ".MuiButtonBase-root": {
      display: "flex !important",
      gap: "18px",
      padding: "16px 0",
    },
    ".MuiListItemIcon-root": {
      display: "inline-block",
      width: "fit-content",
      minWidth: 0,
    },
  }));
  const StyledListItemIcon = styled(ListItemIcon)<{ isactive?: string }>(
    ({ isactive = false }) => ({
      svg: {
        path: {
          fill: isactive == 'true' ? "#fff" : "#202224",
        },
      },
    })
  );
  return (
    <aside className="bg-white h-full w-fulls">
      <Box padding="24px 0" display="flex" justifyContent="center">
        <Image src={Logo} width={121} height={27} alt="logo" />
      </Box>
      <ListItemStyled
        sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}
      >
        {menu.map(({ text, icon, link }, index) => {
          const isActive = pathName === link;
          return (
            <NextLink href={link} key={index}>
              <ListItemButton
                key={index}
                className={`!px-[40px] !py-[12px] ${isActive ? "!bg-[#4880FF] !text-[#ffffff]" : ""}`}
              >
                <StyledListItemIcon isactive={String(isActive)}>
                  {icon}
                </StyledListItemIcon>
                <ListItemText primary={text} />
              </ListItemButton>
            </NextLink>
          );
        })}
      </ListItemStyled>
    </aside>
  );
}
