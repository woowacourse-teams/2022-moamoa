import axiosInstance from '@api/axiosInstance';

const postJoiningStudy = async (studyId: number) => {
  const response = await axiosInstance.post(`/api/studies/${studyId}`);
  return response.data;
};

export default postJoiningStudy;
