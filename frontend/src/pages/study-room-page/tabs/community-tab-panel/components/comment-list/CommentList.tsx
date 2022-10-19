import styled from '@emotion/styled';

import { ArticleId, CommunityComment, StudyId } from '@custom-types';

import { useUserInfo } from '@hooks/useUserInfo';

import EditableComment from '@study-room-page/tabs/community-tab-panel/components/editable-comment/EditableComment';

// 여기서 api호출을 하지 않는 이유는, 위쪽에서 comment를 작성하고 refetch를 해줘야 하기 때문이다
// 그냥 useGetCommunityComments를 두번 호출하는건 별로인가?
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
        {comments.map(({ id, member, lastModifiedDate, content }) => (
          <li key={id}>
            <EditableComment
              id={id}
              articleId={articleId}
              studyId={studyId}
              author={member}
              date={lastModifiedDate}
              content={content}
              isMyComment={userInfo.id === member.id}
            />
          </li>
        ))}
      </ul>
    </Self>
  );
};

export default CommentList;

const Self = styled.div`
  background-color: green;
`;
