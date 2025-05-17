import stringFormat from "string-format";
import { ADMIN_URL } from "@/types/app.url.type";

export default class ApiUtil {
  static encodeURL = (obj: any) => {
    if (!obj) {
      return "";
    }
    const str = [];
    for (const p in obj)
      if (obj.hasOwnProperty(p)) {
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
      }
    return `?${str.join("&")}`;
  };

  static getUrlPath = (
    apiUrl: ADMIN_URL | string,
    params?: object | null,
    query?: object
  ) => {
    let path: string = apiUrl;
    if (params) {
      path = stringFormat(apiUrl, params);
    }
    return `${path}${this.encodeURL(query)}`;
  };
}
