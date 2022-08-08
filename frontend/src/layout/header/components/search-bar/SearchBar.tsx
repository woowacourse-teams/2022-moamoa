import * as S from '@layout/header/components/search-bar/SearchBar.style';

export type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const FiSearch = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="2"
    viewBox="0 0 24 24"
    strokeLinecap="round"
    strokeLinejoin="round"
    height="1em"
    width="1em"
    xmlns="http://www.w3.org/2000/svg"
  >
    <circle cx="11" cy="11" r="8"></circle>
    <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
  </svg>
);

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit, inputName = 'keyword' }) => {
  return (
    <S.Container>
      <form onSubmit={e => onSubmit(e, inputName)}>
        <S.Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
        <S.Button aria-label="검색하기">
          <FiSearch />
        </S.Button>
      </form>
    </S.Container>
  );
};

export default SearchBar;
