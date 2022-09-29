import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { Study } from '@custom-types';

import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@main-page/MainPage.style';
import CreateNewStudyButton from '@main-page/components/create-new-study-button/CreateNewStudyButton';
import FilterSection from '@main-page/components/filter-section/FilterSection';
import StudyCard from '@main-page/components/study-card/StudyCard';
import useMainPage from '@main-page/hooks/useMainPage';

const MainPage: React.FC = () => {
  const { studiesQueryResult, selectedFilters, handleFilterButtonClick, handleCreateNewStudyButtonClick } =
    useMainPage();

  const { isFetching, isError, isSuccess, data, fetchNextPage } = studiesQueryResult;

  const renderStudyList = () => {
    if (isError) {
      return <div>에러가 발생했습니다</div>;
    }

    const searchedStudies = data?.pages.reduce<Array<Study>>((acc, cur) => [...acc, ...cur.studies], []) ?? [];

    if (isSuccess && searchedStudies.length === 0) {
      return <div>검색 결과가 없습니다</div>;
    }

    return (
      <InfiniteScroll isContentLoading={isFetching} onContentLoad={fetchNextPage}>
        <S.CardList>
          {searchedStudies.map((study, i) => (
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
        </S.CardList>
        {isFetching && <div>Loading...</div>}
      </InfiniteScroll>
    );
  };

  return (
    <S.Page>
      <FilterSection selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
      <Wrapper>{renderStudyList()}</Wrapper>
      <CreateNewStudyButton onClick={handleCreateNewStudyButtonClick} />
    </S.Page>
  );
};

export default MainPage;
