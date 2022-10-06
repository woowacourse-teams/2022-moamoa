class AccessTokenController {
  private static ACCESS_TOKEN_KEY = 'mmatk';
  private static TOKEN_DATE_TIME_KEY = 'mmTokenDateTime';

  static setAccessToken(newAccessToken: string) {
    window.sessionStorage.setItem(this.ACCESS_TOKEN_KEY, newAccessToken);
  }

  static setTokenDateTime(expiredTime: number) {
    const currentDateTime = new Date();
    const properDateTime = new Date(currentDateTime);
    const expiredDateTime = new Date(currentDateTime);

    const fiveMin = 5 * 60 * 1000;
    const expiredTimeMinus5Min = expiredTime - fiveMin;
    const properTime = expiredTimeMinus5Min > fiveMin ? expiredTimeMinus5Min : Math.floor(expiredTime * 0.8);
    properDateTime.setMilliseconds(properDateTime.getMilliseconds() + properTime);

    expiredDateTime.setMilliseconds(expiredDateTime.getMilliseconds() + expiredTime);

    window.sessionStorage.setItem(this.TOKEN_DATE_TIME_KEY, JSON.stringify({ properDateTime, expiredDateTime }));
  }

  static get accessToken(): string | null {
    return window.sessionStorage.getItem(this.ACCESS_TOKEN_KEY);
  }

  static get tokenDateTime(): { properDateTime: Date; expiredDateTime: Date } | null {
    const result = window.sessionStorage.getItem(this.TOKEN_DATE_TIME_KEY);
    if (!result) return null;

    try {
      const { properDateTime, expiredDateTime } = JSON.parse(result, (key, value) =>
        key === 'properDateTime' || key === 'expiredDateTime' ? new Date(value) : value,
      ) as {
        properDateTime: unknown;
        expiredDateTime: unknown;
      };

      if (!(properDateTime instanceof Date) || !(expiredDateTime instanceof Date)) return null;
      return { properDateTime, expiredDateTime };
    } catch (error) {
      console.error('corrupted datetime data: ', error);
      return null;
    }
  }

  static get hasAccessToken() {
    return !!this.accessToken;
  }

  static get hasTokenDateTime() {
    return !!this.tokenDateTime;
  }

  static removeAccessToken() {
    window.sessionStorage.removeItem(this.ACCESS_TOKEN_KEY);
  }

  static removeTokenDateTime() {
    window.sessionStorage.removeItem(this.TOKEN_DATE_TIME_KEY);
  }

  static save(accesssToken: string, expiredTime: number) {
    this.setAccessToken(accesssToken);
    this.setTokenDateTime(expiredTime);
  }

  static clear() {
    this.removeAccessToken();
    this.removeTokenDateTime();
  }
}

export default AccessTokenController;
