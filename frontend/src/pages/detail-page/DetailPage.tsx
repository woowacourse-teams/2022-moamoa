import { Navigate } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH } from '@constants';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import Wrapper from '@components/wrapper/Wrapper';

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
        id={id}
        title={title}
        recruitmentStatus={recruitmentStatus}
        excerpt={excerpt}
        startDate={startDate}
        endDate={endDate}
        tags={tags}
        isOwner={userRoleQueryResult.data?.role === 'OWNER'}
      />
      <Divider space="20px" />
      <Flex columnGap="40px">
        <Main>
          <MarkdownRendererContainer>
            <MarkdownRender markdownContent={description} />
          </MarkdownRendererContainer>
          <Divider space="20px" />
          <StudyMemberSection owner={owner} members={members} />
        </Main>
        <Sidebar>
          <FloatButtonContainer>
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
          </FloatButtonContainer>
        </Sidebar>
      </Flex>
      <Divider space="20px" />
      <StudyReviewSection studyId={id} />
      <FixedBottomContainer>
        <StudyWideFloatBox
          studyId={id}
          userRole={userRoleQueryResult.data?.role}
          currentMemberCount={currentMemberCount}
          maxMemberCount={maxMemberCount}
          enrollmentEndDate={enrollmentEndDate}
          recruitmentStatus={recruitmentStatus}
          onRegisterButtonClick={handleRegisterButtonClick}
        />
      </FixedBottomContainer>
    </Wrapper>
  );
};

const Main = styled.div`
  width: 100%;
`;

const MarkdownRendererContainer = styled.div`
  padding: 16px;
  border-radius: 15px;
`;

const Sidebar = styled.div`
  min-width: 30%;

  ${mqDown('lg')} {
    display: none;
  }
`;

const FloatButtonContainer = styled.div`
  position: sticky;
  top: 150px;
  padding-bottom: 20px;
`;

const FixedBottomContainer = styled.div`
  display: none;

  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 5;

  ${mqDown('lg')} {
    display: block;
  }
`;

export default DetailPage;
