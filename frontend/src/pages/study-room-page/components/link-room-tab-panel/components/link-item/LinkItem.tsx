import tw from '@utils/tw';

import type { Link, StudyId } from '@custom-types';

import DropDownBox from '@components/drop-down-box/DropDownBox';
import ModalPortal from '@components/modal/Modal';
import { MeatballMenuSvg } from '@components/svg';

import LinkEditForm from '@study-room-page/components/link-room-tab-panel/components/link-edit-form/LinkEditForm';
import * as S from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem.style';
import { useLinkItem } from '@study-room-page/components/link-room-tab-panel/components/link-item/hooks/useLinkItem';
import LinkPreview from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview';
import UserDescription from '@study-room-page/components/link-room-tab-panel/components/user-description/UserDescription';

export type LinkItemProps = Pick<Link, 'id' | 'author' | 'description' | 'linkUrl'> & { studyId: StudyId };

const previewResult = {
  title: '합성 컴포넌트 어쩌구 저쩌구 쏼라쏼라',
  description: '카카오 엔터테인먼트 FE 기술 블로그',
  imageUrl:
    'https://images.unsplash.com/photo-1572059002053-8cc5ad2f4a38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGdvb2dsZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60',
  domainName: 'naver.com',
};

const LinkItem: React.FC<LinkItemProps> = ({ studyId, id: linkId, linkUrl, author, description }) => {
  const {
    isOpenDropBox,
    isModalOpen,
    handleMeatballMenuClick,
    handleDropDownBoxClose,
    handleModalClose,
    handleEditLinkButtonClick,
    handleDeleteLinkButtonClick,
    handlePutLinkSuccess,
    handlePutLinkError,
  } = useLinkItem({ studyId, linkId });

  const originalContent = {
    linkUrl,
    description,
  };

  return (
    <>
      <S.LinkItemContainer>
        <S.PreviewMeatballMenuContainer>
          <S.MeatballMenuButton aria-label="수정 및 삭제 메뉴" type="button" onClick={handleMeatballMenuClick}>
            <MeatballMenuSvg />
          </S.MeatballMenuButton>
          {isOpenDropBox && (
            <DropDownBox onClose={handleDropDownBoxClose} top="36px" right="-32px">
              <S.DropBoxButtons>
                <li>
                  <S.DropBoxButton type="button" onClick={handleEditLinkButtonClick}>
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
        <a href={linkUrl}>{previewResult && <LinkPreview previewResult={previewResult} />}</a>
        <UserDescription author={author} description={description} css={tw`pl-8 pr-8`} />
      </S.LinkItemContainer>
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleModalClose}>
          <LinkEditForm
            linkId={linkId}
            originalContent={originalContent}
            onPutError={handlePutLinkError}
            onPutSuccess={handlePutLinkSuccess}
          />
        </ModalPortal>
      )}
    </>
  );
};

export default LinkItem;
