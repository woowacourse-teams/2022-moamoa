import { Link } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import { changeDateSeperator } from '@utils';

import type { DateYMD, StudyDetail, StudyId, Tag } from '@custom-types';

import { TextButton } from '@shared/button';
import Flex from '@shared/flex/Flex';
import PageTitle from '@shared/page-title/PageTitle';

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
      <Flex justifyContent="space-between" alignItems="center" fluid>
        <Flex alignItems="center" columnGap="16px">
          <PageTitle>{title}</PageTitle>
          <StudyChip isOpen={recruitmentStatus === RECRUITMENT_STATUS.START} />
        </Flex>
        {isOwner && <EditPageLinkButton studyId={studyId} />}
      </Flex>
      <Period startDate={startDate} endDate={endDate} />
      <Excerpt>{excerpt}</Excerpt>
      <TagList tags={tags} />
    </Flex>
  );
};

export default Head;

const Excerpt = styled.p`
  ${({ theme }) => css`
    padding: 8px 0 16px;

    font-size: ${theme.fontSize.xl};
  `}
`;

type EditPageLinkButtonProps = {
  studyId: StudyId;
};
const EditPageLinkButton: React.FC<EditPageLinkButtonProps> = ({ studyId }) => (
  <Link to={PATH.EDIT_STUDY(studyId)}>
    <TextButton variant="secondary">수정</TextButton>
  </Link>
);

type TagListProps = {
  tags: Array<Tag>;
};
const TagList: React.FC<TagListProps> = ({ tags }) => (
  <Flex columnGap="16px">
    {tags.map(({ id, name }) => (
      <span key={id}>#{name}</span>
    ))}
  </Flex>
);

type PeriodProps = {
  startDate: DateYMD;
  endDate?: DateYMD;
};
const Period: React.FC<PeriodProps> = ({ startDate, endDate }) => (
  <Flex alignItems="center" columnGap="16px">
    <span>시작일: {changeDateSeperator(startDate)}</span> ~
    <span>종료일: {(endDate && changeDateSeperator(endDate)) || '없음'}</span>
  </Flex>
);
