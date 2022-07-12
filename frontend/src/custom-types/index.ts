export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;
export type StudyStatus = 'open' | 'close';
export type Study = {
  id: number;
  title: string;
  description: string;
  thumbnail: string;
  status: StudyStatus;
};

export type StudyListQueryData = {
  studies: Array<Study>;
  hasNext: boolean;
};
