import React from "react";

function IconCompletedOrder({ width = 18, height = 18, color = "#202224" }) {
  return (
    <svg
      width={width}
      height={height}
      viewBox="0 0 15 17"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M0.4375 0.5H1.125H13.5H14.1875V1.1875V16.3125V17H13.5H1.125H0.4375V16.3125V1.1875V0.5ZM1.8125 1.875V5.3125H12.8125V1.875H1.8125ZM1.8125 6.6875V10.8125H12.8125V6.6875H1.8125ZM1.8125 12.1875V15.625H12.8125V12.1875H1.8125Z"
        fill={color}
      />
    </svg>
  );
}

export default IconCompletedOrder;
