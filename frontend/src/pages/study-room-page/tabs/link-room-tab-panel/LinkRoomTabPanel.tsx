import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { Link } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { TextButton } from '@shared/button';
import Divider from '@shared/divider/Divider';
import InfiniteScroll, { type InfiniteScrollProps } from '@shared/infinite-scroll/InfiniteScroll';
import ModalPortal, { ModalProps } from '@shared/modal/Modal';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import LinkForm, { LinkFormProps } from '@link-tab/components/link-form/LinkForm';
import LinkItem from '@link-tab/components/link-item/LinkItem';
import { useLinkRoomTabPanel } from '@link-tab/hooks/useLinkRoomTabPanel';

const LinkRoomTabPanel: React.FC = () => {
  const {
    studyId,
    userInfo,
    infiniteLinksQueryResult,
    isModalOpen,
    isOwnerOrMember,
    handleLinkAddButtonClick,
    handleModalClose,
    handlePostLinkError,
    handlePostLinkSuccess,
  } = useLinkRoomTabPanel();

  const { data, isError, isFetching, fetchNextPage } = infiniteLinksQueryResult;

  const links = data ? data.pages.reduce<Array<Link>>((acc, cur) => [...acc, ...cur.links], []) : [];

  return (
    <PageWrapper>
      {isOwnerOrMember && <AddLinkButton onClick={handleLinkAddButtonClick} />}
      <Divider space="8px" />
      {(() => {
        // @TODO: react-queryv4로 올림
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (links.length === 0) return <NoLinks />;
        return (
          <InfiniteLinkList
            links={links}
            isContentLoading={isFetching}
            studyId={studyId}
            onContentLoad={fetchNextPage}
          />
        );
      })()}
      {isModalOpen && (
        <CreateLinkModal
          author={userInfo}
          onModalOutsideClick={handleModalClose}
          onSuccess={handlePostLinkSuccess}
          onError={handlePostLinkError}
        />
      )}
    </PageWrapper>
  );
};

export default LinkRoomTabPanel;

type InfiniteLinkListProps = { links: Array<Link>; studyId: number } & Omit<InfiniteScrollProps, 'children'>;
const InfiniteLinkList: React.FC<InfiniteLinkListProps> = ({ links, studyId, isContentLoading, onContentLoad }) => (
  <InfiniteScroll isContentLoading={isContentLoading} onContentLoad={onContentLoad}>
    <LinkList>
      {links.map(link => (
        <li key={link.id}>
          <LinkItem
            studyId={studyId}
            id={link.id}
            linkUrl={link.linkUrl}
            author={link.author}
            description={link.description}
          />
        </li>
      ))}
    </LinkList>
  </InfiniteScroll>
);

const LinkList = styled.ul`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;

  ${mqDown('lg')} {
    grid-template-columns: repeat(2, 1fr);
  }

  ${mqDown('sm')} {
    grid-template-columns: repeat(1, 1fr);
  }
`;

const Error = () => <div>에러가 발생했습니다</div>;

const Loading = () => <div>Loading...</div>;

const NoLinks = () => <div>등록된 링크가 없습니다</div>;

type AddLinkButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const AddLinkButton: React.FC<AddLinkButtonProps> = ({ onClick: handleClick }) => (
  <div
    css={css`
      padding-left: 4px;
      padding-right: 4px;
      margin-bottom: 16px;
      text-align: right;
    `}
  >
    <TextButton variant="primary" onClick={handleClick} custom={{ fontSize: 'lg' }}>
      링크 추가하기
    </TextButton>
  </div>
);

type CreateLinkModalProps = {
  author: LinkFormProps['author'];
  onModalOutsideClick: ModalProps['onModalOutsideClick'];
  onSuccess: LinkFormProps['onPostSuccess'];
  onError: LinkFormProps['onPostError'];
};
const CreateLinkModal: React.FC<CreateLinkModalProps> = ({ author, onModalOutsideClick, onSuccess, onError }) => (
  <ModalPortal onModalOutsideClick={onModalOutsideClick}>
    <LinkForm author={author} onPostSuccess={onSuccess} onPostError={onError} />
  </ModalPortal>
);
