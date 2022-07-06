// import문과 declare의 충돌로 나머지 declare문은 common.d.ts에 작성했다
import { theme } from '@styles/theme';

type ThemeConfig = typeof theme;
declare module '@emotion/react' {
  export interface Theme extends ThemeConfig {}
}
