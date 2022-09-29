import { css } from '@emotion/react';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import type { DateYMD, Member, ReviewId, StudyId } from '@custom-types';

import { IconButton, TextButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import DropDownBox from '@components/drop-down-box/DropDownBox';
import Flex from '@components/flex/Flex';
import { KebabMenuIcon } from '@components/icons';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

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
  const {
    isOpen,
    isEditing,
    handleKebabMenuClick,
    handleDropDownBoxClose,
    handleEditReviewBtnClick,
    handleDeleteReviewBtnClick,
    handleCancelEditBtnClick,
    handleEditSuccess,
    handleEditError,
  } = useReviewComment(id, studyId);

  const render = () => {
    if (isEditing) {
      return (
        <ReviewEditForm
          reviewId={id}
          studyId={studyId}
          originalContent={content}
          date={date}
          author={author}
          onCancelEditBtnClick={handleCancelEditBtnClick}
          onEditSuccess={handleEditSuccess}
          onEditError={handleEditError}
        />
      );
    }

    return (
      <Flex flexDirection="column" rowGap="12px">
        <Flex justifyContent="space-between" alignItems="center">
          <UserInfoItem src={author.imageUrl} name={author.username} size="md">
            <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
            <UserInfoItem.Content>{changeDateSeperator(date)}</UserInfoItem.Content>
          </UserInfoItem>
          {isMyComment && (
            <div css={tw`relative`}>
              <IconButton
                ariaLabel="리뷰 수정 삭제 메뉴"
                width="24px"
                height="24px"
                variant="secondary"
                onClick={handleKebabMenuClick}
              >
                <KebabMenuIcon />
              </IconButton>
              <DropDownBox isOpen={isOpen} onClose={handleDropDownBoxClose} top="24px" right="10px" padding="10px">
                <ButtonGroup orientation="vertical">
                  <TextButton variant="secondary" fontSize="sm" onClick={handleEditReviewBtnClick}>
                    수정
                  </TextButton>
                  <Divider />
                  <TextButton variant="secondary" fontSize="sm" onClick={handleDeleteReviewBtnClick}>
                    삭제
                  </TextButton>
                </ButtonGroup>
              </DropDownBox>
            </div>
          )}
        </Flex>
        <p
          css={css`
            word-break: break-all;
          `}
        >
          {content}
        </p>
      </Flex>
    );
  };

  return render();
};

export default ReviewComment;
