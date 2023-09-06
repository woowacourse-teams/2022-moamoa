import type { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useGetInfiniteNoticeComments } from '@api/notice/comments';

import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

import Divider from '@shared/divider/Divider';
import InfiniteScroll from '@shared/infinite-scroll/InfiniteScroll';

import CommentForm from '@notice-tab/components/comment-form/NoticeCommentForm';
import CommentList from '@notice-tab/components/comment-list/NoticeCommentList';

type NoticeCommentSectionProps = {
  studyId: StudyId;
  articleId: ArticleId;
};
const NoticeCommentSection: React.FC<NoticeCommentSectionProps> = ({ studyId, articleId }) => {
  const { userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId }); // @TODO: 꼭 객체로 받아야 하는가?
  const { isFetching, isError, isSuccess, data, refetch, fetchNextPage } = useGetInfiniteNoticeComments({
    studyId,
    articleId,
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
    <div>
      {isOwnerOrMember && (
        <>
          {/* {@TODO: Divider를 밖으로 빼내는 방법을 생각해 보자} */}
          <Divider space="16px" />
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
    </div>
  );
};

export default NoticeCommentSection;

const Error = () => <div>에러가 발생했습니다</div>;

const NoComments = () => <div>댓글이 없습니다</div>;