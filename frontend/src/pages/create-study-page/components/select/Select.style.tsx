import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { Input } from '@components/input/Input.style';

export const Select = styled(Input.withComponent('select'))`
  ${({ theme }) => css`
    line-height: 2;
    border-color: ${theme.colors.secondary.base};
    box-shadow: none;
    border-radius: 3px;
    padding: 0 24px 0 8px;
    min-height: 30px;
    max-width: 25rem;
    -webkit-appearance: none;
    background: ${theme.colors.white};
      url(data:image/svg+xml;charset=US-ASCII,%3Csvg%20width%3D%2220%22%20height%3D%2220%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20d%3D%22M5%206l5%205%205-5%202%201-7%207-7-7%202-1z%22%20fill%3D%22%23555%22%2F%3E%3C%2Fsvg%3E)
      no-repeat right 5px top 55%;
    background-size: 16px 16px;
    vertical-align: middle;
  `}
`;
