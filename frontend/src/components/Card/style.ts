import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Card = styled.div`
  ${({ theme }) => css`
    max-width: 15.625rem;
    width: 100%;
    overflow: hidden;

    border: 0.19rem solid ${theme.colors.primary.base};
    border-radius: 1.56rem;
    box-shadow: 0.5rem 0.5rem 0 0 ${theme.colors.secondary.dark};

    :hover {
      opacity: 0.9;
    }
  `}
`;

export const Image = styled.img`
  width: 100%;
  height: 9rem;

  object-fit: cover;
  object-position: center;
`;

export const Contents = styled.div`
  padding: 0.5rem 0.5rem 0.7rem;
`;

export const Title = styled.h4`
  padding-bottom: 1.25rem;

  font-size: 1.5rem;
  font-weight: 700;
`;

export const Description = styled.p`
  padding-bottom: 1.25rem;

  font-size: 1.25rem;
  word-break: break-word;
`;

export const Extra = styled.div`
  display: flex;
  justify-content: flex-end;
`;
