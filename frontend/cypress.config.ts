import { defineConfig } from 'cypress';

import webpackConfig from './webpack/webpack.dev';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:3000',
  },

  component: {
    devServer: {
      framework: 'react',
      bundler: 'webpack',
      webpackConfig,
    },
  },
});
