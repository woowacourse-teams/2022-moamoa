import { css } from '@emotion/react';
import { type CSSProperties as OriginalCSSProperties } from '@emotion/serialize';

import { BreakPoint, BreakpointsFor, mqDown } from '@styles/responsive';

type KeyOfOriginalCSSProperties = keyof OriginalCSSProperties;

export type PickAllowedCSSProperties<CSSProperties extends KeyOfOriginalCSSProperties> = Pick<
  OriginalCSSProperties,
  CSSProperties
>;

export type CSSPropertyWithValue<AllowedCSSProperties extends KeyOfOriginalCSSProperties> = {
  [K in keyof PickAllowedCSSProperties<AllowedCSSProperties>]: OriginalCSSProperties[K];
};

export type ResponsiveCSSPropertyWithValue<AllowedCSSProperties extends KeyOfOriginalCSSProperties> = BreakpointsFor<
  CSSPropertyWithValue<AllowedCSSProperties>
>;

export type CustomCSS<AllowedCSSProperties extends KeyOfOriginalCSSProperties> =
  CSSPropertyWithValue<AllowedCSSProperties> & { responsive?: ResponsiveCSSPropertyWithValue<AllowedCSSProperties> };

export const getResponsiveStyle = <AllowedCSSProperties extends KeyOfOriginalCSSProperties>(
  breakPoint: BreakPoint,
  styleObject: CSSPropertyWithValue<AllowedCSSProperties>,
) => {
  return css`
    ${mqDown(breakPoint)} {
      ${styleObject}
    }
  `;
};

export const resolveCustomCSS = <AllowedCSSProperties extends KeyOfOriginalCSSProperties>(
  custom?: CustomCSS<AllowedCSSProperties>,
) => {
  if (!custom) return css``;
  const { responsive, ...defaultStyle } = custom;

  if (responsive) {
    const { xs, sm, md, lg, xl, xxl, xxxl } = responsive;
    const xsStyle = xs && getResponsiveStyle<AllowedCSSProperties>('xs', xs);
    const smStyle = sm && getResponsiveStyle<AllowedCSSProperties>('sm', sm);
    const mdStyle = md && getResponsiveStyle<AllowedCSSProperties>('md', md);
    const lgStyle = lg && getResponsiveStyle<AllowedCSSProperties>('lg', lg);
    const xlStyle = xl && getResponsiveStyle<AllowedCSSProperties>('xl', xl);
    const xxlStyle = xxl && getResponsiveStyle<AllowedCSSProperties>('xxl', xxl);
    const xxxlStyle = xxxl && getResponsiveStyle<AllowedCSSProperties>('xxxl', xxxl);

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
