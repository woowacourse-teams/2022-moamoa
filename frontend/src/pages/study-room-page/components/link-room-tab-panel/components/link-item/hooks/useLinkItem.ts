import { useState } from 'react';
import { useQueryClient } from 'react-query';

import type { LinkId, StudyId } from '@custom-types';

import { useDeleteLink } from '@api/link';
import { useGetLinkPreview } from '@api/link-preview';
import { QK_LINKS } from '@api/links';

import { useUserInfo } from '@hooks/useUserInfo';

export type UseLinkItemParams = {
  studyId: StudyId;
  linkId: LinkId;
  linkUrl: string;
};

export const useLinkItem = ({ studyId, linkId, linkUrl }: UseLinkItemParams) => {
  const { mutate } = useDeleteLink();
  const linkPreviewQueryResult = useGetLinkPreview({ linkUrl });

  const [isOpenDropBox, setIsOpenDropBox] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { userInfo } = useUserInfo();

  const queryClient = useQueryClient();
  const refetch = () => {
    queryClient.refetchQueries([QK_LINKS, studyId]);
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
    userInfo,
    linkPreviewQueryResult,
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
