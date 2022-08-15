import type { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import type { DeleteLinkRequestParams, LinkId, StudyId } from '@custom-types';

import { deleteLink } from '@api';

import { useUserInfo } from '@hooks/useUserInfo';

import { QK_FETCH_LINKS } from '@study-room-page/components/link-room-tab-panel/hooks/useGetInfiniteLinkList';

export type UseLinkItemParams = {
  studyId: StudyId;
  linkId: LinkId;
};

const previewResult = {
  title: '합성 컴포넌트 어쩌구 저쩌구 쏼라쏼라',
  description: '카카오 엔터테인먼트 FE 기술 블로그',
  imageUrl:
    'https://images.unsplash.com/photo-1572059002053-8cc5ad2f4a38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGdvb2dsZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60',
  domainName: 'naver.com',
};

export const useLinkItem = ({ studyId, linkId }: UseLinkItemParams) => {
  const { mutate } = useMutation<null, AxiosError, DeleteLinkRequestParams>(deleteLink);
  // TODO: link preview 가져오기
  const [isOpenDropBox, setIsOpenDropBox] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { userInfo } = useUserInfo();

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
    userInfo,
    previewResult,
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
