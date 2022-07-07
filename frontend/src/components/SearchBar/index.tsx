import React from 'react';
import { FiSearch } from 'react-icons/fi';

import * as S from './style';

type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit, inputName = 'keyword' }) => {
  return (
    <S.Container>
      <form onSubmit={e => onSubmit(e, inputName)}>
        <S.Input name={inputName} maxLength={20} placeholder="스터디 검색" />
        <S.Button>
          <FiSearch />
        </S.Button>
      </form>
    </S.Container>
  );
};

export default SearchBar;
