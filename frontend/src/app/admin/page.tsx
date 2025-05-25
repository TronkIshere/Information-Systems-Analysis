"use client";

import { Grid, Container, Typography } from "@mui/material";
import PeopleIcon from "@mui/icons-material/People";
import ShoppingBasketIcon from "@mui/icons-material/ShoppingBasket";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import { StatCard } from "@/components/dashboard/StatCard";
import { LineChart } from "@/components/dashboard/LineChart";

export default function AdminPage() {
  // Sample data for the cards
  const stats = [
    {
      title: "Hôm nay",
      value: "1,689",
      trend: { value: 8.5, isPositive: true, label: "Tăng trong năm" },
      type: "users" as const,
      icon: <PeopleIcon />,
    },
    {
      title: "Tuần này",
      value: "10,293",
      trend: { value: 1.3, isPositive: true, label: "Tăng trong tuần" },
      type: "orders" as const,
      icon: <ShoppingBasketIcon />,
    },
    {
      title: "kì này",
      value: "192,000,000vnđ",
      trend: { value: 4.3, isPositive: false, label: "Xuống trong kì" },
      type: "revenue" as const,
      icon: <AttachMoneyIcon />,
    },
    {
      title: "Năm nay",
      value: "46",
      trend: { value: 1.8, isPositive: true, label: "Tăng trong năm" },
      type: "growth" as const,
      icon: <TrendingUpIcon />,
    },
  ];

  // Sample data for the chart
  const chartData = {
    labels: [
      "Tháng 1",
      "Tháng 2",
      "Tháng 3",
      "Tháng 4",
      "Tháng 5",
      "Tháng 6",
      "Tháng 7",
      "Tháng 8",
    ],
    datasets: [
      {
        label: "Dataset",
        data: [-55, -90, -35, 5, -70, -40, 15, -70],
        borderColor: "#4379EE",
        backgroundColor: "rgba(67, 121, 238, 0.1)",
        tension: 0.4,
        fill: "start",
      },
    ],
  };

  return (
    <div>
      <Container sx={{ py: 4 }}>
        <Typography variant="h5" sx={{ mb: 3, fontWeight: 600 }}>
          Tổng quan
        </Typography>
        <Grid container spacing={3}>
          {stats.map((stat, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <StatCard {...stat} />
            </Grid>
          ))}

          <Grid item xs={12}>
            <LineChart title="Doanh thu" data={chartData} height={400} />
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
