import { memo } from 'react';

import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

import { ToggleButton } from '@shared/button';
import Flex from '@shared/flex/Flex';

export type FilterButtonProps = {
  name: string;
  description: string;
  isChecked: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<'marginBottom'>;
};

const FilterButton: React.FC<FilterButtonProps> = ({ custom, name, description, isChecked, onClick: handleClick }) => {
  const theme = useTheme();
  return (
    <Self css={resolveCustomCSS(custom, theme)}>
      <Flex alignItems="center" custom={{ height: '70px' }}>
        <ToggleButton checked={isChecked} onClick={handleClick}>
          <Flex flexDirection="column" alignItems="center" custom={{ width: '80px' }}>
            <Name>{name}</Name>
            <Description>{description}</Description>
          </Flex>
        </ToggleButton>
      </Flex>
    </Self>
  );
};

export default memo(FilterButton);

const Self = styled.div``;

const Name = styled.span`
  ${({ theme }) => css`
    margin-bottom: 4px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `}
`;

const Description = styled.span`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.sm};
  `}
`;
