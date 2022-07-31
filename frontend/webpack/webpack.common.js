/* eslint-disable @typescript-eslint/no-var-requires */
const { join, resolve } = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: join(__dirname, '../src/index.tsx'),
  devtool: 'eval-source-map',
  output: {
    filename: 'main.js',
    path: join(__dirname, '../dist'),
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.(ts|tsx)$/,
        exclude: /node_modules/,
        use: ['babel-loader'],
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
    }),
    new CleanWebpackPlugin(),
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
      '@context': resolve(__dirname, '../src/context'),
      '@main-page': resolve(__dirname, '../src/pages/main-page'),
      '@detail-page': resolve(__dirname, '../src/pages/detail-page'),
      '@create-study-page': resolve(__dirname, '../src/pages/create-study-page'),
      '@my-study-page': resolve(__dirname, '../src/pages/my-study-page'),
      '@review-page': resolve(__dirname, '../src/pages/review-page'),
      '@layout': resolve(__dirname, '../src/layout'),
      '@hooks': resolve(__dirname, '../src/hooks'),
      '@mocks': resolve(__dirname, '../src/mocks'),
    },
  },
};
