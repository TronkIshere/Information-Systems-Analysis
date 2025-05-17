/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  experimental: {
    appDir: true,
    webpackBuildWorker: true,
  },
  output: "standalone",
  images: {
    unoptimized: true,
    domains: ["localhost"],
  },
};

export default nextConfig;
