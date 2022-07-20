import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/tag/Tag.style';

import { css } from '@emotion/react';

type TagProps = {
  className?: string;
};

const Tag = ({ className }: TagProps) => {
  return (
    <S.Tag className={className}>
      <MetaBox>
        <MetaBox.Title>태그</MetaBox.Title>
        <MetaBox.Content>
          <select
            defaultValue={'4기'}
            css={css`
              width: 100%;
            `}
          >
            <option value="js">JS</option>
            <option value="java">Java</option>
            <option value="react">React</option>
            <option value="spring">Spring</option>
          </select>
        </MetaBox.Content>
      </MetaBox>
    </S.Tag>
  );
};

export default Tag;
