import { Fragment } from 'react';

import styled from '@emotion/styled';

import type { ArticleId, DraftArtcle, StudyDetail, StudyId } from '@custom-types';

import Divider from '@shared/divider/Divider';
import InfiniteScroll, { type InfiniteScrollProps } from '@shared/infinite-scroll/InfiniteScroll';

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
} & Omit<InfiniteScrollProps, 'children'>;

const DraftList: React.FC<DraftListProps> = ({
  articles,
  isContentLoading,
  onContentLoad: handleContentLoad,
  onDeleteDraftItemClick: handleDeleteDraftItemClick,
}) => {
  return (
    <InfiniteScroll isContentLoading={isContentLoading} onContentLoad={handleContentLoad}>
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
    </InfiniteScroll>
  );
};

export default DraftList;

const Self = styled.ul``;
