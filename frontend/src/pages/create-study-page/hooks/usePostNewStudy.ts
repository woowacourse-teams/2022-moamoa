import type { AxiosError } from 'axios';
import { useMutation } from 'react-query';

import type { PostNewStudyRequestBody } from '@custom-types';

import { postNewStudy } from '@api';

const usePostNewStudy = () => {
  return useMutation<null, AxiosError, PostNewStudyRequestBody>(postNewStudy);
};

export default usePostNewStudy;
