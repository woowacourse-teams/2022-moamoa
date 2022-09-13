import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';

import { ChevronDown, XMarkBase, XMarkSM } from '@components/svg';
import { UnstyledButton } from '@components/unstyled-button/UnstyledButton.style';

import * as S from '@create-study-page/components/multi-tag-select/MultiTagSelect.style';

type Option = {
  value: string | number;
  label: string;
};

export type MultiTagSelectProps = {
  name: string;
  options: Array<Option>;
  selectedOptoins?: Array<Option>;
};

const MultiTagSelect = forwardRef<HTMLInputElement, MultiTagSelectProps>(
  ({ selectedOptoins = [], options, name }, inputRef) => {
    const [isOpenMenu, setIsOpenMenu] = useState<boolean>(false);
    const [selectedOptions, setSelectedOptions] = useState<Array<Option>>(selectedOptoins);
    const innerInputRef = useRef() as React.MutableRefObject<HTMLInputElement>;
    const menuRef = useRef<HTMLDivElement>(null);

    const unSelectedOptions = options.filter(({ value }) => {
      return selectedOptions.findIndex(option => option.value === value) === -1;
    });

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
      <UnstyledButton onClick={handleAllClearButton}>
        <S.Center>
          <XMarkBase />
        </S.Center>
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
                  <S.Center>
                    <XMarkSM />
                  </S.Center>
                </S.RemoveSelectedOptionButton>
              </S.SelectedOptionListItem>
            ))}
          </S.SelectedOptionList>
          <S.Indicators>
            {selectedOptions.length > 0 && (
              <S.Center>
                <AllClearButton />
              </S.Center>
            )}
            <S.Center>
              <ChevronDown />
            </S.Center>
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
