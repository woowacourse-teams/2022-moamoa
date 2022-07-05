import styled from '@emotion/styled';

export const Container = styled.div`
  position: relative;
  width: 400px;
`;

// TODO : css 함수 사용하기(코드 자동 완성 및 일관성)
export const Input = styled.input`
  ${({ theme }) => `
    width: 100%;
    padding: 8px 40px;
    overflow: hidden;

    font-size: 20px;
    border-radius: 20px;
    border: 3px solid ${theme.colors.primary.base};

    text-align: center;

    &:focus {
      border-color: ${theme.colors.primary.light};
      &+button {
        svg {
          stroke: ${theme.colors.primary.light};
        }
      }
    }
  `}
`;

export const Button = styled.button`
  ${({ theme }) => `
    display: flex;
    align-items: center;

    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;

    padding-right: 20px;
    font-size: 20px;

    background: transparent;
    border: none;

    &:hover,
    &:active {
      svg {
        stroke: ${theme.colors.primary.light};
      }
    }
  `}
`;
