import styled from '@emotion/styled';

import tw from '@utils/tw';

import type { Link } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { TextButton } from '@components/button';
import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import ModalPortal from '@components/modal/Modal';
import Wrapper from '@components/wrapper/Wrapper';

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

  const renderLinkList = () => {
    const { data, isError, isSuccess, isFetching, fetchNextPage } = infiniteLinksQueryResult;
    if (isError || !isSuccess) {
      return <div>에러가 발생했습니다</div>;
    }

    const links = data.pages.reduce<Array<Link>>((acc, cur) => [...acc, ...cur.links], []);

    if (links.length === 0) {
      return <div>등록된 링크가 없습니다</div>;
    }

    return (
      <InfiniteScroll isContentLoading={isFetching} onContentLoad={fetchNextPage}>
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
  };

  return (
    <Wrapper>
      <div css={tw`py-4 mb-16 text-right`}>
        <TextButton variant="primary" fontSize="lg" onClick={handleLinkAddButtonClick}>
          링크 추가하기
        </TextButton>
      </div>
      {renderLinkList()}
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleModalClose}>
          <LinkForm author={userInfo} onPostSuccess={handlePostLinkSuccess} onPostError={handlePostLinkError} />
        </ModalPortal>
      )}
    </Wrapper>
  );
};

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

export default LinkRoomTabPanel;
