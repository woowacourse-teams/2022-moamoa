import styled from '@emotion/styled';

import { mqDown } from '@utils/media-query';

import { DescriptionTab } from './components/description-tab/DescriptionTab.style';

const sidebarWidth = 280;
const mainGabSidebar = 40;

export const CreateStudyPage = styled.div`
  input,
  textarea,
  select {
    box-shadow: 0 0 0 transparent;
    border-radius: 4px;
    border: 1px solid #d0d7de;
    background-color: #fff;
    padding: 4px 8px;
  }

  select {
    line-height: 2;
    border-color: #d0d7de;
    box-shadow: none;
    border-radius: 3px;
    padding: 0 24px 0 8px;
    min-height: 30px;
    max-width: 25rem;
    -webkit-appearance: none;
    background: #fff
      url(data:image/svg+xml;charset=US-ASCII,%3Csvg%20width%3D%2220%22%20height%3D%2220%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20d%3D%22M5%206l5%205%205-5%202%201-7%207-7-7%202-1z%22%20fill%3D%22%23555%22%2F%3E%3C%2Fsvg%3E)
      no-repeat right 5px top 55%;
    background-size: 16px 16px;
    cursor: pointer;
    vertical-align: middle;
  }

  textarea {
    overflow: auto;
    padding: 8px 10px;
    resize: vertical;
  }

  input.invalid,
  textarea.invalid {
    border: 2px solid red !important;
  }

  & > form {
    & > .title {
      margin-bottom: 20px;
    }
    & > .inner {
      display: flex;
      column-gap: 40px;
      & > .main {
        flex-grow: 1;
        max-width: calc(100% - ${mainGabSidebar}px - ${sidebarWidth}px);
        .title-input {
          display: block;
          width: 100%;
          font-size: 24px;
          line-height: 24px;

          border-radius: 4px;
          border: 1px solid #8c8f94;
          background-color: #fff;
          color: #2c3338;

          padding: 6px 12px;

          margin-bottom: 20px;
        }
        ${DescriptionTab} {
          margin-bottom: 15px;
        }
      }
      & > .sidebar {
        min-width: 280px;
      }

      ${mqDown('md')} {
        flex-direction: column;
        column-gap: 0;
        & > .main {
          max-width: 100%;
          margin-bottom: 15px;
        }
        & > .sidebar {
          min-width: 100%;
        }
      }
    }
  }
`;