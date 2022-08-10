import type { AxiosResponse } from 'axios';

import type { PostLoginRequestParams, PostLoginResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const postLogin = async ({ code }: PostLoginRequestParams) => {
  const response = await axiosInstance.post<
    PostLoginResponseData,
    AxiosResponse<PostLoginResponseData>,
    PostLoginRequestParams
  >(`/api/auth/login/token?code=${code}`);
  return response.data;
};

export default postLogin;
