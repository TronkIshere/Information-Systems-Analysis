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

  static setRoles(roles: string[]) {
    localStorage.setItem("roles", JSON.stringify(roles));
  }

  static getRoles(): string[] {
    if (typeof window === "undefined") return [];
    try {
      return JSON.parse(localStorage.getItem("roles") || "[]");
    } catch (e) {
      console.error("Failed to parse roles from localStorage", e);
      return [];
    }
  }
}
