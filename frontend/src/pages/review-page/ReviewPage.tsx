import * as S from '@review-page/ReviewPage.style';
import { useParams } from 'react-router-dom';

import useFetchStudyReviews from '@pages/detail-page/hooks/useFetchStudyReviews';
import ReviewForm from '@pages/review-page/components/reivew-form/ReviewForm';
import ReviewComment from '@pages/review-page/components/review-comment/ReviewComment';

import Wrapper from '@components/wrapper/Wrapper';

const ReviewPage: React.FC = () => {
  const params = useParams() as { studyId: string };
  const studyId = parseInt(params.studyId, 10);
  const { data, isFetching, refetch } = useFetchStudyReviews(studyId);

  const hasReviews = !!data?.reviews;

  const handlePostSuccess = () => {
    alert('댓글을 성공적으로 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('댓글 입력에 오류가 발생했습니다');
  };

  const renderReviewList = () => {
    if (isFetching) {
      return <div>불러오는중...</div>;
    }

    if (!hasReviews) {
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
            />
          </li>
        ))}
      </S.ReviewList>
    );
  };

  return (
    <Wrapper>
      <S.ReviewPage>
        <div className="top">
          <ReviewForm studyId={studyId} onPostSuccess={handlePostSuccess} onPostError={handlePostError} />
        </div>
        <div className="bottom">{renderReviewList()}</div>
      </S.ReviewPage>
    </Wrapper>
  );
};

export default ReviewPage;
