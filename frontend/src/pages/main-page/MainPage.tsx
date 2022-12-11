import { Suspense } from 'react';
import { ErrorBoundary, FallbackProps } from 'react-error-boundary';
import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import type { Study } from '@custom-types';

import { mqDown } from '@styles/responsive';

import InfiniteScroll, { type InfiniteScrollProps } from '@shared/infinite-scroll/InfiniteScroll';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import CreateNewStudyButton from '@main-page/components/create-new-study-button/CreateNewStudyButton';
import FilterSection from '@main-page/components/filter-section/FilterSection';
import StudyCard from '@main-page/components/study-card/StudyCard';
import useMainPage from '@main-page/hooks/useMainPage';

const Success: React.FC = () => {
  const { studiesQueryResult, selectedFilters, handleFilterButtonClick, handleCreateNewStudyButtonClick } =
    useMainPage();

  const { isFetching, data, fetchNextPage } = studiesQueryResult;

  const searchedStudies = data?.pages.reduce<Array<Study>>((acc, cur) => [...acc, ...cur.studies], []);

  return (
    <>
      <FilterSection selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
      <CreateNewStudyButton onClick={handleCreateNewStudyButtonClick} />
      <PageWrapper>
        {(() => {
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
    </>
  );
};

const MainPage: React.FC = () => {
  return (
    <ErrorBoundary FallbackComponent={ErrorFallback}>
      <Suspense fallback={<LoadingFallback />}>
        <Success />
      </Suspense>
    </ErrorBoundary>
  );
};

export default MainPage;

const ErrorFallback: React.ComponentType<FallbackProps> = ({ error }) => {
  return (
    <PageWrapper>
      <h2>스터디를 불러오는 도중 에러가 발생했습니다</h2>
      <p>{error.message}</p>
    </PageWrapper>
  );
};

const LoadingFallback: React.FC = () => {
  return (
    <PageWrapper>
      <div>스터디 불러오는중</div>
    </PageWrapper>
  );
};

const NoResult: React.FC = () => {
  return <div>결과가 없습니다</div>;
};

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
              isOpen={study.recruitmentStatus === RECRUITMENT_STATUS.START}
            />
          </Link>
        </li>
      ))}
    </CardList>
    {isContentLoading && <LoadingFallback />}
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
