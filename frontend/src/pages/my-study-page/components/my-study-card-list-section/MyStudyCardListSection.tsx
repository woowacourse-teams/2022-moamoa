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

const HiOutlineTrash = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="0"
    viewBox="0 0 24 24"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
    ></path>
  </svg>
);

const MyStudyCardListSection: React.FC<OptionalMyStudyCardListSectionProps> = ({
  className,
  sectionTitle,
  studies,
  disabled = false,
}) => {
  const handleTrashButtonClick = (studyName: string) => () => {
    if (!confirm(`정말 '${studyName}'을(를) 탈퇴하실 건가요? :(`)) return;

    // TODO: mutate
    alert('탈퇴');
  };

  return (
    <S.MyStudyCardListSection className={className}>
      <S.SectionTitle>{sectionTitle}</S.SectionTitle>
      <S.MyStudyList>
        {studies.length === 0 ? (
          <li>해당하는 스터디가 없습니다</li>
        ) : (
          studies.map(study => (
            <S.MyStudyCardItem key={study.id}>
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
              <S.TrashButton type="button" onClick={handleTrashButtonClick(study.title)}>
                <HiOutlineTrash />
              </S.TrashButton>
            </S.MyStudyCardItem>
          ))
        )}
      </S.MyStudyList>
    </S.MyStudyCardListSection>
  );
};

export default MyStudyCardListSection;
