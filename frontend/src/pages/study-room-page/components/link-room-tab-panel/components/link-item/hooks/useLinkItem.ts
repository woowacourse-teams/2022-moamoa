import type { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import type { DeleteLinkRequestParams, LinkId, StudyId } from '@custom-types';

import { deleteLink } from '@api';

import { QK_FETCH_LINKS } from '@study-room-page/components/link-room-tab-panel/hooks/useGetInfiniteLinkList';

export type UseLinkItemParams = {
  studyId: StudyId;
  linkId: LinkId;
};

export const useLinkItem = ({ studyId, linkId }: UseLinkItemParams) => {
  const { mutate } = useMutation<null, AxiosError, DeleteLinkRequestParams>(deleteLink);
  // TODO: link preview 가져오기
  const [isOpenDropBox, setIsOpenDropBox] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.refetchQueries([QK_FETCH_LINKS, studyId]);
  };

  const handleMeatballMenuClick = () => {
    setIsOpenDropBox(prev => !prev);
  };
  const handleDropDownBoxClose = () => setIsOpenDropBox(false);

  const handleModalClose = () => setIsModalOpen(false);
  const handleEditLinkButtonClick = () => {
    setIsModalOpen(true);
  };
  const handleDeleteLinkButtonClick = () => {
    mutate(
      { studyId: Number(studyId), linkId },
      {
        onError: () => {
          alert('링크를 삭제하지 못했습니다 :(');
        },
        onSuccess: () => {
          alert('링크를 삭제했습니다');
          refetch();
        },
      },
    );
  };

  const handlePutLinkSuccess = () => {
    alert('링크를 수정했습니다 :D');
    refetch();
    handleModalClose();
  };
  const handlePutLinkError = () => {
    alert('링크를 수정하지 못했습니다 :(');
  };

  return {
    isOpenDropBox,
    isModalOpen,
    handleMeatballMenuClick,
    handleDropDownBoxClose,
    handleModalClose,
    handleEditLinkButtonClick,
    handleDeleteLinkButtonClick,
    handlePutLinkSuccess,
    handlePutLinkError,
  };
};
