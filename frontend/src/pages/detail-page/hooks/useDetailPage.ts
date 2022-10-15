import { useParams } from 'react-router-dom';

import { usePostMyStudy } from '@api/my-study';
import { useGetStudy } from '@api/study';

import { useAuth } from '@hooks/useAuth';
import { useUserRole } from '@hooks/useUserRole';

const useDetailPage = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const { isLoggedIn } = useAuth();

  const detailQueryResult = useGetStudy({ studyId });

  const { mutate } = usePostMyStudy();
  const { isOwner, isOwnerOrMember, fetchUserRole } = useUserRole({
    studyId,
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
      { studyId },
      {
        onError: () => {
          alert('가입에 실패했습니다.');
        },
        onSuccess: () => {
          alert('가입했습니다 :D');
          detailQueryResult.refetch();
          fetchUserRole();
        },
      },
    );
  };

  return {
    detailQueryResult,
    isOwner,
    isOwnerOrMember,
    studyId,
    handleRegisterButtonClick,
  };
};

export default useDetailPage;
