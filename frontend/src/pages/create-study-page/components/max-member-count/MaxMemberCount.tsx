import { useState } from 'react';

import { MEMBER_COUNT } from '@constants';

import { useFormContext } from '@hooks/useForm';
import usePositiveNumberInput from '@hooks/usePositiveNumberInput';

import * as S from '@create-study-page/components/max-member-count/MaxMemberCount.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

type MaxMemberCountProps = {
  className?: string;
};

const maxMemberCountName = 'max-member-count';

const MaxMemberCount = ({ className }: MaxMemberCountProps) => {
  const [willSelectMaxMember, setWillSelectMaxMember] = useState<boolean>(true);

  const { removeField, register } = useFormContext();

  const { handleKeyDown } = usePositiveNumberInput();

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
          <S.Container>
            <S.Label htmlFor="no-select">선택 안함</S.Label>
            <S.Checkbox id="no-select" checked={!willSelectMaxMember} onChange={handleNoSelectCheckboxChange} />
          </S.Container>
          {willSelectMaxMember && (
            <>
              <S.Label htmlFor={maxMemberCountName}>최대 인원 :</S.Label>
              <S.Input
                {...register(maxMemberCountName, {
                  min: MEMBER_COUNT.MIN.VALUE,
                  max: MEMBER_COUNT.MAX.VALUE,
                })}
                onKeyDown={handleKeyDown}
                type="number"
                id={maxMemberCountName}
                placeholder="최대 인원"
              />
            </>
          )}
        </MetaBox.Content>
      </MetaBox>
    </S.MaxMemberCount>
  );
};

export default MaxMemberCount;
