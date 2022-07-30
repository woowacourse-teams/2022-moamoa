import type { MakeOptional, MyStudyData } from '@custom-types/index';

import MyStudyCard from '@pages/my-study-page/components/my-study-card/MyStudyCard';

import * as S from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection.style';

export type MyStudyCardListSectionProps = {
  className?: string;
  sectionTitle: string;
  myStudies: Array<MyStudyData>;
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
        {myStudies.map(myStudy => (
          <li key={myStudy.id}>
            <MyStudyCard
              title={myStudy.title}
              ownerName={myStudy.owner.username}
              tags={myStudy.tags}
              startDate={myStudy.startDate}
              endDate={myStudy.endDate}
              disabled={disabled}
            />
          </li>
        ))}
      </S.MyStudyList>
    </S.MyStudyCardListSection>
  );
};

export default MyStudyCardListSection;
