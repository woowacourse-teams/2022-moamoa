import { AxiosError } from 'axios';
import { useQueryClient } from 'react-query';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import type { MyStudy } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { QK_MY_STUDIES } from '@api/my-studies';
import { useDeleteMyStudy } from '@api/my-study';

import { useUserInfo } from '@hooks/useUserInfo';

import { LinkedButton } from '@shared/button';
import SectionTitle from '@shared/section-title/SectionTitle';

import MyStudyCard from '@my-study-page/components/my-study-card/MyStudyCard';

export type MyStudyCardListSectionProps = {
  sectionTitle: string;
  studies: Array<MyStudy>;
  done?: boolean;
};

type HandleQuitButtonClickHandler = ({
  title,
  id,
  owner,
}: Pick<MyStudy, 'title' | 'id' | 'owner'>) => (e: React.MouseEvent<HTMLButtonElement>) => void;

const MyStudyCardListSection: React.FC<MyStudyCardListSectionProps> = ({ sectionTitle, studies, done = false }) => {
  const queryClient = useQueryClient();
  const { mutate } = useDeleteMyStudy();
  const { userInfo } = useUserInfo();

  const handleTrashButtonClick: HandleQuitButtonClickHandler =
    ({ title, id, owner }) =>
    e => {
      e.stopPropagation();

      if (userInfo.id === owner.id) {
        alert('스터디장은 스터디를 탈퇴할 수 없습니다.');
        return;
      }

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
    <Self>
      <SectionTitle>{sectionTitle}</SectionTitle>
      <MyStudyList>
        {studies.length === 0 ? (
          <NoResult />
        ) : (
          <MyStudyCardLinkItemList studies={studies} done={done} onQuitButtonClick={handleTrashButtonClick} />
        )}
      </MyStudyList>
    </Self>
  );
};

export default MyStudyCardListSection;

type SelfProps = {
  children: React.ReactNode;
};
const Self: React.FC<SelfProps> = ({ children }) => (
  <section
    css={css`
      padding: 8px;
    `}
  >
    {children}
  </section>
);

type MyStudyListProps = {
  children: React.ReactNode;
};
const MyStudyList: React.FC<MyStudyListProps> = ({ children }) => {
  return (
    <ul
      css={css`
        display: grid;
        grid-template-columns: repeat(3, minmax(auto, 1fr));
        grid-template-rows: 1fr;
        gap: 20px;

        & > li {
          width: 100%;
        }

        ${mqDown('lg')} {
          grid-template-columns: repeat(2, minmax(auto, 1fr));
        }
        ${mqDown('sm')} {
          grid-template-columns: repeat(1, minmax(auto, 1fr));
        }
      `}
    >
      {children}
    </ul>
  );
};

const NoResult = () => <li>해당하는 스터디가 없습니다</li>;

type MyStudyCardLinkItemListProps = {
  studies: MyStudyCardListSectionProps['studies'];
  done: MyStudyCardListSectionProps['done'];
  onQuitButtonClick: HandleQuitButtonClickHandler;
};
const MyStudyCardLinkItemList: React.FC<MyStudyCardLinkItemListProps> = ({
  studies,
  done,
  onQuitButtonClick: handleQuitButtonClick,
}) => (
  <>
    {studies.map(study => (
      <li key={study.id}>
        <LinkedButton to={PATH.STUDY_ROOM(study.id)}>
          <MyStudyCard
            title={study.title}
            ownerName={study.owner.username}
            tags={study.tags}
            startDate={study.startDate}
            endDate={study.endDate}
            done={done}
            onQuitStudyButtonClick={handleQuitButtonClick(study)}
          />
        </LinkedButton>
      </li>
    ))}
  </>
);
