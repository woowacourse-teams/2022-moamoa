import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { Input as OriginalInput } from '@components/input/Input.style';

export const Container = styled.div`
  position: relative;
`;

export const LetterCounterContainer = styled.div`
  position: absolute;
  right: 4px;
  bottom: 2px;

  display: flex;
  justify-content: flex-end;
`;

export const Label = styled.label`
  display: block;

  height: 0;
  width: 0;

  visibility: hidden;
`;

export const Input = styled(OriginalInput)`
  ${({ theme }) => css`
    display: block;
    width: 100%;
    font-size: 24px;
    line-height: 24px;

    border-radius: 4px;
    border: 1px solid ${theme.colors.secondary.dark};
    background-color: ${theme.colors.white};
    color: ${theme.colors.black};

    padding: 6px 12px;

    margin-bottom: 20px;
  `}
`;
