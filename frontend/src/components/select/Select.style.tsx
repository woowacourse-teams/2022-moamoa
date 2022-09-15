import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type SelectProps } from '@components/select/Select';

type StyledSelectProps = Required<Pick<SelectProps, 'fluid'>>;

export const Select = styled.select<StyledSelectProps>`
  ${({ theme, fluid }) => css`
    width: ${fluid ? '100%' : 'auto'};
    min-height: 30px;
    max-width: 25rem;
    padding: 4px 8px;

    border-radius: ${theme.radius.sm};
    border: 1px solid ${theme.colors.secondary.base};
    background-color: ${theme.colors.white};
    background-size: 16px 16px;
    outline: none;
    line-height: 2;
    vertical-align: middle;
    box-shadow: none;

    -webkit-appearance: none;
      url(data:image/svg+xml;charset=US-ASCII,%3Csvg%20width%3D%2220%22%20height%3D%2220%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20d%3D%22M5%206l5%205%205-5%202%201-7%207-7-7%202-1z%22%20fill%3D%22%23555%22%2F%3E%3C%2Fsvg%3E)
      no-repeat right 5px top 55%;

    &:focus {
      border: 1px solid ${theme.colors.primary.light};
    }

    &:disabled {
      color: ${theme.colors.secondary.base};
      border: 1px solid ${theme.colors.secondary.base};
      background-color: ${theme.colors.secondary.light};
    }
  `}
`;
