import axiosInstance from '@api/axiosInstance';

const postNewStudy = async (studyId: number) => {
  const response = await axiosInstance.post(`/api/studies/${studyId}`);
  return response;
};

export default postNewStudy;
