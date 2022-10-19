import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';

import type { DateYMD, Member } from '@custom-types';

import { IconButton, TextButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import DropDownBox from '@shared/drop-down-box/DropDownBox';
import Flex from '@shared/flex/Flex';
import { KebabMenuIcon } from '@shared/icons';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type CommentProps = {
  author: Member;
  date: DateYMD;
  content: string;
  isMyComment: boolean;
  onEditCommentButtonClick: React.MouseEventHandler<HTMLButtonElement>;
  onDeleteCommentButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const Comment: React.FC<CommentProps> = ({
  author,
  date,
  content,
  isMyComment,
  onEditCommentButtonClick: handleEditCommentButtonClick,
  onDeleteCommentButtonClick: handleDeleteCommentButtonClick,
}) => {
  const [isDropDownBoxOpen, setIsDropDownBoxOpen] = useState(false);

  const handleToggleButtonClick = () => {
    setIsDropDownBoxOpen(true);
  };
  const handleDropboxCloseButtonClick = () => {
    setIsDropDownBoxOpen(false);
  };

  return (
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
              isOpen={isDropDownBoxOpen}
              onClose={handleDropboxCloseButtonClick}
              top="24px"
              right="10px"
              custom={{ padding: '10px' }}
            >
              <ButtonGroup orientation="vertical">
                <EditButton onClick={handleEditCommentButtonClick} />
                <Divider />
                <DeleteButton onClick={handleDeleteCommentButtonClick} />
              </ButtonGroup>
            </DropDownBox>
          </CommentHead>
        )}
      </Flex>
      <Content content={content} />
    </Flex>
  );
};

export default Comment;

const CommentHead = styled.div`
  position: relative;
`;

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
