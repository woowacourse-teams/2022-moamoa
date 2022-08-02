import { useContext, useState } from 'react';
import { useInfiniteQuery } from 'react-query';
import { Link, useNavigate } from 'react-router-dom';

import { DEFAULT_STUDY_CARD_QUERY_PARAM, PATH } from '@constants';

import type { GetStudyListResponseData, Study, TagInfo } from '@custom-types';

import { getStudyList } from '@api/getStudyList';

import { useAuth } from '@hooks/useAuth';

import { SearchContext } from '@context/search/SearchProvider';

import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@main-page/MainPage.style';
import CreateNewStudyButton from '@main-page/components/create-new-study-button/CreateNewStudyButton';
import FilterSection from '@main-page/components/filter-section/FilterSection';
import StudyCard from '@main-page/components/study-card/StudyCard';

type PageParam = {
  page: number;
  size: number;
};

const defaultParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
  size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
};

const MainPage: React.FC = () => {
  const { isLoggedIn } = useAuth();
  const { keyword } = useContext(SearchContext);

  const [selectedFilters, setSelectedFilters] = useState<Array<TagInfo>>([]);
  const navigate = useNavigate();

  const getStudyListWithPage = async ({ pageParam = defaultParam }: { pageParam?: PageParam }) => {
    const { page, size } = pageParam;
    const data = await getStudyList({ page, size, title: keyword, selectedFilters });
    return { ...data, page: page + 1 };
  };

  const { data, isFetching, isError, error, fetchNextPage, isFetched } = useInfiniteQuery<
    GetStudyListResponseData & { page: number },
    Error
  >(['infinite-scroll-searched-study-list', keyword, selectedFilters], getStudyListWithPage, {
    getNextPageParam: lastPage => {
      if (!lastPage.hasNext) return;
      return { page: lastPage.page };
    },
  });

  const searchedStudies = data?.pages.reduce<Array<Study>>((acc, cur) => [...acc, ...cur.studies], []) || [];
  const hasSearchResult = isFetched && searchedStudies.length > 0;

  const handleFilterButtonClick = (id: number, categoryName: string) => () => {
    setSelectedFilters(prev => {
      if (prev.some(filter => filter.id === id && filter.categoryName === categoryName)) {
        return prev.filter(filter => !(filter.id === id && filter.categoryName === categoryName));
      }
      return [...prev, { id, categoryName }];
    });
  };

  const handleCreateNewStudyButtonClick = () => {
    if (!isLoggedIn) {
      alert('로그인이 필요합니다');
      return;
    }
    window.scrollTo(0, 0);
    navigate(PATH.CREATE_STUDY);
  };

  return (
    <S.Page>
      <FilterSection selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
      <Wrapper>
        <InfiniteScroll observingCondition={hasSearchResult} onContentLoad={fetchNextPage}>
          {isFetching && <div>Loading...</div>}
          {isError && <div>{error.message}</div>}
          {hasSearchResult ? (
            <S.CardList>
              {searchedStudies.map(study => (
                <li key={study.id}>
                  <Link to={PATH.STUDY_DETAIL(study.id)}>
                    <StudyCard
                      thumbnailUrl={study.thumbnail}
                      thumbnailAlt={`${study.title} 스터디 이미지`}
                      title={study.title}
                      excerpt={study.excerpt}
                      isOpen={study.recruitmentStatus === 'RECRUITMENT_START'}
                    />
                  </Link>
                </li>
              ))}
            </S.CardList>
          ) : (
            isFetched && <div>검색결과가 없습니다</div>
          )}
        </InfiniteScroll>
      </Wrapper>
      <CreateNewStudyButton onClick={handleCreateNewStudyButtonClick} />
    </S.Page>
  );
};

export default MainPage;
