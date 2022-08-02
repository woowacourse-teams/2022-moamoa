declare module '*.jpg';
declare module '*.jpeg';
declare module '*.png';
declare module '*.json';

declare namespace NodeJS {
  export type ProcessEnv = {
    API_URL: string;
    CLIENT_ID: string;
  };
}
