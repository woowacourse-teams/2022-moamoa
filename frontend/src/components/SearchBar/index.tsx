import { FiSearch } from 'react-icons/fi';

import * as S from './style';

const SearchBar: React.FC = () => {
  return (
    <S.Container>
      <S.Input maxLength={20} placeholder="스터디 검색" />
      <S.Button>
        <FiSearch />
      </S.Button>
    </S.Container>
  );
};

export default SearchBar;
