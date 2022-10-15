import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { useGetInfiniteLinks } from '@api/links';

import { useUserInfo } from '@hooks/useUserInfo';
import { useUserRole } from '@hooks/useUserRole';

export const useLinkRoomTabPanel = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { fetchUserInfo, userInfo } = useUserInfo();
  const { isOwnerOrMember } = useUserRole({ studyId });

  const infiniteLinksQueryResult = useGetInfiniteLinks({ studyId });

  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const handleLinkAddButtonClick = () => setIsModalOpen(prev => !prev);
  const handleModalClose = () => setIsModalOpen(false);

  const handlePostLinkSuccess = () => {
    alert('링크를 등록했습니다 :D');
    infiniteLinksQueryResult.refetch();
    handleModalClose();
  };

  const handlePostLinkError = () => {
    alert('링크를 등록하지 못했습니다 :(');
    handleModalClose();
  };

  return {
    studyId,
    userInfo,
    infiniteLinksQueryResult,
    isModalOpen,
    isOwnerOrMember,
    handleLinkAddButtonClick,
    handleModalClose,
    handlePostLinkSuccess,
    handlePostLinkError,
  };
};
