import { theme } from '@styles/theme';

type Size = keyof typeof theme.screens;

export const mqUp = (size: Size) => {
  return `@media (min-width: ${theme.screens[size]})`;
};

export const mqDown = (size: Size) => {
  return `@media (max-width: ${theme.screens[size]})`;
};

type SizeRelationship = {
  xs: 'sm' | 'md' | 'lg' | 'xl' | 'xxl';
  sm: 'md' | 'lg' | 'xl' | 'xxl';
  md: 'lg' | 'xl' | 'xxl';
  lg: 'xl' | 'xxl';
  xl: 'xxl';
  xxl: never;
};

export const mqBetween = <MinSize extends Size>(min: MinSize, max: SizeRelationship[MinSize]) => {
  return `@media (min-width: ${theme.screens[min]} and max-width: calc(${theme.screens[max]} - 0.5px))`;
};
