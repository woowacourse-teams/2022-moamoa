import { memo } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { CustomCSS, resolveCustomCSS } from '@styles/custom-css';

import { ToggleButton } from '@components/button';
import Flex from '@components/flex/Flex';

export type FilterButtonProps = {
  name: string;
  description: string;
  isChecked: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<'marginBottom'>;
};

const FilterButton: React.FC<FilterButtonProps> = ({ custom, name, description, isChecked, onClick: handleClick }) => {
  return (
    <Self css={resolveCustomCSS(custom)}>
      <Flex alignItems="center" height="70px">
        <ToggleButton checked={isChecked} onClick={handleClick}>
          <Flex flexDirection="column" width="80px">
            <Name>{name}</Name>
            <Description>{description}</Description>
          </Flex>
        </ToggleButton>
      </Flex>
    </Self>
  );
};

const Self = styled.div``;

const Name = styled.span`
  ${({ theme }) => css`
    margin-bottom: 4px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `}
`;

export const Description = styled.span`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.sm};
  `}
`;

export default memo(FilterButton);
