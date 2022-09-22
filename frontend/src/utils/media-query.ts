import { type BreakPoint, theme } from '@styles/theme';

export type BreakpointsFor<A> = {
  [key in BreakPoint]?: A;
};

export type BreakpointConfig<A> = A | BreakpointsFor<A>;

export const mqUp = (size: BreakPoint) => {
  return `@media (min-width: ${theme.screens[size]})`;
};

export const mqDown = (size: BreakPoint) => {
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

export const mqBetween = <MinSize extends BreakPoint>(min: MinSize, max: SizeRelationship[MinSize]) => {
  return `@media (min-width: ${theme.screens[min]} and max-width: calc(${theme.screens[max]} - 0.5px))`;
};
