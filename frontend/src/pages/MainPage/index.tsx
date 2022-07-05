import StudyCard from '@components/StudyCard';

import * as S from './style';

const studyList = Array.from({ length: 12 }).map((_, index) => ({
  id: index,
  title: 'JAVA 스터디',
  description: '자바를 공부하는 스터디입니다 :D GO GOGOG GOGO GOGO GO GO GO OGOGOG',
  thumbnail:
    'https://images.unsplash.com/photo-1576506542790-51244b486a6b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80',
  status: 'open',
}));

const MainPage: React.FC = () => {
  return (
    <S.Page>
      <div className="filters"></div>
      <S.CardList>
        <li>
          <StudyCard
            key={'sdfw'}
            thumbnailUrl={
              'https://images.unsplash.com/photo-1432888498266-38ffec3eaf0a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1774&q=80'
            }
            thumbnailAlt={`스터디 이미지`}
            title={'This is Title'}
            description={'Description 입니다 하하하하하'}
            isOpen={true}
          />
        </li>
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
