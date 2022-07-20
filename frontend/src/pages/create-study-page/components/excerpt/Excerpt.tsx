import * as S from '@create-study-page/components/excerpt/Excerpt.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

type ExcerptProps = {
  className?: string;
};

const Excerpt = ({ className }: ExcerptProps) => {
  return (
    <S.Excerpt className={className}>
      <MetaBox>
        <MetaBox.Title>한줄소개</MetaBox.Title>
        <MetaBox.Content>
          <textarea placeholder="한줄소개를 입력해주세요" />
        </MetaBox.Content>
      </MetaBox>
    </S.Excerpt>
  );
};

export default Excerpt;
