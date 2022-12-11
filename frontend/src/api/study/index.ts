import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { MakeOptional, StudyDetail, StudyId, TagId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkStudy } from '@api/study/typeChecker';

import { useSuspendedQuery } from '@hooks/useSuspendedQuery';

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

const postStudy = async (newStudy: ApiStudy['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiStudy['post']['variables']>(
    `/api/studies`,
    newStudy,
  );
  return checkType(response.data, isNull);
};

export const usePostStudy = () => {
  return useMutation<null, AxiosError, ApiStudy['post']['variables']>(postStudy);
};

const getStudy = async ({ studyId }: ApiStudy['get']['variables']) => {
  const response = await axiosInstance.get<ApiStudy['get']['responseData']>(`/api/studies/${studyId}`);
  return checkStudy(response.data);
};

export const useGetStudy = ({ studyId }: ApiStudy['get']['variables']) => {
  return useSuspendedQuery<ApiStudy['get']['responseData'], AxiosError>([QK_STUDY_DETAIL, studyId], () =>
    getStudy({ studyId }),
  );
};

const putStudy = async ({ studyId, editedStudy }: ApiStudy['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>>(`/api/studies/${studyId}`, editedStudy);
  return checkType(response.data, isNull);
};

export const usePutStudy = () => useMutation<null, AxiosError, ApiStudy['put']['variables']>(putStudy);
