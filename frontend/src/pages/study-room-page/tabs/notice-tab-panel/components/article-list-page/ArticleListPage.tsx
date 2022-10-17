import { Link } from 'react-router-dom';

import { type Theme } from '@emotion/react';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@components/@shared/button';
import Divider from '@components/@shared/divider/Divider';
import Flex from '@components/@shared/flex/Flex';

import ArticleList from '@study-room-page/tabs/notice-tab-panel/components/article-list/ArticleList';

type ArticleListPageProps = {
  theme: Theme;
  studyId: StudyId;
};

const ArticleListPage: React.FC<ArticleListPageProps> = ({ theme, studyId }) => {
  const { isOwner } = useUserRole({ studyId }); // @TODO: 객체로 받을 필요가 있나?
  return (
    <>
      <Flex justifyContent="flex-end">{isOwner && <PublishPageLink />}</Flex>
      <Divider color={theme.colors.secondary.dark} space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};

export default ArticleListPage;

const PublishPageLink: React.FC = () => (
  <Link to={PATH.NOTICE_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);
