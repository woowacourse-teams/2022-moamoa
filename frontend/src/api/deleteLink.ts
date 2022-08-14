import type { AxiosResponse } from 'axios';

import type { DeleteLinkRequestParams } from '@custom-types';

import { axiosInstance } from '@api';

const deleteLink = async ({ studyId, linkId }: DeleteLinkRequestParams) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
  );
  return response.data;
};

export default deleteLink;
