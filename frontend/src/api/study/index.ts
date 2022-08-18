import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import type { MakeOptional, StudyDetail, TagId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_STUDY_DETAIL = 'study-detail';

// post
export type PostStudyRequestBody = {
  tagIds?: Array<TagId>;
  thumbnail: string;
} & MakeOptional<
  Pick<
    StudyDetail,
    'title' | 'excerpt' | 'description' | 'maxMemberCount' | 'enrollmentEndDate' | 'startDate' | 'endDate' | 'owner'
  >,
  'maxMemberCount' | 'enrollmentEndDate' | 'endDate' | 'owner'
>;

export const postStudy = async (newStudy: PostStudyRequestBody) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostStudyRequestBody>(`/api/studies`, newStudy);
  return response.data;
};

export const usePostStudy = () => {
  return useMutation<null, AxiosError, PostStudyRequestBody>(postStudy);
};

// get
export type GetStudyRequestParams = {
  studyId: number;
};
export type GetStudyResponseData = StudyDetail;

export const getStudy = async ({ studyId }: GetStudyRequestParams) => {
  const response = await axiosInstance.get<GetStudyResponseData>(`/api/studies/${studyId}`);
  return response.data;
};

export const useGetStudy = ({ studyId }: GetStudyRequestParams) => {
  return useQuery<GetStudyResponseData, AxiosError>([QK_STUDY_DETAIL, studyId], () => getStudy({ studyId }));
};

// put
