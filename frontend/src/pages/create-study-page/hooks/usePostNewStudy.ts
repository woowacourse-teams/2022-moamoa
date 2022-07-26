import { useMutation } from 'react-query';

import postNewStudy from '@api/postNewStudy';

const usePostNewStudy = () => {
  return useMutation(postNewStudy);
};

export default usePostNewStudy;
