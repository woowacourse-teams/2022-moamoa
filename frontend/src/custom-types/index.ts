export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type StudyStatus = 'open' | 'close';

export type Study = {
  id: number;
  title: string;
  excerpt: string;
  thumbnail: string;
  status: StudyStatus;
};

export type Member = {
  id: number;
  username: string;
  profileImage: string;
  profileUrl: string;
};

export type Tag = { id: number; tagName: string };

export type StudyDetail = {
  id: number;
  title: string;
  excerpt: string;
  thumbnail: string;
  status: StudyStatus;
  description: string;
  currentMemberCount: number;
  maxMemberCount: number;
  deadline: string;
  startDate: string;
  endDate: string;
  owner: string;
  members: Array<Member>;
  tags: Array<Tag>;
} & Study;

export type StudyListQueryData = {
  studies: Array<Study>;
  hasNext: boolean;
};

export type StudyReview = {
  id: number;
  member: Member;
  createdAt: string;
  updatedAt: string;
  content: string;
};

export type Filter = {
  id: number;
  shortName: string;
  fullName: string;
  category: {
    id: number;
    name: string;
  };
};

export type FilterListQueryData = {
  filters: Array<Filter>;
};
