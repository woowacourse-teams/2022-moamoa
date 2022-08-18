export type EmptyObject = Record<string, never>;

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type Required<T, K extends keyof T> = T & {
  [P in K]-?: T[P];
};

export type MakeRequired<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>> & Required<T, K>;

export type Noop = () => void;

export type oneToNine = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9;
export type d = oneToNine | 0;
export type DD = `0${oneToNine}` | `1${d}` | `2${d}` | `3${0 | 1}`;
export type MM = `0${oneToNine}` | `1${0 | 1 | 2}`;
export type YYYY = `20${d}${d}`;
export type DateYMD = `${YYYY}-${MM}-${DD}`;

export type RecruitmentStatus = 'RECRUITMENT_START' | 'RECRUITMENT_END';

export type StudyId = number;
export type TagId = number;
export type ReviewId = number;
export type MemberId = number;
export type CategoryId = number;
export type LinkId = number;
export type Page = number;
export type Size = number;

export type Owner = {
  id: MemberId;
  username: string;
  imageUrl: string;
  profileUrl: string;
};

export type Member = {
  id: MemberId;
  username: string;
  imageUrl: string;
  profileUrl: string;
};

export type CategoryName = 'generation' | 'area' | 'subject';

export type Tag = {
  id: TagId;
  name: string;
  description: string;
  category: {
    id: CategoryId;
    name: CategoryName;
  };
};
export type TagInfo = Pick<Tag, 'id'> & { categoryName: CategoryName };

export type StudyDetail = {
  id: StudyId;
  title: string;
  excerpt: string;
  thumbnail: string;
  recruitmentStatus: RecruitmentStatus;
  description: string;
  currentMemberCount: number;
  maxMemberCount?: number;
  createdDate: DateYMD;
  enrollmentEndDate?: DateYMD;
  startDate: DateYMD;
  endDate?: DateYMD;
  owner: Owner & { participationDate: DateYMD; numberOfStudy: number };
  members: Array<Member & { participationDate: DateYMD; numberOfStudy: number }>;
  tags: Array<Tag>;
};

export type Study = Pick<StudyDetail, 'id' | 'title' | 'excerpt' | 'thumbnail' | 'recruitmentStatus'>;

export type StudyReview = {
  id: ReviewId;
  member: Member;
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
  content: string;
};

export type StudyStatus = 'PREPARE' | 'IN_PROGRESS' | 'DONE';

export type MyStudy = Pick<StudyDetail, 'id' | 'title' | 'startDate' | 'endDate'> & {
  studyStatus: StudyStatus;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  owner: Owner;
};

export type UserRole = 'OWNER' | 'MEMBER' | 'NON_MEMBER';

export type Link = {
  id: number;
  author: Member;
  linkUrl: string;
  description: string;
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
};
