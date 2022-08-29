import * as S from '@notice-tab/components/edit-content/EditContent.style';
import { useEffect, useState } from 'react';

import { DESCRIPTION_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import MarkdownRender from '@components/markdown-render/MarkdownRender';

const EditContentTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof EditContentTabIds[keyof typeof EditContentTabIds];

export type EditContentProps = {
  content: string;
};

const EditContent: React.FC<EditContentProps> = ({ content }) => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(EditContentTabIds.write);

  const isValid = !!errors['content']?.hasError;

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField('content');
    if (!field) return;
    if (activeTab !== EditContentTabIds.preview) return;

    const description = field.fieldElement.value;
    setDescription(description);
  }, [activeTab]);

  return (
    <S.EditContent>
      <S.TabListContainer>
        <S.TabList>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === EditContentTabIds.write}
              onClick={handleNavItemClick(EditContentTabIds.write)}
            >
              Write
            </S.TabItemButton>
          </S.Tab>
          <S.Tab>
            <S.TabItemButton
              type="button"
              isActive={activeTab === EditContentTabIds.preview}
              onClick={handleNavItemClick(EditContentTabIds.preview)}
            >
              Preview
            </S.TabItemButton>
          </S.Tab>
        </S.TabList>
      </S.TabListContainer>
      <S.TabPanelsContainer>
        <S.TabPanels>
          <S.TabPanel isActive={activeTab === EditContentTabIds.write}>
            <S.TabContent>
              <S.Textarea
                id="description"
                placeholder={`게시글 내용 (${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
                isValid={isValid}
                defaultValue={content}
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
          <S.TabPanel isActive={activeTab === EditContentTabIds.preview}>
            <S.TabContent>
              <MarkdownRender markdownContent={description} />
            </S.TabContent>
          </S.TabPanel>
        </S.TabPanels>
      </S.TabPanelsContainer>
    </S.EditContent>
  );
};

export default EditContent;
