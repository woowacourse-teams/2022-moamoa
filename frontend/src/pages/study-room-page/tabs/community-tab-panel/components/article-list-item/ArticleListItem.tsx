import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';

import type { CommunityArticle } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import Flex from '@shared/flex/Flex';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type ArticleListItemProps = Pick<CommunityArticle, 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  const theme = useTheme();
  return (
    <Self>
      <Flex alignItems="center">
        <Flex.Item flexGrow={1}>
          <Title theme={theme} title={title} />
        </Flex.Item>
        <UserInfoItem size="md" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(createdDate)}</UserInfoItem.Content>
        </UserInfoItem>
      </Flex>
    </Self>
  );
};

const Self = styled.div`
  ${applyHoverTransitionStyle()}
`;

type TitleProps = {
  theme: Theme;
  title: string;
};
const Title: React.FC<TitleProps> = ({ theme, title }) => (
  <span
    css={css`
      font-size: ${theme.fontSize.lg};
    `}
  >
    {title}
  </span>
);

export default ArticleListItem;
