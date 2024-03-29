import { ArticleId, StudyId } from '@custom-types';

export const PATH = {
  MAIN: '/',
  STUDY_DETAIL: (studyId: ':studyId' | StudyId = ':studyId') => `/study/${studyId}`,
  CREATE_STUDY: '/study/create',
  EDIT_STUDY: (studyId: ':studyId' | StudyId = ':studyId') => `/study/edit/${studyId}`,
  MY_STUDY: '/my/study',
  DRAFT: '/draft',
  LOGIN: '/login',

  STUDY_ROOM: (studyId: ':studyId' | StudyId = ':studyId') => `/studyroom/${studyId}`,

  REVIEW: 'reviews',
  LINK: 'links',

  NOTICE: 'notice',
  NOTICE_PUBLISH: 'article/publish',
  NOTICE_ARTICLE: (articleId: ':articleId' | ArticleId = ':articleId') => `article/${articleId}`,
  NOTICE_EDIT: (articleId: ':articleId' | ArticleId = ':articleId') => `article/${articleId}/edit`,

  COMMUNITY: 'community',
  COMMUNITY_PUBLISH: 'article/publish',
  DRAFT_COMMUNITY_PUBLISH: (articleId: ':articleId' | ArticleId = ':articleId') => `draft-article/${articleId}/publish`,
  COMMUNITY_ARTICLE: (articleId: ':articleId' | ArticleId = ':articleId') => `article/${articleId}`,
  COMMUNITY_EDIT: (articleId: ':articleId' | ArticleId = ':articleId') => `article/${articleId}/edit`,
} as const;

export const API_ERROR = {
  EXPIRED_REFRESH_TOKEN: {
    CODE: 4001,
    MESSAGE: '오류가 발생했습니다 :(',
  },
};

export const DEFAULT_STUDY_CARD_QUERY_PARAM = {
  PAGE: 0,
  SIZE: 12,
};
export const DEFAULT_LINK_QUERY_PARAM = {
  PAGE: 0,
  SIZE: 9,
};
export const DEFAULT_COMMUNITY_ARTICLE_DRAFT_QUERY_PARAM = {
  PAGE: 0,
  SIZE: 6,
};

export const DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT = 6;
export const DEFAULT_LOAD_STUDY_REVIEW_COUNT = 6;

export const EXCERPT_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 50,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};

export const TITLE_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 30,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};

export const DESCRIPTION_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 20000,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};

export const MEMBER_COUNT = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}명 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 100,
    get MESSAGE() {
      return `${this.VALUE}명까지 입력할 수 있습니다`;
    },
  },
};

export const REVIEW_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 200,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};

export const LINK_URL_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 1000,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
  FORMAT: {
    TEST(text: string) {
      return /http(s)?:\/\/[(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/.test(text);
    },
    get MESSAGE() {
      return 'URL을 입력해주세요.';
    },
  },
};

export const LINK_DESCRIPTION_LENGTH = {
  MAX: {
    VALUE: 40,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};

export const SUBJECT_TAG_COUNT = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `주제를 최소 ${this.VALUE}개 선택해야 합니다.`;
    },
  },
};

export const COMMA = ',';

export const RECRUITMENT_STATUS = {
  START: 'RECRUITMENT_START',
  END: 'RECRUITMENT_END',
} as const;

export const USER_ROLE = {
  OWNER: 'OWNER',
  MEMBER: 'MEMBER',
  NON_MEMBER: 'NON_MEMBER',
} as const;

export const CATEGORY_NAME = {
  GENERATION: 'generation',
  AREA: 'area',
  SUBJECT: 'subject',
  AREA_FE: 'area-fe',
  AREA_BE: 'area-be',
} as const;

export const STUDY_STATUS = {
  PREPARE: 'PREPARE',
  IN_PROGRESS: 'IN_PROGRESS',
  DONE: 'DONE',
} as const;

export const PERIOD = {
  START_DATE: 'start-date',
  END_DATE: 'end-date',
};

export const TITLE = 'title';

export const CONTENT = 'content';

export const DRAFT_SAVE_TIME = {
  FIVE_MINUTES: 5 * 60 * 1000,
  THIRTY_SECONDS: 3 * 1000,
};
