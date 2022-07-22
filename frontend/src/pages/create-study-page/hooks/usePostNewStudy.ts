import { useMutation } from 'react-query';

import postNewStudy from '@api/postNewStudy';

const usePostNewStudy = () => {
  return useMutation('post-new-study', postNewStudy);
};

export default usePostNewStudy;
