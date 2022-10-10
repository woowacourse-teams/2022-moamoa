import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import type { MakeOptional, StudyDetail, StudyId, TagId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_STUDY_DETAIL = 'study-detail';

export type ApiStudy = {
  get: {
    params: {
      studyId: StudyId;
    };
    responseData: StudyDetail;
    variables: ApiStudy['get']['params'];
  };
  post: {
    body: {
      tagIds?: Array<TagId>;
      thumbnail: string;
    } & MakeOptional<
      Pick<
        StudyDetail,
        'title' | 'excerpt' | 'description' | 'maxMemberCount' | 'enrollmentEndDate' | 'startDate' | 'endDate' | 'owner'
      >,
      'maxMemberCount' | 'enrollmentEndDate' | 'endDate' | 'owner'
    >;
    variables: ApiStudy['post']['body'];
  };
  put: {
    params: { studyId: StudyId };
    body: ApiStudy['post']['body'];
    variables: ApiStudy['put']['params'] & { editedStudy: ApiStudy['put']['body'] };
  };
};

export const postStudy = async (newStudy: ApiStudy['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiStudy['post']['variables']>(
    `/api/studies`,
    newStudy,
  );
  return response.data;
};

export const usePostStudy = () => {
  return useMutation<null, AxiosError, ApiStudy['post']['variables']>(postStudy);
};

export const getStudy = async ({ studyId }: ApiStudy['get']['variables']) => {
  const response = await axiosInstance.get<ApiStudy['get']['responseData']>(`/api/studies/${studyId}`);
  return response.data;
};

export const useGetStudy = ({ studyId }: ApiStudy['get']['variables']) => {
  return useQuery<ApiStudy['get']['responseData'], AxiosError>([QK_STUDY_DETAIL, studyId], () => getStudy({ studyId }));
};

export const putStudy = async ({ studyId, editedStudy }: ApiStudy['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>>(`/api/studies/${studyId}`, editedStudy);
  return response.data;
};

export const usePutStudy = () => useMutation<null, AxiosError, ApiStudy['put']['variables']>(putStudy);
