import { memo } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import tw from '@utils/tw';

import { ToggleButton } from '@components/button';
import Flex from '@components/flex/Flex';

export type FilterButtonProps = {
  name: string;
  description: string;
  isChecked: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const FilterButton: React.FC<FilterButtonProps> = ({ name, description, isChecked, onClick: handleClick }) => {
  return (
    <div css={tw`mb-8`}>
      <Flex alignItems="center" height="70px">
        <ToggleButton checked={isChecked} onClick={handleClick}>
          <Flex flexDirection="column" width="80px">
            <Name>{name}</Name>
            <Description>{description}</Description>
          </Flex>
        </ToggleButton>
      </Flex>
    </div>
  );
};

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
