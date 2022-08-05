import { useMutation, useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import type { EmptyObject, GetUserRoleResponseData, PostJoiningStudyRequestParams } from '@custom-types';

import { postJoiningStudy } from '@api';
import getUserRole from '@api/getUserRole';

import { useAuth } from '@hooks/useAuth';

import useGetDetail from '@detail-page/hooks/useGetDetail';

const useDetailPage = () => {
  const { studyId } = useParams() as { studyId: string };
  const { isLoggedIn } = useAuth();

  const detailQueryResult = useGetDetail(Number(studyId));

  const { mutate } = useMutation<EmptyObject, Error, PostJoiningStudyRequestParams>(postJoiningStudy);
  const userRoleQueryResult = useQuery<GetUserRoleResponseData, Error>(
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
