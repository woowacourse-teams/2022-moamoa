import type * as CSS from 'csstype';
import React from 'react';

import { css } from '@emotion/react';

import { CustomCSS, getResponsiveStyle, resolveCustomCSS } from '@styles/custom-css';
import parseStyle from '@styles/parseStyle';
import { type BreakpointsFor } from '@styles/responsive';

export type CSSProperty = keyof CSS.StandardProperties;

export type FlexBoxStyleProperty = {
  alignItems?: CSS.Properties['alignItems'];
  justifyContent?: CSS.Properties['justifyContent'];
  flexDirection?: CSS.Properties['flexDirection'];
  flexWrap?: CSS.Properties['flexWrap'];
  rowGap?: CSS.Properties['gap'];
  columnGap?: CSS.Properties['gap'];
};

export type FlexItemStyleProperty = {
  grow: CSS.Properties['flexGrow'];
  custom: CustomCSS<'width' | 'height' | 'maxWidth' | 'maxHeight' | 'minWidth' | 'minHeight'>;
};

export type FlexBoxProps = Partial<
  {
    children: React.ReactNode;
    custom?: CustomCSS<'width' | 'height' | 'maxWidth' | 'maxHeight' | 'minWidth' | 'minHeight'>;
  } & FlexBoxStyleProperty &
    BreakpointsFor<FlexBoxStyleProperty>
>;

const Flex: React.FC<FlexBoxProps> = ({
  children,
  alignItems = 'flex-start',
  justifyContent = 'flex-start',
  flexDirection = 'row',
  flexWrap = 'nowrap',
  rowGap = '0',
  columnGap = '0',
  custom,
  ...responsive
}) => {
  const { xs, sm, md, lg, xl, xxl, xxxl } = responsive;

  const style = css`
    ${resolveCustomCSS(custom)}

    ${parseStyle({
      alignItems,
      justifyContent,
      flexDirection,
      flexWrap,
      rowGap,
      columnGap,
    })}

    ${xxxl && getResponsiveStyle('xxxl', xxxl)}
    ${xxl && getResponsiveStyle('xxl', xxl)}
    ${xl && getResponsiveStyle('xl', xl)}
    ${lg && getResponsiveStyle('lg', lg)}
    ${md && getResponsiveStyle('md', md)}
    ${sm && getResponsiveStyle('sm', sm)}
    ${xs && getResponsiveStyle('xs', xs)}
  `;

  return <div css={style}>{children}</div>;
};

export type FlexItemStyle = {
  flexGrow: CSS.Properties['flexGrow'];
};

export type FlexItemProps = Partial<{ children?: React.ReactNode } & FlexItemStyle>;

const FlexItem: React.FC<FlexItemProps> = ({ flexGrow }) => {
  const style = css`
    ${parseStyle({ flexGrow })}
  `;
  return <div css={style}></div>;
};

export default Object.assign(Flex, {
  Item: FlexItem,
});
