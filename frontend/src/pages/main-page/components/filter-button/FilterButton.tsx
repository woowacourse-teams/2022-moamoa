import { memo } from 'react';

import tw from '@utils/tw';

import { ToggleButton } from '@design/components/button';

import * as S from '@main-page/components/filter-button/FilterButton.style';

export type FilterButtonProps = {
  name: string;
  description: string;
  isChecked: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const FilterButton: React.FC<FilterButtonProps> = ({ name, description, isChecked, onClick: handleClick }) => {
  return (
    <div css={tw`flex items-center h-70 mb-8`}>
      <ToggleButton checked={isChecked} onClick={handleClick}>
        <div css={tw`flex flex-col w-80`}>
          <S.Name>{name}</S.Name>
          <S.Description>{description}</S.Description>
        </div>
      </ToggleButton>
    </div>
  );
};

export default memo(FilterButton);
