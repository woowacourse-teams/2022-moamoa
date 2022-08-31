import tw from '@utils/tw';

import type { Link, StudyId } from '@custom-types';

import type { ApiLinkPreview } from '@api/link-preview';

import DropDownBox from '@components/drop-down-box/DropDownBox';
import ModalPortal from '@components/modal/Modal';
import { MeatballMenuSvg } from '@components/svg';

import LinkEditForm from '@study-room-page/components/link-room-tab-panel/components/link-edit-form/LinkEditForm';
import * as S from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem.style';
import { useLinkItem } from '@study-room-page/components/link-room-tab-panel/components/link-item/hooks/useLinkItem';
import LinkPreview from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview';
import UserDescription from '@study-room-page/components/link-room-tab-panel/components/user-description/UserDescription';

export type LinkItemProps = Pick<Link, 'id' | 'author' | 'description' | 'linkUrl'> & {
  studyId: StudyId;
};

const LinkItem: React.FC<LinkItemProps> = ({ studyId, id: linkId, linkUrl, author, description }) => {
  const {
    userInfo,
    linkPreviewQueryResult,
    isOpenDropBox,
    isModalOpen,
    handleMeatballMenuClick,
    handleDropDownBoxClose,
    handleModalClose,
    handleEditLinkButtonClick,
    handleDeleteLinkButtonClick,
    handlePutLinkSuccess,
    handlePutLinkError,
  } = useLinkItem({ studyId, linkId, linkUrl });

  const isMyLink = author.id === userInfo.id;
  const originalContent = {
    linkUrl,
    description,
  };

  const renderLinkPreview = () => {
    const { data, isError, isSuccess, isFetching } = linkPreviewQueryResult;

    const errorPreviewResult: ApiLinkPreview['get']['responseData'] = {
      title: '%Error%',
      description: '링크 불러오기에 실패했습니다 :(',
      imageUrl: null,
      domainName: null,
    };
    if (isFetching) return <div>로딩중...</div>;

    if (isError || !isSuccess) return <LinkPreview previewResult={errorPreviewResult} linkUrl={linkUrl} />;

    return <LinkPreview previewResult={data} linkUrl={linkUrl} />;
  };

  return (
    <>
      <S.LinkItemContainer>
        {isMyLink && (
          <S.PreviewMeatballMenuContainer>
            <S.MeatballMenuButton aria-label="수정 및 삭제 메뉴" type="button" onClick={handleMeatballMenuClick}>
              <MeatballMenuSvg />
            </S.MeatballMenuButton>
            {isOpenDropBox && (
              <DropDownBox onClose={handleDropDownBoxClose} top="36px" right="-32px">
                <S.DropBoxButtonList>
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
                </S.DropBoxButtonList>
              </DropDownBox>
            )}
          </S.PreviewMeatballMenuContainer>
        )}
        <a href={linkUrl} rel="noreferrer" target="_blank">
          {renderLinkPreview()}
        </a>
        <UserDescription author={author} description={description} css={tw`pl-8 pr-8`} />
      </S.LinkItemContainer>
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleModalClose}>
          <LinkEditForm
            linkId={linkId}
            author={userInfo}
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
