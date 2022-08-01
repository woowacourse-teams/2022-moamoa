import { AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { Navigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils/dates';

import postJoiningStudy from '@api/postJoiningStudy';

import { useAuth } from '@hooks/useAuth';

import StudyMemberSection from '@pages/detail-page/components/study-member-section/StudyMemberSection';
import StudyWideFloatBox from '@pages/detail-page/components/study-wide-float-box/StudyWideFloatBox';

import Divider from '@components/divider/Divider';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@detail-page/DetailPage.style';
import Head from '@detail-page/components/head/Head';
import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import StudyReviewSection from '@detail-page/components/study-review-section/StudyReviewSection';
import useFetchDetail from '@detail-page/hooks/useFetchDetail';

const DetailPage = () => {
  const { studyId } = useParams() as { studyId: string };

  const { isLoggedIn } = useAuth();

  const studyDetailQueryResult = useFetchDetail(Number(studyId));
  const { mutate } = useMutation<AxiosResponse, Error, number>(postJoiningStudy);

  const handleRegisterBtnClick = () => {
    if (!isLoggedIn) {
      alert('로그인이 필요합니다.');
      return;
    }

    mutate(Number(studyId), {
      onError: () => {
        alert('가입에 실패했습니다.');
      },
      onSuccess: () => {
        alert('가입했습니다 :D');
        studyDetailQueryResult.refetch();
      },
    });
  };

  if (!studyId) {
    alert('잘못된 접근입니다.');
    return <Navigate to={PATH.MAIN} replace={true} />;
  }

  if (studyDetailQueryResult.isFetching) return <div>Loading...</div>;

  if (!studyDetailQueryResult.data) return <div>No Data</div>;

  // TODO: background에 thumbnail 이미지 사용
  const {
    id,
    title,
    excerpt,
    thumbnail,
    recruitmentStatus,
    description,
    currentMemberCount,
    maxMemberCount,
    enrollmentEndDate,
    startDate,
    endDate,
    owner,
    members,
    tags,
  } = studyDetailQueryResult.data;

  return (
    <Wrapper>
      <Head
        title={title}
        recruitmentStatus={recruitmentStatus}
        excerpt={excerpt}
        startDate={changeDateSeperator(startDate)}
        endDate={changeDateSeperator(endDate)}
        tags={tags}
      />
      <Divider space={2} />
      <S.Main>
        <S.MainDescription>
          <S.MarkDownContainer>
            <MarkdownRender markdownContent={description} />
          </S.MarkDownContainer>
          <Divider space={2} />
          <StudyMemberSection owner={owner} members={members} />
        </S.MainDescription>
        <S.FloatButtonContainer>
          <S.StickyContainer>
            <StudyFloatBox
              ownerName={owner.username}
              currentMemberCount={currentMemberCount}
              maxMemberCount={maxMemberCount}
              enrollmentEndDate={enrollmentEndDate}
              recruitmentStatus={recruitmentStatus}
              handleRegisterBtnClick={handleRegisterBtnClick}
            />
          </S.StickyContainer>
        </S.FloatButtonContainer>
      </S.Main>
      <Divider space={2} />
      <StudyReviewSection studyId={id} />
      <S.FixedBottomContainer>
        <StudyWideFloatBox
          currentMemberCount={currentMemberCount}
          maxMemberCount={maxMemberCount}
          enrollmentEndDate={enrollmentEndDate}
          recruitmentStatus={recruitmentStatus}
          handleRegisterBtnClick={handleRegisterBtnClick}
        />
      </S.FixedBottomContainer>
    </Wrapper>
  );
};

export default DetailPage;
