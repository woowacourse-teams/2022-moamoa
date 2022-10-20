import styled from '@emotion/styled';

import { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useGetInfiniteCommunityComments } from '@api/community/comments';

import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

import Divider from '@components/@shared/divider/Divider';
import InfiniteScroll from '@components/@shared/infinite-scroll/InfiniteScroll';

import CommentForm from '@study-room-page/tabs/notice-tab-panel/components/comment-form/CommentForm';
import CommentList from '@study-room-page/tabs/notice-tab-panel/components/comment-list/CommentList';

type CommentSectionProps = {
  studyId: StudyId;
  articleId: ArticleId;
};
const CommentSection: React.FC<CommentSectionProps> = ({ studyId, articleId }) => {
  const { userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId }); // @TODO: 꼭 객체로 받아야 하는가?
  const { isFetching, isError, isSuccess, data, refetch, fetchNextPage } = useGetInfiniteCommunityComments({
    studyId,
    articleId,
    size: 9999, // @TODO: backend쪽에 pagination이 구현이 안되었기 때문에 우선 많이 불러온다
  });

  const handlePostSuccess = () => {
    alert('댓글을 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('댓글 입력에 오류가 발생했습니다');
  };

  const handleContentLoaded = () => {
    fetchNextPage();
  };

  return (
    <Self>
      {isOwnerOrMember && (
        <>
          <CommentForm
            author={userInfo}
            studyId={studyId}
            articleId={articleId}
            onPostSuccess={handlePostSuccess}
            onPostError={handlePostError}
          />
        </>
      )}
      <Divider space={isOwnerOrMember ? '30px' : '8px'} />
      {(() => {
        if (isError) return <Error />;
        if (isSuccess) {
          const comments = data?.pages.reduce<Array<CommunityComment>>((acc, cur) => [...acc, ...cur.comments], []);
          if (comments.length === 0) return <NoComments />;
          return (
            <InfiniteScroll isContentLoading={isFetching} onContentLoad={handleContentLoaded}>
              <CommentList studyId={studyId} articleId={articleId} comments={comments} />
            </InfiniteScroll>
          );
        }
      })()}
    </Self>
  );
};

export default CommentSection;

const Self = styled.div``;

const Error = () => <div>에러가 발생했습니다</div>;

const NoComments = () => <div>댓글이 없습니다</div>;
