import * as S from '@notice-tab/components/publish-content/PublishContent.style';
import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import MarkdownRender from '@design/components/markdown-render/MarkdownRender';

const PublishContentTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof PublishContentTabIds[keyof typeof PublishContentTabIds];

const PublishContent = () => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(PublishContentTabIds.write);

  const isValid = !!errors['content']?.hasError;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField('content');
    if (!field) return;
    if (activeTab !== PublishContentTabIds.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  return (
    <S.PublishContent>
      <S.TabListContainer>
        <S.TabList>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === PublishContentTabIds.write}
              onClick={handleNavItemClick(PublishContentTabIds.write)}
            >
              Write
            </S.TabItemButton>
          </S.Tab>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === PublishContentTabIds.preview}
              onClick={handleNavItemClick(PublishContentTabIds.preview)}
            >
              Preview
            </S.TabItemButton>
          </S.Tab>
        </S.TabList>
      </S.TabListContainer>
      <S.TabPanelsContainer>
        <S.TabPanels>
          <S.TabPanel isActive={activeTab === PublishContentTabIds.write}>
            <S.TabContent>
              <S.Textarea
                id="description"
                placeholder={`게시글 내용 (${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
                isValid={isValid}
                {...register('content', {
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
          <S.TabPanel isActive={activeTab === PublishContentTabIds.preview}>
            <S.TabContent>
              <MarkdownRender markdownContent={description} />
            </S.TabContent>
          </S.TabPanel>
        </S.TabPanels>
      </S.TabPanelsContainer>
    </S.PublishContent>
  );
};

export default PublishContent;
