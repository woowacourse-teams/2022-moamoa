import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import tw from '@utils/tw';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import Label from '@design/components/label/Label';
import MarkdownRender from '@design/components/markdown-render/MarkdownRender';
import MetaBox from '@design/components/meta-box/MetaBox';
import Textarea from '@design/components/textarea/Textarea';

type TabIds = typeof editContentTabIds[keyof typeof editContentTabIds];

export type EditContentProps = {
  content: string;
};

const editContentTabIds = {
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

  const [activeTab, setActiveTab] = useState<TabIds>(editContentTabIds.write);

  const isValid = !errors[CONTENT]?.hasError;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField(CONTENT);
    if (!field) return;
    if (activeTab !== editContentTabIds.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  const renderTabContent = () => {
    const isWriteTab = activeTab === editContentTabIds.write;

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
          <li>
            <ToggleButton
              variant="secondary"
              checked={activeTab === editContentTabIds.write}
              onClick={handleNavItemClick(editContentTabIds.write)}
            >
              Write
            </ToggleButton>
          </li>
          <li>
            <ToggleButton
              variant="secondary"
              checked={activeTab === editContentTabIds.preview}
              onClick={handleNavItemClick(editContentTabIds.preview)}
            >
              Preview
            </ToggleButton>
          </li>
        </ButtonGroup>
      </MetaBox.Title>
      <MetaBox.Content>
        <div css={tw`h-[50vh]`}>{renderTabContent()}</div>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default EditContent;
