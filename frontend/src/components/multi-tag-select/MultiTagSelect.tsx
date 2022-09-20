import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';

import tw from '@utils/tw';

import UnstyledButton from '@components/button/unstyled-button/UnstyledButton';
import Center from '@components/center/Center';
import DownArrowIcon from '@components/icons/down-arrow-icon/DownArrowIcon';
import XMarkIcon from '@components/icons/x-mark-icon/XMarkIcon';
import * as S from '@components/multi-tag-select/MultiTagSelect.style';

type Option = {
  value: string | number;
  label: string;
};

export type MultiTagSelectProps = {
  name: string;
  options: Array<Option>;
  defaultSelectedOptions?: Array<Option>;
};

const MultiTagSelect = forwardRef<HTMLInputElement | undefined, MultiTagSelectProps>(
  ({ defaultSelectedOptions = [], options, name }, inputRef) => {
    const [isOpenMenu, setIsOpenMenu] = useState<boolean>(false);
    const [selectedOptions, setSelectedOptions] = useState<Array<Option>>(defaultSelectedOptions);
    const innerInputRef = useRef<HTMLInputElement>(null);
    const menuRef = useRef<HTMLDivElement>(null);

    const unSelectedOptions = options.filter(
      ({ value }) => selectedOptions.findIndex(option => option.value === value) === -1,
    );

    const serializedSelectedValues = selectedOptions.map(({ value }) => value).join(',');

    useEffect(() => {
      if (!isOpenMenu || !menuRef.current) return;
      const handleClose = (event: MouseEvent) => {
        if (event.target === null) return;
        if (event.target === menuRef.current) return;
        const path = event.composedPath();
        const isMenuClicked = path.findIndex(el => el === menuRef.current) > 0;
        !isMenuClicked && setIsOpenMenu(false);
      };

      requestAnimationFrame(() => document.body.addEventListener('click', handleClose));
      return () => document.body.removeEventListener('click', handleClose);
    }, [isOpenMenu]);

    useImperativeHandle(
      inputRef,
      () => {
        if (!innerInputRef.current) return;
        innerInputRef.current.value = serializedSelectedValues;
        return innerInputRef.current;
      },
      [serializedSelectedValues],
    );

    const handleSelectControlClick = () => {
      setIsOpenMenu(prev => !prev);
    };

    const handleSelectMenuItemClick = (option: Option) => () => {
      setSelectedOptions(prev => [...prev, option]);
    };

    const handleRemoveSelectValueButtonClick = (option: Option) => (event: React.MouseEvent<HTMLButtonElement>) => {
      event.stopPropagation(); // 전파를 멈춰야 handleSelectControlClick이 호출되지 않는다

      setSelectedOptions(prev => {
        return prev.filter(({ value }) => option.value !== value);
      });
    };

    const handleAllClearButton = (event: React.MouseEvent<HTMLButtonElement>) => {
      event.stopPropagation();

      setSelectedOptions([]);
      setIsOpenMenu(true);
    };

    const AllClearButton = () => (
      <UnstyledButton css={tw`text-18`} onClick={handleAllClearButton}>
        <Center>
          <XMarkIcon />
        </Center>
      </UnstyledButton>
    );

    return (
      <S.Container>
        <S.SelectControl onClick={handleSelectControlClick}>
          <S.SelectedOptionList>
            {selectedOptions.map(option => (
              <S.SelectedOptionListItem key={option.value}>
                <S.SelectedOption>{option.label}</S.SelectedOption>
                <S.RemoveSelectedOptionButton onClick={handleRemoveSelectValueButtonClick(option)}>
                  <Center>
                    <XMarkIcon />
                  </Center>
                </S.RemoveSelectedOptionButton>
              </S.SelectedOptionListItem>
            ))}
          </S.SelectedOptionList>
          <S.Indicators>
            {selectedOptions.length > 0 && (
              <Center>
                <AllClearButton />
              </Center>
            )}
            <Center>
              <DownArrowIcon />
            </Center>
          </S.Indicators>
        </S.SelectControl>
        {isOpenMenu && unSelectedOptions.length > 0 && (
          <S.SelectMenuContainer ref={menuRef}>
            <ul>
              {unSelectedOptions.map(option => (
                <S.SelectMenuItem key={option.value}>
                  <S.SelectMenuItemButton onClick={handleSelectMenuItemClick(option)}>
                    {option.label}
                  </S.SelectMenuItemButton>
                </S.SelectMenuItem>
              ))}
            </ul>
          </S.SelectMenuContainer>
        )}
        <input hidden name={name} ref={innerInputRef} />
      </S.Container>
    );
  },
);
MultiTagSelect.displayName = 'MultipleTagSelect';

export default MultiTagSelect;
