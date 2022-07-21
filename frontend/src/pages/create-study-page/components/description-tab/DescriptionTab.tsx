import * as S from '@create-study-page/components/description-tab/DescriptionTab.style';
import cn from 'classnames';
import { useEffect, useState } from 'react';

import { useFormContext } from '@hooks/useForm';

import MarkdownRender from '@components/markdown-render/MarkdownRender';

const studyDescriptionTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof studyDescriptionTabIds[keyof typeof studyDescriptionTabIds];

const DescriptionTab = () => {
  const { register, getField } = useFormContext();

  const [markdownText, setMarkdownText] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(studyDescriptionTabIds.write);

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    const field = getField('markdown');
    if (!field) return;
    if (activeTab !== studyDescriptionTabIds.preview) return;

    const markdownText = field._ref.value;
    setMarkdownText(markdownText);
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
              <textarea {...register('markdown')}></textarea>
            </div>
          </div>
          <div className={cn('tab-panel', { active: activeTab === studyDescriptionTabIds.preview })}>
            <div className="tab-content">
              <MarkdownRender markdownContent={markdownText} />
            </div>
          </div>
        </div>
      </div>
    </S.DescriptionTab>
  );
};

export default DescriptionTab;
