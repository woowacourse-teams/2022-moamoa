import type { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import { QK_FETCH_STUDY_REVIEWS } from '@constants';

import type { DeleteReviewRequestBody, ReviewId, StudyId } from '@custom-types';

import { deleteReview } from '@api';

const useReviewComment = (id: ReviewId, studyId: StudyId) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const { mutateAsync } = useMutation<null, AxiosError, DeleteReviewRequestBody>(deleteReview);
  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.refetchQueries([QK_FETCH_STUDY_REVIEWS, studyId]);
  };

  const handleKebabMenuClick = () => {
    setIsOpen(prev => !prev);
  };

  const handleDropDownBoxClose = () => {
    setIsOpen(false);
  };

  const handleDeleteReviewBtnClick = () => {
    mutateAsync({ reviewId: id, studyId })
      .then(() => {
        alert('성공적으로 삭제되었습니다');
        refetch();
      })
      .catch(() => {
        alert('알수없는 에러가 발생했습니다');
      });
  };

  const handleEditReviewBtnClick = () => {
    setIsEditing(true);
  };

  const handleCancelEditBtnClick = () => {
    setIsEditing(false);
  };

  const handleEditSuccess = () => {
    alert('성공적으로 수정했습니다!');
    setIsEditing(false);
    refetch();
  };
  const handleEditError = () => {
    alert('수정에 에러가 발생했습니다!');
  };

  return {
    isOpen,
    isEditing,
    setIsEditing,
    handleKebabMenuClick,
    handleDropDownBoxClose,
    handleDeleteReviewBtnClick,
    handleEditReviewBtnClick,
    handleCancelEditBtnClick,
    handleEditSuccess,
    handleEditError,
  };
};

export default useReviewComment;
