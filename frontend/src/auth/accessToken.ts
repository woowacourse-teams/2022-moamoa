class AccessTokenController {
  private static accessToken = '';

  static setAccessToken(newAccessToken: string) {
    this.accessToken = newAccessToken;
  }

  static getAccessToken() {
    return this.accessToken;
  }

  static removeAccessToken() {
    this.accessToken = '';
  }
}

export default AccessTokenController;
