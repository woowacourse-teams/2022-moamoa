/* eslint-disable @typescript-eslint/no-var-requires */
const webpack = require('webpack');
const { join } = require('path');
const { merge } = require('webpack-merge');

require('dotenv').config({ path: join(__dirname, '../env/.env.local') });

const common = require('./webpack.common');

module.exports = merge(common, {
  mode: 'development',
  devtool: 'eval-source-map',
  devServer: {
    open: true,
    port: 3000,
    compress: true,
    client: {
      overlay: {
        errors: true,
        warnings: true,
      },
    },
    historyApiFallback: true,
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env.API_URL': JSON.stringify(process.env.API_URL),
      'process.env.CLIENT_ID': JSON.stringify(process.env.CLIENT_ID),
      'process.env.LINK_PREVIEW_API_URL': JSON.stringify(process.env.LINK_PREVIEW_API_URL),
    }),
  ],
});
