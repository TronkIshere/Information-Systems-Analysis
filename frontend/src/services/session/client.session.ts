"use client";
import Cookies from "js-cookie";
import { AUTH_TOKEN_KEY } from "@/constants";

export default class ClientSession {
  static setAuthToken(token: string) {
    Cookies.set(AUTH_TOKEN_KEY, token);
  }
  static removeAuthToken() {
    Cookies.remove(AUTH_TOKEN_KEY);
  }
}
