import React, { ReactNode } from "react";
import Header from "@/components/ui/header";
import SideNav from "@/components/ui/SideNav";

function AdminLayout({ children }: { children: ReactNode }) {
  return (
    <div className="grid h-screen main-content bg-dashboard">
      <SideNav />

      <Header />

      <main className="overflow-y-auto">{children}</main>
    </div>
  );
}

export default AdminLayout;
