import { AxiosError } from 'axios';
import { useQueryClient } from 'react-query';

import { PATH } from '@constants';

import tw from '@utils/tw';

import type { MyStudy } from '@custom-types';

import { QK_MY_STUDIES } from '@api/my-studies';
import { useDeleteMyStudy } from '@api/my-study';

import LinkedButton from '@components/button/linked-button/LinkedButton';
import SectionTitle from '@components/section-title/SectionTitle';

import * as S from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection.style';
import MyStudyCard from '@my-study-page/components/my-study-card/MyStudyCard';

export type MyStudyCardListSectionProps = {
  sectionTitle: string;
  studies: Array<MyStudy>;
  end?: boolean;
};

const MyStudyCardListSection: React.FC<MyStudyCardListSectionProps> = ({ sectionTitle, studies, end = false }) => {
  const queryClient = useQueryClient();
  const { mutate } = useDeleteMyStudy();

  const handleTrashButtonClick =
    ({ title, id }: Pick<MyStudy, 'title' | 'id'>) =>
    (e: React.MouseEvent<HTMLButtonElement>) => {
      e.stopPropagation();

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
    <section css={tw`p-8`}>
      <SectionTitle>{sectionTitle}</SectionTitle>
      <S.MyStudyList>
        {studies.length === 0 ? (
          <li>해당하는 스터디가 없습니다</li>
        ) : (
          studies.map(study => (
            <li key={study.id}>
              <LinkedButton to={PATH.STUDY_ROOM(study.id)}>
                <MyStudyCard
                  title={study.title}
                  ownerName={study.owner.username}
                  tags={study.tags}
                  startDate={study.startDate}
                  endDate={study.endDate}
                  end={end}
                  onQuitStudyButtonClick={handleTrashButtonClick(study)}
                />
              </LinkedButton>
            </li>
          ))
        )}
      </S.MyStudyList>
    </section>
  );
};

export default MyStudyCardListSection;
