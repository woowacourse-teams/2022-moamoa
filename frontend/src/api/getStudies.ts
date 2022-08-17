import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import type { GetStudiesRequestParams, GetStudiesResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
const getStudies = async ({ page = PAGE, size = SIZE, title, selectedFilters }: GetStudiesRequestParams) => {
  const tagParams = selectedFilters.map(({ id, categoryName }) => `&${categoryName}=${id}`).join('');
  const titleParams = title && `&title=${title}`;

  const response = await axiosInstance.get<GetStudiesResponseData>(
    `/api/studies/search?page=${page}&size=${size}${titleParams}${tagParams}`,
  );
  return response.data;
};

export default getStudies;
