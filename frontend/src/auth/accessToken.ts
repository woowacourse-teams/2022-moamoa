import { AxiosError } from 'axios';

import { API_ERROR } from '@constants';

import { deleteRefreshToken, getAccessToken } from '@api';

class AccessTokenController {
  private static ACCESS_TOKEN_KEY = 'accessToken';
  private static _tokenExpiredMsTime: number = 20 * 60000;

  static setAccessToken(newAccessToken: string) {
    window.sessionStorage.setItem(this.ACCESS_TOKEN_KEY, newAccessToken);
  }

  static get accessToken() {
    return window.sessionStorage.getItem(this.ACCESS_TOKEN_KEY);
  }

  static setTokenExpiredMsTime(newTime: number) {
    this._tokenExpiredMsTime = Math.max(Math.floor(newTime * 0.8), newTime - 5 * 60000);
  }

  static get tokenExpiredMsTime() {
    return this._tokenExpiredMsTime;
  }

  static get hasAccessToken() {
    return !!this.accessToken;
  }

  static removeAccessToken() {
    window.sessionStorage.removeItem(this.ACCESS_TOKEN_KEY);
  }

  private static async fetchLogout() {
    try {
      await deleteRefreshToken();
      this.removeAccessToken();
    } catch (error) {
      alert('로그아웃에 실패했습니다. :(');
      window.location.reload();
    }
  }

  static async fetchAccessTokenWithRefresh() {
    try {
      const data = await getAccessToken();
      this.setAccessToken(data.accessToken);
      this.setTokenExpiredMsTime(data.expiredTime);

      setTimeout(() => {
        this.fetchAccessTokenWithRefresh();
      }, this.tokenExpiredMsTime);
    } catch (error) {
      if (!(error instanceof AxiosError)) return;
      if (error.response?.data.code === API_ERROR.EXPIRED_REFRESH_TOKEN.CODE) {
        await this.fetchLogout();
      }
    }
  }
}

export default AccessTokenController;
