import { css } from '@emotion/react';
import { type CSSProperties as AllCSSKeyValue } from '@emotion/serialize';

import { BreakPoint, BreakpointsFor, mqDown } from '@styles/responsive';

type AllCSSKeys = keyof AllCSSKeyValue;

type PickAllowedCSSKeyValue<AllowedCSSKeys extends AllCSSKeys> = {
  [CSSKey in AllowedCSSKeys]?: AllCSSKeyValue[CSSKey];
};

type ResponsiveCSSKeyValue<AllowedCSSKeys extends AllCSSKeys> = BreakpointsFor<PickAllowedCSSKeyValue<AllowedCSSKeys>>;

export type CustomCSS<AllowedCSSKeys extends AllCSSKeys> = PickAllowedCSSKeyValue<AllowedCSSKeys> & {
  responsive?: ResponsiveCSSKeyValue<AllowedCSSKeys>;
};

export const getResponsiveStyle = <AllowedCSSKeys extends AllCSSKeys>(
  breakPoint: BreakPoint,
  styleObject: PickAllowedCSSKeyValue<AllowedCSSKeys>,
) => {
  return css`
    ${mqDown(breakPoint)} {
      ${styleObject}
    }
  `;
};

export const resolveCustomCSS = <AllowedCSSKeys extends AllCSSKeys>(custom?: CustomCSS<AllowedCSSKeys>) => {
  if (!custom) return css``;
  const { responsive, ...defaultStyle } = custom;

  if (responsive) {
    const { xs, sm, md, lg, xl, xxl, xxxl } = responsive;
    const xsStyle = xs && getResponsiveStyle<AllowedCSSKeys>('xs', xs);
    const smStyle = sm && getResponsiveStyle<AllowedCSSKeys>('sm', sm);
    const mdStyle = md && getResponsiveStyle<AllowedCSSKeys>('md', md);
    const lgStyle = lg && getResponsiveStyle<AllowedCSSKeys>('lg', lg);
    const xlStyle = xl && getResponsiveStyle<AllowedCSSKeys>('xl', xl);
    const xxlStyle = xxl && getResponsiveStyle<AllowedCSSKeys>('xxl', xxl);
    const xxxlStyle = xxxl && getResponsiveStyle<AllowedCSSKeys>('xxxl', xxxl);

    return css`
      ${defaultStyle}
      // 우선순위가 중요합니다!! xxxl -> xs 순으로 놓아야 합니다
      ${xxxlStyle}
      ${xxlStyle}
      ${xlStyle}
      ${lgStyle}
      ${mdStyle}
      ${smStyle}
      ${xsStyle}
    `;
  }

  return css`
    ${defaultStyle}
  `;
};
