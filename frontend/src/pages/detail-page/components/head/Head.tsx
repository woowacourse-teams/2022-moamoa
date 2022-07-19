import type { StudyDetail } from '@custom-types/index';

import StudyChip from '@components/study-chip/StudyChip';
import TagChip from '@components/tag-chip/TagChip';

import * as S from '@detail-page/components/head/Head.style';

type HeadProps = Pick<StudyDetail, 'title' | 'status' | 'startDate' | 'endDate' | 'excerpt' | 'tags'> & {
  countOfReviews?: number;
};

const Head: React.FC<HeadProps> = ({ title, status, countOfReviews, startDate, endDate, excerpt, tags }) => {
  return (
    <S.Head>
      <div className="title-container">
        <h1>{title}</h1>
        <StudyChip className="chip" isOpen={status === 'open'} />
      </div>
      <div className="extra-info-container">
        <span className="review-count">{countOfReviews ? `후기 ${countOfReviews}개` : 'loading...'}</span>
        <span>{`${startDate} ~ ${endDate}`}</span>
      </div>
      <h3 className="excerpt">{excerpt}</h3>
      <div className="tag-container">
        {tags.map(({ id, tagName }) => (
          <TagChip key={id} className="chip">
            {tagName}
          </TagChip>
        ))}
      </div>
    </S.Head>
  );
};

export default Head;
