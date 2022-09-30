import { css } from '@emotion/react';
import styled from '@emotion/styled';

import tw from '@utils/tw';

import { mqDown } from '@styles/responsive';

import { IconButton } from '@components/button';
import Form from '@components/form/Form';
import { SearchIcon } from '@components/icons';

export type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit, inputName = 'keyword' }) => {
  return (
    <Self>
      <Form onSubmit={e => onSubmit(e, inputName)}>
        <Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
        <div css={tw`absolute top-10 right-14`}>
          <IconButton ariaLabel="검색하기" width="fit-content" height="fit-content" variant="secondary" fontSize="xl">
            <SearchIcon />
          </IconButton>
        </div>
      </Form>
    </Self>
  );
};

export const Self = styled.div`
  position: relative;
`;

export const Input = styled.input`
  ${({ theme }) => css`
    width: 100%;
    padding: 8px 40px;
    overflow: hidden;

    font-size: ${theme.fontSize.lg};
    border-radius: ${theme.radius.lg};
    border: 3px solid ${theme.colors.primary.base};

    text-align: center;

    &:focus {
      border-color: ${theme.colors.primary.light};
      & + button {
        svg {
          stroke: ${theme.colors.primary.light};
        }
      }
    }

    ${mqDown('md')} {
      font-size: ${theme.fontSize.lg};
    }
    ${mqDown('sm')} {
      font-size: ${theme.fontSize.md};
    }
  `}
`;

export default SearchBar;
