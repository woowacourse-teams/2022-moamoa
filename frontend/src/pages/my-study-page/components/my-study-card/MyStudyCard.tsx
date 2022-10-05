import React from 'react';

import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { Tag } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import { IconButton } from '@components/button';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';
import { CrownIcon, TrashcanIcon } from '@components/icons';

export type MyStudyCardProps = {
  title: string;
  ownerName: string;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  startDate: string;
  endDate?: string;
  done?: boolean;
  onQuitStudyButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const MyStudyCard: React.FC<MyStudyCardProps> = ({
  title,
  ownerName,
  tags,
  startDate,
  endDate,
  done = false,
  onQuitStudyButtonClick: handleQuitStudyButtonClick,
}) => {
  const theme = useTheme();
  return (
    <Self done={done}>
      <Card shadow custom={{ height: '100%', padding: '16px', gap: '8px', position: 'relative' }}>
        <Card.Heading>{title}</Card.Heading>
        <Card.Content custom={{ fontSize: theme.fontSize.md }}>
          <Owner name={ownerName} />
          <TagList tags={tags} />
          <Period startDate={startDate} endDate={endDate} />
        </Card.Content>
        <QuitStudyButton onClick={handleQuitStudyButtonClick} />
      </Card>
    </Self>
  );
};

type StyledMyStudyCardProps = Pick<MyStudyCardProps, 'done'>;

const doneStyle = (theme: Theme) => css`
  & * {
    color: ${theme.colors.secondary.dark} !important;
  }

  & svg {
    stroke: ${theme.colors.secondary.dark} !important;
  }
`;

const Self = styled.div<StyledMyStudyCardProps>`
  ${({ theme, done }) => css`
    height: 100%;

    ${done && doneStyle(theme)}

    ${applyHoverTransitionStyle()}
  `}
`;

type OwnerProps = {
  name: string;
};
const Owner: React.FC<OwnerProps> = ({ name }) => (
  <div
    css={css`
      margin-bottom: 20px;
    `}
  >
    <CrownIcon />
    <span>{name}</span>
  </div>
);

type TagListProps = {
  tags: MyStudyCardProps['tags'];
};
const TagList: React.FC<TagListProps> = ({ tags }) => {
  const style = css`
    ${nLineEllipsis(1)};
    margin-bottom: 10px;
    line-height: 20px;
  `;
  return (
    <div css={style}>
      <Flex columnGap="8px" flexWrap="wrap">
        {tags.map(({ id, name }) => (
          <span key={id}>#{name}</span>
        ))}
      </Flex>
    </div>
  );
};

type PeriodProps = {
  startDate: string;
  endDate?: string;
};
const Period: React.FC<PeriodProps> = ({ startDate, endDate }) => (
  <div
    css={css`
      margin-bottom: 16px;
    `}
  >
    <span>{startDate}</span> ~ <span>{endDate || ''}</span>
  </div>
);

type QuitStudyButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const QuitStudyButton: React.FC<QuitStudyButtonProps> = ({ onClick: handleClick }) => {
  const style = css`
    position: absolute;
    bottom: 12px;
    right: 12px;
    z-index: 3;
  `;
  return (
    <div css={style}>
      <IconButton
        variant="secondary"
        onClick={handleClick}
        ariaLabel="스터디 탈퇴"
        custom={{ width: 'auto', height: 'auto' }}
      >
        <TrashcanIcon />
      </IconButton>
    </div>
  );
};

export default MyStudyCard;
