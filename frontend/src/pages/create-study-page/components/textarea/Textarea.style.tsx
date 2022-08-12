import styled from '@emotion/styled';

import { Input } from '@create-study-page/components/input/Input.style';

export const Textarea = styled(Input.withComponent('textarea'))`
  overflow: auto;
  padding: 8px 10px;
`;
