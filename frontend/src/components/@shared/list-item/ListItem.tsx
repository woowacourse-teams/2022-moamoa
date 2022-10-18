import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import Flex from '@shared/flex/Flex';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type ListItemProps = {
  title: string;
  src: string;
  username: string;
  subInfo: string;
};

const ListItem: React.FC<ListItemProps> = ({ title, src, username, subInfo }) => {
  return (
    <Self>
      <Flex alignItems="center">
        <Flex.Item flexGrow={1}>
          <Title>{title}</Title>
        </Flex.Item>
        <UserInfoItem size="md" src={src} name={username}>
          <UserInfoItem.Heading>{username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{subInfo}</UserInfoItem.Content>
        </UserInfoItem>
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
