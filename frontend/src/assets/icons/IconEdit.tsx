import React from "react";

function IconEdit({ width = 18, height = 18 }) {
  return (
    <svg
      width={width}
      height={height}
      viewBox="0 0 18 18"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g opacity="0.6">
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M9.69683 10.424L7.22217 10.778L7.5755 8.30267L13.9395 1.93867C14.5253 1.35288 15.475 1.35288 16.0608 1.93867C16.6466 2.52446 16.6466 3.47421 16.0608 4.06L9.69683 10.424Z"
          stroke="black"
          strokeWidth="1.2"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M13.2319 2.646L15.3533 4.76733"
          stroke="black"
          strokeWidth="1.2"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M13.5 10.5V15.5C13.5 16.0523 13.0523 16.5 12.5 16.5H2.5C1.94772 16.5 1.5 16.0523 1.5 15.5V5.5C1.5 4.94772 1.94772 4.5 2.5 4.5H7.5"
          stroke="black"
          strokeWidth="1.2"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </g>
    </svg>
  );
}

export default IconEdit;
