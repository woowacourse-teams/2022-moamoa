import { useState } from 'react';

import type { Link } from '@custom-types';

import ModalPortal from '@components/modal/Modal';
import { PlusSvg } from '@components/svg';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/components/link-room-tab-panel/LinkRoomTabPanel.style';
import LinkForm from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm';
import LinkItem from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem';

export type LinkRoomTabPanelProps = {};

const links: Array<Link> = [
  {
    id: 1,
    createdDate: '2020-08-14',
    lastModifiedDate: '2020-08-14',
    linkUrl: 'https://www.naver.com/',
    author: {
      id: 1,
      username: 'your-name',
      profileUrl: '/',
      imageUrl:
        'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
    },
    description: '안녕하세요 글자는 약 40자 정도로 하면 좋을 것 같네요. 가나다라마바사아',
  },
  {
    id: 2,
    createdDate: '2020-08-14',
    lastModifiedDate: '2020-08-14',
    linkUrl: 'https://www.naver.com/',
    author: {
      id: 1,
      username: 'your-name',
      profileUrl: '/',
      imageUrl:
        'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
    },
    description: '안녕하세요 글자는 약 40자 정도로 하면 좋을 것 같네요. 가나다라마바사아',
  },
  {
    id: 3,
    createdDate: '2020-08-14',
    lastModifiedDate: '2020-08-14',
    linkUrl: 'https://www.naver.com/',
    author: {
      id: 1,
      username: 'your-name',
      profileUrl: '/',
      imageUrl:
        'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
    },
    description: '안녕하세요 글자는 약 40자 정도로 하면 좋을 것 같네요. 가나다라마바사아',
  },
  {
    id: 4,
    createdDate: '2020-08-14',
    lastModifiedDate: '2020-08-14',
    linkUrl: 'https://www.naver.com/',
    author: {
      id: 1,
      username: 'your-name',
      profileUrl: '/',
      imageUrl:
        'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
    },
    description: '안녕하세요 글자는 약 40자 정도로 하면 좋을 것 같네요. 가나다라마바사아',
  },
];

const LinkRoomTabPanel: React.FC<LinkRoomTabPanelProps> = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLinkAddButtonClick = () => setIsModalOpen(prev => !prev);
  const handleOutsideModalClick = () => setIsModalOpen(false);

  const handleEditLinkButtonnClick = () => {
    setIsModalOpen(true);
  };
  const handleDeleteLinkButtonClick = () => {
    // TODO mutate
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
      <S.LinkList>
        {links.map(link => (
          <li key={link.id}>
            <LinkItem
              linkUrl={link.linkUrl}
              author={link.author}
              description={link.description}
              onEditLinkButtonClick={handleEditLinkButtonnClick}
              onDeleteLinkButtonClick={handleDeleteLinkButtonClick}
            />
          </li>
        ))}
      </S.LinkList>
      {isModalOpen && (
        <ModalPortal onModalOutsideClick={handleOutsideModalClick}>
          <LinkForm />
        </ModalPortal>
      )}
    </Wrapper>
  );
};

export default LinkRoomTabPanel;
