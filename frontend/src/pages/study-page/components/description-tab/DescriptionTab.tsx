import { useEffect, useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DESCRIPTION_LENGTH } from '@constants';

import type { StudyDetail } from '@custom-types';

import { UseFormRegister, makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Label from '@components/label/Label';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import MetaBox from '@components/meta-box/MetaBox';
import Textarea from '@components/textarea/Textarea';

const tabMode = {
  write: 'write',
  preview: 'preview',
} as const;

type TabIds = typeof tabMode[keyof typeof tabMode];

export type DescriptionTabProps = {
  originalDescription?: StudyDetail['description'];
};

const DESCRIPTION = 'description';

const DescriptionTab: React.FC<DescriptionTabProps> = ({ originalDescription }) => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(tabMode.write);

  const isValid = !errors[DESCRIPTION]?.hasError;

  const handleNavItemClick = (tabId: TabIds) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField(DESCRIPTION);
    if (!field) return;
    if (activeTab !== tabMode.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  const isWriteTab = activeTab === tabMode.write;

  return (
    <Self>
      <MetaBox>
        <MetaBox.Title>
          <ButtonGroup gap="8px">
            <WriteTabButton activeTab={activeTab} onClick={handleNavItemClick(tabMode.write)} />
            <PreviewTabButton activeTab={activeTab} onClick={handleNavItemClick(tabMode.preview)} />
          </ButtonGroup>
        </MetaBox.Title>
        <MetaBox.Content>
          <div
            css={css`
              height: 400px;
            `}
          >
            <WriteTab
              isOpen={isWriteTab}
              isValid={isValid}
              originalDescription={originalDescription ?? ''}
              register={register}
            />
            <MarkdownRendererTab isOpen={!isWriteTab} description={description} />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </Self>
  );
};

const Self = styled.div`
  margin-bottom: 20px;
`;

type WriteTabButtonProps = {
  activeTab: TabIds;
  onClick: () => void;
};
const WriteTabButton: React.FC<WriteTabButtonProps> = ({ activeTab, onClick: handleClick }) => (
  <ToggleButton variant="secondary" checked={activeTab === tabMode.write} onClick={handleClick}>
    Write
  </ToggleButton>
);

type PreviewTabButtonProps = WriteTabButtonProps;
const PreviewTabButton: React.FC<PreviewTabButtonProps> = ({ activeTab, onClick: handleClick }) => (
  <ToggleButton variant="secondary" checked={activeTab === tabMode.preview} onClick={handleClick}>
    Preview
  </ToggleButton>
);

type WriteTabProps = {
  isOpen: boolean;
  isValid: boolean;
  originalDescription: string;
  register: UseFormRegister;
};
const WriteTab: React.FC<WriteTabProps> = ({ isOpen, isValid, originalDescription, register }) => {
  const style = css`
    display: ${isOpen ? 'block' : 'none'};
    height: 100%;
  `;
  return (
    <div css={style}>
      <Label htmlFor={DESCRIPTION} hidden>
        소개글
      </Label>
      <Textarea
        id={DESCRIPTION}
        placeholder={`*스터디 소개글(${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
        invalid={!isValid}
        defaultValue={originalDescription}
        {...register(DESCRIPTION, {
          validate: (val: string) => {
            if (val.length < DESCRIPTION_LENGTH.MIN.VALUE) {
              return makeValidationResult(true, DESCRIPTION_LENGTH.MIN.MESSAGE);
            }
            return makeValidationResult(false);
          },
          validationMode: 'change',
          minLength: DESCRIPTION_LENGTH.MIN.VALUE,
          maxLength: DESCRIPTION_LENGTH.MAX.VALUE,
          required: true,
        })}
      ></Textarea>
    </div>
  );
};

type MarkdownRendererTabProps = {
  isOpen: boolean;
  description: string;
};
const MarkdownRendererTab: React.FC<MarkdownRendererTabProps> = ({ isOpen, description }) => {
  const style = css`
    display: ${isOpen ? 'block' : 'none'};
  `;
  return (
    <div css={style}>
      <MarkdownRender markdownContent={description} />
    </div>
  );
};

export default DescriptionTab;
