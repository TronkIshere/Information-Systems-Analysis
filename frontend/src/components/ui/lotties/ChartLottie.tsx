"use client";
import React from "react";
import { DotLottieReact } from "@lottiefiles/dotlottie-react";
interface IChartLottie {
  className?: string;
}

function ChartLottie({ className }: IChartLottie) {
  return (
    <div className={className}>
      <DotLottieReact
        src="https://lottie.host/08f5ff43-3bb0-4b42-ae21-09d35a06c092/ZnMVcwPyLQ.lottie"
        loop
        autoplay
      />
    </div>
  );
}

export default ChartLottie;
