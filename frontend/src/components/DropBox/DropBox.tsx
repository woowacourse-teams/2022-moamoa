import { ReactNode } from 'react';
import { FiSearch } from 'react-icons/fi';

import * as S from './DropBox.style';

export interface DropBoxProps {
  children: Array<ReactNode>;
}

const DropBox: React.FC<DropBoxProps> = ({ children }) => {
  return (
    <S.DropBoxContainer>
      <S.FilterSearchBar>
        <FiSearch />
        <S.FilterSearchInput type="text" maxLength={15} placeholder="태그 검색" />
      </S.FilterSearchBar>
      <S.FilterList>{children}</S.FilterList>
    </S.DropBoxContainer>
  );
};

export default DropBox;
