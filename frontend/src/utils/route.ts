import { usePathname, useRouter, useSearchParams } from "next/navigation";
import {
  PrefetchOptions,
  NavigateOptions,
  AppRouterInstance,
} from "next/dist/shared/lib/app-router-context.shared-runtime";
import ApiUtil from "@/utils/api.until";
import { ADMIN_URL } from "@/types/app.url.type";
import AppUtil from "@/utils/app.until";

export type URLProps = {
  href: ADMIN_URL | string;
  params?: object | null;
  query?: object;
  options?: NavigateOptions | PrefetchOptions;
};

export interface AppRoute {
  queries: any;
  pathName: string;
  route: AppRouterInstance;
  push: (props: URLProps | ADMIN_URL | string) => any;
  prefetch: (props: URLProps | ADMIN_URL | string) => any;
  forward: () => any;
  replace: (props: URLProps | ADMIN_URL | string) => any;
  refresh: () => any;
  back: () => any;
  open: (props: URLProps | ADMIN_URL | string) => any;
}
export default function useAppRoute(): AppRoute {
  const route = useRouter();
  const pathName = usePathname();
  const searchParams = useSearchParams();
  const queries = AppUtil.getSearchObjectFromUrl(searchParams);
  /**
   * Navigate to the previous history entry.
   */
  const back = () => {
    route.back();
  };
  /**
   * Navigate to the next history entry.
   */
  const forward = () => {
    route.forward();
  };
  /**
   * Refresh the current page.
   */
  const refresh = () => {
    route.refresh();
  };
  /**
   * Navigate to the provided href.
   * Pushes a new history entry.
   */
  const push = (props: URLProps | ADMIN_URL | string) => {
    if (typeof props == "string") {
      route.push(props);
    } else {
      route.push(
        ApiUtil.getUrlPath(props.href, props.params, props.query),
        <NavigateOptions>props.options
      );
    }
  };
  /**
   * Navigate to the provided href.
   * Replaces the current history entry.
   */
  const replace = (props: URLProps | ADMIN_URL | string) => {
    if (typeof props == "string") {
      route.replace(props);
    } else {
      route.replace(
        ApiUtil.getUrlPath(props.href, props.params, props.query),
        <NavigateOptions>props.options
      );
    }
  };
  /**
   * Prefetch the provided href.
   */
  const prefetch = (props: URLProps | ADMIN_URL | string) => {
    if (typeof props == "string") {
      route.prefetch(props);
    } else {
      route.prefetch(
        ApiUtil.getUrlPath(props.href, props.params, props.query),
        <PrefetchOptions>props.options
      );
    }
  };

  const open = (props: URLProps | ADMIN_URL | string) => {
    let url;
    if (typeof props == "string") {
      url = props;
    } else {
      url = ApiUtil.getUrlPath(props.href, props.params, props.query);
    }
    window.open(url, "_blank");
  };
  return {
    queries,
    pathName,
    route,
    push,
    prefetch,
    forward,
    replace,
    refresh,
    back,
    open,
  };
}
