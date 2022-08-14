import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useParams } from 'react-router-dom';

import { DeleteLinkRequestParams } from '@custom-types';

import { deleteLink } from '@api';

import { useGetInfiniteLinkList } from '@study-room-page/components/link-room-tab-panel/hooks/useGetInfiniteLinkList';

export const useLinkRoomTabPanel = () => {
  const { studyId } = useParams<{ studyId: string }>();
  const infiniteLinkListQueryResult = useGetInfiniteLinkList({ studyId: Number(studyId) });
  const { mutate } = useMutation<null, AxiosError, DeleteLinkRequestParams>(deleteLink);

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLinkAddButtonClick = () => setIsModalOpen(prev => !prev);
  const handleModalClose = () => setIsModalOpen(false);

  const handleEditLinkButtonnClick = (linkId: number) => () => {
    setIsModalOpen(true);
  };
  const handleDeleteLinkButtonClick = (linkId: number) => () => {
    mutate(
      { studyId: Number(studyId), linkId },
      {
        onError: () => {
          alert('링크를 삭제하지 못했습니다 :(');
        },
        onSuccess: () => {
          alert('링크를 삭제했습니다');
          infiniteLinkListQueryResult.refetch();
        },
      },
    );
  };

  const handlePostLinkSuccess = () => {
    alert('링크를 등록했습니다 :D');
    infiniteLinkListQueryResult.refetch();
    handleModalClose();
  };

  const handlePostLinkError = (error: Error) => {
    alert('링크를 등록하지 못했습니다 :(');
    handleModalClose();
  };

  return {
    infiniteLinkListQueryResult,
    isModalOpen,
    handleLinkAddButtonClick,
    handleModalClose,
    handleEditLinkButtonnClick,
    handleDeleteLinkButtonClick,
    handlePostLinkSuccess,
    handlePostLinkError,
  };
};
