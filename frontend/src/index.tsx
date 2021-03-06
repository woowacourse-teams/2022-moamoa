import { createRoot } from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import { SearchProvider } from '@context/search/SearchProvider';

import App from './App';

if (process.env.NODE_ENV == 'development') {
  const { worker } = require('./mocks/browser');
  worker.start();
}

const $root = document.getElementById('root');
if ($root) {
  const root = createRoot($root);
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        refetchOnWindowFocus: false,
      },
    },
  });
  root.render(
    <ThemeProvider theme={theme}>
      <QueryClientProvider client={queryClient}>
        <SearchProvider>
          <GlobalStyles />
          <App />
        </SearchProvider>
      </QueryClientProvider>
    </ThemeProvider>,
  );
} else {
  throw new Error('root element is not exist');
}
