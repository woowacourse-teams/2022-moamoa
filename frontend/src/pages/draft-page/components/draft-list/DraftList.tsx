import { Fragment } from 'react';

import type { ArticleId, DraftArtcle, StudyDetail, StudyId } from '@custom-types';

import Divider from '@shared/divider/Divider';

import DraftListItem from '@draft-page/components/draft-list-item/DraftListItem';

export type DraftListProps = {
  articles: Array<
    Omit<DraftArtcle, 'content'> & {
      study: {
        id: StudyId;
        title: StudyDetail['title'];
      };
    }
  >;
  onDeleteDraftItemClick: (studyId: StudyId, articleId: ArticleId) => React.MouseEventHandler<HTMLButtonElement>;
};

const DraftList: React.FC<DraftListProps> = ({ articles, onDeleteDraftItemClick: handleDeleteDraftItemClick }) => {
  return (
    <ul>
      {articles.map(article => (
        <Fragment key={article.id}>
          <DraftListItem
            id={article.id}
            study={article.study}
            title={article.title}
            createdDate={article.createdDate}
            onDeleteDraftItemClick={handleDeleteDraftItemClick(article.study.id, article.id)}
          />
          <Divider />
        </Fragment>
      ))}
    </ul>
  );
};

export default DraftList;
