import type { Link } from '@custom-types';

import InfiniteScroll from '@components/infinite-scroll/InfiniteScroll';
import ModalPortal from '@components/modal/Modal';
import { PlusSvg } from '@components/svg';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/components/link-room-tab-panel/LinkRoomTabPanel.style';
import LinkForm from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm';
import LinkItem from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem';
import { useLinkRoomTabPanel } from '@study-room-page/components/link-room-tab-panel/hooks/useLinkRoomTabPanel';

const LinkRoomTabPanel: React.FC = () => {
  const {
    infiniteLinkListQueryResult,
    isModalOpen,
    handleLinkAddButtonClick,
    handleModalClose,
    handleEditLinkButtonnClick,
    handleDeleteLinkButtonClick,
    handlePostLinkError,
    handlePostLinkSuccess,
  } = useLinkRoomTabPanel();

  const renderLinkList = () => {
    const { data, isError, isSuccess, fetchNextPage } = infiniteLinkListQueryResult;
    if (isError || !isSuccess) {
      return <div>에러가 발생했습니다</div>;
    }

    const links = data.pages.reduce<Array<Link>>((acc, cur) => [...acc, ...cur.links], []);

    if (links.length === 0) {
      return <div>등록된 링크가 없습니다</div>;
    }

    return (
      <InfiniteScroll observingCondition={true} onContentLoad={fetchNextPage}>
        <S.LinkList>
          {links.map(link => (
            <li key={link.id}>
              <LinkItem
                linkUrl={link.linkUrl}
                author={link.author}
                description={link.description}
                onEditLinkButtonClick={handleEditLinkButtonnClick(link.id)}
                onDeleteLinkButtonClick={handleDeleteLinkButtonClick(link.id)}
              />
            </li>
          ))}
        </S.LinkList>
      </InfiniteScroll>
    );
  };

  return (
    <Wrapper>
      <S.LinkAddButtonContainer>
        <S.LinkAddButton type="button" onClick={handleLinkAddButtonClick}>
          <S.PlusSvgContainer>
            <PlusSvg />
          </S.PlusSvgContainer>
          <span>링크 추가하기</span>
        </S.LinkAddButton>
      </S.LinkAddButtonContainer>
      {renderLinkList()}
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleModalClose}>
          <LinkForm onPostSuccess={handlePostLinkSuccess} onPostError={handlePostLinkError} />
        </ModalPortal>
      )}
    </Wrapper>
  );
};

export default LinkRoomTabPanel;
