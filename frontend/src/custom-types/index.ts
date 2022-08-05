export type EmptyObject = Record<string, never>;

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type Required<T, K extends keyof T> = T & {
  [P in K]-?: T[P];
};

export type MakeRequired<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>> & Required<T, K>;

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

export type TagInfo = {
  id: TagId;
  categoryName: string;
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

export type StudyStatus = 'PREPARE' | 'IN_PROGRESS' | 'DONE';

export type MyStudy = Pick<
  StudyDetail,
  'id' | 'title' | 'currentMemberCount' | 'maxMemberCount' | 'startDate' | 'endDate' | 'owner' | 'tags'
> & {
  studyStatus: StudyStatus;
};

export type UserRole = 'OWNER' | 'MEMBER' | 'NON_MEMBER';

export type GetStudyDetailRequestParams = {
  studyId: number;
};
export type GetStudyDetailResponseData = StudyDetail;

export type GetStudyListRequestParams = {
  page?: number;
  size?: number;
  title: string;
  selectedFilters: Array<TagInfo>;
};
export type GetStudyListResponseData = {
  studies: Array<Study>;
  hasNext: boolean;
};

export type GetTagListResponseData = {
  tags: Array<Tag>;
};

export type PostTokenRequestParams = {
  code: string;
};
export type PostTokenResponseData = {
  token: string;
};

export type GetReviewResponseData = {
  reviews: Array<StudyReview>;
  totalCount: number;
};
export type GetReviewRequestParams = {
  studyId: number;
  size?: number;
};
export type PostReviewRequestParams = {
  studyId: StudyId;
};
export type PostReviewRequestBody = {
  content: string;
};
export type PostReviewRequestVariables = PostReviewRequestParams & PostReviewRequestBody;

export type PatchReviewRequestParams = {
  studyId: number;
  reviewId: number;
};
export type PutReviewRequestBody = {
  content: string;
};
export type PutReviewRequestVariables = PatchReviewRequestParams & PutReviewRequestBody;

export type DeleteReviewRequestBody = {
  studyId: StudyId;
  reviewId: ReviewId;
};

export type GetMyStudyResponseData = {
  studies: Array<MyStudy>;
};

export type PostJoiningStudyRequestParams = {
  studyId: StudyId;
};

export type PostNewStudyRequestBody = {
  tagIds?: Array<TagId>;
  thumbnail: string;
} & MakeOptional<
  Pick<
    StudyDetail,
    'title' | 'excerpt' | 'description' | 'maxMemberCount' | 'enrollmentEndDate' | 'startDate' | 'endDate' | 'owner'
  >,
  'maxMemberCount' | 'enrollmentEndDate' | 'endDate' | 'owner'
>;

export type GetUserRoleRequestParams = {
  studyId: StudyId;
};
export type GetUserRoleResponseData = {
  role: UserRole;
};

export type GetUserInformationResponseData = Member;
