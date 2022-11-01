import { useParams } from 'react-router-dom';

import { useGetStudyReviews } from '@api/reviews';

import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

import Divider from '@shared/divider/Divider';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ReviewForm from '@review-tab/components/reivew-form/ReviewForm';
import ReviewList from '@review-tab/components/review-list/ReviewList';

const ReviewTabPanel: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId });

  const { data, isFetching, refetch, isError, isSuccess } = useGetStudyReviews({
    studyId,
  });

  const handlePostSuccess = () => {
    alert('리뷰를 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('리뷰 입력에 오류가 발생했습니다');
  };

  return (
    <PageWrapper>
      {isOwnerOrMember && (
        <>
          <ReviewForm
            author={userInfo}
            studyId={studyId}
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
          const { reviews } = data;
          if (reviews.length === 0) return <NoReview />;
          return <ReviewList reviews={reviews} studyId={studyId} userInfo={userInfo} />;
        }
      })()}
    </PageWrapper>
  );
};

export default ReviewTabPanel;

const Loading = () => <div>Loaindg...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoReview = () => <div>리뷰가 없습니다</div>;
