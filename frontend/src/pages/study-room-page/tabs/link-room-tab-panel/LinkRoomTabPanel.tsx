import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type Link } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { TextButton } from '@components/button';
import InfiniteScroll, { InfiniteScrollProps } from '@components/infinite-scroll/InfiniteScroll';
import ModalPortal from '@components/modal/Modal';
import PageWrapper from '@components/page-wrapper/PageWrapper';

import LinkForm from '@study-room-page/tabs/link-room-tab-panel/components/link-form/LinkForm';
import LinkItem from '@study-room-page/tabs/link-room-tab-panel/components/link-item/LinkItem';
import { useLinkRoomTabPanel } from '@study-room-page/tabs/link-room-tab-panel/hooks/useLinkRoomTabPanel';

const LinkRoomTabPanel: React.FC = () => {
  const {
    studyId,
    userInfo,
    infiniteLinksQueryResult,
    isModalOpen,
    handleLinkAddButtonClick,
    handleModalClose,
    handlePostLinkError,
    handlePostLinkSuccess,
  } = useLinkRoomTabPanel();

  const { data, isError, isSuccess, isFetching, fetchNextPage } = infiniteLinksQueryResult;

  const links = data ? data.pages.reduce<Array<Link>>((acc, cur) => [...acc, ...cur.links], []) : [];

  return (
    <PageWrapper>
      {isFetching && <Loading />}
      {isError && <Error />}
      {isSuccess && links.length === 0 && <NoLinks />}
      <AddLinkButton onClick={handleLinkAddButtonClick} />
      {isSuccess && links.length > 0 && (
        <InfiniteLinkList links={links} isContentLoading={isFetching} studyId={studyId} onContentLoad={fetchNextPage} />
      )}
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleModalClose}>
          <LinkForm author={userInfo} onPostSuccess={handlePostLinkSuccess} onPostError={handlePostLinkError} />
        </ModalPortal>
      )}
    </Wrapper>
  );
};

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

export default LinkRoomTabPanel;
