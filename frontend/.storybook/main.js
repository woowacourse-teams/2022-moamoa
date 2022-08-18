const { resolve } = require('path');
const { ESBuildMinifyPlugin } = require('esbuild-loader');

module.exports = {
  stories: ['../src/**/*.stories.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: ['@storybook/addon-links', '@storybook/addon-essentials', '@storybook/addon-interactions'],
  framework: '@storybook/react',
  core: {
    builder: 'webpack5',
  },
  webpackFinal: async config => {
    config.resolve.alias = {
      ...config.resolve.alias,
      '@root': resolve(__dirname, '../'),
      '@src': resolve(__dirname, '../src/'),
      '@components': resolve(__dirname, '../src/components'),
      '@styles': resolve(__dirname, '../src/styles'),
      '@types': resolve(__dirname, '../src/types'),
      '@pages': resolve(__dirname, '../src/pages'),
      '@assets': resolve(__dirname, '../src/assets'),
      '@utils': resolve(__dirname, '../src/utils'),
      '@constants': resolve(__dirname, '../src/constants.ts'),
      '@api': resolve(__dirname, '../src/api'),
      '@auth': resolve(__dirname, '../src/auth'),
      '@context': resolve(__dirname, '../src/context'),
      '@detail-page': resolve(__dirname, '../src/pages/detail-page'),
      '@main-page': resolve(__dirname, '../src/pages/main-page'),
      '@create-study-page': resolve(__dirname, '../src/pages/create-study-page'),
      '@edit-study-page': resolve(__dirname, '../src/pages/edit-study-page'),
      '@my-study-page': resolve(__dirname, '../src/pages/my-study-page'),
      '@community-tab': resolve(__dirname, '../src/pages/study-room-page/tabs/community-tab-panel'),
      '@study-room-page': resolve(__dirname, '../src/pages/study-room-page'),
      '@login-redirect-page': resolve(__dirname, '../src/pages/login-redirect-page'),
      '@error-page': resolve(__dirname, '../src/pages/error-page'),
      '@layout': resolve(__dirname, '../src/layout'),
      '@hooks': resolve(__dirname, '../src/hooks'),
      '@mocks': resolve(__dirname, '../src/mocks'),
    };

    config.module.rules = [
      ...config.module.rules,
      {
        test: /\.(ts|tsx)$/,
        exclude: /node_modules/,
        loader: 'esbuild-loader',
        options: {
          loader: 'tsx',
          target: 'es2022',
        },
      },
      {
        test: /\.(png|jpg|jpeg)$/i,
        type: 'asset/resource',
      },
    ];

    config.optimization.minimizer = [
      new ESBuildMinifyPlugin({
        target: 'es2020',
      }),
    ];

    return config;
  },
};
