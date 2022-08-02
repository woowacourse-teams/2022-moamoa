import { useMemo } from 'react';
import { useQuery } from 'react-query';

import { css } from '@emotion/react';

import type { GetMyStudyResponseData, MyStudy } from '@custom-types';

import { getMyStudyList } from '@api';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@my-study-page/MyStudyPage.style';
import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';

const MyStudyPage: React.FC = () => {
  const { data, isFetching, isError } = useQuery<GetMyStudyResponseData, Error>('my-studies', getMyStudyList);

  const studies: Record<string, Array<MyStudy>> = useMemo(() => {
    return {
      prepare: data?.studies.filter(({ studyStatus }) => studyStatus === 'IN_PROGRESS') || [],
      inProgress: data?.studies.filter(({ studyStatus }) => studyStatus === 'PREPARE') || [],
      done: data?.studies.filter(({ studyStatus }) => studyStatus === 'DONE') || [],
    };
  }, [data]);

  const mb20 = css`
    margin-bottom: 20px;
  `;

  return (
    <Wrapper>
      <S.PageTitle>가입한 스터디 목록</S.PageTitle>
      <Divider />
      {isFetching && <div>로딩 중...</div>}
      {isError && <div>내 스터디 불러오기를 실패했습니다</div>}
      <MyStudyCardListSection css={mb20} sectionTitle="활동 중!" studies={studies.inProgress} />
      <MyStudyCardListSection css={mb20} sectionTitle="곧 시작해요!" studies={studies.prepare} />
      <MyStudyCardListSection css={mb20} sectionTitle="종료했어요" studies={studies.done} disabled={true} />
    </Wrapper>
  );
};

export default MyStudyPage;
