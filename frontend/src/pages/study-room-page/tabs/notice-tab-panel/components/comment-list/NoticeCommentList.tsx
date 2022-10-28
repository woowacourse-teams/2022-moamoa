import React from 'react';

import type { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@shared/divider/Divider';

import EditableComment from '@notice-tab/components/editable-comment/NoticeEditableComment';

type NoticeCommentListProps = {
  articleId: ArticleId;
  studyId: StudyId;
  comments: Array<CommunityComment>;
};
const NoticeCommentList: React.FC<NoticeCommentListProps> = ({ studyId, articleId, comments }) => {
  const { userInfo } = useUserInfo();
  return (
    <div>
      <ul>
        {comments.map(({ id, author, lastModifiedDate, content }) => (
          <React.Fragment key={id}>
            <li>
              <EditableComment
                id={id}
                articleId={articleId}
                studyId={studyId}
                author={author}
                date={lastModifiedDate}
                content={content}
                isMyComment={userInfo.id === author.id}
              />
            </li>
            <Divider space="30px" />
          </React.Fragment>
        ))}
      </ul>
    </div>
  );
};

export default NoticeCommentList;
