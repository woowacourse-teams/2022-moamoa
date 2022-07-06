declare module '*.jpg';
declare module '*.jpeg';
declare module '*.png';
declare module '*.json';

declare namespace NodeJS {
  export interface ProcessEnv {
    API_URL: string;
  }
}
