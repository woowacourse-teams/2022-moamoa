import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';
import { useState } from 'react';
import { useParams } from 'react-router-dom';

import StudyWideFloatBox from '@pages/detail-page/components/study-wide-float-box/StudyWideFloatBox';

import MarkdownRender from '@components/MarkdownRender/MarkdownRender';
import Divider from '@components/divider/Divider';

import * as S from '@detail-page/DetailPage.style';
import Head from '@detail-page/components/head/Head';
import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import StudyMemberList from '@detail-page/components/study-member-list/StudyMemberList';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';
import useFetchDetail from '@detail-page/hooks/useFetchDetail';
import useFetchStudyReviews from '@detail-page/hooks/useFetchStudyReviews';

const DetailPage = () => {
  const { studyId } = useParams() as { studyId: string };
  const [isVisibleLoadAllReviewsBtn, setIsVisibleLoadAllReviewsBtn] = useState<boolean>(true);
  const shouldLoadAll = !isVisibleLoadAllReviewsBtn;
  const studyDetailQueryResult = useFetchDetail(studyId);
  const studyReviewsQueryResult = useFetchStudyReviews(studyId, DEFAULT_LOAD_STUDY_REVIEW_COUNT, shouldLoadAll);

  const handleClickRegisterBtn = () => {
    alert('스터디에 가입했습니다!');
  };

  const handleClickLoadAllReviewsBtn = () => {
    setIsVisibleLoadAllReviewsBtn(false);
  };

  if (studyDetailQueryResult.isFetching) return <div>Loading...</div>;

  if (!studyDetailQueryResult.data) return <div>No Data...</div>;

  const {
    id,
    title,
    excerpt,
    thumbnail,
    status,
    description,
    currentMemberCount,
    maxMemberCount,
    deadline,
    startDate,
    endDate,
    owner,
    members,
    tags,
  } = studyDetailQueryResult.data.study;

  const countOfReviews = studyReviewsQueryResult?.data?.totalResults;

  return (
    <div>
      <Head
        title={title}
        status={status}
        excerpt={excerpt}
        startDate={startDate}
        endDate={endDate}
        tags={tags}
        countOfReviews={countOfReviews}
      />
      <Divider space={2} />
      <S.Main>
        <div className="left">
          <MarkdownRender markdownContent={description} />
          <Divider space={2} />
          <StudyMemberList members={members} />
        </div>
        <div className="right">
          <div className="sticky">
            <StudyFloatBox
              studyId={id}
              owner={owner}
              currentMemberCount={currentMemberCount}
              maxMemberCount={maxMemberCount}
              deadline={deadline}
              onClickRegisterBtn={handleClickRegisterBtn}
            />
          </div>
        </div>
      </S.Main>
      <Divider space={2} />
      <S.ExtraInfo>
        <h1 className="title">
          {studyReviewsQueryResult.data ? `후기 ${studyReviewsQueryResult.data.totalResults}개` : 'loading...'}
        </h1>
        {studyReviewsQueryResult.data?.reviews ? (
          <>
            <ul className="review-list">
              {studyReviewsQueryResult.data.reviews.map(review => {
                return (
                  <li key={review.id} className="review">
                    <StudyReviewCard
                      profileImageUrl={review.member.profileImage}
                      username={review.member.username}
                      reviewDate={review.createdAt}
                      review={review.content}
                    />
                  </li>
                );
              })}
            </ul>
            <div className="load-all-reviews-button">
              {isVisibleLoadAllReviewsBtn && (
                <MoreButton
                  status={'fold'}
                  onClick={handleClickLoadAllReviewsBtn}
                  foldText="- 접기"
                  unfoldText="+ 더보기"
                />
              )}
            </div>
          </>
        ) : (
          <div>loading...</div>
        )}
      </S.ExtraInfo>
      <S.FixedBottomContainer>
        <StudyWideFloatBox
          studyId={id}
          owner={owner}
          currentMemberCount={currentMemberCount}
          maxMemberCount={maxMemberCount}
          deadline={deadline}
          onClickRegisterBtn={handleClickRegisterBtn}
        />
      </S.FixedBottomContainer>
    </div>
  );
};

export default DetailPage;
