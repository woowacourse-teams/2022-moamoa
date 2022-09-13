import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import { ToggleButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import Label from '@design/components/label/Label';
import MarkdownRender from '@design/components/markdown-render/MarkdownRender';
import MetaBox from '@design/components/meta-box/MetaBox';
import Textarea from '@design/components/textarea/Textarea';

const studyDescriptionTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof studyDescriptionTabIds[keyof typeof studyDescriptionTabIds];

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

  const [activeTab, setActiveTab] = useState<TabIds>(studyDescriptionTabIds.write);

  const isValid = !errors[DESCRIPTION]?.hasError;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField(DESCRIPTION);
    if (!field) return;
    if (activeTab !== studyDescriptionTabIds.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  const renderTabContent = () => {
    const isWriteTab = activeTab === studyDescriptionTabIds.write;

    return (
      <>
        <div css={isWriteTab ? tw`h-full` : tw`hidden`}>
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
        <div css={isWriteTab && tw`hidden`}>
          <MarkdownRender markdownContent={description} />
        </div>
      </>
    );
  };

  return (
    <div css={tw`mb-20`}>
      <MetaBox>
        <MetaBox.Title>
          <ButtonGroup gap="8px">
            <li>
              <ToggleButton
                variant="secondary"
                checked={activeTab === studyDescriptionTabIds.write}
                onClick={handleNavItemClick(studyDescriptionTabIds.write)}
              >
                Write
              </ToggleButton>
            </li>
            <li>
              <ToggleButton
                variant="secondary"
                checked={activeTab === studyDescriptionTabIds.preview}
                onClick={handleNavItemClick(studyDescriptionTabIds.preview)}
              >
                Preview
              </ToggleButton>
            </li>
          </ButtonGroup>
        </MetaBox.Title>
        <MetaBox.Content>
          <div css={tw`h-400`}>{renderTabContent()}</div>
        </MetaBox.Content>
      </MetaBox>
    </div>
  );
};

export default DescriptionTab;
