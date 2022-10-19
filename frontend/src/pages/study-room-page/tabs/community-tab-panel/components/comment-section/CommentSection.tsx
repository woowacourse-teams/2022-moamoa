import styled from '@emotion/styled';

import { ArticleId, StudyId } from '@custom-types';

import { useGetCommunityComments } from '@api/community-comments';

import { FormProvider, useForm } from '@hooks/useForm';
import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

import Divider from '@components/@shared/divider/Divider';

import CommentForm from '@study-room-page/tabs/community-tab-panel/components/comment-form/CommentForm';
import CommentList from '@study-room-page/tabs/community-tab-panel/components/comment-list/CommentList';

type CommentSectionProps = {
  studyId: StudyId;
  articleId: ArticleId;
};
const CommentSection: React.FC<CommentSectionProps> = ({ studyId, articleId }) => {
  const { userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId }); // @TODO: 꼭 객체로 받아야 하는가?
  const { isFetching, isError, isSuccess, data, refetch } = useGetCommunityComments({ studyId, articleId });

  const handlePostSuccess = () => {
    alert('댓글을 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('댓글 입력에 오류가 발생했습니다');
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
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isSuccess) {
          const { comments } = data;
          if (comments.length === 0) return <NoComments />;
          return <CommentList studyId={studyId} articleId={articleId} comments={comments} />;
        }
      })()}
    </Self>
  );
};

export default CommentSection;

const Self = styled.div``;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoComments = () => <div>댓글이 없습니다</div>;
