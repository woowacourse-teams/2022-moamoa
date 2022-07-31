import { useCallback, useEffect, useState } from 'react';

import { ReviewId } from '@custom-types';

const useReviewComment = (id: ReviewId) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const handleDropDownClick = useCallback(() => {
    setIsOpen(prev => !prev);
  }, []);

  const handleDeleteReviewBtnClick = () => {
    alert(`review-${id} 를 삭제했습니다`);
  };

  const handleEditReviewBtnClick = () => {
    alert(`review-${id} 를 삭제했습니다`);
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
    setIsOpen,
    handleDropDownClick,
    handleDeleteReviewBtnClick,
    handleEditReviewBtnClick,
  };
};

export default useReviewComment;
