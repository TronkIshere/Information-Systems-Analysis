import React, { SVGProps } from "react";

interface IconProps extends SVGProps<SVGSVGElement> {
  width?: number;
  height?: number;
}

function IconBin({ width = 18, height = 16, ...props }: IconProps) {
  return (
    <svg
      width={width}
      height={height}
      viewBox="0 0 18 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      {...props}
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M13.2001 15.4H4.8001C4.13736 15.4 3.6001 14.8627 3.6001 14.2V3.4H14.4001V14.2C14.4001 14.8627 13.8628 15.4 13.2001 15.4Z"
        stroke="#EF3826"
        strokeWidth="1.2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M7.20029 11.8V7"
        stroke="#EF3826"
        strokeWidth="1.2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M10.8004 11.8V7"
        stroke="#EF3826"
        strokeWidth="1.2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M1.2002 3.4H16.8002"
        stroke="#EF3826"
        strokeWidth="1.2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M10.8 1H7.2C6.53726 1 6 1.53726 6 2.2V3.4H12V2.2C12 1.53726 11.4627 1 10.8 1Z"
        stroke="#EF3826"
        strokeWidth="1.2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

export default IconBin;
