import { Link, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@shared/button';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';

import ArticleList from '@community-tab/components/article-list/CommunityArticleList';

// @TODO: 공지사항임이 드러나는 이름으로 변경하기 || 공통 컴포넌트로 분리

const CommunityArticleListPage: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { isOwnerOrMember } = useUserRole({ studyId });

  return (
    <>
      {isOwnerOrMember && (
        <Flex justifyContent="flex-end">
          <PublishPageLink />
        </Flex>
      )}
      <Divider space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};

export default CommunityArticleListPage;

const PublishPageLink: React.FC = () => (
  <Link to={PATH.COMMUNITY_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);
