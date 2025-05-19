import Link from "next/link";
// import BgHero from "@/assets/images/background-hero.png";
import Logo from "@/assets/images/logo.png";
import Image from "next/image";
import { ADMIN_URL } from "@/types/app.url.type";

export default function Home() {
  return (
    <>
      <div className="relative">
        <div
          id="container-hero"
          className="bg-no-repeat bg-cover min-h-[calc(100vh-69px)]"
          // style={{ backgroundImage: `url(${BgHero.src})` }}
        >
          <header className="bg-transparent w-full relative z-1">
            <div className="container mx-auto px-4 flex justify-between items-center">
              <div className="flex items-center space-x-2">
                <Image src={Logo} width={120} height={120} alt="logo" />
              </div>

              <div>
                <Link
                  href={ADMIN_URL.HOME}
                  className="bg-blue-600 rounded-full btn-primary-landing"
                >
                  Đăng nhập
                </Link>
              </div>

              <button className="md:hidden text-gray-600">
                <svg
                  className="w-6 h-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M4 6h16M4 12h16M4 18h16"
                  />
                </svg>
              </button>
            </div>
          </header>
          {/* Hero Section with custom background */}
          <section
            id="section-hero"
            className="h-[calc(100vh-69px)] flex-1 flex items-center overflow-hidden"
          >
            {/* Content */}
            <div className="container mx-auto px-4 py-16 flex flex-col md:flex-row items-center relative z-10">
              <div className="md:w-1/2 mb-10 md:mb-0">
                <div className="mb-6">
                  <h2 className="text-5xl font-bold mb-4 color-yellow">
                    Đây là component
                  </h2>
                  <h3 className="text-5xl font-bold mb-6 color-yellow">Hero</h3>
                </div>
                <p className="mb-8 text-white">nội dung ở đây</p>
                <div className="flex flex-col sm:flex-row gap-4">
                  <a
                    href="#"
                    className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-full text-center"
                  >
                    Button
                  </a>
                  <a
                    href="#"
                    className="border border-btn-landing color-btn-outline px-6 py-3 rounded-full text-center"
                  >
                    Button
                  </a>
                </div>
              </div>
            </div>
          </section>
        </div>

        <nav className="hidden md:flex items-center space-x-8 rounded-md">
          <a href="#" className="text-white hover:text-blue-800 text-nowrap">
            Home
          </a>
          <a href="#" className="text-white hover:text-blue-800 text-nowrap">
            Tin tức
          </a>
          <a href="#" className="text-white hover:text-blue-800 text-nowrap">
            Liên hệ
          </a>
        </nav>
      </div>

      {/* Footer */}
      <footer className="bg-blue-900 text-white py-8">
        <div className="container mx-auto px-4">
          <div className="flex flex-col md:flex-row justify-between">
            <div className="mb-6 md:mb-0">
              <div className="flex items-center space-x-2 mb-4">
                <div>
                  <h1 className="text-xl font-bold">
                    <span className="text-blue-300">NGUYỄN HỮU</span>
                    <span className="text-yellow-400"> TRỌNG</span>
                  </h1>
                </div>
              </div>
              <p className="text-blue-200 max-w-md">
                Cho em xin điểm A+++ để qua môn
              </p>
            </div>

            <div className="grid grid-cols-2 md:grid-cols-3 gap-8">
              <div>
                <h3 className="text-lg font-bold mb-4 text-blue-200">Column</h3>
                <ul className="space-y-2">
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-bold mb-4 text-blue-200">Column</h3>
                <ul className="space-y-2">
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-bold mb-4 text-blue-200">Column</h3>
                <ul className="space-y-2">
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                  <li>
                    <a href="#" className="text-blue-100 hover:text-white">
                      điền sau
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="border-t border-blue-800 mt-8 pt-8 text-center text-blue-300">
            <p>
              &copy; {new Date().getFullYear()} Nguyễn Hữu Trọng. All rights
              reserved.
            </p>
          </div>
        </div>
      </footer>
    </>
  );
}
