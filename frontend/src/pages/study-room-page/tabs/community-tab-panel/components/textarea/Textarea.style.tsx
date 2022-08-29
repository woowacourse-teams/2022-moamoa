import styled from '@emotion/styled';

import { Input } from '@components/input/Input.style';

export const Textarea = styled(Input.withComponent('textarea'))`
  overflow: auto;
  padding: 8px 10px;
`;
