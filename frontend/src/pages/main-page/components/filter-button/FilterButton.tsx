import { memo } from 'react';

import tw from '@utils/tw';

import { ToggleButton } from '@components/button';
import Flex from '@components/flex/Flex';

import * as S from '@main-page/components/filter-button/FilterButton.style';

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
          <Flex direction="column" width="80px">
            <S.Name>{name}</S.Name>
            <S.Description>{description}</S.Description>
          </Flex>
        </ToggleButton>
      </Flex>
    </div>
  );
};

export default memo(FilterButton);
