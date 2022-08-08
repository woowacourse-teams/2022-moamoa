import { createRoot } from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import { LoginProvider } from '@context/login/LoginProvider';
import { SearchProvider } from '@context/search/SearchProvider';
import { UserInfoProvider } from '@context/userInfo/UserInfoProvider';

import App from './App';

if (process.env.NODE_ENV == 'development') {
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const { worker } = require('./mocks/browser');
  // worker.start();j
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
        <UserInfoProvider>
          <LoginProvider>
            <SearchProvider>
              <GlobalStyles />
              <BrowserRouter>
                <App />
              </BrowserRouter>
            </SearchProvider>
          </LoginProvider>
        </UserInfoProvider>
      </QueryClientProvider>
    </ThemeProvider>,
  );
} else {
  throw new Error('root element is not exist');
}
