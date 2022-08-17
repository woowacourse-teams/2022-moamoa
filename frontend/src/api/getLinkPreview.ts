import axios from 'axios';
import type { AxiosError } from 'axios';

import type { GetLinkPreviewRequestParams, GetLinkPreviewResponseData } from '@custom-types';

import AccessTokenController from '@auth/accessToken';

const axiosInstance = axios.create({
  // TODO: 링크 미리보기 서버 배포 url로 변경 필요
  baseURL: process.env.LINK_PREVIEW_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

const handleAxiosError = (error: AxiosError<{ message: string }>) => {
  const data = error.response?.data;
  if (data?.message) {
    console.error(data.message);
    // TODO: 커스텀 에러 코드를 만들어서 그에 맞는 message를 담은 error 객체를 return 하도록 해야 함
    return Promise.reject(error);
  }

  console.error('서버에 에러가 발생했습니다.', `코드: ${error.code}`);
  error.message = '알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요 :(';
  return Promise.reject(error);
};

axiosInstance.interceptors.response.use(response => response, handleAxiosError);

axiosInstance.interceptors.request.use(
  config => {
    const accessToken = AccessTokenController.accessToken;

    if (!accessToken) return config;
    if (!config.headers) {
      config.headers = {
        Authorization: `Bearer ${accessToken}`,
      };
      return config;
    }
    config.headers['Authorization'] = `Bearer ${accessToken}`;

    return config;
  },
  error => {
    return Promise.reject(error);
  },
);

const getLinkPreview = async ({ linkUrl }: GetLinkPreviewRequestParams) => {
  const response = await axiosInstance.get<GetLinkPreviewResponseData>(`/api/link-preview?linkUrl=${linkUrl}`);
  return response.data;
};

export default getLinkPreview;
