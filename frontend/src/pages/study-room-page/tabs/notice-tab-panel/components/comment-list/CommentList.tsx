import React from 'react';

import styled from '@emotion/styled';

import { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@components/@shared/divider/Divider';

import EditableComment from '@study-room-page/tabs/notice-tab-panel/components/editable-comment/EditableComment';

type CommentListProps = {
  articleId: ArticleId;
  studyId: StudyId;
  comments: Array<CommunityComment>;
};
const CommentList: React.FC<CommentListProps> = ({ studyId, articleId, comments }) => {
  const { userInfo } = useUserInfo();
  return (
    <Self>
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
    </Self>
  );
};

export default CommentList;

const Self = styled.div``;
