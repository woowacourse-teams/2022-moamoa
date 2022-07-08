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
<<<<<<< HEAD
        <S.Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
=======
        <S.Input name={inputName} maxLength={20} placeholder="스터디 검색" />
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c
        <S.Button>
          <FiSearch />
        </S.Button>
      </form>
    </S.Container>
  );
};

export default SearchBar;
