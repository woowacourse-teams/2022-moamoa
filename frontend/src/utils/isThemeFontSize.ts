import { hasOwnProperty } from '@utils/hasOwnProperty';

import { ThemeFontSize, theme } from '@styles/theme';

const isThemeFontSize = (fontSize: unknown): fontSize is ThemeFontSize => {
  if (typeof fontSize === 'string') {
    if (hasOwnProperty(theme.fontSize, fontSize)) {
      return true;
    }
  }
  return false;
};

export default isThemeFontSize;
