export const PATH = {
  MAIN: '/',
  STUDY_DETAIL: (studyId: ':studyId' | number = ':studyId') => `/study/${studyId}`,
  CREATE_STUDY: '/study/create',
  EDIT_STUDY: (studyId: ':studyId' | number = ':studyId') => `/study/edit/${studyId}`,
  MY_STUDY: '/my/study',
  STUDY_ROOM: (studyId: ':studyId' | number = ':studyId') => `/studyroom/${studyId}`,
  LOGIN: '/login',
  COMMUNITY: (studyId: ':studyId' | number = ':studyId') => `/studyroom/${studyId}/community`,
  REVIEW: (studyId: string | number = ':studyId') => `/studyroom/${studyId}/reviews`,
  COMMUNITY_ARTICLE: (studyId: string | number = ':studyId', articleId: string | number = ':articleId') =>
    `/studyroom/${studyId}/community/article/${articleId}`,
  COMMUNITY_PUBLISH: (studyId: string | number = ':studyId') => `/studyroom/${studyId}/community/article/publish`,
  COMMUNITY_EDIT: (studyId: string | number = ':studyId', articleId: string | number = ':articleId') =>
    `/studyroom/${studyId}/community/article/${articleId}/edit`,
};

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

export const DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT = 6;
export const DEFAULT_LOAD_STUDY_REVIEW_COUNT = 6;

export const BREAK_POINTS = {
  xs: 0,
  sm: 576,
  md: 768,
  lg: 992,
  xl: 1280,
  xxl: 1400,
};

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
      return /[(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/.test(
        text,
      );
    },
    get MESSAGE() {
      return 'URL을 입력해주세요.';
    },
  },
};

export const LINK_DESCRIPTION_LENGTH = {
  MIN: {
    VALUE: 1,
    get MESSAGE() {
      return `${this.VALUE}글자 이상이어야 합니다`;
    },
  },
  MAX: {
    VALUE: 40,
    get MESSAGE() {
      return `${this.VALUE}글자까지 입력할 수 있습니다`;
    },
  },
};
