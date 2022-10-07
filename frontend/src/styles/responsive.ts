import arrayOfAll from '@utils/arrayOfAll';

import { theme } from '@styles/theme';

export type BreakPoint = 'xs' | 'sm' | 'md' | 'lg' | 'xl' | 'xxl' | 'xxxl';
const arrayOfAllBreakPoint = arrayOfAll<BreakPoint>();
export const breakPoints = arrayOfAllBreakPoint(['xs', 'sm', 'md', 'lg', 'xl', 'xxl', 'xxxl']);

export const dimensions = {
  [breakPoints[0]]: '0',
  [breakPoints[1]]: '576px',
  [breakPoints[2]]: '768px',
  [breakPoints[3]]: '992px',
  [breakPoints[4]]: '1280px',
  [breakPoints[5]]: '1400px',
  [breakPoints[6]]: '2000px',
};

export type BreakpointsFor<T> = {
  [key in BreakPoint]?: T;
};

export const mqUp = (size: BreakPoint) => {
  return `@media (min-width: ${theme.screens[size]})`;
};

export const mqDown = (size: BreakPoint) => {
  return `@media (max-width: ${theme.screens[size]})`;
};

type SizeRelationship = {
  xs: 'sm' | 'md' | 'lg' | 'xl' | 'xxl' | 'xxxl';
  sm: 'md' | 'lg' | 'xl' | 'xxl' | 'xxxl';
  md: 'lg' | 'xl' | 'xxl' | 'xxxl';
  lg: 'xl' | 'xxl' | 'xxxl';
  xl: 'xxl' | 'xxxl';
  xxl: 'xxxl';
  xxxl: never;
};

export const mqBetween = <MinSize extends BreakPoint>(min: MinSize, max: SizeRelationship[MinSize]) => {
  return `@media (min-width: ${theme.screens[min]} and max-width: calc(${theme.screens[max]} - 0.5px))`;
};
