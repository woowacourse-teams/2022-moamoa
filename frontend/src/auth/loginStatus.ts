import { AxiosError, type AxiosPromise } from 'axios';

import { deleteLogout, getLoginStatus, getRefresh } from '@api/auth';

type AxiosRefetchFn = () => AxiosPromise;

class LoginStatusController {
  private static _initialLoginStatus = false;

  static get initialLoginStatus() {
    return this._initialLoginStatus;
  }

  static setInitialLoginStatus(isLoggedIn: boolean) {
    this._initialLoginStatus = isLoggedIn;
  }

  private static async fetchLogout() {
    try {
      await deleteLogout();
    } catch (error) {
      alert('알 수 없는 문제로 로그아웃에 실패했습니다. :( 관리자에게 문의해주세요.');
    }
  }

  static async fetchLoginStatusWhenReload() {
    try {
      const data = await getLoginStatus();
      this.setInitialLoginStatus(data.isLoggedIn);
    } catch (error) {
      if (!(error instanceof AxiosError)) return;
      await this.fetchLogout();
    }
  }

  static async fetchAccessTokenWithRefresh(refetch: AxiosRefetchFn) {
    try {
      await getRefresh();
      return refetch();
    } catch (error) {
      console.error('리프레시 실패');
      await this.fetchLogout();
      return Promise.reject(error);
    }
  }
}

export default LoginStatusController;
