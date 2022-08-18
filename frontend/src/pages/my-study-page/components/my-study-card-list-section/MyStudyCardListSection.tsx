import { AxiosError } from 'axios';
import { useQueryClient } from 'react-query';
import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { MakeOptional, MyStudy } from '@custom-types';

import { QK_MY_STUDIES } from '@api/my-studies';
import { useDeleteMyStudy } from '@api/my-study';

import { TrashCanSvg } from '@components/svg';

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
  const queryClient = useQueryClient();
  const { mutate } = useDeleteMyStudy();

  const handleTrashButtonClick =
    ({ title, id }: Pick<MyStudy, 'title' | 'id'>) =>
    () => {
      if (!confirm(`정말 '${title}'을(를) 탈퇴하실 건가요? :(`)) return;

      mutate(
        { studyId: id },
        {
          onError: error => {
            if (error instanceof AxiosError) console.error(error.message);
            alert('문제가 발생하여 스터디를 탈퇴하지 못했습니다');
          },
          onSuccess: () => {
            queryClient.refetchQueries(QK_MY_STUDIES);
            alert('스터디를 탈퇴했습니다.');
          },
        },
      );
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
              <S.TrashButton type="button" onClick={handleTrashButtonClick(study)}>
                <TrashCanSvg />
              </S.TrashButton>
            </S.MyStudyCardItem>
          ))
        )}
      </S.MyStudyList>
    </S.MyStudyCardListSection>
  );
};

export default MyStudyCardListSection;
