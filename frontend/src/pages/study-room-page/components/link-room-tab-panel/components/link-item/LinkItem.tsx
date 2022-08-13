import { useState } from 'react';

import tw from '@utils/tw';

import type { Link } from '@custom-types';

import DropDownBox from '@components/drop-down-box/DropDownBox';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem.style';
import LinkPreview from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview';
import UserDescription from '@study-room-page/components/link-room-tab-panel/components/user-description/UserDescription';

export type LinkItemProps = Pick<Link, 'author' | 'description' | 'linkUrl'>;

const previewResult = {
  title: '합성 컴포넌트 어쩌구 저쩌구 쏼라쏼라',
  description: '카카오 엔터테인먼트 FE 기술 블로그',
  imageUrl:
    'https://images.unsplash.com/photo-1572059002053-8cc5ad2f4a38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGdvb2dsZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60',
  domainName: 'naver.com',
};

const MeatballMenuSvg = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 20 20"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z"></path>
  </svg>
);

const LinkItem: React.FC<LinkItemProps> = ({ linkUrl, author, description }) => {
  // TODO: link preview 가져오기
  const [isOpenDropBox, setIsOpenDropBox] = useState(false);

  const handleMeatballMenuClick = () => {
    setIsOpenDropBox(prev => !prev);
  };

  const handleDropDownBoxClose = () => setIsOpenDropBox(false);
  const handleEditLinkButtonnClick = () => {
    // TOOD: open modal
  };
  const handleDeleteLinkButtonClick = () => {
    // TODO mutate
  };

  return (
    <S.LinkItemContainer>
      {previewResult && (
        <>
          <S.PreviewMeatballMenuContainer>
            <S.MeatballMenuButton aria-label="수정 삭제 메뉴 버튼" type="button" onClick={handleMeatballMenuClick}>
              <MeatballMenuSvg />
            </S.MeatballMenuButton>
            {isOpenDropBox && (
              <DropDownBox onClose={handleDropDownBoxClose} top="36px" right="-32px">
                <S.DropBoxButtons>
                  <li>
                    <S.DropBoxButton type="button" onClick={handleEditLinkButtonnClick}>
                      수정
                    </S.DropBoxButton>
                  </li>
                  <li>
                    <S.DropBoxButton type="button" onClick={handleDeleteLinkButtonClick}>
                      삭제
                    </S.DropBoxButton>
                  </li>
                </S.DropBoxButtons>
              </DropDownBox>
            )}
          </S.PreviewMeatballMenuContainer>
          <a href={linkUrl}>
            <LinkPreview previewResult={previewResult} />
          </a>
        </>
      )}
      <UserDescription author={author} description={description} css={tw`pl-8 pr-8`} />
    </S.LinkItemContainer>
  );
};

export default LinkItem;
