import { forwardRef, useEffect, useRef, useState } from 'react';

import { isFunction, isObject } from '@utils';

import { type UseFormRegisterReturn } from '@hooks/useForm';

import Center from '@components/center/Center';
import DownArrowIcon from '@components/icons/down-arrow-icon/DownArrowIcon';
import XMarkIcon from '@components/icons/x-mark-icon/XMarkIcon';
import * as S from '@components/multi-tag-select/MultiTagSelect.style';

type Option = {
  value: string | number;
  label: string;
};

export type MultiTagSelectProps = {
  options: Array<Option>;
  defaultSelectedOptions?: Array<Option>;
  invalid?: boolean;
} & Omit<UseFormRegisterReturn, 'ref'>;

const MultiTagSelect = forwardRef<HTMLInputElement, MultiTagSelectProps>(
  ({ defaultSelectedOptions = [], options, invalid = false, ...registerReturns }, inputRef) => {
    const [isOpenMenu, setIsOpenMenu] = useState<boolean>(false);
    const [selectedOptions, setSelectedOptions] = useState<Array<Option>>(defaultSelectedOptions);
    const innerInputRef = useRef<HTMLInputElement>(null);

    const unSelectedOptions = options.filter(({ value }) => {
      const hasOption = selectedOptions.some(option => option.value === value);
      return !hasOption;
    });

    const serializedSelectedValues = selectedOptions.map(({ value }) => value).join(',');

    // 아래 방식대로 하면 input ChangeEvent가 발생하지 않음!
    useEffect(() => {
      if (!innerInputRef.current) return;
      if (!inputRef) return;
      if (isFunction(inputRef)) {
        inputRef(innerInputRef.current);
        innerInputRef.current.value = serializedSelectedValues;
        return;
      }
      if (isObject(inputRef)) {
        inputRef.current = innerInputRef.current;
        innerInputRef.current.value = serializedSelectedValues;
        return;
      }
      console.error('ref의 타입이 올바르지 않습니다');
    }, [inputRef, serializedSelectedValues]);

    const handleSelectControlClick = () => {
      setIsOpenMenu(prev => !prev);
    };

    const handleSelectButtonClick = (option: Option) => () => {
      setSelectedOptions(prev => [...prev, option]);
    };

    const handleUnselectButtonClick = (option: Option) => (e: React.MouseEvent<HTMLButtonElement>) => {
      e.stopPropagation(); // 전파를 멈춰야 handleSelectControlClick이 호출되지 않는다

      setSelectedOptions(prev => prev.filter(({ value }) => option.value !== value));
    };

    const handleAllClearButtonClick = (e: React.MouseEvent<HTMLButtonElement>) => {
      e.stopPropagation();

      setSelectedOptions([]);
      setIsOpenMenu(true);
    };

    const handleDropDownClose = () => setIsOpenMenu(false);

    return (
      <S.Container>
        <S.SelectControl onClick={handleSelectControlClick} invalid={invalid}>
          <S.SelectedOptionList>
            {selectedOptions.map(option => (
              <S.SelectedOption key={option.value}>
                <S.SelectedOptionValue>{option.label}</S.SelectedOptionValue>
                <S.UnselectButton fontSize="sm" onClick={handleUnselectButtonClick(option)}>
                  <Center>
                    <XMarkIcon />
                  </Center>
                </S.UnselectButton>
              </S.SelectedOption>
            ))}
          </S.SelectedOptionList>
          <S.Indicators>
            {selectedOptions.length > 0 && (
              <Center>
                <S.AllClearButton fontSize="md" onClick={handleAllClearButtonClick}>
                  <Center>
                    <XMarkIcon />
                  </Center>
                </S.AllClearButton>
              </Center>
            )}
            <Center>
              <DownArrowIcon />
            </Center>
          </S.Indicators>
        </S.SelectControl>
        {isOpenMenu && unSelectedOptions.length > 0 && (
          <S.DropDown top={'calc(100% + 10px)'} left={0} right={0} isOpen={isOpenMenu} onClose={handleDropDownClose}>
            <ul>
              {unSelectedOptions.map(option => (
                <S.UnselectedOption key={option.value}>
                  <S.SelectButton fontSize="sm" onClick={handleSelectButtonClick(option)}>
                    {option.label}
                  </S.SelectButton>
                </S.UnselectedOption>
              ))}
            </ul>
          </S.DropDown>
        )}
        <input type="text" hidden ref={innerInputRef} {...registerReturns} />
      </S.Container>
    );
  },
);
MultiTagSelect.displayName = 'MultipleTagSelect';

export default MultiTagSelect;
