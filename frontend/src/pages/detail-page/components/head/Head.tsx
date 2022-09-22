import { Link } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { StudyDetail } from '@custom-types';

import { TextButton } from '@components/button';
import Flex from '@components/flex/Flex';
import PageTitle from '@components/page-title/PageTitle';
import StudyChip from '@components/study-chip/StudyChip';

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
    <Flex flexDirection="column" rowGap="4px">
      <Flex justifyContent="space-between" alignItems="center">
        <Flex alignItems="center" columnGap="16px">
          <PageTitle>{title}</PageTitle>
          <StudyChip isOpen={recruitmentStatus === 'RECRUITMENT_START'} />
        </Flex>
        {isOwner && (
          <Link to={PATH.EDIT_STUDY(studyId)}>
            <TextButton variant="secondary">수정</TextButton>
          </Link>
        )}
      </Flex>
      <Flex alignItems="center" columnGap="16px">
        <span>시작일: {changeDateSeperator(startDate)}</span> ~
        <span>종료일: {(endDate && changeDateSeperator(endDate)) || '없음'}</span>
      </Flex>
      <Excerpt>&quot;{excerpt}&quot;</Excerpt>
      <Flex columnGap="16px">
        {tags.map(({ id, name }) => (
          <span key={id}>#{name}</span>
        ))}
      </Flex>
    </Flex>
  );
};

const Excerpt = styled.p`
  ${({ theme }) => css`
    padding: 8px 0 16px;

    font-size: ${theme.fontSize.xl};
  `}
`;

export default Head;
