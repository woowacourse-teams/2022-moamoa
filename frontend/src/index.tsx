import { AxiosError } from 'axios';
import { createRoot } from 'react-dom/client';
import { QueryCache, QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import AccessTokenController from '@auth/accessToken';

import { LoginProvider } from '@context/login/LoginProvider';
import { SearchProvider } from '@context/search/SearchProvider';
import { UserInfoProvider } from '@context/userInfo/UserInfoProvider';

import App from './App';

if (process.env.NODE_ENV == 'development') {
  // eslint-disable-next-line @typescript-eslint/no-var-requires
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
    queryCache: new QueryCache({
      onError: error => {
        if (error instanceof AxiosError) {
          if (error.response?.status === 401) {
            alert(`문제가 발생했습니다. 관리자에게 문의해주세요 :( ${error.message}`);
            window.location.reload();
          }
        }
      },
    }),
  });

  AccessTokenController.fetchAccessTokenWithRefresh().finally(() => {
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
  });
} else {
  throw new Error('root element is not exist');
}
