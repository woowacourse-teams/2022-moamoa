import { hasOwnProperty } from '@utils/hasOwnProperty';
import { isString } from '@utils/typeChecker';

import { type ThemeFontSize, theme } from '@styles/theme';

const isThemeFontSize = (fontSize: unknown): fontSize is ThemeFontSize =>
  isString(fontSize) && hasOwnProperty(theme.fontSize, fontSize);

export default isThemeFontSize;
