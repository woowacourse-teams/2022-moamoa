import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@styles/responsive';

import { IconButton } from '@shared/button';
import Form from '@shared/form/Form';
import { SearchIcon } from '@shared/icons';

export type SearchBarProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>, inputName: string) => void;
  inputName?: string;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSubmit: handleSubmit, inputName = 'keyword' }) => {
  return (
    <Self>
      <Inner>
        <Form onSubmit={e => handleSubmit(e, inputName)}>
          <Input name={inputName} maxLength={20} placeholder="스터디 제목 검색" />
          <SearchButton />
        </Form>
      </Inner>
    </Self>
  );
};

export default SearchBar;

const Self = styled.div`
  position: relative;
`;

const Inner = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;

  ${mqDown('lg')} {
    position: static;
    left: 0;
    top: 0;
    transform: none;
  }
`;

const Input = styled.input`
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

const SearchButton: React.FC = () => (
  <div
    css={css`
      position: absolute;
      top: 10px;
      right: 14px;
    `}
  >
    <IconButton
      ariaLabel="검색하기"
      variant="secondary"
      custom={{ width: 'fit-content', height: 'fit-content', fontSize: 'xl' }}
    >
      <SearchIcon />
    </IconButton>
  </div>
);
