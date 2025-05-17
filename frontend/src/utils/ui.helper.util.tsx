'use client'
import { deepClone } from "./deepClone";
import ReactDOM from "react-dom/client";
import { ToastType } from "@/types/toast";
import ToastContent from "@/components/ui/toast/ToastContent";

export type ToastProps = {
  type: ToastType;
  message: string | any;
  title?: string;
};

export default class UIHelper {
  static showToast(props: ToastProps) {
    const _props = deepClone(props);
    const node = document?.getElementById("top-container");
    if (node) {
      if (node.hasChildNodes()) {
        node.replaceChildren();
      }
      const div_root = document.createElement("div");
      div_root.className = "_toast_display";
      const root = ReactDOM.createRoot(div_root);
      const onClose = () => {
        root.unmount();
        setTimeout(() => {
          node.replaceChildren();
        });
      };
      root.render(
        <ToastContent
          title={_props.title}
          variant={_props.type}
          message={_props.message}
          onClose={onClose}
        />
      );
      node.appendChild(div_root);
    }
  }
}
