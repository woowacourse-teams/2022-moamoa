import DOMPurify from 'dompurify';
import { marked } from 'marked';
import { useEffect, useRef } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import markdown from '@styles/markdown';

type MarkdownRenderProps = {
  markdownContent: string;
};

const MarkdownRender = ({ markdownContent }: MarkdownRenderProps) => {
  const contentRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!contentRef.current) return;
    const cleanHtml = DOMPurify.sanitize(marked.parse(markdownContent));
    contentRef.current.innerHTML = cleanHtml;
  }, [contentRef, markdownContent]);

  return (
    <Self>
      <div
        css={css`
          ${markdown}
        `}
        ref={contentRef}
      ></div>
    </Self>
  );
};

export default MarkdownRender;

const Self = styled.div`
  overflow-y: auto;
  height: 100%;
`;
