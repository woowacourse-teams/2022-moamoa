import { useQuery } from 'react-query';

import { css } from '@emotion/react';

import type { GetMyStudyResponseData } from '@custom-types';
import { MyStudy } from '@custom-types';

import { getMyStudyList } from '@api/getMyStudyList';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@my-study-page/MyStudyPage.style';
import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';

const MyStudyPage: React.FC = () => {
  const { data, isFetching, isError } = useQuery<GetMyStudyResponseData, Error>('my-studies', getMyStudyList);

  const studies: Record<string, Array<MyStudy>> = {
    prepare: [],
    inProgress: [],
    done: [],
  };

  const myStudies =
    data?.studies.reduce((acc, study) => {
      if (study.studyStatus === 'IN_PROGRESS') {
        acc.inProgress.push(study);
      }
      if (study.studyStatus === 'PREPARE') {
        acc.prepare.push(study);
      }
      if (study.studyStatus === 'DONE') {
        acc.done.push(study);
      }
      return acc;
    }, studies) || studies;

  const mb20 = css`
    margin-bottom: 20px;
  `;

  return (
    <Wrapper>
      <S.PageTitle>가입한 스터디 목록</S.PageTitle>
      <Divider />
      {isFetching && <div>로딩 중...</div>}
      {isError && <div>내 스터디 불러오기를 실패했습니다</div>}
      <MyStudyCardListSection css={mb20} sectionTitle="활동 중!" myStudies={myStudies.inProgress} />
      <MyStudyCardListSection css={mb20} sectionTitle="곧 시작해요!" myStudies={myStudies.prepare} />
      <MyStudyCardListSection css={mb20} sectionTitle="종료했어요" myStudies={myStudies.done} disabled={true} />
    </Wrapper>
  );
};

export default MyStudyPage;
