export const PATH = {
  MAIN: '/',
  STUDY_DETAIL: (studyId = ':studyId') => `/study/${studyId}`,
  LOGIN: '/login',
};

export const DEFAULT_STUDY_CARD_QUERY_PARAM = {
  PAGE: 0,
  SIZE: 12,
};

export const QK_FETCH_STUDY_DETAIL = 'fetch_study_detail';
export const QK_FETCH_STUDY_REVIEWS = 'fetch_study_reviews';

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

export const VALIDATIONS = {
  EXCERPT: {
    LENGTH: {
      MIN: 1,
      MAX: 50,
    },
  },
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
