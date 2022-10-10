import { Link } from 'react-router-dom';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import { changeDateSeperator } from '@utils';

import type { StudyDetail } from '@custom-types';

import { TextButton } from '@components/button';
import Flex from '@components/flex/Flex';
import PageTitle from '@components/page-title/PageTitle';
import StudyChip from '@components/study-chip/StudyChip';

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
          <PageTitle>{title}</PageTitle>
          <StudyChip isOpen={recruitmentStatus === RECRUITMENT_STATUS.START} />
        </Flex>
        {isOwner && (
          <Link to={PATH.EDIT_STUDY(studyId)}>
            <TextButton variant="secondary">수정</TextButton>
          </Link>
        )}
      </Flex>
      <Flex alignItems="center" gap="16px">
        <span>시작일: {changeDateSeperator(startDate)}</span> ~
        <span>종료일: {(endDate && changeDateSeperator(endDate)) || '없음'}</span>
      </Flex>
      <S.Excerpt>&quot;{excerpt}&quot;</S.Excerpt>
      <Flex gap="16px" wrap>
        {tags.map(({ id, name }) => (
          <span key={id}>#{name}</span>
        ))}
      </Flex>
    </Flex>
  );
};

export default Head;
