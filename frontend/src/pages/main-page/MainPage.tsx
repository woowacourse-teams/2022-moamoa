import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import type { Study } from '@custom-types';

import { mqDown } from '@styles/responsive';

import InfiniteScroll, { type InfiniteScrollProps } from '@components/infinite-scroll/InfiniteScroll';
import PageWrapper from '@components/page-wrapper/PageWrapper';

import CreateNewStudyButton from '@main-page/components/create-new-study-button/CreateNewStudyButton';
import FilterSection from '@main-page/components/filter-section/FilterSection';
import StudyCard from '@main-page/components/study-card/StudyCard';
import useMainPage from '@main-page/hooks/useMainPage';

const MainPage: React.FC = () => {
  const { studiesQueryResult, selectedFilters, handleFilterButtonClick, handleCreateNewStudyButtonClick } =
    useMainPage();

  const { isFetching, isError, isSuccess, data, fetchNextPage } = studiesQueryResult;

  const searchedStudies = data?.pages.reduce<Array<Study>>((acc, cur) => [...acc, ...cur.studies], []);

  return (
    <Page>
      <FilterSection selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
      <PageWrapper>
        {(() => {
          if (isError) return <Error />;
          if (!searchedStudies || (searchedStudies && searchedStudies.length === 0)) return <NoResult />;
          return (
            <InfinitScrollCardList
              isContentLoading={isFetching}
              onContentLoad={fetchNextPage}
              studies={searchedStudies}
            />
          );
        })()}
      </PageWrapper>
      <CreateNewStudyButton onClick={handleCreateNewStudyButtonClick} />
    </Page>
  );
};

const Error = () => <div>에러가 발생했습니다</div>;

const NoResult = () => <div>검색 결과가 없습니다</div>;

type InfinitScrollCardListProps = { studies: Array<Study> } & Omit<InfiniteScrollProps, 'children'>;

const InfinitScrollCardList: React.FC<InfinitScrollCardListProps> = ({
  studies,
  isContentLoading,
  onContentLoad: handleContentLoad,
}) => (
  <InfiniteScroll isContentLoading={isContentLoading} onContentLoad={handleContentLoad}>
    <CardList>
      {studies.map((study, i) => (
        <li key={study.id}>
          <Link to={PATH.STUDY_DETAIL(study.id)}>
            <StudyCard
              thumbnailUrl={`static/${(i + 1) % 29}.jpg`}
              thumbnailAlt={`${study.title} 스터디 이미지`}
              title={study.title}
              excerpt={study.excerpt}
              tags={study.tags}
              isOpen={study.recruitmentStatus === 'RECRUITMENT_START'}
            />
          </Link>
        </li>
      ))}
    </CardList>
    {isContentLoading && <Loading />}
  </InfiniteScroll>
);

const CardList = styled.ul`
  display: grid;
  grid-template-columns: repeat(4, minmax(auto, 1fr));
  grid-template-rows: 1fr;
  gap: 32px;

  & > li {
    width: 100%;
  }

  ${mqDown('lg')} {
    grid-template-columns: repeat(3, 1fr);
  }
  ${mqDown('md')} {
    grid-template-columns: repeat(2, 1fr);
  }
  ${mqDown('sm')} {
    grid-template-columns: repeat(1, 256px);
    place-content: center;
  }
`;

const Page = styled.div``;

const Loading = () => <div>Loading...</div>;

export default MainPage;
