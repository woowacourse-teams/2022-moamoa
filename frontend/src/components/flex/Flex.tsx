import type * as CSS from 'csstype';
import React from 'react';

import { css } from '@emotion/css';
import styled from '@emotion/styled';

import getResponsiveStyle from '@utils/getResponsiveStyle';
import { BreakpointsFor } from '@utils/media-query';
import parseStyle from '@utils/parseStyle';

export type CSSProperty = keyof CSS.StandardProperties;

export type BoxStyleProperty = {
  width: CSS.Properties['width'];
  height: CSS.Properties['height'];
  minWidth: CSS.Properties['minWidth'];
  minHeight: CSS.Properties['minHeight'];
  maxWidth: CSS.Properties['maxWidth'];
  maxHeight: CSS.Properties['maxHeight'];
};

export type FlexBoxStyleProperty = {
  alignItems: CSS.Properties['alignItems'];
  justifyContent: CSS.Properties['justifyContent'];
  flexDirection: CSS.Properties['flexDirection'];
  flexWrap: CSS.Properties['flexWrap'];
  rowGap: CSS.Properties['gap'];
  columnGap: CSS.Properties['gap'];
} & BoxStyleProperty;

export type FlexItemStyleProperty = {
  grow: CSS.Properties['flexGrow'];
} & BoxStyleProperty;

export type FlexBoxProps = Partial<
  { children?: React.ReactNode } & FlexBoxStyleProperty & BreakpointsFor<FlexBoxStyleProperty>
>;

const Flex: React.FC<FlexBoxProps> = ({
  children = null,
  alignItems,
  justifyContent,
  flexDirection = 'row',
  flexWrap = 'nowrap',
  rowGap,
  columnGap,
  width,
  height,
  minWidth,
  minHeight,
  maxWidth,
  maxHeight,
  ...responsiveObjs
}) => {
  const Component = styled.div`
    box-sizing: border-box;
    display: flex;
    ${parseStyle({
      alignItems,
      justifyContent,
      flexDirection,
      flexWrap,
      rowGap,
      columnGap,
      width,
      height,
      minWidth,
      minHeight,
      maxWidth,
      maxHeight,
    })};
    ${getResponsiveStyle(responsiveObjs)};
  `;

  return <Component>{children}</Component>;
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
  FlexItem,
});
