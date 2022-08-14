import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { useGetInfiniteLinkList } from '@study-room-page/components/link-room-tab-panel/hooks/useGetInfiniteLinkList';

export const useLinkRoomTabPanel = () => {
  const { studyId } = useParams<{ studyId: string }>();
  const infiniteLinkListQueryResult = useGetInfiniteLinkList({ studyId: Number(studyId) });

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLinkAddButtonClick = () => setIsModalOpen(prev => !prev);
  const handleModalClose = () => setIsModalOpen(false);

  const handleEditLinkButtonnClick = () => {
    setIsModalOpen(true);
  };
  const handleDeleteLinkButtonClick = () => {
    // TODO mutate
  };

  return {
    infiniteLinkListQueryResult,
    isModalOpen,
    handleLinkAddButtonClick,
    handleModalClose,
    handleEditLinkButtonnClick,
    handleDeleteLinkButtonClick,
  };
};
