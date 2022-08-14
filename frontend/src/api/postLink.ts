import type { AxiosResponse } from 'axios';

import type { PostLinkRequestBody, PostLinkRequestVariables } from '@custom-types';

import { axiosInstance } from '@api';

const postLink = async ({ studyId, linkUrl, description }: PostLinkRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostLinkRequestBody>(
    `/api/studies/${studyId}/reference-room/links`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export default postLink;
