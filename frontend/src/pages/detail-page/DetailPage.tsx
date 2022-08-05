import { Navigate } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import Divider from '@components/divider/Divider';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@detail-page/DetailPage.style';
import Head from '@detail-page/components/head/Head';
import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import StudyMemberSection from '@detail-page/components/study-member-section/StudyMemberSection';
import StudyReviewSection from '@detail-page/components/study-review-section/StudyReviewSection';
import StudyWideFloatBox from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';
import useDetailPage from '@detail-page/hooks/useDetailPage';

const DetailPage: React.FC = () => {
  const { studyId, detailQueryResult, userRoleQueryResult, handleRegisterButtonClick } = useDetailPage();
  const { isFetching, isSuccess, isError, data } = detailQueryResult;

  if (!studyId) {
    alert('잘못된 접근입니다.');
    return <Navigate to={PATH.MAIN} replace={true} />;
  }

  if (isFetching) return <div>Loading...</div>;

  if (!isSuccess || isError) return <div>조회에 실패했습니다</div>;

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
  } = data;

  // TODO: background에 thumbnail 이미지 사용

  return (
    <Wrapper>
      <Head
        title={title}
        recruitmentStatus={recruitmentStatus}
        excerpt={excerpt}
        startDate={changeDateSeperator(startDate)}
        endDate={endDate && changeDateSeperator(endDate)}
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
              studyId={id}
              userRole={userRoleQueryResult.data?.role}
              ownerName={owner.username}
              currentMemberCount={currentMemberCount}
              maxMemberCount={maxMemberCount}
              enrollmentEndDate={enrollmentEndDate}
              recruitmentStatus={recruitmentStatus}
              onRegisterButtonClick={handleRegisterButtonClick}
            />
          </S.StickyContainer>
        </S.FloatButtonContainer>
      </S.Main>
      <Divider space={2} />
      <StudyReviewSection studyId={id} />
      <S.FixedBottomContainer>
        <StudyWideFloatBox
          studyId={id}
          userRole={userRoleQueryResult.data?.role}
          currentMemberCount={currentMemberCount}
          maxMemberCount={maxMemberCount}
          enrollmentEndDate={enrollmentEndDate}
          recruitmentStatus={recruitmentStatus}
          onRegisterButtonClick={handleRegisterButtonClick}
        />
      </S.FixedBottomContainer>
    </Wrapper>
  );
};

export default DetailPage;
