import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { useGetStudyReviews } from '@api/reviews';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import ReviewForm from '@review-tab/components/reivew-form/ReviewForm';
import ReviewComment from '@review-tab/components/review-comment/ReviewComment';

const ReviewTabPanel: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { data, isFetching, refetch, isError, isSuccess } = useGetStudyReviews({ studyId });
  const { userInfo, fetchUserInfo } = useUserInfo();

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

  const renderReviewList = () => {
    if (isFetching) {
      return <div>불러오는중...</div>;
    }

    if (isError || !isSuccess) {
      return <div>에러가 발생했습니다</div>;
    }

    if (data.reviews.length === 0) {
      return <div>첫 리뷰를 남겨주세요!</div>;
    }

    return (
      <>
        {data.reviews.map(review => (
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
      </>
    );
  };

  return (
    <Wrapper>
      <ReviewForm author={userInfo} studyId={studyId} onPostSuccess={handlePostSuccess} onPostError={handlePostError} />
      <Divider space="30px" />
      {renderReviewList()}
    </Wrapper>
  );
};

export default ReviewTabPanel;
