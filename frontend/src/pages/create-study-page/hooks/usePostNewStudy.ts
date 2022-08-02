import { useMutation } from 'react-query';

import type { EmptyObject, PostNewStudyRequestBody } from '@custom-types';

import postNewStudy from '@api/postNewStudy';

const usePostNewStudy = () => {
  return useMutation<EmptyObject, Error, PostNewStudyRequestBody>(postNewStudy);
};

export default usePostNewStudy;
