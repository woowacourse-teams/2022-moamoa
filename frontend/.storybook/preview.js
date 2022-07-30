import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import { FormProvider } from '@hooks/useForm';

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

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

export const decorators = [
  (Story, context) => {
    return (
      <BrowserRouter>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={theme}>
            <FormProvider>
              <SearchProvider>
                <GlobalStyles />
                <Story {...context} />
              </SearchProvider>
            </FormProvider>
          </ThemeProvider>
        </QueryClientProvider>
      </BrowserRouter>
    );
  },
];
