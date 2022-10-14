import Divider from '@shared/divider/Divider';
import PageTitle from '@shared/page-title/PageTitle';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';
import { useMyStudyPage } from '@my-study-page/hooks/useMyStudyPage';

const MyStudyPage: React.FC = () => {
  const { myStudyQueryResult, studies } = useMyStudyPage();

  const { isFetching, isError } = myStudyQueryResult;

  return (
    <PageWrapper>
      <PageTitle align="center">가입한 스터디 목록</PageTitle>
      <Divider />
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        return (
          <>
            <MyStudyCardListSection sectionTitle="활동 중!" studies={studies.inProgress} />
            <Divider space="10px" />
            <MyStudyCardListSection sectionTitle="곧 시작해요!" studies={studies.prepare} />
            <Divider space="10px" />
            <MyStudyCardListSection sectionTitle="종료했어요" done studies={studies.done} />
          </>
        );
      })()}
    </PageWrapper>
  );
};

export default MyStudyPage;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>내 스터디 불러오기를 실패했습니다</div>;
