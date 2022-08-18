import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import type { StudyDetail } from '@custom-types';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import MarkdownRender from '@components/markdown-render/MarkdownRender';

import * as S from '@create-study-page/components/description-tab/DescriptionTab.style';

const studyDescriptionTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof studyDescriptionTabIds[keyof typeof studyDescriptionTabIds];

export type DescriptionTabProps = {
  originalDescription?: StudyDetail['description'];
};

const DescriptionTab: React.FC<DescriptionTabProps> = ({ originalDescription }) => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(studyDescriptionTabIds.write);

  const isValid = !!errors['description']?.hasError;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField('description');
    if (!field) return;
    if (activeTab !== studyDescriptionTabIds.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  return (
    <S.DescriptionTab>
      <S.TabListContainer>
        <S.TabList>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === studyDescriptionTabIds.write}
              onClick={handleNavItemClick(studyDescriptionTabIds.write)}
            >
              Write
            </S.TabItemButton>
          </S.Tab>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === studyDescriptionTabIds.preview}
              onClick={handleNavItemClick(studyDescriptionTabIds.preview)}
            >
              Preview
            </S.TabItemButton>
          </S.Tab>
        </S.TabList>
      </S.TabListContainer>
      <S.TabPanelsContainer>
        <S.TabPanels>
          <S.TabPanel isActive={activeTab === studyDescriptionTabIds.write}>
            <S.TabContent>
              {/* TODO: HiddenLabel Component 생성 */}
              <S.Label htmlFor="description">소개글</S.Label>
              <S.Textarea
                id="description"
                placeholder={`*스터디 소개글(${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
                isValid={isValid}
                defaultValue={originalDescription}
                {...register('description', {
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
              ></S.Textarea>
            </S.TabContent>
          </S.TabPanel>
          <S.TabPanel isActive={activeTab === studyDescriptionTabIds.preview}>
            <S.TabContent>
              <MarkdownRender markdownContent={description} />
            </S.TabContent>
          </S.TabPanel>
        </S.TabPanels>
      </S.TabPanelsContainer>
    </S.DescriptionTab>
  );
};

export default DescriptionTab;
