import { css } from '@emotion/react';

import type { StudyDetail } from '@custom-types/index';

import Chip from '@components/Chip';
import StudyChip from '@components/StudyChip';

type HeadProps = Pick<StudyDetail, 'title' | 'status' | 'startDate' | 'endDate' | 'excerpt' | 'tags'> & {
  countOfReviews?: number;
};

const Head: React.FC<HeadProps> = ({ title, status, countOfReviews, startDate, endDate, excerpt, tags }) => {
  return (
    <div>
      <div
        css={css`
          display: flex;
          column-gap: 20px;
          margin-bottom: 20px;
        `}
      >
        <h1>{title}</h1>
        <StudyChip isOpen={status === 'open'} />
      </div>
      <div
        css={css`
          margin-bottom: 20px;
        `}
      >
        <span
          css={css`
            margin-right: 20px;
          `}
        >
          {countOfReviews ? `후기 ${countOfReviews}개` : 'loading...'}
        </span>
        <span>{`${startDate} ~ ${endDate}`}</span>
      </div>
      <h3
        css={css`
          margin-bottom: 20px;
        `}
      >
        {excerpt}
      </h3>
      <div
        css={css`
          display: flex;
          column-gap: 15px;
        `}
      >
        {tags.map(({ id, tagName }) => (
          <Chip key={id} disabled>
            {tagName}
          </Chip>
        ))}
      </div>
    </div>
  );
};

export default Head;
