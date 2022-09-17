import tw from '@utils/tw';

import * as S from '@layout/header/components/search-bar/SearchBar.style';

import { IconButton } from '@components/button';
import Form from '@components/form/Form';
import { SearchIcon } from '@components/icons';

export type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit, inputName = 'keyword' }) => {
  return (
    <div css={tw`relative`}>
      <Form onSubmit={e => onSubmit(e, inputName)}>
        <S.Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
        <div css={tw`absolute top-10 right-14`}>
          <IconButton ariaLabel="검색하기" width="fit-content" height="fit-content" variant="secondary" fontSize="xl">
            <SearchIcon />
          </IconButton>
        </div>
      </Form>
    </div>
  );
};

export default SearchBar;
