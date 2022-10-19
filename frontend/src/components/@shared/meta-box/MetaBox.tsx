import { type ReactNode } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

const MetaBox = ({ children }: { children: ReactNode }) => {
  return <MetaBoxSelf>{children}</MetaBoxSelf>;
};

const MetaBoxTitle = ({ children }: { children: ReactNode }) => {
  return <MetaBoxTitleSelf>{children}</MetaBoxTitleSelf>;
};

const MetaBoxContent = ({ children }: { children: ReactNode }) => {
  return <MetaBoxContentSelf>{children}</MetaBoxContentSelf>;
};

const MetaBoxSelf = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.white};
    min-width: 255px;
    border: 1px solid ${theme.colors.secondary.base};
    border-radius: ${theme.radius.sm};
  `}
`;

const MetaBoxTitleSelf = styled.h2`
  ${({ theme }) => css`
    padding: 8px 12px;

    font-size: ${theme.fontSize.md};
    font-weight: ${theme.fontWeight.bold};

    border-bottom: 1px solid ${theme.colors.secondary.base};
  `}
`;

const MetaBoxContentSelf = styled.div`
  padding: 8px 12px;
`;

export default Object.assign(MetaBox, {
  Title: MetaBoxTitle,
  Content: MetaBoxContent,
});
