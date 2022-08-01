import { useParams } from 'react-router-dom';

import { Member } from '@custom-types';

import useFetchStudyReviews from '@pages/detail-page/hooks/useFetchStudyReviews';
import ReviewForm from '@pages/review-page/components/reivew-form/ReviewForm';
import ReviewComment from '@pages/review-page/components/review-comment/ReviewComment';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@review-page/ReviewPage.style';

const ReviewPage: React.FC = () => {
  const params = useParams() as { studyId: string };
  const studyId = parseInt(params.studyId, 10);
  const { data, isFetching, refetch } = useFetchStudyReviews(studyId);
  const author: Member = {
    id: 1,
    profileUrl: 'https://github.com/airman5573',
    imageUrl: 'https://cdn.mos.cms.futurecdn.net/yCPyoZDQBBcXikqxkeW2jJ-1200-80.jpg',
    username: 'earth',
  };

  const hasReviews = !!data?.reviews;

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
        {
          <ReviewForm
            author={author}
            studyId={studyId}
            onPostSuccess={handlePostSuccess}
            onPostError={handlePostError}
          />
        }
        <Divider />
        {renderReviewList()}
      </S.ReviewPage>
    </Wrapper>
  );
};

export default ReviewPage;
