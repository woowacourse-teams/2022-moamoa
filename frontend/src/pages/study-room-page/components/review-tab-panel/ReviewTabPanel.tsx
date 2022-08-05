import { useEffect } from 'react';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import useGetStudyReviews from '@detail-page/hooks/useGetStudyReviews';

import * as S from '@study-room-page/components/review-tab-panel/ReviewTabPanel.style';
import ReviewForm from '@study-room-page/components/review-tab-panel/components/reivew-form/ReviewForm';
import ReviewComment from '@study-room-page/components/review-tab-panel/components/review-comment/ReviewComment';

export type ReviewTabPanelProps = {
  studyId: number;
};

const ReviewTabPanel: React.FC<ReviewTabPanelProps> = ({ studyId }) => {
  const { data, isFetching, refetch, isError, isSuccess } = useGetStudyReviews(studyId);
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
      <S.ReviewList>
        {data.reviews.map(review => (
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
        ))}
      </S.ReviewList>
    );
  };

  return (
    <Wrapper>
      <S.ReviewTabPanel>
        {
          <ReviewForm
            author={userInfo}
            studyId={studyId}
            onPostSuccess={handlePostSuccess}
            onPostError={handlePostError}
          />
        }
        <Divider />
        {renderReviewList()}
      </S.ReviewTabPanel>
    </Wrapper>
  );
};

export default ReviewTabPanel;
