import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils/media-query';

export const Main = styled.div`
  display: flex;
  column-gap: 40px;
`;

export const MainDescription = styled.section`
  width: 100%;
  min-width: 0;
`;

export const FloatButtonContainer = styled.div`
  min-width: 30%;

  ${mqDown('lg')} {
    display: none;
  }
`;

export const StickyContainer = styled.div`
  position: sticky;
  top: 150px;
  padding-bottom: 20px;
`;

export const FixedBottomContainer = styled.div`
  display: none;

  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: white;

  padding: 16px 24px;

  background-color: rgb(255, 255, 255);
  border-top: 1px solid rgb(221, 221, 221);

  ${mqDown('lg')} {
    display: block;
  }
`;

export const MarkDownContainer = styled.section`
  ${({ theme }) => css`
    padding: 16px;

    background-color: ${theme.colors.white};
    border-radius: 15px;
  `}
`;
