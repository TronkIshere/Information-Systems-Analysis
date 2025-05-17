"use client";

import NextLink from "next/link";
import { useSearchParams } from "next/navigation";

import { paths } from "@/config/path";
import { useLogin, loginInputSchema } from "@/services/auth";
import {
  Button,
  Checkbox,
  FormControlLabel,
  Stack,
  styled,
  Typography,
} from "@mui/material";
import { Form } from "@/components/ui/form";
import { Input } from "@/components/ui/form/input";
import { useState } from "react";
import { AuthResponse } from "@/types/api";
type LoginFormProps = {
  onSuccess?: (data:AuthResponse) => void;
};

export const LoginForm = ({ onSuccess }: LoginFormProps) => {
  const login = useLogin({
    onSuccess,
  });
  const [checked, setChecked] = useState(false);

  const searchParams = useSearchParams();
  const redirectTo = searchParams?.get("redirectTo");

  const StyledCheckbox = styled(Checkbox)(({ theme }) => ({
    fill: "#A3A3A3",
    "& .MuiSvgIcon-root": {
      fill: "#A3A3A3",
    },
    "& .MuiCheckbox-root": {
      padding: "0 !important",
    },
  }));


  return (
    <Stack
      direction="row"
      alignItems="center"
      justifyContent="center"
      height="100%"
      className="relative"
    >
      <div className="w-[630px] rounded-3xl bg-white py-[90px] px-[57px] text-center">
        <Typography className="text-center mb-2" variant="headline-large">
          Đăng nhập
        </Typography>
        <Typography component="p" className="text-center !mb-[37px] paragraph">
          Vui lòng nhập email và mật khẩu của bạn để tiếp tục
        </Typography>
        <Form
          onSubmit={(values: {
            email: string;
            password: string;
            remember: boolean;
          }) => {
            login.mutate(values);
          }}
          schema={loginInputSchema}
          className="text-left"
        >
          {({ register, formState }) => (
            <>
              <Input
                type="email"
                label="Email:"
                className="bg-input"
                error={formState.errors["email"]}
                registration={register("email")}
              />
              <Input
                type="password"
                label="Mật khẩu:"
                className="bg-input"
                error={formState.errors["password"]}
                registration={register("password")}
              />
              <Stack direction="row" alignItems="center">
                <FormControlLabel
                  control={
                    <StyledCheckbox
                      checked={checked}
                      onChange={() => setChecked(!checked)}
                      icon={<Checkbox />}
                      checkedIcon={<Checkbox />}
                    />
                  }
                  label="Nhớ mật khẩu"
                  sx={{ color: "#6B7280", fontSize: "16px" }}
                />
              </Stack>
              <div className="flex justify-center">
                <Button
                  loading={login.isPending}
                  type="submit"
                  className="w-[70%]  font-bold"
                  variant="primary"
                >
                  Đăng nhập
                </Button>
              </div>
            </>
          )}
        </Form>
        <div className="mt-2 flex items-center justify-center">
          <div className="text-sm flex items-center gap-[20px]">
            <Typography component="p">Bạn chưa có tài khoản?</Typography>
            <NextLink
              href={paths.auth.register.getHref(redirectTo)}
              className="font-medium underline text-blue-600 hover:text-blue-500"
            >
              Tạo tài khoản
            </NextLink>
          </div>
        </div>
      </div>
    </Stack>
  );
};
