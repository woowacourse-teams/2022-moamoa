import StudyCard from '@components/StudyCard';

import * as S from './style';

const studyList = Array.from({ length: 12 }).map((_, index) => ({
  id: index,
  title: 'JAVA 스터디',
  description: '자바를 공부하는 스터디입니다 :D GO GOGOG GOGO GOGO GO GO GO OGOGOG',
  thumbnail:
    'https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1673&q=80',
  status: 'open',
}));

const MainPage: React.FC = () => {
  return (
    <S.Page>
      <div className="filters"></div>
      <S.CardList>
        {studyList.map(study => (
          <li>
            <StudyCard
              key={study.id}
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
