import { createRoot } from 'react-dom/client';

import { ThemeProvider } from '@emotion/react';

import GlobalStyles from '@styles/Globalstyles';
import { theme } from '@styles/theme';

import App from './App';

const $root = document.getElementById('root');
if ($root) {
  const root = createRoot($root);
  root.render(
    <ThemeProvider theme={theme}>
      <GlobalStyles />
      <App />
    </ThemeProvider>,
  );
} else {
  throw new Error('root element is not exist');
}
