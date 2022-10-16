import { forwardRef, useEffect, useRef, useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { isFunction, isObject } from '@utils';

import { UseFormRegister } from '@hooks/useForm';

import UnstyledButton from '@shared/button/unstyled-button/UnstyledButton';
import Center from '@shared/center/Center';
import ImportedDropDownBox, {
  type DropDownBoxProps as ImportedDropDownBoxProps,
} from '@shared/drop-down-box/DropDownBox';
import { DownArrowIcon, XMarkIcon } from '@shared/icons';

export type Option = {
  value: string | number;
  label: string;
};

export type MultiTagSelectProps = {
  options: Array<Option>;
  defaultSelectedOptions?: Array<Option>;
  invalid?: boolean;
} & Omit<UseFormRegister, 'ref'>;

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
      <Self>
        <SelectControl onClick={handleSelectControlClick} invalid={invalid}>
          <SelectedOptionList>
            {selectedOptions.map(option => (
              <SelectedOption key={option.value}>
                <SelectedOptionValue>{option.label}</SelectedOptionValue>
                <UnselectButton onClick={handleUnselectButtonClick(option)} />
              </SelectedOption>
            ))}
          </SelectedOptionList>
          <Indicators>
            {selectedOptions.length > 0 && (
              <Center>
                <AllClearButton onClick={handleAllClearButtonClick} />
              </Center>
            )}
            <Center>
              <DownArrowIcon />
            </Center>
          </Indicators>
        </SelectControl>
        {isOpenMenu && unSelectedOptions.length > 0 && (
          <DropDown isOpen={isOpenMenu} onClose={handleDropDownClose}>
            <ul>
              {unSelectedOptions.map(option => (
                <UnselectedOption key={option.value}>
                  <SelectButton onClick={handleSelectButtonClick(option)}>{option.label}</SelectButton>
                </UnselectedOption>
              ))}
            </ul>
          </DropDown>
        )}
        <input type="text" hidden ref={innerInputRef} {...registerReturns} />
      </Self>
    );
  },
);
MultiTagSelect.displayName = 'MultipleTagSelect';

export default MultiTagSelect;

const Self = styled.div`
  position: relative;
  width: 100%;
`;

const SelectControl = styled.div<{ invalid: boolean }>`
  ${({ theme, invalid }) => css`
    position: relative;

    display: flex;
    flex-wrap: wrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    align-items: center;

    min-height: 38px;

    -webkit-box-align: center;
    background-color: rgb(255, 255, 255);
    border-color: rgb(204, 204, 204);
    border-radius: 4px;
    border-style: solid;
    border-width: 1px;
    padding-right: 8px;

    ${invalid &&
    css`
      border: 1px solid ${theme.colors.red};

      &:focus {
        border: 1px solid ${theme.colors.red};
      }
    `}
  `}
`;

const SelectedOptionList = styled.ul`
  position: relative;

  display: flex;
  flex: 1 1 0%;
  flex-wrap: wrap;
  align-items: center;

  -webkit-box-align: center;
  padding: 2px 8px;
  overflow: hidden;
`;

const SelectedOption = styled.li`
  ${({ theme }) => css`
    display: flex;

    background-color: ${theme.colors.secondary.base};
    border-radius: 2px;
    margin: 2px;
    font-size: 14px;
  `}
`;

const SelectedOptionValue = styled.div`
  padding: 3px 3px 3px 6px;
  font-size: 12px;

  border-radius: 2px;
  color: rgb(51, 51, 51);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

type UnselectButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const UnselectButton: React.FC<UnselectButtonProps> = ({ onClick: handleClick }) => (
  <UnstyledButton
    onClick={handleClick}
    custom={{
      display: 'flex',
      alignItems: 'center',
      fontSize: 'sm',
      borderRadius: '2px',
      paddingLeft: '4px',
      paddingRight: '4px',
    }}
  >
    <Center>
      <XMarkIcon />
    </Center>
  </UnstyledButton>
);

const Indicators = styled.div`
  display: flex;
  align-items: center;
  align-self: stretch;
  -webkit-box-align: center;

  flex-shrink: 0;
  row-gap: 10px;
`;

type DropDownProps = { children?: React.ReactNode } & Pick<ImportedDropDownBoxProps, 'isOpen' | 'onClose'>;

const DropDown = ({ children, isOpen, onClose }: DropDownProps) => (
  <ImportedDropDownBox
    top={'calc(100% + 10px)'}
    left={0}
    right={0}
    isOpen={isOpen}
    onClose={onClose}
    custom={{ maxHeight: '180px', backgroundColor: 'white', boxShadow: '0 0 4px 0', borderRadius: '4px' }}
  >
    {children}
  </ImportedDropDownBox>
);

const UnselectedOption = styled.li`
  ${({ theme }) => css`
    font-size: 20px;
    &:hover {
      background-color: ${theme.colors.secondary.light}; // TODO: 색 세분화 필요
      color: white;
    }
  `}
`;

type SelectButtonProps = {
  children: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const SelectButton: React.FC<SelectButtonProps> = ({ children, onClick: handleClick }) => (
  <UnstyledButton
    onClick={handleClick}
    custom={{ width: '100%', height: '100%', padding: '10px', textAlign: 'left', fontSize: 'sm' }}
  >
    {children}
  </UnstyledButton>
);

type AllClearButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
export const AllClearButton: React.FC<AllClearButtonProps> = ({ onClick: handleClick }) => (
  <UnstyledButton onClick={handleClick}>
    <Center>
      <XMarkIcon />
    </Center>
  </UnstyledButton>
);
