import styled from '@emotion/styled';

export const StudyCardContainer = styled.div`
  position: relative;
  height: 280px;

  transition: transform 0.2s ease;

  :hover {
    opacity: 0.9;
    transform: translate3d(0, -5px, 0);
  }
`;
