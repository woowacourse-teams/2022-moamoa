import styled from '@emotion/styled';

import { type Link, type StudyId } from '@custom-types';

import { type ApiLinkPreview } from '@api/link-preview';

import { IconButton, TextButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import DropDownBox from '@components/drop-down-box/DropDownBox';
import { MeatballMenuIcon } from '@components/icons';
import ModalPortal from '@components/modal/Modal';

import LinkEditForm from '@link-tab/components/link-edit-form/LinkEditForm';
import { useLinkItem } from '@link-tab/components/link-item/hooks/useLinkItem';
import LinkPreview, { type LinkPreviewProps } from '@link-tab/components/link-preview/LinkPreview';
import UserDescription from '@link-tab/components/user-description/UserDescription';

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

  const { data, isError, isSuccess, isFetching } = linkPreviewQueryResult;

  return (
    <>
      <Self>
        {isMyLink && (
          <MyLink>
            <ToggleButton onClick={handleMeatballMenuClick} />
            <DropDownBox
              isOpen={isOpenDropBox}
              onClose={handleDropDownBoxClose}
              top="24px"
              right="-36px"
              custom={{ padding: '8px' }}
            >
              <ButtonGroup orientation="vertical">
                <EditButton onClick={handleEditLinkButtonClick} />
                <Divider space="8px" />
                <DeleteButton onClick={handleDeleteLinkButtonClick} />
              </ButtonGroup>
            </DropDownBox>
          </MyLink>
        )}
        <Preview
          isFetching={isFetching}
          isError={isError}
          isSuccess={isSuccess}
          linkUrl={linkUrl}
          previewResult={data}
        />
        <UserDescription author={author} description={description} />
      </Self>
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

const Self = styled.div`
  position: relative;
`;

const Loading = () => <div>Loading...</div>;

type PreviewProps = {
  isFetching: boolean;
  isError: boolean;
  isSuccess: boolean;
  linkUrl: LinkPreviewProps['linkUrl'];
  previewResult?: LinkPreviewProps['previewResult'];
};
const Preview: React.FC<PreviewProps> = ({ linkUrl, previewResult, isFetching, isError, isSuccess }) => (
  <a href={linkUrl} rel="noreferrer" target="_blank">
    {isFetching && <Loading />}
    {isError && !isSuccess && <ErrorPreview linkUrl={linkUrl} />}
    {isSuccess && previewResult && <LinkPreview previewResult={previewResult} linkUrl={linkUrl} />}
  </a>
);

type ErrorPreviewProps = {
  linkUrl: LinkPreviewProps['linkUrl'];
};
const ErrorPreview: React.FC<ErrorPreviewProps> = ({ linkUrl }) => {
  const errorPreviewResult: ApiLinkPreview['get']['responseData'] = {
    title: '%Error%',
    description: '링크 불러오기에 실패했습니다 :(',
    imageUrl: null,
    domainName: null,
  };
  return <LinkPreview previewResult={errorPreviewResult} linkUrl={linkUrl} />;
};

const MyLink = styled.div`
  position: absolute;
  top: 8px;
  right: 8px;
  z-index: 3;
`;

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

export default LinkItem;
