import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { MakeOptional, MyStudy } from '@custom-types';

import * as S from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection.style';
import MyStudyCard from '@my-study-page/components/my-study-card/MyStudyCard';

export type MyStudyCardListSectionProps = {
  className?: string;
  sectionTitle: string;
  studies: Array<MyStudy>;
  disabled: boolean;
};

type OptionalMyStudyCardListSectionProps = MakeOptional<MyStudyCardListSectionProps, 'disabled'>;

const MyStudyCardListSection: React.FC<OptionalMyStudyCardListSectionProps> = ({
  className,
  sectionTitle,
  studies,
  disabled = false,
}) => {
  return (
    <S.MyStudyCardListSection className={className}>
      <S.SectionTitle>{sectionTitle}</S.SectionTitle>
      <S.MyStudyList>
        {studies.length === 0 ? (
          <li>해당하는 스터디가 없습니다</li>
        ) : (
          studies.map(study => (
            <li key={study.id}>
              <Link to={PATH.STUDY_ROOM(study.id)}>
                <MyStudyCard
                  title={study.title}
                  ownerName={study.owner.username}
                  tags={study.tags}
                  startDate={study.startDate}
                  endDate={study.endDate}
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
