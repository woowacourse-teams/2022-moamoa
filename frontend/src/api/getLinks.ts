import type { GetLinksRequestParams, GetLinksResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getLinks = async ({ studyId, page, size }: GetLinksRequestParams) => {
  const response = await axiosInstance.get<GetLinksResponseData>(
    `/api/studies/${studyId}/reference-room/links?page=${page}&size=${size}`,
  );
  return response.data;
};

export default getLinks;
