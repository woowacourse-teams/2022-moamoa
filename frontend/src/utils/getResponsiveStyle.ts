import * as CSS from 'csstype';

import { BreakpointsFor, mqDown } from '@utils/media-query';
import parseStyle from '@utils/parseStyle';

import { breakPoints } from '@styles/theme';

const getResponsiveStyle = (obj: BreakpointsFor<CSS.StandardProperties>) => {
  return breakPoints
    .reduce((acc, cur) => {
      const group = obj[cur];
      if (!group) return acc;
      acc.push(`${mqDown(cur)}{ ${parseStyle(group)}}`);
      return acc;
    }, [] as Array<string>)
    .join('');
};

export default getResponsiveStyle;
