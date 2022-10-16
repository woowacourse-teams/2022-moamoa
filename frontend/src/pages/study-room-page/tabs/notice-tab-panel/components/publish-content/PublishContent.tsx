import { useEffect, useState } from 'react';

import { css } from '@emotion/react';

import { DESCRIPTION_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Label from '@shared/label/Label';
import MarkdownRender from '@shared/markdown-render/MarkdownRender';
import MetaBox from '@shared/meta-box/MetaBox';
import Textarea from '@shared/textarea/Textarea';

type TabIds = typeof tabMode[keyof typeof tabMode];

const tabMode = {
  write: 'write',
  preview: 'preview',
};

const CONTENT = 'content';

const PublishContent: React.FC = () => {
  const {
    formState: { errors },
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(tabMode.write);

  const isValid = !errors[CONTENT]?.hasError;

  const isWriteTab = activeTab === tabMode.write;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField(CONTENT);
    if (!field) return;
    if (activeTab !== tabMode.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  return (
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
            height: 50vh;
          `}
        >
          <WriteTab isOpen={isWriteTab} isValid={isValid} />
          <MarkdownRendererTab isOpen={!isWriteTab} description={description} />
        </div>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default PublishContent;

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
};
const WriteTab: React.FC<WriteTabProps> = ({ isOpen, isValid }) => {
  const { register } = useFormContext();
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
  `;
  return (
    <div css={style}>
      <MarkdownRender markdownContent={description} />
    </div>
  );
};
