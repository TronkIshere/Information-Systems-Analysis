import {
  Card,
  CardContent,
  Typography,
  Box,
  SxProps,
  Theme,
} from "@mui/material";
import { TrendingUp, TrendingDown } from "@mui/icons-material";

const CARD_STYLES = {
  users: {
    background: '#F4F7FE',
    iconBg: '#E9EFFF',
  },
  orders: {
    background: '#F4F7FE',
    iconBg: '#FFF6E9',
  },
  revenue: {
    background: '#F4F7FE',
    iconBg: '#E6F5EF',
  },
  growth: {
    background: '#F4F7FE',
    iconBg: '#FFE7E7',
  }
} as const;

interface StatCardProps {
  title: string;
  value: string | number;
  trend?: {
    value: number;
    isPositive: boolean;
    label: string;
  };
  type: keyof typeof CARD_STYLES;
  icon?: React.ReactNode;
  sx?: SxProps<Theme>;
}

export function StatCard({
  title,
  value,
  trend,
  type,
  icon,
  sx,
}: StatCardProps) {
  const cardStyle = CARD_STYLES[type];

  return (
    <Card
      sx={{
        height: "100%",
        background: '#fff',
        borderRadius: 3,
        position: "relative",
        overflow: "hidden",
        boxShadow: 'rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;',
        ...sx,
      }}
    >
      <CardContent>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
            mb: 1,
          }}>
          <Box>
            <Typography
              variant="body2"
              color="text.secondary"
              sx={{ mb: 0.5, fontWeight: 500 }}
            >
              {title}
            </Typography>
            <Typography
              variant="h4"
              component="div"
              sx={{ 
                fontWeight: 600,
                fontSize: '1.75rem',
                mb: 1
              }}
            >
              {value}
            </Typography>
          </Box>
          {icon && (
            <Box
              sx={{
                width: 48,
                height: 48,
                borderRadius: 2,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                bgcolor: cardStyle.iconBg,
                '& svg': {
                  fontSize: 24,
                  color: 'text.primary',
                }
              }}>
              {icon}
            </Box>
          )}
        </Box>

        {trend && (
          <Box 
            sx={{ 
              display: "flex", 
              alignItems: "center", 
              gap: 0.5,
            }}
          >
            <Typography 
              variant="body2" 
              component="span"
              sx={{ 
                color: trend.isPositive ? '#4CAF50' : '#FF5252',
                display: 'flex',
                alignItems: 'center',
                gap: 0.5,
                fontWeight: 500,
              }}
            >
              {trend.isPositive ? (
                <TrendingUp fontSize="small" />
              ) : (
                <TrendingDown fontSize="small" />
              )}
              {trend.value}%
            </Typography>
            <Typography 
              variant="body2" 
              color="text.secondary"
              sx={{ fontWeight: 400 }}
            >
              {trend.label}
            </Typography>
          </Box>
        )}
      </CardContent>
    </Card>
  );
}
