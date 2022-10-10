import type { MyStudy } from '@custom-types';

import Divider from '@components/divider/Divider';
import PageTitle from '@components/page-title/PageTitle';
import PageWrapper from '@components/page-wrapper/PageWrapper';

import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';
import { type StudyType, useMyStudyPage } from '@my-study-page/hooks/useMyStudyPage';

const MyStudyPage: React.FC = () => {
  const { myStudyQueryResult, studies } = useMyStudyPage();

  const { isFetching, isError, isSuccess } = myStudyQueryResult;

  return (
    <PageWrapper>
      <PageTitle align="center">가입한 스터디 목록</PageTitle>
      <Divider />
      {isFetching && <Loading />}
      {isError && !isSuccess && <Error />}
      {!isError && isSuccess && (
        <>
          <MyStudyCardListSection sectionTitle="활동 중!" studies={studies.inProgress} />
          <Divider space="10px" />
          <MyStudyCardListSection sectionTitle="곧 시작해요!" studies={studies.prepare} />
          <Divider space="10px" />
          <MyStudyCardListSection sectionTitle="종료했어요" done studies={studies.done} />
        </>
      )}
    </PageWrapper>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>내 스터디 불러오기를 실패했습니다</div>;

export default MyStudyPage;
