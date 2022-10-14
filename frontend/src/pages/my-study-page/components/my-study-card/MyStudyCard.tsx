import React from 'react';

import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { DateYMD, Tag } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import { IconButton } from '@shared/button';
import Card from '@shared/card/Card';
import Flex from '@shared/flex/Flex';
import { CrownIcon, TrashcanIcon } from '@shared/icons';

export type MyStudyCardProps = {
  title: string;
  ownerName: string;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  startDate: DateYMD;
  endDate?: DateYMD;
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
  return (
    <Self done={done}>
      <Card shadow custom={{ height: '100%', padding: '16px', gap: '8px', position: 'relative' }}>
        <Card.Heading>{title}</Card.Heading>
        <Card.Content custom={{ fontSize: 'md' }}>
          <Owner name={ownerName} />
          <TagList tags={tags} />
          <Period startDate={startDate} endDate={endDate} />
        </Card.Content>
        <QuitStudyButton onClick={handleQuitStudyButtonClick} />
      </Card>
    </Self>
  );
};

export default MyStudyCard;

const doneStyle = (theme: Theme) => css`
  & * {
    color: ${theme.colors.secondary.dark} !important;
  }

  & svg {
    stroke: ${theme.colors.secondary.dark} !important;
  }
`;

type SelfProps = {
  children: React.ReactNode;
} & Pick<MyStudyCardProps, 'done'>;
const Self: React.FC<SelfProps> = ({ children, done }) => {
  const theme = useTheme();
  return (
    <div
      css={css`
        height: 100%;

        ${done && doneStyle(theme)}

        ${applyHoverTransitionStyle()}
      `}
    >
      {children}
    </div>
  );
};

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
  return (
    <div
      css={css`
        ${nLineEllipsis(1)};
        margin-bottom: 10px;
        line-height: 20px;
      `}
    >
      <Flex columnGap="8px" flexWrap="wrap">
        {tags.map(({ id, name }) => (
          <span key={id}>#{name}</span>
        ))}
      </Flex>
    </div>
  );
};

type PeriodProps = {
  startDate: DateYMD;
  endDate?: DateYMD;
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
  return (
    <div
      css={css`
        position: absolute;
        bottom: 12px;
        right: 12px;
        z-index: 3;
      `}
    >
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
