import type { AxiosResponse } from 'axios';

import type { PostTokenRequestParams, PostTokenResponseData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const postAccessToken = async ({ code }: PostTokenRequestParams) => {
  const response = await axiosInstance.post<
    PostTokenResponseData,
    AxiosResponse<PostTokenResponseData>,
    PostTokenRequestParams
  >(`/api/login/token?code=${code}`);
  return response.data;
};

export default postAccessToken;
