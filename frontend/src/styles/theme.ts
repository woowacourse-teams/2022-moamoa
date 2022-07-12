export interface Theme {}

export const COLORS = {
  BLUE100: '#534bae',
  BLUE200: '#1a237e',
  BLUE300: '#000051',
  GRAY100: '#f9fbfc',
  GRAY200: '#cfd8dc',
  GRAY300: '#9ea7aa',
  WHITE: '#ffffff',
  BLACK: '#000000',
};

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
    white: COLORS.WHITE,
    black: COLORS.BLACK,
  },
};
