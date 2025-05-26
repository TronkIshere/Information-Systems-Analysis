"use client";
import React, { ReactNode, useMemo } from "react";
import Header from "@/components/ui/header";
import SideNav from "@/components/ui/SideNav";
import ClientSession from "@/services/session/client.session";
import { useRouter } from "next/navigation";
import { MenuItem } from "@/types/api";
import { getAdminMenu, getStudentMenu } from "../ui/SideNav/untils";

function DashboardLayout({
  children,
}: {
  children: ReactNode;
}): React.JSX.Element {
  const router = useRouter();
  const roles = ClientSession.getRoles();

  const menu = useMemo(() => {
    if (roles.includes("ROLE_ADMIN")) {
      return getAdminMenu();
    }

    if (roles.includes("ROLE_STUDENT")) {
      return getStudentMenu();
    }

    router.push("/login");
    return [];
  }, [roles, router]);

  return (
    <div className="grid h-screen main-content bg-dashboard">
      <SideNav menu={menu as MenuItem[]} />
      <Header />
      <main className="overflow-y-auto">{children}</main>
    </div>
  );
}

export default DashboardLayout;
