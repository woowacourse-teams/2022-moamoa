import { BREAK_POINTS } from '@constants';

type Size = keyof typeof BREAK_POINTS;

export const mqUp = (size: Size) => {
  return `@media (min-width: ${BREAK_POINTS[size]}px)`;
};

export const mqDown = (size: Size) => {
  return `@media (max-width: ${BREAK_POINTS[size]}px)`;
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
  return `@media (min-width: ${BREAK_POINTS[min]}px and max-width: ${BREAK_POINTS[max] - 0.5}px)`;
};
