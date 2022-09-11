import { useState } from 'react';

import { MEMBER_COUNT } from '@constants';

import tw from '@utils/tw';

import { StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';
import usePositiveNumberInput from '@hooks/usePositiveNumberInput';

import Checkbox from '@design/components/checkbox/Checkbox';
import Flex from '@design/components/flex/Flex';
import Input from '@design/components/input/Input';
import Label from '@design/components/label/Label';
import MetaBox from '@design/components/meta-box/MetaBox';

export type MaxMemberCountProps = {
  originalMaxMemberCount?: StudyDetail['maxMemberCount'];
};

const maxMemberCountName = 'max-member-count';

const MaxMemberCount = ({ originalMaxMemberCount }: MaxMemberCountProps) => {
  const [willSelectMaxMember, setWillSelectMaxMember] = useState<boolean>(originalMaxMemberCount ? true : false);

  const { removeField, register } = useFormContext();

  const { handleKeyDown } = usePositiveNumberInput();

  const handleNoSelectCheckboxChange = () => {
    setWillSelectMaxMember(prev => {
      if (prev) removeField(maxMemberCountName);
      return !prev;
    });
  };

  return (
    <MetaBox>
      <MetaBox.Title>스터디 최대 인원</MetaBox.Title>
      <MetaBox.Content>
        <Flex gap="8px">
          <Label htmlFor="no-select">선택 안함</Label>
          <Checkbox id="no-select" checked={!willSelectMaxMember} onChange={handleNoSelectCheckboxChange} />
        </Flex>
        {willSelectMaxMember && (
          <Flex gap="8px" alignItems="center">
            <Label htmlFor={maxMemberCountName}>최대 인원 :</Label>
            <div css={tw`flex-grow`}>
              <Input
                id={maxMemberCountName}
                type="number"
                fluid
                placeholder="최대 인원"
                defaultValue={originalMaxMemberCount}
                onKeyDown={handleKeyDown}
                {...register(maxMemberCountName, {
                  min: MEMBER_COUNT.MIN.VALUE,
                  max: MEMBER_COUNT.MAX.VALUE,
                })}
              />
            </div>
          </Flex>
        )}
      </MetaBox.Content>
    </MetaBox>
  );
};

export default MaxMemberCount;
