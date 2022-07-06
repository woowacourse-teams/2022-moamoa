import StudyCard from '@components/StudyCard';

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;
export type StudyStatus = 'open' | 'close';
export type StudyCard = {
  id: number;
  title: string;
  description: string;
  thumbnail: string;
  status: StudyStatus;
};

export type StudyCardListQueryData = {
  studies: Array<StudyCard>;
  hasNext: boolean;
};
