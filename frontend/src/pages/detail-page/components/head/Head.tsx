import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { StudyDetail } from '@custom-types';

import { TextButton } from '@components/button';
import Flex from '@components/flex/Flex';
import StudyChip from '@components/study-chip/StudyChip';
import Title from '@components/title/Title';

import * as S from '@detail-page/components/head/Head.style';

export type HeadProps = Pick<
  StudyDetail,
  'id' | 'title' | 'recruitmentStatus' | 'startDate' | 'endDate' | 'excerpt' | 'tags'
> & {
  isOwner: boolean;
};

const Head: React.FC<HeadProps> = ({
  id: studyId,
  title,
  recruitmentStatus,
  startDate,
  endDate,
  excerpt,
  tags,
  isOwner,
}) => {
  return (
    <Flex direction="column" rowGap="4px">
      <Flex justifyContent="space-between" alignItems="center">
        <Flex alignItems="center" gap="16px">
          <Title.Page>{title}</Title.Page>
          <StudyChip isOpen={recruitmentStatus === 'RECRUITMENT_START'} />
        </Flex>
        {isOwner && (
          <Link to={PATH.EDIT_STUDY(studyId)}>
            <TextButton fontSize="md" variant="secondary">
              수정
            </TextButton>
          </Link>
        )}
      </Flex>
      <Flex alignItems="center" gap="16px">
        <span>시작일: {changeDateSeperator(startDate)}</span> ~
        <span>종료일: {(endDate && changeDateSeperator(endDate)) || '없음'}</span>
      </Flex>
      <S.Excerpt>&quot;{excerpt}&quot;</S.Excerpt>
      <Flex gap="16px">
        {tags.map(({ id, name }) => (
          <span key={id}>#{name}</span>
        ))}
      </Flex>
    </Flex>
  );
};

export default Head;
