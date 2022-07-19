import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';
import { useContext, useState } from 'react';
import { useInfiniteQuery } from 'react-query';
import { Link } from 'react-router-dom';

import type { Study, StudyListQueryData } from '@custom-types/index';

import { getStudyList } from '@api/getStudyList';

import { SearchContext } from '@context/search/SearchProvider';

import * as S from '@pages/main-page/MainPage.style';
import type { FilterInfo } from '@pages/main-page/filter-section/FilterSection';
import FilterSection from '@pages/main-page/filter-section/FilterSection';
import StudyCard from '@pages/main-page/study-card/StudyCard';

import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import Wrapper from '@components/wrapper/Wrapper';

type PageParam = {
  page: number;
  size: number;
};

const defaultParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
  size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
};

const MainPage: React.FC = () => {
  const { keyword } = useContext(SearchContext);
  const [selectedFilters, setSelectedFilters] = useState<Array<FilterInfo>>([]);

  const getStudyListWithPage = async ({ pageParam = defaultParam }: { pageParam?: PageParam }) => {
    const { page, size } = pageParam;
    const data: StudyListQueryData = await getStudyList(page, size, keyword, selectedFilters);
    return { ...data, page: page + 1 };
  };

  const { data, isLoading, isError, error, fetchNextPage } = useInfiniteQuery<
    StudyListQueryData & { page: number },
    Error
  >(['infinite-scroll-searched-study-list', keyword, selectedFilters], getStudyListWithPage, {
    getNextPageParam: lastPage => {
      if (!lastPage.hasNext) return;
      return { page: lastPage.page };
    },
  });

  const searchedStudies = data?.pages.reduce<Array<Study>>((acc, cur) => [...acc, ...cur.studies], []) || [];
  const hasSearchResult = searchedStudies.length > 0;

  const handleFilterButtonClick = (id: number, categoryName: string) => () => {
    setSelectedFilters(prev => {
      if (prev.some(filter => filter.id === id && filter.categoryName === categoryName)) {
        return prev.filter(filter => !(filter.id === id && filter.categoryName === categoryName));
      }
      return [...prev, { id, categoryName }];
    });
  };

  return (
    <S.Page>
      <FilterSection selectedFilters={selectedFilters} handleFilterButtonClick={handleFilterButtonClick} />
      <Wrapper>
        <InfiniteScroll observingCondition={hasSearchResult} handleContentLoad={fetchNextPage}>
          {isLoading && <div>Loading...</div>}
          {isError && <div>{error.message}</div>}
          {hasSearchResult ? (
            <S.CardList>
              {searchedStudies.map(study => (
                <li key={study.id}>
                  <Link to={`study/${study.id}`}>
                    <StudyCard
                      thumbnailUrl={study.thumbnail}
                      thumbnailAlt={`${study.title} 스터디 이미지`}
                      title={study.title}
                      excerpt={study.excerpt}
                      isOpen={study.status === 'open'}
                    />
                  </Link>
                </li>
              ))}
            </S.CardList>
          ) : (
            <div>검색결과가 없습니다</div>
          )}
        </InfiniteScroll>
      </Wrapper>
    </S.Page>
  );
};

export default MainPage;
