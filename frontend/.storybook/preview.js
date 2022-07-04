import { ThemeProvider } from '@emotion/react';
import React from 'react';

import { theme } from '@styles/theme';

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

export const decorators = [
  (Story, context) => {
    return (
      <ThemeProvider theme={theme}>
        <Story {...context} />
      </ThemeProvider>
    );
  },
];
