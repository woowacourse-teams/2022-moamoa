import * as S from '@layout/header/components/search-bar/SearchBar.style';

import { SearchSvg } from '@components/svg';

export type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit, inputName = 'keyword' }) => {
  return (
    <S.Container>
      <form onSubmit={e => onSubmit(e, inputName)}>
        <S.Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
        <S.Button aria-label="검색하기">
          <SearchSvg />
        </S.Button>
      </form>
    </S.Container>
  );
};

export default SearchBar;
