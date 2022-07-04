import { ThemeProvider } from '@emotion/react';
import { createRoot } from 'react-dom/client';

import { theme } from '@styles/theme';

import App from './App';

const $root = document.getElementById('root');
if ($root) {
  const root = createRoot($root);
  root.render(
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>,
  );
} else {
  throw new Error('root element is not exist');
}
