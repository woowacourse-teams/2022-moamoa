import type { MakeOptional, StudyDetail } from '@custom-types/index';

import axiosInstance from '@api/axiosInstance';

export type StudyDetailPostData = {
  tagIds: Array<number>;
  thumbnail: string;
} & MakeOptional<
  Pick<
    StudyDetail,
    'title' | 'excerpt' | 'description' | 'maxMemberCount' | 'enrollmentEndDate' | 'startDate' | 'endDate' | 'owner'
  >,
  'maxMemberCount' | 'enrollmentEndDate' | 'endDate' | 'owner'
>;

const postNewStudy = async (data: StudyDetailPostData) => {
  const response = await axiosInstance.post<any, any, StudyDetailPostData>(`/api/studies`, data);
  console.log('response', response);
  return response;
};

export default postNewStudy;
