import { AxiosError } from 'axios';

import { API_ERROR } from '@constants';

import { deleteRefreshToken, getAccessToken } from '@api';

class AccessTokenController {
  private static _accessToken: string | null = null;
  private static _tokenExpiredMsTime: number = 20 * 60000;

  static setAccessToken(newAccessToken: string) {
    this._accessToken = newAccessToken;
  }

  static get accessToken() {
    return this._accessToken;
  }

  static setTokenExpiredMsTime(newTime: number) {
    this._tokenExpiredMsTime = Math.max(Math.floor(newTime * 0.8), newTime - 5 * 60000);
  }

  static get tokenExpiredMsTime() {
    return this._tokenExpiredMsTime;
  }

  static get hasAccessToken() {
    return !!this._accessToken;
  }

  static removeAccessToken() {
    this._accessToken = null;
  }

  private static async fetchLogout() {
    try {
      await deleteRefreshToken();
      AccessTokenController.removeAccessToken();
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
