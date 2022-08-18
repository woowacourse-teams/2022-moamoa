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

export type Study = {
  id: StudyId;
  title: string;
  excerpt: string;
  thumbnail: string;
  recruitmentStatus: RecruitmentStatus;
};

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

export type StudyTag = { id: TagId; name: string };

export type StudyDetail = {
  id: StudyId;
  title: string;
  excerpt: string;
  thumbnail: string;
  recruitmentStatus: RecruitmentStatus;
  description: string;
  currentMemberCount: number;
  maxMemberCount?: number;
  createdDate: string;
  enrollmentEndDate?: string;
  startDate: string;
  endDate?: string;
  owner: Owner;
  members: Array<Member>;
  tags: Array<StudyTag>;
} & Study;

export type StudyReview = {
  id: ReviewId;
  member: Member;
  createdDate: DateYMD;
  lastModifiedDate: string;
  content: string;
};

export type Tag = {
  id: TagId;
  name: string;
  description: string;
  category: {
    id: CategoryId;
    name: string;
  };
};
export type TagInfo = Pick<Tag, 'id'> & { categoryName: Tag['category']['name'] };

export type StudyStatus = 'PREPARE' | 'IN_PROGRESS' | 'DONE';

export type MyStudy = Pick<
  StudyDetail,
  'id' | 'title' | 'currentMemberCount' | 'maxMemberCount' | 'startDate' | 'endDate' | 'owner' | 'tags'
> & {
  studyStatus: StudyStatus;
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

export type CommunityArticle = {
  id: number;
  author: Member;
  title: string;
  content: string;
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
};

export type CommunityArticleMode = 'publish' | 'edit';
