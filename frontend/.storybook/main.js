const { resolve } = require('path');

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
      '@context': resolve(__dirname, '../src/context'),
      '@detail-page': resolve(__dirname, '../src/pages/detail-page'),
      '@create-study-page': resolve(__dirname, '../src/pages/create-study-page'),
      '@layout': resolve(__dirname, '../src/layout'),
      '@hooks': resolve(__dirname, '../src/hooks'),
    };

    config.module.rules[0].use[0].options.presets = [
      require.resolve('@babel/preset-env'),
      [
        require.resolve('@babel/preset-react'),
        {
          runtime: 'automatic',
          importSource: '@emotion/react',
        },
      ],
    ];

    config.module.rules[0].use[0].options.plugins = [
      [
        require.resolve('@emotion/babel-plugin'),
        {
          sourceMap: true,
          autoLabel: 'dev-only',
          labelFormat: '[local]',
          cssPropOptimization: true,
        },
      ],
    ];

    return config;
  },
};
