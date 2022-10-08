import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { type Link, type LinkId, type StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiLink = {
  post: {
    params: { studyId: StudyId };
    body: Pick<Link, 'linkUrl' | 'description'>;
    variables: ApiLink['post']['params'] & ApiLink['post']['body'];
  };
  put: {
    params: { studyId: StudyId; linkId: LinkId };
    body: ApiLink['post']['body'];
    variables: ApiLink['put']['params'] & ApiLink['put']['body'];
  };
  delete: {
    params: { studyId: StudyId; linkId: LinkId };
    variables: ApiLink['delete']['params'];
  };
};

export const postLink = async ({ studyId, linkUrl, description }: ApiLink['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiLink['post']['body']>(
    `/api/studies/${studyId}/reference-room/links`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export const usePostLink = () => useMutation<null, AxiosError, ApiLink['post']['variables']>(postLink);

// put

export const putLink = async ({ studyId, linkId, linkUrl, description }: ApiLink['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiLink['put']['body']>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export const usePutLink = () => useMutation<null, AxiosError, ApiLink['put']['variables']>(putLink);

export const deleteLink = async ({ studyId, linkId }: ApiLink['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
  );
  return response.data;
};

export const useDeleteLink = () => useMutation<null, AxiosError, ApiLink['delete']['variables']>(deleteLink);
