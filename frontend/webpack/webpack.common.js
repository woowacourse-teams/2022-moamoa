/* eslint-disable @typescript-eslint/no-var-requires */
const { join, resolve } = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const { ESBuildMinifyPlugin } = require('esbuild-loader');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: join(__dirname, '../src/index.tsx'),
  output: {
    filename: '[name]-[contenthash].js',
    path: join(__dirname, '../dist'),
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        loader: 'esbuild-loader',
        options: {
          loader: 'tsx',
          target: 'esnext',
        },
      },
      {
        test: /\.(png|jpg|jpeg)$/i,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: join(__dirname, '../public/index.html'),
      favicon: join(__dirname, '../public/favicon.png'),
      meta: {
        description: { name: 'description', contnet: '스터디를 쉽고, 편리하게! 스터디 아카이브 모아모아입니다' },
        'og:title': { property: 'og:title', content: '모아모아' },
        'og:type': { property: 'og:type', content: 'website' },
        'og:description': {
          name: 'og:description',
          contnet: '스터디를 쉽고, 편리하게! 스터디 아카이브 모아모아입니다',
        },
        'og:url': { property: 'og:url', content: 'https://moamoa.space' },
        'og:image': { property: 'og:image', content: '%PUBLIC_URL%/open-graph-image.png' },
        'og:image:width': { property: 'og:url', content: 1200 },
        'og:image:height': { property: 'og:url', content: 630 },
      },
    }),
    new CleanWebpackPlugin(),
    new CopyPlugin({
      patterns: [{ from: '../frontend/static', to: '../dist/static' }],
    }),
  ],
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js'],
    alias: {
      '@root': resolve(__dirname, '../'),
      '@src': resolve(__dirname, '../src'),
      '@components': resolve(__dirname, '../src/components'),
      '@styles': resolve(__dirname, '../src/styles'),
      '@custom-types': resolve(__dirname, '../src/custom-types/index.ts'),
      '@pages': resolve(__dirname, '../src/pages'),
      '@assets': resolve(__dirname, '../src/assets'),
      '@utils': resolve(__dirname, '../src/utils'),
      '@constants': resolve(__dirname, '../src/constants.ts'),
      '@api': resolve(__dirname, '../src/api'),
      '@auth': resolve(__dirname, '../src/auth'),
      '@context': resolve(__dirname, '../src/context'),
      '@main-page': resolve(__dirname, '../src/pages/main-page'),
      '@detail-page': resolve(__dirname, '../src/pages/detail-page'),
      '@study-page': resolve(__dirname, '../src/pages/study-page'),
      '@create-study-page': resolve(__dirname, '../src/pages/study-page/create-study-page'),
      '@edit-study-page': resolve(__dirname, '../src/pages/study-page/edit-study-page'),
      '@my-study-page': resolve(__dirname, '../src/pages/my-study-page'),
      '@study-room-page': resolve(__dirname, '../src/pages/study-room-page'),
      '@notice-tab': resolve(__dirname, '../src/pages/study-room-page/tabs/notice-tab-panel'),
      '@community-tab': resolve(__dirname, '../src/pages/study-room-page/tabs/community-tab-panel'),
      '@link-tab': resolve(__dirname, '../src/pages/study-room-page/tabs/link-room-tab-panel'),
      '@review-tab': resolve(__dirname, '../src/pages/study-room-page/tabs/review-tab-panel'),
      '@error-page': resolve(__dirname, '../src/pages/error-page'),
      '@login-redirect-page': resolve(__dirname, '../src/pages/login-redirect-page'),
      '@layout': resolve(__dirname, '../src/layout'),
      '@hooks': resolve(__dirname, '../src/hooks'),
      '@mocks': resolve(__dirname, '../src/mocks'),
    },
  },
  optimization: {
    minimizer: [
      new ESBuildMinifyPlugin({
        target: 'esnext',
      }),
    ],
  },
  cache: {
    type: 'filesystem',
    buildDependencies: {
      config: [__filename],
    },
  },
};
