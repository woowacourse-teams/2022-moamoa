import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import tw from '@utils/tw';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Label from '@components/label/Label';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
import MetaBox from '@components/meta-box/MetaBox';
import Textarea from '@components/textarea/Textarea';

type TabIds = typeof tabMode[keyof typeof tabMode];

export type EditContentProps = {
  content: string;
};

const tabMode = {
  write: 'write',
  preview: 'preview',
};

const CONTENT = 'content';

const EditContent: React.FC<EditContentProps> = ({ content }) => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(tabMode.write);

  const isValid = !errors[CONTENT]?.hasError;

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

  const renderTabContent = () => {
    const isWriteTab = activeTab === tabMode.write;

    return (
      <>
        <div css={isWriteTab ? tw`h-full` : tw`hidden`}>
          <Label htmlFor={CONTENT} hidden>
            소개글
          </Label>
          <Textarea
            id={CONTENT}
            placeholder={`게시글 내용 (${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
            invalid={!isValid}
            defaultValue={content}
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
        <div css={isWriteTab && tw`hidden`}>
          <MarkdownRender markdownContent={description} />
        </div>
      </>
    );
  };

  return (
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
        <div css={tw`h-[50vh]`}>{renderTabContent()}</div>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default EditContent;
