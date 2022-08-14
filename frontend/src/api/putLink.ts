import type { AxiosResponse } from 'axios';

import type { PutLinkRequestBody, PutLinkRequestVariables } from '@custom-types';

import { axiosInstance } from '@api';

const putLink = async ({ studyId, linkId, linkUrl, description }: PutLinkRequestVariables) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, PutLinkRequestBody>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export default putLink;
