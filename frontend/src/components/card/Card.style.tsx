import { css } from '@emotion/react';
import styled from '@emotion/styled';

import Image from '@components/image/Image';

export const Card = styled.div`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;

    height: 350px;
    overflow: hidden;

    border: 3px solid ${theme.colors.primary.base};
    border-radius: 25px;
    box-shadow: 8px 8px 0 0 ${theme.colors.secondary.dark};

    :hover {
      opacity: 0.9;
    }
  `}
`;

export const ImageContainer = styled.div`
  flex-grow: 1;

  overflow: hidden;
`;

export const CardImage = styled(Image)`
  width: 100%;
  height: 100%;

  object-fit: cover;
  object-position: center;
`;

export const Content = styled.div`
  padding: 8px 8px 12px;

  background-color: ${({ theme }) => theme.colors.secondary.light};
`;

const onelineEllipsis = css`
  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  word-break: break-all;
`;

export const Title = styled.h4`
  margin-bottom: 20px;

  font-size: 24px;
  font-weight: 700;
  line-height: 24px;
  ${onelineEllipsis};
`;

export const Excerpt = styled.p`
  width: 100%;
  margin-bottom: 20px;

  font-size: 20px;
  line-height: 20px;
  ${onelineEllipsis};
`;

export const Extra = styled.div`
  display: flex;
  justify-content: flex-end;
`;
