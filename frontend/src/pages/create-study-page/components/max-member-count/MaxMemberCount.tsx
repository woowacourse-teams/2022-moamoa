import { useState } from 'react';

import { css } from '@emotion/react';

import { MEMBER_COUNT } from '@constants';

import { useFormContext } from '@hooks/useForm';

import PositiveNumberInput from '@components/positive-number-input/PositiveNumberInput';

import * as S from '@create-study-page/components/max-member-count/MaxMemberCount.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

type MaxMemberCountProps = {
  className?: string;
};

const MaxMemberCount = ({ className }: MaxMemberCountProps) => {
  const [count, setCount] = useState<number>(0);
  const { register } = useFormContext();

  return (
    <S.MaxMemberCount className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 최대 인원</MetaBox.Title>
        <MetaBox.Content>
          <label
            htmlFor="max-member-count"
            css={css`
              margin-right: 10px;
            `}
          >
            최대 인원 :
          </label>
          <PositiveNumberInput
            {...register('max-member-count', {
              min: MEMBER_COUNT.MIN.VALUE,
              max: MEMBER_COUNT.MAX.VALUE,
            })}
            id="max-member-count"
            placeholder="최대 인원"
            value={count}
            onChange={value => {
              setCount(Number(value));
            }}
          ></PositiveNumberInput>
        </MetaBox.Content>
      </MetaBox>
    </S.MaxMemberCount>
  );
};

export default MaxMemberCount;
