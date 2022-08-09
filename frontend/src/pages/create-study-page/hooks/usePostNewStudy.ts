import type { AxiosError } from 'axios';
import { useMutation } from 'react-query';

import type { EmptyObject, PostNewStudyRequestBody } from '@custom-types';

import { postNewStudy } from '@api';

const usePostNewStudy = () => {
  return useMutation<EmptyObject, AxiosError, PostNewStudyRequestBody>(postNewStudy);
};

export default usePostNewStudy;
