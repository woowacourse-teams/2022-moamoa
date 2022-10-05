import { MyStudy } from '@custom-types';

import Divider from '@components/divider/Divider';
import PageTitle from '@components/page-title/PageTitle';
import Wrapper from '@components/wrapper/Wrapper';

import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';
import { StudyType, useMyStudyPage } from '@my-study-page/hooks/useMyStudyPage';

const MyStudyPage: React.FC = () => {
  const { myStudyQueryResult, studies } = useMyStudyPage();

  const { isFetching, isError, isSuccess } = myStudyQueryResult;

  return (
    <Wrapper>
      <PageTitle align="center">가입한 스터디 목록</PageTitle>
      <Divider />
      {isFetching && <Loading />}
      {isError && !isSuccess && <Error />}
      {!isError && isSuccess && (
        <>
          <InProgressStudyList studies={studies} />
          <Divider space="10px" />
          <PrepareStudyList studies={studies} />
          <Divider space="10px" />
          <DoneStudyList studies={studies} />
        </>
      )}
    </Wrapper>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>내 스터디 불러오기를 실패했습니다</div>;

type InProgressStudyListProps = {
  studies: Record<StudyType, Array<MyStudy>>;
};
const InProgressStudyList: React.FC<InProgressStudyListProps> = ({ studies }) => (
  <MyStudyCardListSection sectionTitle="활동 중!" studies={studies.inProgress} />
);

type PrepareStudyListProps = InProgressStudyListProps;
const PrepareStudyList: React.FC<PrepareStudyListProps> = ({ studies }) => (
  <MyStudyCardListSection sectionTitle="곧 시작해요!" studies={studies.prepare} />
);

type DoneStudyListProps = InProgressStudyListProps;
const DoneStudyList: React.FC<DoneStudyListProps> = ({ studies }) => (
  <MyStudyCardListSection sectionTitle="종료했어요" done={true} studies={studies.done} />
);

export default MyStudyPage;
