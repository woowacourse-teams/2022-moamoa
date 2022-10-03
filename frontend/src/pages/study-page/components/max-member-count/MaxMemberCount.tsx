import { useState } from 'react';

import { MEMBER_COUNT } from '@constants';

import tw from '@utils/tw';

import { StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';
import usePositiveNumberInput from '@hooks/usePositiveNumberInput';

import Checkbox from '@components/checkbox/Checkbox';
import Flex from '@components/flex/Flex';
import Input from '@components/input/Input';
import Label from '@components/label/Label';
import MetaBox from '@components/meta-box/MetaBox';

export type MaxMemberCountProps = {
  originalMaxMemberCount?: StudyDetail['maxMemberCount'];
};

const MAX_MEMBER_COUNT = 'max-member-count';

const MaxMemberCount = ({ originalMaxMemberCount }: MaxMemberCountProps) => {
  const [isMaxMemberCountInputEnabled, setIsMaxMemberCountInputEnabled] = useState<boolean>(
    originalMaxMemberCount ? true : false,
  );

  const { removeField, register } = useFormContext();

  const { handleKeyDown } = usePositiveNumberInput();

  const handleNoSelectCheckboxChange = () => {
    setIsMaxMemberCountInputEnabled(prev => {
      if (prev) removeField(MAX_MEMBER_COUNT);
      return !prev;
    });
  };

  return (
    <MetaBox>
      <MetaBox.Title>스터디 최대 인원</MetaBox.Title>
      <MetaBox.Content>
        <Flex columnGap="8px">
          <Label htmlFor="no-select">선택 안함</Label>
          <Checkbox id="no-select" checked={!isMaxMemberCountInputEnabled} onChange={handleNoSelectCheckboxChange} />
        </Flex>
        {isMaxMemberCountInputEnabled && (
          <Flex columnGap="8px" alignItems="center">
            <Label htmlFor={MAX_MEMBER_COUNT}>최대 인원 :</Label>
            <div css={tw`flex-grow`}>
              <Input
                id={MAX_MEMBER_COUNT}
                type="number"
                fluid
                placeholder="최대 인원"
                defaultValue={originalMaxMemberCount}
                onKeyDown={handleKeyDown}
                {...register(MAX_MEMBER_COUNT, {
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