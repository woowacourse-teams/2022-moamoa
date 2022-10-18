import { type Theme, css } from '@emotion/react';
import { type CSSProperties } from '@emotion/serialize';

import { hasOwnProperty } from '@utils/hasOwnProperty';
import isThemeFontSize from '@utils/isThemeFontSize';

import { BreakPoint, BreakpointsFor, mqDown } from '@styles/responsive';
import { type ThemeFontSize, theme as defaultTheme } from '@styles/theme';

type AllCSSObject = CSSProperties & {
  fontSize: ThemeFontSize | CSSProperties['fontSize'];
};

type AllCSSKeys = keyof AllCSSObject;

type PickAllowedCSSObject<AllowedCSSKeys extends AllCSSKeys> = {
  [CSSKey in AllowedCSSKeys]?: AllCSSObject[CSSKey];
};

type ResponsiveCSSObject<AllowedCSSKeys extends AllCSSKeys> = BreakpointsFor<PickAllowedCSSObject<AllowedCSSKeys>>;

export type CustomCSS<AllowedCSSKeys extends AllCSSKeys> = PickAllowedCSSObject<AllowedCSSKeys> & {
  responsive?: ResponsiveCSSObject<AllowedCSSKeys>;
};

export const getResponsiveStyle = <AllowedCSSKeys extends AllCSSKeys>(
  breakPoint: BreakPoint,
  styleObject: PickAllowedCSSObject<AllowedCSSKeys>,
) => {
  return css`
    ${mqDown(breakPoint)} {
      ${styleObject}
    }
  `;
};

export const resolveCustomCSS = <AllowedCSSKeys extends AllCSSKeys>(
  custom?: CustomCSS<AllowedCSSKeys>,
  theme: Theme = defaultTheme,
) => {
  if (!custom) return css``;
  const { responsive, ...defaultStyle } = custom;

  if (hasOwnProperty(defaultStyle, 'fontSize')) {
    const fontSize = defaultStyle['fontSize'];
    if (isThemeFontSize(fontSize)) {
      defaultStyle['fontSize'] = theme.fontSize[fontSize];
    }
  }

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
      // 왜냐하면 getResponsiveStyle에서 mqDown(@media (max-width))을 사용했기 때문입니다
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
