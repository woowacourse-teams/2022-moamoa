export type EmptyObject = Record<string, never>;

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type StudyStatus = 'OPEN' | 'CLOSE';

export type Study = {
  id: number;
  title: string;
  excerpt: string;
  thumbnail: string;
  status: StudyStatus;
};

export type Owner = {
  id: number;
  username: string;
  imageUrl: string;
  profileUrl: string;
};

export type Member = {
  id: number;
  username: string;
  imageUrl: string;
  profileUrl: string;
};

export type StudyTag = { id: number; name: string };

export type StudyDetail = {
  id: number;
  title: string;
  excerpt: string;
  thumbnail: string;
  status: StudyStatus;
  description: string;
  currentMemberCount: number;
  maxMemberCount: number;
  createdAt: string;
  enrollmentEndDate: string;
  startDate: string;
  endDate: string;
  owner: Owner;
  members: Array<Member>;
  tags: Array<StudyTag>;
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

export interface TagInfo {
  id: number;
  categoryName: string;
}

export type Tag = {
  id: number;
  name: string;
  description: string;
  category: {
    id: number;
    name: string;
  };
};

export type TagListQueryData = {
  tags: Array<Tag>;
};

export type TokenQueryData = {
  token: string;
};
