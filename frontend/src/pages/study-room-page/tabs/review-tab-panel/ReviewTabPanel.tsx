import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import type { Member, StudyId, StudyReview } from '@custom-types';

import { useGetStudyReviews } from '@api/reviews';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@shared/divider/Divider';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ReviewForm from '@review-tab/components/reivew-form/ReviewForm';
import ReviewComment from '@review-tab/components/review-comment/ReviewComment';

const ReviewTabPanel: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { data, isFetching, refetch, isError, isSuccess } = useGetStudyReviews({ studyId });
  const { userInfo, fetchUserInfo } = useUserInfo();

  const reviews = data?.reviews;

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const handlePostSuccess = () => {
    alert('댓글을 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('댓글 입력에 오류가 발생했습니다');
  };

  return (
    <PageWrapper>
      <ReviewForm author={userInfo} studyId={studyId} onPostSuccess={handlePostSuccess} onPostError={handlePostError} />
      <Divider space="30px" />
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isSuccess && reviews) {
          if (reviews.length === 0) return <NoReview />;
          return <ReviewList reviews={reviews} studyId={studyId} userInfo={userInfo} />;
        }
      })()}
    </PageWrapper>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoReview = () => <div>첫 리뷰를 남겨주세요!</div>;

type ReviewListProps = {
  reviews: Array<StudyReview>;
  studyId: StudyId;
  userInfo: Member;
};
const ReviewList: React.FC<ReviewListProps> = ({ reviews, studyId, userInfo }) => (
  <ul>
    {reviews.map(review => (
      <>
        <li key={review.id}>
          <ReviewComment
            id={review.id}
            studyId={studyId}
            author={review.member}
            date={review.createdDate}
            content={review.content}
            isMyComment={userInfo.id === review.member.id}
          />
        </li>
        <Divider space="30px" />
      </>
    ))}
  </ul>
);

export default ReviewTabPanel;
