import type { StudyDetail } from '@custom-types/index';

import StudyChip from '@components/study-chip/StudyChip';
import TagChip from '@components/tag-chip/TagChip';

import * as S from '@detail-page/components/head/Head.style';

export type HeadProps = Pick<StudyDetail, 'title' | 'status' | 'startDate' | 'endDate' | 'excerpt' | 'tags'>;

const Head: React.FC<HeadProps> = ({ title, status, startDate, endDate, excerpt, tags }) => {
  return (
    <S.Head>
      <S.TitleContainer>
        <S.StudyTitle>{title}</S.StudyTitle>
        <StudyChip isOpen={status === 'OPEN'} />
      </S.TitleContainer>
      <S.ExtraInfoContainer>
        <span>시작일: {startDate}</span> ~ <span>종료일: {endDate || '없음'}</span>
      </S.ExtraInfoContainer>
      <S.Excerpt>&quot;{excerpt}&quot;</S.Excerpt>
      <S.TagContainer>
        {tags.map(({ id, name }) => (
          <TagChip key={id}>{name}</TagChip>
        ))}
      </S.TagContainer>
    </S.Head>
  );
};

export default Head;
