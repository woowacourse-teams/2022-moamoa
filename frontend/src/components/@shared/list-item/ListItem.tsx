import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

import Flex from '@shared/flex/Flex';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type ListItemProps = {
  title: string;
  userInfo?: {
    src: string;
    username: string;
  };
  subInfo: string;
  custom?: CustomCSS<'padding'>;
};

const ListItem: React.FC<ListItemProps> = ({ title, userInfo, subInfo, custom }) => {
  return (
    <Self css={resolveCustomCSS(custom)}>
      <Flex alignItems="center">
        <Flex.Item>
          <Title>{title}</Title>
        </Flex.Item>
        {userInfo ? (
          <UserInfoItem size="md" src={userInfo.src} name={userInfo.username}>
            <UserInfoItem.Heading>{userInfo.username}</UserInfoItem.Heading>
            <UserInfoItem.Content>{subInfo}</UserInfoItem.Content>
          </UserInfoItem>
        ) : (
          <SubInfo>{subInfo}</SubInfo>
        )}
      </Flex>
    </Self>
  );
};

export default ListItem;

// 외부 커스텀을 위해 li 대신 div 사용: orbit 및 react-spectrum 참고
const Self = styled.div``;

type TitleProps = {
  children: string;
};
const Title: React.FC<TitleProps> = ({ children }) => {
  const theme = useTheme();

  return (
    <span
      css={css`
        font-size: ${theme.fontSize.lg};
        ${nLineEllipsis(1)}
      `}
    >
      {children}
    </span>
  );
};

type SubInfoProps = {
  children: string;
};
const SubInfo: React.FC<SubInfoProps> = ({ children }) => {
  const theme = useTheme();

  return (
    <p
      css={css`
        min-width: fit-content;

        color: ${theme.colors.secondary.dark};
        font-size: ${theme.fontSize.sm};
      `}
    >
      {children}
    </p>
  );
};
