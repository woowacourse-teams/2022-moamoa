import React from 'react';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import { SearchProvider } from '@context/search/SearchProvider';

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
        <SearchProvider>
          <GlobalStyles />
          <Story {...context} />
        </SearchProvider>
      </ThemeProvider>
    );
  },
];
