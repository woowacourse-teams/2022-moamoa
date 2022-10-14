import { useEffect, useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DESCRIPTION_LENGTH } from '@constants';

import type { StudyDetail } from '@custom-types';

import { type UseFormRegister, makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Label from '@shared/label/Label';
import MarkdownRender from '@shared/markdown-render/MarkdownRender';
import MetaBox from '@shared/meta-box/MetaBox';
import Textarea from '@shared/textarea/Textarea';

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
            <ToggleButton
              variant="secondary"
              checked={activeTab === tabMode.write}
              onClick={handleNavItemClick(tabMode.write)}
            >
              Write
            </ToggleButton>
            <ToggleButton
              variant="secondary"
              checked={activeTab === tabMode.preview}
              onClick={handleNavItemClick(tabMode.preview)}
            >
              Preview
            </ToggleButton>
          </ButtonGroup>
        </MetaBox.Title>
        <MetaBox.Content>
          <div
            css={css`
              height: 400px;
            `}
          >
            <WriteTab isOpen={isWriteTab} isValid={isValid} originalDescription={originalDescription ?? ''} />
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

type WriteTabProps = {
  isOpen: boolean;
  isValid: boolean;
  originalDescription: string;
};
const WriteTab: React.FC<WriteTabProps> = ({ isOpen, isValid, originalDescription }) => {
  const style = css`
    display: ${isOpen ? 'block' : 'none'};
    height: 100%;
  `;
  const { register } = useFormContext();

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
