"use client";
import React from "react";
import Popper from "@mui/material/Popper";
import NextLink from "next/link";

import {
  Avatar,
  Box,
  Fade,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Stack,
  Typography,
} from "@mui/material";

import ExpandCircleDownOutlinedIcon from "@mui/icons-material/ExpandCircleDownOutlined";
import { USER_LIST_SETTING } from "./untils";

export default function ProfileHeader() {
  const [open, setOpen] = React.useState(false);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    setOpen((previousOpen) => !previousOpen);
  };

  const canBeOpen = open && Boolean(anchorEl);
  const id = canBeOpen ? "transition-popper" : undefined;
  return (
    <div>
      <button aria-describedby={id} type="button" onClick={handleClick}>
        <Stack
          direction="row"
          className="max-w-[169px] gap-[20px] cursor-pointer">
          <Avatar
            sx={{ width: 50, height: 50 }}
            alt="avatar"
            src="https://i.pravatar.cc/300"
          />
          <Box>
            <Typography
              component="h4"
              fontWeight={700}
              className="color-subtitle-primary whitespace-nowrap">
              Moni Roy
            </Typography>
            <Typography component="p" fontWeight={400} lineHeight="100%">
              Admin
            </Typography>
          </Box>
          <Box display="flex" alignItems="center">
            <ExpandCircleDownOutlinedIcon sx={{ width: 18, height: 18 }} />
          </Box>
        </Stack>
      </button>
      <Popper 
        id={id} 
        open={open} 
        anchorEl={anchorEl} 
        transition
        placement="bottom-end"
        sx={{
          zIndex: 1300,
          mt: 1,
          '& .MuiPaper-root': {
            minWidth: 200,
          }
        }}
      >
        {({ TransitionProps }) => (
          <Fade {...TransitionProps} timeout={350}>
            <Box 
              sx={{ 
                bgcolor: "background.paper",
                boxShadow: '0px 2px 8px rgba(0,0,0,0.15)',
                borderRadius: 1,
              }}
            >
              <List
                sx={{
                  width: '100%',
                  minWidth: 200,
                  p: 1,
                }}
              >
                {USER_LIST_SETTING.map(({ title, icon, link, action }, index) => (
                  <ListItemButton
                    key={index}
                    component={NextLink}
                    href={link}
                    onClick={action}
                    sx={{
                      borderRadius: 1,
                      mb: index < USER_LIST_SETTING.length - 1 ? 0.5 : 0,
                      '&:hover': {
                        bgcolor: 'rgba(0, 0, 0, 0.04)',
                      },
                    }}
                  >
                    <ListItemIcon sx={{ minWidth: 36 }}>
                      {icon}
                    </ListItemIcon>
                    <ListItemText 
                      primary={
                        <Typography fontSize={14}>
                          {title}
                        </Typography>
                      }
                    />
                  </ListItemButton>
                ))}
              </List>
            </Box>
          </Fade>
        )}
      </Popper>
    </div>
  );
}
