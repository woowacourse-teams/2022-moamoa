import { useTheme } from '@emotion/react';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

type CrownIconProps = {
  custom?: CustomCSS<'position' | 'top' | 'left' | 'zIndex'>;
};
const CrownIcon: React.FC<CrownIconProps> = ({ custom }) => {
  const theme = useTheme();
  return (
    <span css={resolveCustomCSS(custom, theme)}>
      <svg
        stroke={theme.colors.tertiary.base}
        fill={theme.colors.tertiary.base}
        strokeWidth="2"
        viewBox="0 0 24 24"
        strokeLinecap="round"
        strokeLinejoin="round"
        height="20"
        width="20"
        xmlns="http://www.w3.org/2000/svg"
      >
        <desc></desc>
        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
        <path d="M12 6l4 6l5 -4l-2 10h-14l-2 -10l5 4z"></path>
      </svg>
    </span>
  );
};

export default CrownIcon;
