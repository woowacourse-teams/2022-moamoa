import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@shared/button';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';

import ArticleList from '@notice-tab/components/article-list/NoticeArticleList';

type NoticeArticleListPageProps = {
  studyId: StudyId;
};
// @TODO: 공지사항임이 드러나는 이름으로 변경하기 || 공통 컴포넌트로 분리
const NoticeArticleListPage: React.FC<NoticeArticleListPageProps> = ({ studyId }) => {
  const { isOwner } = useUserRole({ studyId }); // @TODO: 객체로 받을 필요가 있나?
  return (
    <>
      {isOwner && (
        <Flex justifyContent="flex-end">
          <PublishPageLink />
        </Flex>
      )}
      <Divider space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};

export default NoticeArticleListPage;

const PublishPageLink: React.FC = () => (
  <Link to={PATH.NOTICE_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);
