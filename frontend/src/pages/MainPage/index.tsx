<<<<<<< HEAD
import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';
import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery, useQuery } from 'react-query';
=======
import { useContext } from 'react';
import { useQuery } from 'react-query';
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c

import type { Study, StudyListQueryData } from '@custom-types/index';

import { getStudyList } from '@api/getStudyList';
import { getStudyListSearchedByTitle } from '@api/getStudyListSearchedByTitle';

import { SearchContext } from '@context/search/SearchProvider';

import StudyCard from '@components/StudyCard';

import * as S from './style';

<<<<<<< HEAD
type PageParam = {
  page: number;
  size: number;
  keyword?: string;
};

const defaultParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
  size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
};

const MainPage: React.FC = () => {
  const { keyword } = useContext(SearchContext);

  const getStudyListWithPage = async ({ pageParam = defaultParam }: { pageParam?: PageParam }) => {
    const { page, size } = pageParam;
    const data = await getStudyList(page, size);
    return { ...data, page: page + 1 };
  };

  const getStudyListSearchedByTitleWithPage = async ({ pageParam = defaultParam }: { pageParam?: PageParam }) => {
    const { page, size } = pageParam;
    const data = await getStudyListSearchedByTitle(page, size, keyword!);
    return { ...data, page: page + 1, keyword };
  };

  const endRef = useRef<HTMLDivElement>(null);

  const studyListQueryResult = useInfiniteQuery<any, StudyListQueryData>(
    'infinite-scroll-study-list',
    getStudyListWithPage,
    {
      getNextPageParam: lastPage => {
        if (!lastPage) return;
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );

  const searchedStudyListQueryResult = useInfiniteQuery<any, StudyListQueryData>(
    ['infinite-scroll-searched-study-list', keyword],
    getStudyListSearchedByTitleWithPage,
    {
      getNextPageParam: lastPage => {
        if (!lastPage) return;
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
=======
const MainPage: React.FC = () => {
  const { keyword } = useContext(SearchContext);
  const { data, status: dataStatus } = useQuery<unknown, unknown, StudyListQueryData>('study-list', () =>
    getStudyList(0, 12),
  );
  const { data: searchedStudyListQueryData, status: searchStatus } = useQuery<unknown, unknown, StudyListQueryData>(
    ['searched-study-card-list', keyword],
    () => getStudyListSearchedByTitle(0, 12, keyword),
    {
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c
      enabled: !!keyword,
    },
  );

<<<<<<< HEAD
  const noSearchResult = !!(keyword.length > 0 && searchedStudyListQueryResult.data?.pages[0].studies.length === 0);
  const hasSearchResult = !!(
    keyword.length > 0 &&
    searchedStudyListQueryResult.data &&
    searchedStudyListQueryResult.data.pages[0].studies.length > 0
  );
  const hasBaseStudyList = !!(studyListQueryResult?.data && studyListQueryResult.data.pages[0].studies.length > 0);
  const shouldDisplayBaseStudyList = !!(keyword.length === 0 && hasBaseStudyList);

  useEffect(() => {
    if (!endRef.current) return;

    const observer = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting) {
        if (hasSearchResult) {
          searchedStudyListQueryResult.fetchNextPage();
        }
        if (shouldDisplayBaseStudyList) {
          studyListQueryResult.fetchNextPage();
        }
      }
    });
    observer.observe(endRef.current);
    return () => observer.disconnect();
  }, [hasSearchResult, shouldDisplayBaseStudyList]);

=======
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c
  const renderStudyCardList = (data: Array<Study>) => {
    return (
      <S.CardList>
        {data.map(study => (
          <li key={study.id}>
            <StudyCard
              thumbnailUrl={study.thumbnail}
              thumbnailAlt={`${study.title} 스터디 이미지`}
              title={study.title}
              description={study.description}
              isOpen={study.status === 'open'}
            />
          </li>
        ))}
      </S.CardList>
    );
  };

  const renderList = () => {
<<<<<<< HEAD
    if (noSearchResult) {
      return <div>검색결과가 없습니다</div>;
    }
    if (hasSearchResult) {
      const searchedStudies = searchedStudyListQueryResult.data.pages.reduce((acc, cur) => {
        return [...acc, ...cur.studies];
      }, []);
      return renderStudyCardList(searchedStudies);
    }

    if (hasBaseStudyList && shouldDisplayBaseStudyList) {
      const studies = studyListQueryResult.data.pages.reduce((acc, cur) => {
        return [...acc, ...cur.studies];
      }, []);
      return renderStudyCardList(studies);
    }
=======
    if (keyword.length > 0) {
      if (!searchedStudyListQueryData) return;
      if (searchedStudyListQueryData.studies.length === 0) {
        return <div>검색결과가 없습니다</div>;
      }
      return renderStudyCardList(searchedStudyListQueryData.studies);
    }
    return data && renderStudyCardList(data.studies);
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c
  };

  return (
    <S.Page>
      <div className="filters"></div>
<<<<<<< HEAD
      {(studyListQueryResult.status === 'loading' || searchedStudyListQueryResult.status === 'loading') && (
        <div>Loading...</div>
      )}
      {renderList()}
      <div ref={endRef} />
=======
      {(dataStatus === 'loading' || searchStatus === 'loading') && <div>Loading...</div>}
      {renderList()}
>>>>>>> 1cddf47d74e317571a4325c73eae56521eaeea7c
    </S.Page>
  );
};

export default MainPage;
