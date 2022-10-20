import React from 'react';
import { useParams } from 'react-router-dom';

import { useTheme } from '@emotion/react';

import { StudyReview } from '@custom-types';

import { useGetInfiniteStudyReviews } from '@api/reviews';

import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

import Divider from '@shared/divider/Divider';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import { TextButton } from '@components/@shared/button';
import InfiniteScroll from '@components/@shared/infinite-scroll/InfiniteScroll';

import ReviewList from '@study-room-page/tabs/review-tab-panel/components/review-list/ReviewList';

import ReviewForm from '@review-tab/components/reivew-form/ReviewForm';

const ReviewTabPanel: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId });

  const { data, isFetching, refetch, isError, isSuccess, fetchNextPage } = useGetInfiniteStudyReviews({ studyId });

  const handlePostSuccess = () => {
    alert('리뷰를 추가했습니다');
    refetch();
  };

  const handlePostError = () => {
    alert('리뷰 입력에 오류가 발생했습니다');
  };

  const handleContentLoaded = () => {
    fetchNextPage();
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
        if (isError) return <Error />;
        if (isSuccess) {
          const reviews = data?.pages.reduce<Array<StudyReview>>((acc, cur) => [...acc, ...cur.reviews], []);
          if (reviews.length === 0) return <NoReview />;
          return (
            <InfiniteScroll isContentLoading={isFetching} onContentLoad={handleContentLoaded}>
              <ReviewList reviews={reviews} studyId={studyId} userInfo={userInfo} />
            </InfiniteScroll>
          );
        }
      })()}
    </PageWrapper>
  );
};

export default ReviewTabPanel;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoReview = () => <div>리뷰가 없습니다</div>;

type LoadMoreButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const LoadMoreButton: React.FC<LoadMoreButtonProps> = ({ onClick: handleClick }) => {
  const theme = useTheme();
  return (
    <TextButton
      variant="secondary"
      fluid
      onClick={handleClick}
      custom={{ backgroundColor: theme.colors.secondary.base }}
    >
      더보기
    </TextButton>
  );
};
