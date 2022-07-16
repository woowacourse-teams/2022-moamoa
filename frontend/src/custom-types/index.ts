export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;
export type StudyStatus = 'open' | 'close';
export type Study = {
  id: number;
  title: string;
  excerpt: string;
  thumbnail: string;
  status: StudyStatus;
};

export type StudyListQueryData = {
  studies: Array<Study>;
  hasNext: boolean;
};

export type Filter = {
  id: number;
  shortName: string;
  description: string;
  category: {
    id: number;
    name: string;
  };
};

export type FilterListQueryData = {
  filters: Array<Filter>;
};
