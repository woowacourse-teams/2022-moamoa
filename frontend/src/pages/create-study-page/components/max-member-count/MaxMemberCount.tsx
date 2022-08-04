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

const maxMemberCountName = 'max-member-count';

const MaxMemberCount = ({ className }: MaxMemberCountProps) => {
  const [willSelectMaxMember, setWillSelectMaxMember] = useState<boolean>(true);
  const [count, setCount] = useState<number>(1);

  const { removeField } = useFormContext();

  const { register } = useFormContext();

  const handleNoSelectCheckboxChange = () => {
    setWillSelectMaxMember(prev => {
      if (prev) removeField(maxMemberCountName);
      return !prev;
    });
  };

  return (
    <S.MaxMemberCount className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 최대 인원</MetaBox.Title>
        <MetaBox.Content>
          <div
            css={css`
              display: flex;
              margin-bottom: 8px;
            `}
          >
            <label htmlFor="no-select">선택 안함</label>
            <input
              css={css`
                margin-left: 4px;
              `}
              type="checkbox"
              id="no-select"
              checked={!willSelectMaxMember}
              onChange={handleNoSelectCheckboxChange}
            />
          </div>
          {willSelectMaxMember && (
            <>
              <label
                htmlFor={maxMemberCountName}
                css={css`
                  margin-right: 10px;
                `}
              >
                최대 인원 :
              </label>
              <PositiveNumberInput
                {...register(maxMemberCountName, {
                  min: MEMBER_COUNT.MIN.VALUE,
                  max: MEMBER_COUNT.MAX.VALUE,
                })}
                id={maxMemberCountName}
                placeholder="최대 인원"
                value={count}
                onChange={value => {
                  setCount(Number(value));
                }}
              ></PositiveNumberInput>
            </>
          )}
        </MetaBox.Content>
      </MetaBox>
    </S.MaxMemberCount>
  );
};

export default MaxMemberCount;
