import { useCallback, useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import { QK_FETCH_STUDY_REVIEWS } from '@constants';

import { DeleteReviewQueryData, EmptyObject, ReviewId, StudyId } from '@custom-types';

import { deleteReview } from '@api/deleteReview';

const useReviewComment = (id: ReviewId, studyId: StudyId) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const { mutateAsync } = useMutation<EmptyObject, Error, DeleteReviewQueryData>(deleteReview);
  const queryClient = useQueryClient();

  const handleDropDownClick = useCallback(() => {
    setIsOpen(prev => !prev);
  }, []);

  const handleDeleteReviewBtnClick = () => {
    alert('리뷰 삭제');
    mutateAsync({ reviewId: id, studyId })
      .then(() => {
        alert('성공적으로 삭제되었습니다');
        queryClient.refetchQueries([QK_FETCH_STUDY_REVIEWS, studyId]);
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
    handleDropDownClick,
    handleDeleteReviewBtnClick,
    handleEditReviewBtnClick,
    handleCancelEditBtnClick,
  };
};

export default useReviewComment;
