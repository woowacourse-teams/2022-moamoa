import { Fragment } from 'react';

import styled from '@emotion/styled';

import type { ArticleId, DraftArtcle, StudyId } from '@custom-types';

import Divider from '@shared/divider/Divider';

import DraftListItem from '@draft-page/components/draft-list-item/DraftListItem';

export type DraftListProps = {
  articles: Array<Omit<DraftArtcle, 'content'>>;
};

const DraftList: React.FC<DraftListProps> = ({ articles }) => {
  const handleDeleteDraftItemClick = (studyId: StudyId, articleId: ArticleId) => () => {
    console.log(studyId, articleId);
  };

  return (
    <Self>
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
    </Self>
  );
};

export default DraftList;

const Self = styled.ul``;
