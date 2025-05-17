import {
  dehydrate,
  HydrationBoundary,
  QueryClient,
} from "@tanstack/react-query";
import { ReactNode } from "react";

import { AppProvider } from "@/app/provider";
import { getUserQueryOptions } from "@/services/auth";
import { theme } from "@/config/theme";
import "@/styles/globals.scss";

import { ThemeProvider } from "@mui/material/styles";
import { adminFont } from "@/config/font";

export const metadata = {
  title: "Analysis",
  description: "Nguyễn Hữu Trọng",
};

const RootLayout = async ({ children }: { children: ReactNode }) => {
  const queryClient = new QueryClient();

  await queryClient.prefetchQuery(getUserQueryOptions());

  const dehydratedState = dehydrate(queryClient);

  return (
    <html lang="vi">
      <body className={adminFont.className}>
        <div id="top-container" />
        <AppProvider>
          <ThemeProvider theme={theme}>
            <HydrationBoundary state={dehydratedState}>
              {children}
            </HydrationBoundary>
          </ThemeProvider>
        </AppProvider>
      </body>
    </html>
  );
};

export default RootLayout;

// We are not prerendering anything because the app is highly dynamic
// and the data depends on the user so we need to send cookies with each request
export const dynamic = "force-dynamic";
