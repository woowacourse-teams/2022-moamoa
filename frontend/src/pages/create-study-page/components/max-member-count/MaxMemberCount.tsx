import * as S from '@create-study-page/components/max-member-count/MaxMemberCount.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

import { css } from '@emotion/react';

type MaxMemberCountProps = {
  className?: string;
};

const MaxMemberCount = ({ className }: MaxMemberCountProps) => {
  return (
    <S.MaxMemberCount className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 최대 인원</MetaBox.Title>
        <MetaBox.Content>
          {/* TODO: Perfect Number Input사용하기  */}
          <label
            htmlFor="max-member-count"
            css={css`
              margin-right: 10px;
            `}
          >
            최대 인원 :{' '}
          </label>
          <input id="max-member-count" type="number" placeholder="최대 인원" />
        </MetaBox.Content>
      </MetaBox>
    </S.MaxMemberCount>
  );
};

export default MaxMemberCount;
