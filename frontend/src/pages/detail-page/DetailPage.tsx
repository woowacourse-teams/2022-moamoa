import { Navigate } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import { theme } from '@styles/theme';

import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
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
      <Flex gap="40px">
        <section css={tw`w-full`}>
          <section css={tw`p-16 rounded-[${theme.radius.md}]`}>
            <MarkdownRender markdownContent={description} />
          </section>
          <Divider space="20px" />
          <StudyMemberSection owner={owner} members={members} />
        </section>
        <S.FloatButtonContainer>
          <div css={tw`sticky top-150 pb-20`}>
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
          </div>
        </S.FloatButtonContainer>
      </Flex>
      <Divider space="20px" />
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
