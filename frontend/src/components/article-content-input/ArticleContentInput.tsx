import { useEffect, useState } from 'react';

import { css } from '@emotion/react';

import { CONTENT, DESCRIPTION_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Label from '@shared/label/Label';
import MarkdownRender from '@shared/markdown-render/MarkdownRender';
import MetaBox from '@shared/meta-box/MetaBox';
import Textarea from '@shared/textarea/Textarea';

type TabIds = typeof tabMode[keyof typeof tabMode];

export type ArticleContentInputProps = {
  originalContent?: string;
};

const tabMode = {
  write: 'write',
  preview: 'preview',
};

const ArticleContentInput: React.FC<ArticleContentInputProps> = ({ originalContent }) => {
  const { getField } = useFormContext();

  const [description, setDescription] = useState<string>(originalContent ?? '');
  const [activeTab, setActiveTab] = useState<TabIds>(tabMode.write);

  const handleTabButtonClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    if (activeTab !== tabMode.preview) return;

    const contentField = getField(CONTENT);
    if (!contentField) return;

    setDescription(contentField.fieldElement.value);
  }, [activeTab]);

  const isWriteTab = activeTab === tabMode.write;

  return (
    <MetaBox>
      <MetaBox.Title>
        <ButtonGroup gap="8px">
          <WriteTabButton activeTab={activeTab} onClick={handleTabButtonClick(tabMode.write)} />
          <PreviewTabButton activeTab={activeTab} onClick={handleTabButtonClick(tabMode.preview)} />
        </ButtonGroup>
      </MetaBox.Title>
      <MetaBox.Content>
        <div
          css={css`
            height: 50vh;
          `}
        >
          <WriteTab isOpen={isWriteTab} defaultValue={originalContent ?? ''} />
          <MarkdownRendererTab isOpen={!isWriteTab} description={description} />
        </div>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default ArticleContentInput;

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
  defaultValue: string;
};
const WriteTab: React.FC<WriteTabProps> = ({ isOpen, defaultValue }) => {
  const {
    register,
    formState: { errors },
  } = useFormContext();

  const isValid = !errors[CONTENT]?.hasError;

  const style = css`
    display: ${isOpen ? 'block' : 'none'};
    height: 100%;
  `;
  return (
    <div css={style}>
      <Label htmlFor={CONTENT} hidden>
        소개글
      </Label>
      <Textarea
        id={CONTENT}
        placeholder={`게시글 내용 (${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
        invalid={!isValid}
        defaultValue={defaultValue}
        {...register(CONTENT, {
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
    height: 100%;
    overflow: auto;
  `;
  return (
    <div css={style}>
      <MarkdownRender markdownContent={description} />
    </div>
  );
};
