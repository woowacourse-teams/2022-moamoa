import { Navigate } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH } from '@constants';

import { mqDown } from '@styles/responsive';

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

  // TODO: background에 thumbnail 이미지 사용
  return (
    <Wrapper>
      {isFetching && <Loading />}
      {isError && <Error />}
      {isSuccess && (
        <>
          <Head
            id={data.id}
            title={data.title}
            recruitmentStatus={data.recruitmentStatus}
            excerpt={data.excerpt}
            startDate={data.startDate}
            endDate={data.endDate}
            tags={data.tags}
            isOwner={userRoleQueryResult.data?.role === 'OWNER'}
          />
          <Divider space="20px" />
          <Flex columnGap="40px">
            <Main>
              <MarkdownRendererContainer>
                <MarkdownRender markdownContent={data.description} />
              </MarkdownRendererContainer>
              <Divider space="20px" />
              <StudyMemberSection owner={data.owner} members={data.members} />
            </Main>
            <Sidebar>
              <FloatButtonContainer>
                <StudyFloatBox
                  studyId={data.id}
                  userRole={userRoleQueryResult.data?.role}
                  ownerName={data.owner.username}
                  currentMemberCount={data.currentMemberCount}
                  maxMemberCount={data.maxMemberCount}
                  enrollmentEndDate={data.enrollmentEndDate}
                  recruitmentStatus={data.recruitmentStatus}
                  onRegisterButtonClick={handleRegisterButtonClick}
                />
              </FloatButtonContainer>
            </Sidebar>
          </Flex>
          <Divider space="20px" />
          <StudyReviewSection studyId={data.id} />
          <FixedBottomContainer>
            <StudyWideFloatBox
              studyId={data.id}
              userRole={userRoleQueryResult.data?.role}
              currentMemberCount={data.currentMemberCount}
              maxMemberCount={data.maxMemberCount}
              enrollmentEndDate={data.enrollmentEndDate}
              recruitmentStatus={data.recruitmentStatus}
              onRegisterButtonClick={handleRegisterButtonClick}
            />
          </FixedBottomContainer>
        </>
      )}
    </Wrapper>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>조회에 실패했습니다</div>;

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
