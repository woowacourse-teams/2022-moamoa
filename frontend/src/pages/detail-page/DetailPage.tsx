import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';
import { useState } from 'react';
import { useParams } from 'react-router-dom';

import StudyWideFloatBox from '@pages/detail-page/components/study-wide-float-box/StudyWideFloatBox';

import Divider from '@components/divider/Divider';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@detail-page/DetailPage.style';
import Head from '@detail-page/components/head/Head';
import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import StudyMemberList from '@detail-page/components/study-member-list/StudyMemberList';
import useFetchDetail from '@detail-page/hooks/useFetchDetail';
import useFetchStudyReviews from '@detail-page/hooks/useFetchStudyReviews';

import StudyReviewList from './components/study-review-list/StudyReviewList';

const DetailPage = () => {
  const { studyId } = useParams() as { studyId: string };

  const studyDetailQueryResult = useFetchDetail(Number(studyId));

  const [isVisibleLoadAllReviewsBtn, setIsVisibleLoadAllReviewsBtn] = useState<boolean>(true);
  const shouldLoadAll = !isVisibleLoadAllReviewsBtn;
  const size = shouldLoadAll ? undefined : DEFAULT_LOAD_STUDY_REVIEW_COUNT;
  const studyReviewsQueryResult = useFetchStudyReviews(Number(studyId), size);

  const handleClickLoadAllReviewsBtn = () => {
    setIsVisibleLoadAllReviewsBtn(prev => !prev);
  };

  const handleRegisterBtnClick = (studyId: number) => () => {
    alert('스터디에 가입했습니다!');
  };

  if (studyDetailQueryResult.isFetching) return <div>Loading...</div>;

  if (!studyDetailQueryResult.data) return <div>No Data...</div>;

  if (studyReviewsQueryResult.isFetching) return <div>Loading...</div>;

  if (!studyReviewsQueryResult.data) return <div>No Data...</div>;

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

  const { totalResults: reviewCount, reviews } = studyReviewsQueryResult.data;

  return (
    <Wrapper>
      <Head
        title={title}
        status={status}
        excerpt={excerpt}
        startDate={startDate}
        endDate={endDate}
        tags={tags}
        reviewCount={reviewCount}
      />
      <Divider space={2} />
      <S.Main>
        <S.MainDescription>
          <S.MarkDownContainer>
            <MarkdownRender markdownContent={description} />
          </S.MarkDownContainer>
          <Divider space={2} />
          <StudyMemberList members={members} />
        </S.MainDescription>
        <S.FloatButtonContainer>
          <S.StickyContainer>
            <StudyFloatBox
              studyId={id}
              owner={owner}
              currentMemberCount={currentMemberCount}
              maxMemberCount={maxMemberCount}
              deadline={deadline}
              handleRegisterBtnClick={handleRegisterBtnClick}
            />
          </S.StickyContainer>
        </S.FloatButtonContainer>
      </S.Main>
      <Divider space={2} />
      <StudyReviewList
        reviews={reviews}
        reviewCount={reviewCount}
        showAll={isVisibleLoadAllReviewsBtn}
        handleMoreBtnClick={handleClickLoadAllReviewsBtn}
      />
      <S.FixedBottomContainer>
        <StudyWideFloatBox
          studyId={id}
          owner={owner}
          currentMemberCount={currentMemberCount}
          maxMemberCount={maxMemberCount}
          deadline={deadline}
          handleRegisterBtnClick={handleRegisterBtnClick}
        />
      </S.FixedBottomContainer>
    </Wrapper>
  );
};

export default DetailPage;
