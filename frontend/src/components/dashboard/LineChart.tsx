'use client'
import { useEffect, useRef } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ChartData,
  ChartOptions,
  LineController
} from 'chart.js';
import { Card, CardContent, Box, Typography } from '@mui/material';

// Register ChartJS components
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  LineController,
  Title,
  Tooltip,
  Legend
);

const defaultOptions: ChartOptions<'line'> = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: true,
      position: 'top' as const,
      align: 'start' as const,
      labels: {
        boxWidth: 40,
        usePointStyle: false,
        padding: 20,
        color: '#666666',
        font: {
          size: 14
        }
      },
    },
    tooltip: {
      backgroundColor: 'white',
      titleColor: '#333333',
      bodyColor: '#666666',
      borderColor: '#EAEAEA',
      borderWidth: 1,
      padding: 12,
      displayColors: true,
      callbacks: {
        title: (items) => items[0].label,
        label: (item) => `${item.formattedValue}`,
      },
    },
  },
  scales: {
    x: {
      grid: {
        display: true,
        color: '#EAEAEA',
      },
      border: {
        display: false,
      },
      ticks: {
        color: '#2B3034',
        padding: 8,
        font: {
          size: 12
        }
      },
    },
    y: {
      min: -100,
      max: 20,
      border: {
        display: false,
      },
      grid: {
        color: '#EAEAEA',
      },
      ticks: {
        color: '#666666',
        padding: 12,
        stepSize: 20,
        font: {
          size: 12
        }
      },
    },
  },
  elements: {
    line: {
      tension: 0.4,
      borderWidth: 3,
      borderColor: '#4379EE',
      fill: 'start',
      backgroundColor: 'rgba(67, 121, 238, 0.1)',
    },
    point: {
      radius: 4,
      hitRadius: 8,
      hoverRadius: 6,
      backgroundColor: '#4379EE',
      borderColor: '#FFFFFF',
      borderWidth: 2,
    },
  },
};

interface LineChartProps {
  title?: string;
  data: ChartData<'line'>;
  options?: ChartOptions<'line'>;
  height?: number;
}

export function LineChart({ title, data, options = {}, height = 350 }: LineChartProps) {
  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstance = useRef<any>(null);

  useEffect(() => {
    if (!chartRef.current) return;

    // Destroy previous chart instance
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    // Create new chart instance
    const ctx = chartRef.current.getContext('2d');
    if (!ctx) return;

    chartInstance.current = new ChartJS(ctx, {
      type: 'line',
      data,
      options: {
        ...defaultOptions,
        ...options,
      },
    });

    // Cleanup on unmount
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [data, options]);

  return (
    <Card sx={{ 
      boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.05)',
      borderRadius: '8px',
    }}>
      <CardContent>
        {title && (
          <Typography variant="h5" gutterBottom sx={{ mb: 3, color: '#333333' }}>
            {title}
          </Typography>
        )}
        <Box sx={{ height, position: 'relative' }}>
          <canvas ref={chartRef} />
        </Box>
      </CardContent>
    </Card>
  );
} 