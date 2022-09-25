import tw from '@utils/tw';

import type { Link } from '@custom-types';

import { TextButton } from '@components/button';
import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import ModalPortal from '@components/modal/Modal';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@link-tab/LinkRoomTabPanel.style';
import LinkForm from '@link-tab/components/link-form/LinkForm';
import LinkItem from '@link-tab/components/link-item/LinkItem';
import { useLinkRoomTabPanel } from '@link-tab/hooks/useLinkRoomTabPanel';

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
        <S.LinkList>
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
        </S.LinkList>
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

export default LinkRoomTabPanel;
