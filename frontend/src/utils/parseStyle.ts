import * as CSS from 'csstype';

import { CSSPropertyMap } from '@constants';

const parseStyle = (style: CSS.StandardProperties) => {
  const cssText = (Object.keys(style) as Array<keyof CSS.StandardProperties>)
    .reduce((acc, cur) => {
      if (style[cur]) {
        acc.push(`${CSSPropertyMap[cur]}: ${style[cur]}`);
      }
      return acc;
    }, [] as Array<string>)
    .join(';');
  return cssText;
};

export default parseStyle;
