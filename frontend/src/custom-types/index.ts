import { CATEGORY_NAME, RECRUITMENT_STATUS, STUDY_STATUS, USER_ROLE } from '@constants';

export type EmptyObject = Record<string, never>;

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type MakeRequired<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>> & Required<Pick<T, K>>;

export type Merge<T, U> = Omit<T, keyof U> & U;

export type Noop = () => void;

export type CssLengthUnits = 'px' | 'rem' | '%';
export type CssLength = 0 | `${number}${CssLengthUnits}` | 'auto' | 'fit-content' | `calc(${string})`;

export type oneToNine = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9;
export type d = oneToNine | 0;
export type DD = `0${oneToNine}` | `1${d}` | `2${d}` | `3${0 | 1}`;
export type MM = `0${oneToNine}` | `1${0 | 1 | 2}`;
export type YYYY = `20${d}${d}`;
export type DateYMD = `${YYYY}-${MM}-${DD}`;

export type RecruitmentStatus = typeof RECRUITMENT_STATUS[keyof typeof RECRUITMENT_STATUS];

export type StudyId = number;
export type TagId = number;
export type ReviewId = number;
export type CommunityCommentId = number;
export type NoticeCommentId = number;
export type MemberId = number;
export type CategoryId = number;
export type LinkId = number;
export type Page = number;
export type Size = number;
export type ArticleId = number;

export type Member = {
  id: MemberId;
  username: string;
  imageUrl: string;
  profileUrl: string;
};

export type Owner = Member;

export type CategoryName = typeof CATEGORY_NAME[keyof typeof CATEGORY_NAME];

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

export type Study = Pick<StudyDetail, 'id' | 'title' | 'excerpt' | 'thumbnail' | 'recruitmentStatus'> & {
  tags: Array<Pick<Tag, 'id' | 'name'>>;
};

export type StudyReview = {
  id: ReviewId;
  member: Member; // @TODO: member -> author로 변경한다
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
  content: string;
};

export type CommunityComment = {
  id: CommunityCommentId;
  author: Member;
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
  content: string;
};

export type StudyStatus = typeof STUDY_STATUS[keyof typeof STUDY_STATUS];

export type MyStudy = Pick<StudyDetail, 'id' | 'title' | 'startDate' | 'endDate'> & {
  studyStatus: StudyStatus;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  owner: Owner;
};

export type UserRole = typeof USER_ROLE[keyof typeof USER_ROLE];

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

export type NoticeArticle = {
  id: number;
  author: Member;
  title: string;
  content: string;
  createdDate: DateYMD;
  lastModifiedDate: DateYMD;
};

export type NoticeArticleMode = 'publish' | 'edit';

// api
export type GetMethod<Params, ResponseData> = {
  variables: Params;
  responseData: ResponseData;
};

export type PostMethod<Params, Body, ResponseData> = {
  variables: Params & Body;
  responseData: ResponseData;
};

export type PutMethod<Params, Body> = {
  variables: Params & Body;
  responseData: null;
};

export type DeleteMethod<Params> = {
  variables: Params;
  responseData: null;
};
