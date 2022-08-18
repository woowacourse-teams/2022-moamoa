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
};

export type ThemeColor = typeof COLORS[keyof typeof COLORS];
