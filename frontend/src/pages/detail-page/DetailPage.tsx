import { useParams } from 'react-router-dom';

import { changeDateSeperator } from '@utils/dates';

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

  const studyDetailQueryResult = useFetchDetail(Number(studyId));

  const handleRegisterBtnClick = (studyId: number) => () => {
    alert('아직 준비중입니다 :D');
  };

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
          <StudyMemberSection members={members} />
        </S.MainDescription>
        <S.FloatButtonContainer>
          <S.StickyContainer>
            <StudyFloatBox
              studyId={id}
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
          studyId={id}
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
