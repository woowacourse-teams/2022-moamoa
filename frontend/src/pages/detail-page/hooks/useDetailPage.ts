import { useParams } from 'react-router-dom';

import { useGetUserRole } from '@api/member';
import { usePostMyStudy } from '@api/my-study';
import { useGetStudy } from '@api/study';

import { useAuth } from '@hooks/useAuth';

const useDetailPage = () => {
  const { studyId } = useParams() as { studyId: string };
  const { isLoggedIn } = useAuth();

  const detailQueryResult = useGetStudy({ studyId: Number(studyId) });

  const { mutate } = usePostMyStudy();
  const userRoleQueryResult = useGetUserRole({
    studyId: Number(studyId),
    options: {
      enabled: isLoggedIn,
    },
  });

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
