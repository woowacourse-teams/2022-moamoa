import { useState } from 'react';

import { MEMBER_COUNT } from '@constants';

import type { StudyDetail } from '@custom-types';

import { type UseFormRegister, useFormContext } from '@hooks/useForm';
import usePositiveNumberInput from '@hooks/usePositiveNumberInput';

import Checkbox from '@shared/checkbox/Checkbox';
import Flex from '@shared/flex/Flex';
import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';

export type MaxMemberCountProps = {
  originalMaxMemberCount?: StudyDetail['maxMemberCount'];
};

const MAX_MEMBER_COUNT = 'max-member-count';

const MaxMemberCount = ({ originalMaxMemberCount }: MaxMemberCountProps) => {
  const [isMaxMemberCountInputEnabled, setIsMaxMemberCountInputEnabled] = useState<boolean>(
    originalMaxMemberCount ? true : false,
  );

  const { removeField } = useFormContext();

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
        <ToggleCheckbox isChecked={!isMaxMemberCountInputEnabled} onClick={handleNoSelectCheckboxChange} />
        {isMaxMemberCountInputEnabled && (
          <MaxMemberCountField defaultValue={originalMaxMemberCount ?? 0} onKeyDown={handleKeyDown} />
        )}
      </MetaBox.Content>
    </MetaBox>
  );
};

type ToggleCheckboxProps = {
  isChecked: boolean;
  onClick?: React.ChangeEventHandler<HTMLInputElement>;
};
const ToggleCheckbox: React.FC<ToggleCheckboxProps> = ({ isChecked, onClick: handleClick }) => (
  <Flex columnGap="8px">
    <Label htmlFor="no-select">선택 안함</Label>
    <Checkbox id="no-select" checked={isChecked} onChange={handleClick} />
  </Flex>
);

type MaxMemberCountFieldProps = {
  defaultValue: number;
  onKeyDown: React.KeyboardEventHandler<HTMLInputElement>;
};
const MaxMemberCountField: React.FC<MaxMemberCountFieldProps> = ({ defaultValue, onKeyDown: handleKeyDown }) => {
  const { removeField, register } = useFormContext();

  return (
    <Flex columnGap="8px" alignItems="center">
      <Label htmlFor={MAX_MEMBER_COUNT}>최대 인원 :</Label>
      <Flex.Item flexGrow={1}>
        <Input
          id={MAX_MEMBER_COUNT}
          type="number"
          fluid
          placeholder="최대 인원"
          defaultValue={defaultValue}
          onKeyDown={handleKeyDown}
          {...register(MAX_MEMBER_COUNT, {
            min: MEMBER_COUNT.MIN.VALUE,
            max: MEMBER_COUNT.MAX.VALUE,
          })}
        />
      </Flex.Item>
    </Flex>
  );
};

export default MaxMemberCount;
