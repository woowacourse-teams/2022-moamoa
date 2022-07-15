import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { Member, StudyReview } from '@custom-types/index';

import Avatar from '@components/Avatar';
import Chip from '@components/Chip';
import MarkdownRender from '@components/MarkdownRender/MarkdownRender';
import StudyChip from '@components/StudyChip';

import useFetchDetail from './hooks/useFetchDetail';
import useFetchStudyReviews from './hooks/useFetchStudyReviews';

const Divider = styled.div`
  height: 1px;
  background-color: black;
  margin-top: 10px;
  margin-bottom: 10px;
`;

const StudyMemberList = ({ members }: { members: Array<Member> }) => {
  return (
    <div>
      <h1>스터디원</h1>
      <div
        css={css`
          display: grid;
          grid-template-columns: repeat(6, minmax(auto, 1fr));
          grid-column-gap: 30px;
          grid-row-gap: 15px;
        `}
      >
        {members.map(({ id, username, profileImage, profileUrl }) => (
          <a
            key={id}
            href={profileUrl}
            css={css`
              padding: 10px;
              border-radius: 20px;
              border: 1px solid black;
              display: flex;
              flex-direction: column;
              align-items: center;
            `}
          >
            <Avatar profileImg={profileImage} profileAlt="프로필 이미지" />
            <div className="name">{username}</div>
          </a>
        ))}
      </div>
    </div>
  );
};

type ReviewCardProps = {
  profileImageUrl: string;
  username: string;
  reviewDate: string;
  review: string;
};

const ReviewCard: React.FC<ReviewCardProps> = ({ profileImageUrl, username, reviewDate, review }) => {
  return (
    <div>
      <div
        css={css`
          display: flex;
          margin-bottom: 10px;
        `}
      >
        <Avatar profileImg={profileImageUrl} profileAlt="프로필 이미지" />
        <div>
          <div>{username}</div>
          <div>{reviewDate}</div>
        </div>
      </div>
      <div>{review}</div>
    </div>
  );
};

const StudyReviews: React.FC<{ reviews: Array<StudyReview> }> = ({ reviews }) => {
  return (
    <div
      css={css`
        display: grid;
        grid-template-columns: repeat(2, minmax(auto, 1fr));
        grid-template-rows: 1fr;
        row-gap: 32px;
        column-gap: 60px;
        place-items: center;
      `}
    >
      {reviews.map(review => {
        return (
          <ReviewCard
            key={review.id}
            profileImageUrl={review.member.profileImage}
            username={review.member.username}
            reviewDate={review.createdAt}
            review={review.content}
          />
        );
      })}
    </div>
  );
};

const DetailPage = () => {
  const { studyId } = useParams();
  const studyDetailQueryResult = useFetchDetail(studyId!);
  const studyReviewsQueryResult = useFetchStudyReviews(studyId!);

  const entranceRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!entranceRef.current) return;

    const observer = new IntersectionObserver(
      entries => {
        if (entries[0].isIntersecting) {
          console.log('entries : ', entries);
        }
      },
      {
        rootMargin: '-200px',
      },
    );
    observer.observe(entranceRef.current);
    return () => observer.disconnect();
  }, [studyDetailQueryResult.isSuccess]);

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
            `}
          >
            <h1>{title}</h1>
            <StudyChip isOpen={status === 'open'} />
          </div>
          <div>
            <span
              css={css`
                margin-right: 20px;
              `}
            >
              {studyReviewsQueryResult.data ? `후기 ${studyReviewsQueryResult.data.reviews.length}개` : 'loading...'}
            </span>
            <span>{`${startDate} ~ ${endDate}`}</span>
          </div>
          <h1>{excerpt}</h1>
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
        <Divider />
        <div
          css={css`
            display: flex;
          `}
        >
          <div className="left">
            <MarkdownRender markdownContent={description} />
            <Divider />
            <StudyMemberList members={members} />
          </div>
          <div
            className="right"
            css={css`
              min-width: 300px;
            `}
          >
            <div
              ref={entranceRef}
              css={css`
                border: 1px solid black;
                border-radius: 20px;
                padding: 20px;
                position: sticky;
                top: 0;
              `}
            >
              <div
                css={css`
                  margin-bottom: 10px;
                `}
              >
                <div>
                  <strong>2월 10일</strong>까지 가입 가능
                </div>
                <div
                  css={css`
                    display: flex;
                    justify-content: space-between;
                  `}
                >
                  <span>모집인원</span>
                  <span>0 / 10</span>
                </div>
                <div
                  css={css`
                    display: flex;
                    justify-content: space-between;
                  `}
                >
                  <span>스터디장</span>
                  <span>airman5573</span>
                </div>
              </div>
              <button
                css={css`
                  width: 100%;
                  padding: 12px;
                `}
              >
                스터디 방 가입하기
              </button>
            </div>
          </div>
        </div>
        <Divider />
        <div>
          <h1>
            {studyReviewsQueryResult.data ? `후기 ${studyReviewsQueryResult.data.reviews.length}개` : 'loading...'}
          </h1>
          {studyReviewsQueryResult.data?.reviews ? (
            <StudyReviews reviews={studyReviewsQueryResult.data.reviews} />
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
