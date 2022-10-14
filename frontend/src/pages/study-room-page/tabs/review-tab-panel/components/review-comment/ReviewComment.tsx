import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';

import type { DateYMD, Member, ReviewId, StudyId } from '@custom-types';

import { IconButton, TextButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import DropDownBox from '@shared/drop-down-box/DropDownBox';
import Flex from '@shared/flex/Flex';
import { KebabMenuIcon } from '@shared/icons';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

import useReviewComment from '@review-tab/components/review-comment/useReviewComment';
import ReviewEditForm from '@review-tab/components/review-edit-form/ReviewEditForm';

export type ReviewCommentProps = {
  id: ReviewId;
  studyId: StudyId;
  author: Member;
  date: DateYMD;
  content: string;
  isMyComment: boolean;
};

const ReviewComment: React.FC<ReviewCommentProps> = ({ id, studyId, author, date, content, isMyComment }) => {
  const theme = useTheme();
  const {
    isOpen,
    isEditing,
    handleKebabMenuClick,
    handleDropboxCloseButtonClick,
    handleCancelEditButtonClick,
    handleDeleteReviewButtonClick,
    handleEditReviewButtonClick,
    handleEditSuccess,
    handleEditError,
  } = useReviewComment(id, studyId);

  return (
    <>
      {isEditing && (
        <ReviewEditForm
          reviewId={id}
          studyId={studyId}
          originalContent={content}
          date={date}
          author={author}
          onCancelEditBtnClick={handleCancelEditButtonClick}
          onEditSuccess={handleEditSuccess}
          onEditError={handleEditError}
        />
      )}
      {!isEditing && (
        <Self
          theme={theme}
          author={author}
          isMyComment={isMyComment}
          isOpen={isOpen}
          date={date}
          content={content}
          onToggleButtonClick={handleKebabMenuClick}
          onDropBoxCloseButtonClick={handleDropboxCloseButtonClick}
          onEditReivewButtonClick={handleEditReviewButtonClick}
          onDeleteReivewButtonClick={handleDeleteReviewButtonClick}
        />
      )}
    </>
  );
};

const CommentHead = styled.div`
  position: relative;
`;

type SelfProps = {
  theme: Theme;
  isOpen: boolean;
  content: string;
  onToggleButtonClick: React.MouseEventHandler<HTMLButtonElement>;
  onDropBoxCloseButtonClick: () => void;
  onEditReivewButtonClick: () => void;
  onDeleteReivewButtonClick: () => void;
} & Omit<ReviewCommentProps, 'id' | 'studyId'>;
const Self: React.FC<SelfProps> = ({
  author,
  isMyComment,
  isOpen,
  date,
  content,
  onToggleButtonClick: handleToggleButtonClick,
  onDropBoxCloseButtonClick: handleDropBoxButtonClick,
  onEditReivewButtonClick: handleEditReviewButtonClick,
  onDeleteReivewButtonClick: handleDeleteReviewButtonClick,
}) => (
  <Flex flexDirection="column" rowGap="12px">
    <Flex justifyContent="space-between" alignItems="center" fluid>
      <UserInfoItem src={author.imageUrl} name={author.username} size="md">
        <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        <UserInfoItem.Content>{changeDateSeperator(date)}</UserInfoItem.Content>
      </UserInfoItem>
      {isMyComment && (
        <CommentHead>
          <ToggleButton onClick={handleToggleButtonClick} />
          <DropDownBox
            isOpen={isOpen}
            onClose={handleDropBoxButtonClick}
            top="24px"
            right="10px"
            custom={{ padding: '10px' }}
          >
            <ButtonGroup orientation="vertical">
              <EditButton onClick={handleEditReviewButtonClick} />
              <Divider />
              <DeleteButton onClick={handleDeleteReviewButtonClick} />
            </ButtonGroup>
          </DropDownBox>
        </CommentHead>
      )}
    </Flex>
    <Content content={content} />
  </Flex>
);

type ContentProps = {
  content: string;
};
const Content: React.FC<ContentProps> = ({ content }) => (
  <p
    css={css`
      word-break: break-all;
    `}
  >
    {content}
  </p>
);

type EditButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const EditButton: React.FC<EditButtonProps> = ({ onClick: handleClick }) => (
  <TextButton variant="secondary" onClick={handleClick} custom={{ fontSize: 'sm' }}>
    수정
  </TextButton>
);

type DeleteButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const DeleteButton: React.FC<DeleteButtonProps> = ({ onClick: handleClick }) => (
  <TextButton variant="secondary" onClick={handleClick} custom={{ fontSize: 'sm' }}>
    삭제
  </TextButton>
);

type ToggleButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const ToggleButton: React.FC<ToggleButtonProps> = ({ onClick: handleClick }) => (
  <IconButton
    ariaLabel="리뷰 수정 삭제 메뉴"
    variant="secondary"
    onClick={handleClick}
    custom={{ width: '24px', height: '24px' }}
  >
    <KebabMenuIcon />
  </IconButton>
);

export default ReviewComment;
