import * as S from '@review-page/components/review-comment/ReviewComment.style';
import { useCallback, useEffect, useState } from 'react';

import { PROFILE_IMAGE_URL } from '@constants';

import Avatar from '@components/avatar/Avatar';
import DotDotDot from '@components/dotdotdot/DotDotDot';

export type ReviewCommentProps = {
  id: number;
};

const ReviewComment: React.FC<ReviewCommentProps> = ({ id }) => {
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
      requestAnimationFrame(() => {
        document.addEventListener('click', handleDropDownClick);
      });
    }
  }, [isOpen, handleDropDownClick]);

  return (
    <S.ReviewComment>
      <div className="top">
        <div className="left">
          <S.Author>
            <Avatar profileImg={PROFILE_IMAGE_URL} profileAlt="프로필 이미지" size="sm" />
            <S.Name>nan-noo</S.Name>
          </S.Author>
        </div>
        <div className="right">
          <S.Date>2022.08.13</S.Date>
          <S.DropDown isOpen={isOpen}>
            <DotDotDot onClick={handleDropDownClick} />
            <ul className="menu">
              <li>
                <button onClick={handleEditReviewBtnClick}>수정</button>
              </li>
              <li>
                <button onClick={handleDeleteReviewBtnClick}>삭제</button>
              </li>
            </ul>
          </S.DropDown>
        </div>
      </div>
      <div className="bottom">
        <div className="content">
          후기후기 라라라랄라ㅏ abcedfsf rksk가나다라 마바사ㅏ 아자ㅏ 아런아렁날
          ㅓㅇㄴㄹㄴ아렁나러ㅏㄴ어라먼아러낭ㄹㅇㄴㄹㄴㅇ 낭런아럼;닌아럼;니아런;이ㅏ런일아아아앙 나나나나나나
          ㅏㅇㄹㅇㄹ알ㅇㄹㅇㄹㅇ ㄴㄹ날가나다라가나다라 가낟 ㄴ안ㅇ란ㅇㄹ ㅇ ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹ
          ㅇㄴㄹㅁㅇㄴㄴㅇㄹㅇㄴㄹ ㄹㅇㄴㄹㅁㄴㅇㄹㄴㅇㄹㄴㅇㄹ ㄹㅇㄴㅁㄴㅇㄹㅁㄴㅇㄹㄴㅇㄹㄴ ㅇㄹㅇ
        </div>
      </div>
    </S.ReviewComment>
  );
};

export default ReviewComment;
