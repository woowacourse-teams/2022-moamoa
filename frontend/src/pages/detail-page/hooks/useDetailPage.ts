import type { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import type { GetUserRoleResponseData, PostJoiningStudyRequestParams } from '@custom-types';

import { getUserRole, postJoiningStudy } from '@api';

import { useAuth } from '@hooks/useAuth';

import useGetDetail from '@detail-page/hooks/useGetDetail';

const useDetailPage = () => {
  const { studyId } = useParams() as { studyId: string };
  const { isLoggedIn } = useAuth();

  const detailQueryResult = useGetDetail(Number(studyId));

  const { mutate } = useMutation<null, AxiosError, PostJoiningStudyRequestParams>(postJoiningStudy);
  const userRoleQueryResult = useQuery<GetUserRoleResponseData, AxiosError>(
    'my-role',
    () => getUserRole({ studyId: Number(studyId) }),
    {
      enabled: isLoggedIn,
    },
  );

  const handleRegisterButtonClick = () => {
    if (!isLoggedIn) {
      alert('로그인이 필요합니다.');
      return;
    }

    mutate(
      { studyId: Number(studyId) },
      {
        onError: () => {
          alert('가입에 실패했습니다.');
        },
        onSuccess: () => {
          alert('가입했습니다 :D');
          detailQueryResult.refetch();
          userRoleQueryResult.refetch();
        },
      },
    );
  };

  return {
    detailQueryResult,
    userRoleQueryResult,
    studyId,
    handleRegisterButtonClick,
  };
};

export default useDetailPage;
