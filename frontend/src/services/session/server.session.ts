import { cookies as serverCookies } from "next/headers";
import { AUTH_TOKEN_KEY } from "@/constants";

export default class ServerSession {
  static async getAuthToken() {
    return (await serverCookies())?.get(AUTH_TOKEN_KEY)?.value;
  }

  static getAuthSession() {
    return `Bearer ${this.getAuthToken()}`;
  }
}
