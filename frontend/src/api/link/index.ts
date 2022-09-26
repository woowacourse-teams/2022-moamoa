import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import type { Link, LinkId, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// post
export type PostLinkRequestBody = Pick<Link, 'linkUrl' | 'description'>;
export type PostLinkRequestParams = { studyId: StudyId };
export type PostLinkRequestVariables = PostLinkRequestBody & PostLinkRequestParams;

export const postLink = async ({ studyId, linkUrl, description }: PostLinkRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostLinkRequestBody>(
    `/api/studies/${studyId}/reference-room/links`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export const usePostLink = () => useMutation<null, AxiosError, PostLinkRequestVariables>(postLink);

// put
export type PutLinkRequestBody = PostLinkRequestBody;
export type PutLinkRequestParams = { studyId: StudyId; linkId: LinkId };
export type PutLinkRequestVariables = PutLinkRequestBody & PutLinkRequestParams;

export const putLink = async ({ studyId, linkId, linkUrl, description }: PutLinkRequestVariables) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, PutLinkRequestBody>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
    {
      linkUrl,
      description,
    },
  );
  return response.data;
};

export const usePutLink = () => useMutation<null, AxiosError, PutLinkRequestVariables>(putLink);

// delete
export type DeleteLinkRequestParams = { studyId: StudyId; linkId: LinkId };

export const deleteLink = async ({ studyId, linkId }: DeleteLinkRequestParams) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(
    `/api/studies/${studyId}/reference-room/links/${linkId}`,
  );
  return response.data;
};

export const useDeleteLink = () => useMutation<null, AxiosError, DeleteLinkRequestParams>(deleteLink);
