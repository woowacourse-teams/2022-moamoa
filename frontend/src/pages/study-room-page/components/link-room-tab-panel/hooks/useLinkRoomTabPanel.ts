import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { useGetInfiniteLinkList } from '@study-room-page/components/link-room-tab-panel/hooks/useGetInfiniteLinkList';

export const useLinkRoomTabPanel = () => {
  const { studyId } = useParams<{ studyId: string }>();
  const infiniteLinkListQueryResult = useGetInfiniteLinkList({ studyId: Number(studyId) });

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLinkAddButtonClick = () => setIsModalOpen(prev => !prev);
  const handleModalClose = () => setIsModalOpen(false);

  const handlePostLinkSuccess = () => {
    alert('링크를 등록했습니다 :D');
    infiniteLinkListQueryResult.refetch();
    handleModalClose();
  };

  const handlePostLinkError = () => {
    alert('링크를 등록하지 못했습니다 :(');
    handleModalClose();
  };

  return {
    studyId: Number(studyId),
    infiniteLinkListQueryResult,
    isModalOpen,
    handleLinkAddButtonClick,
    handleModalClose,
    handlePostLinkSuccess,
    handlePostLinkError,
  };
};
