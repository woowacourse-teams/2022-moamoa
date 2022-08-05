import cn from 'classnames';
import { useEffect, useState } from 'react';

import { css } from '@emotion/react';

import { DESCRIPTION_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import MarkdownRender from '@components/markdown-render/MarkdownRender';

import * as S from '@create-study-page/components/description-tab/DescriptionTab.style';

const studyDescriptionTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof studyDescriptionTabIds[keyof typeof studyDescriptionTabIds];

const DescriptionTab = () => {
  const {
    formState: { errors },
    register,
    getField,
  } = useFormContext();

  const [description, setDescription] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(studyDescriptionTabIds.write);

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
      <div className="tab-list-container">
        <ul className="tab-list">
          <li className="tab">
            <button
              className={cn('tab-item-button', { active: activeTab === studyDescriptionTabIds.write })}
              type="button"
              onClick={handleNavItemClick(studyDescriptionTabIds.write)}
            >
              Write
            </button>
          </li>
          <li className="tab">
            <button
              className={cn('tab-item-button', { active: activeTab === studyDescriptionTabIds.preview })}
              type="button"
              onClick={handleNavItemClick(studyDescriptionTabIds.preview)}
            >
              Preview
            </button>
          </li>
        </ul>
      </div>
      <div className="tab-panels-container">
        <div className="tab-panels">
          <div className={cn('tab-panel', { active: activeTab === studyDescriptionTabIds.write })}>
            <div className="tab-content">
              <label // TODO: HiddenLabel Component 생성
                htmlFor="description"
                css={css`
                  display: block;

                  height: 0;
                  width: 0;

                  visibility: hidden;
                `}
              >
                소개글
              </label>
              <textarea
                id="description"
                placeholder={`*스터디 소개글(${DESCRIPTION_LENGTH.MAX.VALUE}자 제한)`}
                className={cn({ invalid: !!errors['description']?.hasError })}
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
              ></textarea>
            </div>
          </div>
          <div className={cn('tab-panel', { active: activeTab === studyDescriptionTabIds.preview })}>
            <div className="tab-content">
              <MarkdownRender markdownContent={description} />
            </div>
          </div>
        </div>
      </div>
    </S.DescriptionTab>
  );
};

export default DescriptionTab;
