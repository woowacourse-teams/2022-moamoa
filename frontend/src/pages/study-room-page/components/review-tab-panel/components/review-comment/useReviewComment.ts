import { useCallback, useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import { QK_FETCH_STUDY_REVIEWS } from '@constants';

import type { DeleteReviewRequestBody, EmptyObject, ReviewId, StudyId } from '@custom-types';

import deleteReview from '@api/deleteReview';

const useReviewComment = (id: ReviewId, studyId: StudyId) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const { mutateAsync } = useMutation<EmptyObject, Error, DeleteReviewRequestBody>(deleteReview);
  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.refetchQueries([QK_FETCH_STUDY_REVIEWS, studyId]);
  };

  const handleDropDownClick = useCallback(() => {
    setIsOpen(prev => !prev);
  }, []);

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

  useEffect(() => {
    document.removeEventListener('click', handleDropDownClick);
    if (isOpen) {
      // 이벤트 전파가 끝나기 전에 document에 click event listener가 붙기 때문에
      // click event listener를 add하는 일을 다음 frame으로 늦춘다
      // Test: https://codepen.io/airman5573/pen/qBopRpO
      requestAnimationFrame(() => {
        document.addEventListener('click', handleDropDownClick);
      });
    }
  }, [isOpen, handleDropDownClick]);

  return {
    isOpen,
    isEditing,
    setIsEditing,
    handleDropDownClick,
    handleDeleteReviewBtnClick,
    handleEditReviewBtnClick,
    handleCancelEditBtnClick,
    handleEditSuccess,
    handleEditError,
  };
};

export default useReviewComment;
