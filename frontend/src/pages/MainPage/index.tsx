import { useQuery } from 'react-query';

import type { StudyCardListQueryData } from '@custom-types/index';

import { getCardStudyList } from '@api/getStudyCardList';

import StudyCard from '@components/StudyCard';

import * as S from './style';

const MainPage: React.FC = () => {
  const { data, isError, isLoading, isFetching } = useQuery<unknown, unknown, StudyCardListQueryData>(
    'study-card-list',
    () => getCardStudyList(0, 12),
    {
      retry: 3,
    },
  );

  return (
    <S.Page>
      <div className="filters"></div>
      <S.CardList>
        {data?.studies.map(study => (
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
    </S.Page>
  );
};

export default MainPage;
