import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import Avatar, { AVATAR_SIZE } from '@shared/avatar/Avatar';
import Flex from '@shared/flex/Flex';

export type UserInfoItemProps = {
  children: React.ReactNode;
  src: string;
  name: string;
  size: keyof typeof AVATAR_SIZE;
};

export type UserInfoItemHeadingProps = {
  children: React.ReactNode;
};

export type UserInfoItemContentProps = {
  children: React.ReactNode;
};

const UserInfoItem: React.FC<UserInfoItemProps> = ({ children, src, name, size }) => {
  return (
    <ItemSelf>
      <Flex columnGap="8px">
        <Avatar src={src} name={name} size={size} />
        <Flex.Item flexGrow={1}>
          <Flex flexDirection="column" columnGap="2px">
            {children}
          </Flex>
        </Flex.Item>
      </Flex>
    </ItemSelf>
  );
};

const UserInfoItemHeading: React.FC<UserInfoItemHeadingProps> = ({ children }) => {
  return <ItemHeadingSelf>{children}</ItemHeadingSelf>;
};

const UserInfoItemContent: React.FC<UserInfoItemContentProps> = ({ children }) => {
  return <ItemContentSelf>{children}</ItemContentSelf>;
};

export const ItemSelf = styled.div`
  ${({ theme }) => css`
    padding: 8px;

    background: transparent;
    border-radius: ${theme.radius.sm};
  `}
`;

export const ItemHeadingSelf = styled.p`
  ${({ theme }) => css`
    font-weight: ${theme.fontWeight.bold};
  `}

  ${nLineEllipsis(1)};
`;

export const ItemContentSelf = styled.p`
  ${({ theme }) => css`
    color: ${theme.colors.secondary.dark};
    font-size: ${theme.fontSize.sm};
  `}

  ${nLineEllipsis(2)};
`;

export default Object.assign(UserInfoItem, {
  Heading: UserInfoItemHeading,
  Content: UserInfoItemContent,
});
