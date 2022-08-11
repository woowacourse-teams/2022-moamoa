import { AxiosError } from 'axios';

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
    this._tokenExpiredMsTime = newTime;
  }

  static get tokenExpiredMsTime() {
    return this._tokenExpiredMsTime;
  }

  static removeAccessToken() {
    this._accessToken = null;
  }

  private static async fetchLogout() {
    try {
      await deleteRefreshToken();
      AccessTokenController.removeAccessToken();
    } catch (error) {
      alert('로그아웃에 실패했습니다. 새로고침 해주세요.');
    }
  }

  static async fetchAccessTokenWithRefresh() {
    try {
      const data = await getAccessToken();
      this.setAccessToken(data.accessToken);
      this.setTokenExpiredMsTime(Math.floor(data.expiredTime * 0.8));

      setTimeout(() => {
        this.fetchAccessTokenWithRefresh();
      }, this.tokenExpiredMsTime);
    } catch (error) {
      if (error instanceof AxiosError) {
        if (error.response?.data.code === 4001) {
          await this.fetchLogout();
        }
      }
    }
  }
}

export default AccessTokenController;
