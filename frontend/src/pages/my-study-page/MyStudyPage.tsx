import Divider from '@components/divider/Divider';
import PageTitle from '@components/page-title/PageTitle';
import Wrapper from '@components/wrapper/Wrapper';

import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';
import { useMyStudyPage } from '@my-study-page/hooks/useMyStudyPage';

const MyStudyPage: React.FC = () => {
  const { myStudyQueryResult, studies } = useMyStudyPage();

  const { isFetching, isError, isSuccess } = myStudyQueryResult;

  const renderStudyListSections = () => {
    if (isFetching) {
      return <div>로딩 중...</div>;
    }

    if (isError || !isSuccess) {
      return <div>내 스터디 불러오기를 실패했습니다</div>;
    }

    return (
      <>
        <MyStudyCardListSection sectionTitle="활동 중!" studies={studies.inProgress} />
        <Divider space="10px" />
        <MyStudyCardListSection sectionTitle="곧 시작해요!" studies={studies.prepare} />
        <Divider space="10px" />
        <MyStudyCardListSection sectionTitle="종료했어요" studies={studies.done} done />
      </>
    );
  };

  return (
    <Wrapper>
      <PageTitle align="center">가입한 스터디 목록</PageTitle>
      <Divider />
      {renderStudyListSections()}
    </Wrapper>
  );
};

export default MyStudyPage;
