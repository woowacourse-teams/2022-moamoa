class AccessTokenController {
  private static _accessToken = '';

  static setAccessToken(newAccessToken: string) {
    this._accessToken = newAccessToken;
  }

  static get accessToken() {
    return this._accessToken;
  }

  static removeAccessToken() {
    this._accessToken = '';
  }

  static async fetchRefreshToken() {}
}

export default AccessTokenController;
