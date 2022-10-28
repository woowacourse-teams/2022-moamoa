import React from 'react';

import type { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useUserInfo } from '@hooks/useUserInfo';

import Divider from '@shared/divider/Divider';

import EditableComment from '@community-tab/components/editable-comment/CommunityEditableComment';

// 여기서 api호출을 하지 않는 이유는, 위쪽에서 comment를 작성하고 refetch를 해줘야 하기 때문이다
// 그냥 useGetCommunityComments를 두번 호출하는건 별로인가?
type CommunityCommentListProps = {
  articleId: ArticleId;
  studyId: StudyId;
  comments: Array<CommunityComment>;
};
const CommunityCommentList: React.FC<CommunityCommentListProps> = ({ studyId, articleId, comments }) => {
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

export default CommunityCommentList;
