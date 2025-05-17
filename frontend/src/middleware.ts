import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import { AUTH_TOKEN_KEY } from '@/constants';
import { ADMIN_URL } from '@/types/app.url.type';

export function middleware(request: NextRequest) {
  const token = request.cookies.get(AUTH_TOKEN_KEY);
  const path = request.nextUrl.pathname;
  if (
    path.startsWith(ADMIN_URL.HOME) &&
    !path.startsWith(ADMIN_URL.LOGIN) &&
    !token
  ) {
    const url = request.nextUrl.clone();
    url.pathname = ADMIN_URL.LOGIN;
    return NextResponse.redirect(url);
  }
  const requestHeaders = new Headers(request.headers);
  requestHeaders.set('x-url', request.url);
  const nextResponse: NextResponse = NextResponse.next({
    request: {
      // Apply new request headers
      headers: requestHeaders,
    },
  });
  return nextResponse;
}

// only applies this middleware to files in the app directory
export const config = {
  matcher: '/((?!api|static|.*\\..*|_next).*)',
};
