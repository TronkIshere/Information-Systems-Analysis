"use client";

import NextLink from "next/link";
import { useSearchParams } from "next/navigation";

import { paths } from "@/config/path";
import { useLogin, loginInputSchema, useRegister } from "@/services/auth";
import { Button, Stack, Typography } from "@mui/material";
import { Form } from "@/components/ui/form";
import { Input } from "@/components/ui/form/input";
type LoginFormProps = {
  onSuccess?: () => void;
};

export const RegisterForm = ({ onSuccess }: LoginFormProps) => {
  const registerAccount = useRegister({
    onSuccess,
  });

  const searchParams = useSearchParams();
  const redirectTo = searchParams?.get("redirectTo");
  return (
    <Stack
      direction="row"
      alignItems="center"
      justifyContent="center"
      height="100%"
      className="relative"
    >
      <div className="w-[43.75vw] rounded-3xl bg-white py-[90px] px-[57px] text-center">
        <Typography
          className="text-center !font-medium mb-2"
          variant="headline-large"
        >
          Tạo tài khoản
        </Typography>
        <Typography component="p" className="text-center !mb-[37px] paragraph">
          Tạo một tài khoản để tiếp tục
        </Typography>
        <Form
          onSubmit={(values: {
            name: string;
            loginName: string;
            password: string;
            phoneNumber: string;
          }) => {
            registerAccount.mutate(values);
          }}
          schema={loginInputSchema}
          className="text-left"
        >
          {({ register, formState }) => (
            <>
              <Input
                type="email"
                label="Địa chỉ mail:"
                placeholder="esteban_schiller@gmail.com"
                className="bg-input"
                error={formState.errors["email"]}
                registration={register("email")}
              />
              <Input
                type="text"
                label="Số điện thoại"
                placeholder="Số điện thoại"
                className="bg-input"
                error={formState.errors["phoneNumber"]}
                registration={register("phoneNumber")}
              />
              <Input
                type="password"
                label="Mật khẩu"
                className="bg-input"
                error={formState.errors["password"]}
                registration={register("password")}
              />
              <Input
                type="password"
                label="Xác nhận lại mật khẩu:"
                className="bg-input"
                error={formState.errors["password"]}
                registration={register("password")}
              />
              <div>
                <Button
                  loading={registerAccount.isPending}
                  type="submit"
                  className="w-full font-bold"
                  variant="primary"
                >
                  Đăng ký
                </Button>
              </div>
            </>
          )}
        </Form>
        <div className="mt-2 flex items-center justify-end">
          <div className="text-sm flex items-center gap-[20px]">
            <Typography component="p">Bạn đã có tài khoản?</Typography>
            <NextLink
              href={paths.auth.register.getHref(redirectTo)}
              className="font-medium underline text-blue-600 hover:text-blue-500"
            >
              Đăng nhập
            </NextLink>
          </div>
        </div>
      </div>
    </Stack>
  );
};
