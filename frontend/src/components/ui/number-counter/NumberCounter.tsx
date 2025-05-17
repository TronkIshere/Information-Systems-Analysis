"use client";
import React, { useLayoutEffect } from "react";
import { gsap } from "gsap";

interface INumberCounter {
  start: string | number;
  end: string | number;
  prefix?: string;
}

function NumberCounter({ start, end, prefix = "" }: INumberCounter) {
  useLayoutEffect(() => {
    gsap.registerEffect({
      name: "counter",
      extendTimeline: true,
      defaults: {
        end: 0,
        duration: 0.5,
        ease: "power1",
        increment: 1,
      },
      effect: (targets: any, config: any) => {
        const tl = gsap.timeline();
        const num = targets[0].innerText.replace(/\,/g, "");
        targets[0].innerText = num;

        tl.to(
          targets,
          {
            duration: config.duration,
            innerText: config.end,
            //snap:{innerText:config.increment},
            modifiers: {
              innerText: function (innerText) {
                return gsap.utils
                  .snap(config.increment, innerText)
                  .toString()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
              },
            },
            ease: config.ease,
          },
          0
        );

        return tl;
      },
    });
    const tl = gsap.timeline();
    tl.counter(".number-counter", { end: end, increment: 10, duration: 1 });
    tl.restart(true, false)
  }, [prefix]);

  return (
    <div className="flex gap-2 items-center">
      <h5 className="number-counter text-white text-2xl">{start}</h5>
      {prefix && <span>{prefix}</span>}
    </div>
  );
}

export default NumberCounter;
