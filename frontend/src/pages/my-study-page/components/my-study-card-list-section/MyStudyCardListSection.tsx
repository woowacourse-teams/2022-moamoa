import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { MakeOptional, MyStudy } from '@custom-types';

import * as S from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection.style';
import MyStudyCard from '@my-study-page/components/my-study-card/MyStudyCard';

export type MyStudyCardListSectionProps = {
  className?: string;
  sectionTitle: string;
  myStudies: Array<MyStudy>;
  disabled: boolean;
};

type OptionalMyStudyCardListSectionProps = MakeOptional<MyStudyCardListSectionProps, 'disabled'>;

const MyStudyCardListSection: React.FC<OptionalMyStudyCardListSectionProps> = ({
  className,
  sectionTitle,
  myStudies,
  disabled = false,
}) => {
  return (
    <S.MyStudyCardListSection className={className}>
      <S.SectionTitle>{sectionTitle}</S.SectionTitle>
      <S.MyStudyList>
        {myStudies.length === 0 ? (
          <li>해당하는 스터디가 없습니다</li>
        ) : (
          myStudies.map(myStudy => (
            <li key={myStudy.id}>
              <Link to={PATH.STUDY_ROOM(myStudy.id)}>
                <MyStudyCard
                  title={myStudy.title}
                  ownerName={myStudy.owner.username}
                  tags={myStudy.tags}
                  startDate={myStudy.startDate}
                  endDate={myStudy.endDate}
                  disabled={disabled}
                />
              </Link>
            </li>
          ))
        )}
      </S.MyStudyList>
    </S.MyStudyCardListSection>
  );
};

export default MyStudyCardListSection;
