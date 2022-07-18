import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';
import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';

import { mqDown } from '@utils/media-query';

import Chip from '@components/Chip';
import MarkdownRender from '@components/MarkdownRender/MarkdownRender';
import StudyChip from '@components/StudyChip';
import Divider from '@components/divider/Divider';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';

import StudyMemberList from './components/study-member-list/StudyMemberList';
import StudyReviewList from './components/study-review-list/StudyReviewList';
import useFetchDetail from './hooks/useFetchDetail';
import useFetchStudyReviews from './hooks/useFetchStudyReviews';

const DetailPage = () => {
  const { studyId } = useParams();
  const [isVisibleLoadAllReviewsBtn, setIsVisibleLoadAllReviewsBtn] = useState<boolean>(true);
  const shouldLoadAll = !isVisibleLoadAllReviewsBtn;
  const studyDetailQueryResult = useFetchDetail(studyId!);
  const studyReviewsQueryResult = useFetchStudyReviews(studyId!, DEFAULT_LOAD_STUDY_REVIEW_COUNT, shouldLoadAll);

  const handleClickRegisterBtn = () => {
    alert('스터디에 가입했습니다!');
  };

  const handleClickLoadAllReviewsBtn = () => {
    setIsVisibleLoadAllReviewsBtn(false);
  };

  const render = () => {
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
    return (
      <>
        <div>
          <div
            css={css`
              display: flex;
              column-gap: 20px;
              margin-bottom: 20px;
            `}
          >
            <h1>{title}</h1>
            <StudyChip isOpen={status === 'open'} />
          </div>
          <div
            css={css`
              margin-bottom: 20px;
            `}
          >
            <span
              css={css`
                margin-right: 20px;
              `}
            >
              {studyReviewsQueryResult.data ? `후기 ${studyReviewsQueryResult.data.reviews.length}개` : 'loading...'}
            </span>
            <span>{`${startDate} ~ ${endDate}`}</span>
          </div>
          <h3
            css={css`
              margin-bottom: 20px;
            `}
          >
            {excerpt}
          </h3>
          <div
            css={css`
              display: flex;
              column-gap: 15px;
            `}
          >
            {tags.map(({ id, tagName }) => (
              <Chip key={id} disabled>
                {tagName}
              </Chip>
            ))}
          </div>
        </div>
        <Divider space={2} />
        <div
          css={css`
            display: flex;
            column-gap: 80px;
          `}
        >
          <div
            className="left"
            css={css`
              width: 100%;
              min-width: 0;
            `}
          >
            <MarkdownRender markdownContent={description} />
            <Divider space={2} />
            <StudyMemberList members={members} />
          </div>
          <div
            className="right"
            css={css`
              min-width: 25%;
              ${mqDown('lg')} {
                display: none;
              }
            `}
          >
            <div
              css={css`
                position: sticky;
                top: 100px;
                padding-bottom: 20px;
              `}
            >
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
        </div>
        <Divider space={2} />
        <div>
          <h1
            css={css`
              margin-bottom: 30px;
            `}
          >
            {studyReviewsQueryResult.data ? `후기 ${studyReviewsQueryResult.data.totalResults}개` : 'loading...'}
          </h1>
          {studyReviewsQueryResult.data?.reviews ? (
            <>
              <StudyReviewList reviews={studyReviewsQueryResult.data.reviews} />
              <div
                css={css`
                  text-align: center;
                `}
              >
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
        </div>
      </>
    );
  };

  return <div>{studyDetailQueryResult.isFetching ? <div>Loading...</div> : render()}</div>;
};

export default DetailPage;
