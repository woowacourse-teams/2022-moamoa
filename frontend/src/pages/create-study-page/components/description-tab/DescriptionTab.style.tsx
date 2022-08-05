import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const DescriptionTab = styled.div`
  ${({ theme }) => css`
    border-radius: 6px;
    border: 1px solid ${theme.colors.secondary.dark};
    overflow: hidden;
    .tab-list-container {
      padding-left: 10px;
      padding-top: 10px;
      background-color: ${theme.colors.secondary.light};
      border-bottom: 1px solid ${theme.colors.secondary.dark};
      .tab-list {
        display: flex;
        .tab {
          .tab-item-button {
            border: none;
            line-height: 24px;
            font-weight: 600;
            padding: 8px 16px;
            transition: color 0.2s cubic-bezier(0.3, 0, 0.5, 1);
            background-color: inherit;
            color: ${theme.colors.secondary.base};

            margin-bottom: -1px;

            &.active {
              background-color: ${theme.colors.white};
              color: ${theme.colors.black};
              border-top-left-radius: 12px;
              border-top-right-radius: 12px;
              border: 1px solid ${theme.colors.secondary.dark};
              border-bottom: none;
            }
          }
        }
      }
    }
    .tab-panels-container {
      padding: 10px;
      background-color: ${theme.colors.white};
      height: 50vh;
      .tab-panels {
        min-height: 100%;
        height: 100%;
        .tab-panel {
          height: 100%;
          display: none;

          &.active {
            display: block;
          }

          .tab-content {
            height: 100%;

            textarea {
              display: block;

              width: 100%;
              height: 100%;
              padding: 12px;

              border-radius: 6px;
              background-color: ${theme.colors.secondary.light};
              border: 1px solid ${theme.colors.secondary.dark};
              font-size: 16px;

              &:focus {
                background-color: ${theme.colors.white};
              }
            }
          }
        }
      }
    }
  `}
`;
