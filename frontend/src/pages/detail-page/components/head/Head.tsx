import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { StudyDetail } from '@custom-types';

import StudyChip from '@components/study-chip/StudyChip';
import TagChip from '@components/tag-chip/TagChip';

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
    <S.Head>
      <S.TitleContainer>
        <S.Title>
          <S.StudyTitle>{title}</S.StudyTitle>
          <StudyChip isOpen={recruitmentStatus === 'RECRUITMENT_START'} />
        </S.Title>
        {isOwner && (
          <S.ButtonsContainer>
            <Link to={PATH.EDIT_STUDY(studyId)}>
              <S.Button type="button">수정</S.Button>
            </Link>
          </S.ButtonsContainer>
        )}
      </S.TitleContainer>
      <S.ExtraInfoContainer>
        <span>시작일: {changeDateSeperator(startDate)}</span> ~
        <span>종료일: {(endDate && changeDateSeperator(endDate)) || '없음'}</span>
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
