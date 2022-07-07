import { useContext } from 'react';
import { useQuery } from 'react-query';

import type { Study, StudyListQueryData } from '@custom-types/index';

import { getStudyList } from '@api/getStudyList';
import { getStudyListSearchedByTitle } from '@api/getStudyListSearchedByTitle';

import { SearchContext } from '@context/search/SearchProvider';

import StudyCard from '@components/StudyCard';

import * as S from './style';

const MainPage: React.FC = () => {
  const { keyword } = useContext(SearchContext);
  const { data, status: dataStatus } = useQuery<unknown, unknown, StudyListQueryData>('study-list', () =>
    getStudyList(0, 12),
  );
  const { data: searchedStudyListQueryData, status: searchStatus } = useQuery<unknown, unknown, StudyListQueryData>(
    ['searched-study-card-list', keyword],
    () => getStudyListSearchedByTitle(0, 12, keyword),
    {
      enabled: !!keyword,
    },
  );

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
    if (keyword.length > 0) {
      if (!searchedStudyListQueryData) return;
      if (searchedStudyListQueryData.studies.length === 0) {
        return <div>검색결과가 없습니다</div>;
      }
      return renderStudyCardList(searchedStudyListQueryData.studies);
    }
    return data && renderStudyCardList(data.studies);
  };

  return (
    <S.Page>
      <div className="filters"></div>
      {(dataStatus === 'loading' || searchStatus === 'loading') && <div>Loading...</div>}
      {renderList()}
    </S.Page>
  );
};

export default MainPage;
