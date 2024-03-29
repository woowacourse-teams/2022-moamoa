import { type Theme, css } from '@emotion/react';

import { dimensions } from '@styles/responsive';

export type ThemeColor = typeof COLORS[keyof typeof COLORS];
export type ThemeFontSize = keyof Theme['fontSize'];

export const COLORS = {
  YELLOW200: '#FFD54F',
  BLUE100: '#534bae',
  BLUE200: '#1a237e',
  BLUE300: '#000051',
  GRAY100: '#f9fbfc',
  GRAY200: '#cfd8dc',
  GRAY300: '#9ea7aa',
  WHITE: '#ffffff',
  BLACK: '#000000',
  RED: '#ef5e51',
} as const;

export const theme = {
  colors: {
    primary: {
      base: COLORS.BLUE200,
      light: COLORS.BLUE100,
      dark: COLORS.BLUE300,
    },
    secondary: {
      base: COLORS.GRAY200,
      light: COLORS.GRAY100,
      dark: COLORS.GRAY300,
    },
    tertiary: {
      base: COLORS.YELLOW200,
    },
    white: COLORS.WHITE,
    black: COLORS.BLACK,
    red: COLORS.RED,
  },
  screens: { ...dimensions },
  fontSize: {
    xs: '12px',
    sm: '14px',
    md: '16px',
    lg: '20px',
    xl: '24px',
    xxl: '32px',
    xxxl: '40px',
  },
  fontWeight: {
    light: 300,
    normal: 400,
    bold: 600,
    bolder: 800,
  },
  radius: {
    none: 0,
    xs: '5px',
    sm: '10px',
    md: '15px',
    lg: '20px',
    xl: '25px',
    circle: '50%',
  },
};

export const applyHoverTranslateTransitionStyle = () => css`
  transition: transform 0.2s ease;

  &:hover {
    transform: translate3d(0, -5px, 0);
  }
`;

export const applyHoverBgTransitionStyle = (theme: Theme) => css`
  transition: background-color 0.2s ease;

  &:hover,
  &:active {
    background-color: ${theme.colors.white};
  }
`;
