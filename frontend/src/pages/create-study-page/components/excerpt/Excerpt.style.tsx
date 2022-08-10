import styled from '@emotion/styled';

import { Textarea as OriginalTextarea } from '@create-study-page/components/textarea/Textarea.style';

export const Excerpt = styled.div``;

export const Container = styled.div`
  position: relative;
`;

export const Label = styled.label`
  display: block;

  height: 0;
  width: 0;

  visibility: hidden;
`;

export const LetterCounterContainer = styled.div`
  position: absolute;
  right: 4px;
  bottom: 2px;

  display: flex;
  justify-content: flex-end;
`;

export const Textarea = styled(OriginalTextarea)`
  display: block;
  min-height: 50px;
  width: 100%;
  font-size: 16px;
`;
