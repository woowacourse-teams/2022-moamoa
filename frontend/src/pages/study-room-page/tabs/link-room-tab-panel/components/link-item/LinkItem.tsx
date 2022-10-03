import { Theme, useTheme } from '@emotion/react';

import tw from '@utils/tw';

import type { Link, StudyId } from '@custom-types';

import type { ApiLinkPreview } from '@api/link-preview';

import { IconButton, TextButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import DropDownBox from '@components/drop-down-box/DropDownBox';
import { MeatballMenuIcon } from '@components/icons';
import ModalPortal from '@components/modal/Modal';

import LinkEditForm from '@link-tab/components/link-edit-form/LinkEditForm';
import { useLinkItem } from '@link-tab/components/link-item/hooks/useLinkItem';
import LinkPreview from '@link-tab/components/link-preview/LinkPreview';
import UserDescription from '@link-tab/components/user-description/UserDescription';

export type LinkItemProps = Pick<Link, 'id' | 'author' | 'description' | 'linkUrl'> & {
  studyId: StudyId;
};

const LinkItem: React.FC<LinkItemProps> = ({ studyId, id: linkId, linkUrl, author, description }) => {
  const theme = useTheme();
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
      <div css={tw`relative`}>
        {isMyLink && (
          <div css={tw`absolute top-8 right-8 z-3`}>
            <ToggleButton onClick={handleMeatballMenuClick} />
            <DropDownBox
              isOpen={isOpenDropBox}
              onClose={handleDropDownBoxClose}
              top="24px"
              right="-36px"
              custom={{ padding: '8px' }}
            >
              <ButtonGroup orientation="vertical">
                <EditButton theme={theme} onClick={handleEditLinkButtonClick} />
                <Divider space="8px" />
                <DeleteButton theme={theme} onClick={handleDeleteLinkButtonClick} />
              </ButtonGroup>
            </DropDownBox>
          </div>
        )}
        <a href={linkUrl} rel="noreferrer" target="_blank">
          {renderLinkPreview()}
        </a>
        <UserDescription author={author} description={description} />
      </div>
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

type ToggleButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const ToggleButton: React.FC<ToggleButtonProps> = ({ onClick: handleClick }) => (
  <IconButton
    ariaLabel="수정 및 삭제 메뉴"
    onClick={handleClick}
    variant="secondary"
    custom={{ width: '30px', height: '30px' }}
  >
    <MeatballMenuIcon />
  </IconButton>
);

type EditButtonProps = {
  theme: Theme;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const EditButton: React.FC<EditButtonProps> = ({ theme, onClick: handleClick }) => (
  <TextButton variant="secondary" onClick={handleClick} custom={{ fontSize: theme.fontSize.sm }}>
    수정
  </TextButton>
);

type DeleteButtonProps = {
  theme: Theme;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const DeleteButton: React.FC<DeleteButtonProps> = ({ theme, onClick: handleClick }) => (
  <TextButton variant="secondary" onClick={handleClick} custom={{ fontSize: theme.fontSize.sm }}>
    삭제
  </TextButton>
);

export default LinkItem;
