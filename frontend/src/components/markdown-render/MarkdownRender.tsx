import DOMPurify from 'dompurify';
import { marked } from 'marked';
import { useEffect, useRef } from 'react';

import { css } from '@emotion/react';

import tw from '@utils/tw';

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
    <div css={tw`overflow-auto h-full`}>
      <div
        css={css`
          ${markdown}
        `}
        ref={contentRef}
      ></div>
    </div>
  );
};

export default MarkdownRender;
