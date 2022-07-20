import * as S from '@create-study-page/components/description-tab/DescriptionTab.style';
import cn from 'classnames';
import { useEffect, useRef, useState } from 'react';

import MarkdownRender from '@components/markdown-render/MarkdownRender';

const studyDescriptionTabIds = {
  write: 'write',
  preview: 'preview',
};

type TabIds = typeof studyDescriptionTabIds[keyof typeof studyDescriptionTabIds];

const DescriptionTab = () => {
  const markdownTextAreaRef = useRef<HTMLTextAreaElement | null>(null);
  const [markdownText, setMarkdownText] = useState<string>('');

  const [activeTab, setActiveTab] = useState<TabIds>(studyDescriptionTabIds.write);

  const handleNavItemClick = (tabId: string) => () => {
    setActiveTab(tabId);
  };

  useEffect(() => {
    if (!markdownTextAreaRef.current) return;
    if (activeTab !== studyDescriptionTabIds.preview) return;

    const markdownText = markdownTextAreaRef.current.value;
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
              <textarea ref={markdownTextAreaRef}></textarea>
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
