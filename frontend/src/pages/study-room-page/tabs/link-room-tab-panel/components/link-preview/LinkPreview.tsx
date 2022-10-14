import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { ApiLinkPreview } from '@api/link-preview';

import Card from '@shared/card/Card';
import { RightUpArrowIcon } from '@shared/icons';
import Image from '@shared/image/Image';

export type LinkPreviewProps = {
  previewResult: ApiLinkPreview['get']['responseData'];
  linkUrl: string;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, linkUrl }) => {
  const domain = new URL(linkUrl);

  return (
    <Self>
      <Card custom={{ height: '240px' }}>
        <Thumbnail alt={previewResult.title} src={previewResult.imageUrl} />
        <PreviewDomain>
          <RightUpArrowIcon />
          <span>{domain.hostname.replace('www.', '')}</span>
        </PreviewDomain>
        <div>
          <Card.Heading custom={{ marginBottom: '8px' }}>{previewResult.title}</Card.Heading>
          <Card.Content>{previewResult.description}</Card.Content>
        </div>
      </Card>
    </Self>
  );
};

const Self = styled.div`
  position: relative;

  &:hover {
    & div {
      visibility: visible;
      opacity: 1;
    }
  }
`;

type ThumbnailProps = {
  alt?: string;
  src?: string;
};
const Thumbnail: React.FC<ThumbnailProps> = ({ alt, src }) => (
  <div
    css={css`
      margin-bottom: 16px;
      overflow: hidden;
    `}
  >
    <Image shape="rectangular" alt={`${alt} 썸네일`} src={src} />
  </div>
);

const PreviewDomain = styled.div`
  ${({ theme }) => css`
    position: absolute;
    bottom: 82px;
    right: 8px;
    width: 110px;
    height: 30px;
    padding: 4px;

    background-color: ${theme.colors.white};
    border-radius: ${theme.radius.md};
    text-align: center;
    visibility: hidden;
    opacity: 0;
    transition: visibility 0.2s ease, opacity 0.2s ease;

    & > span {
      font-weight: ${theme.fontWeight.bold};
      font-size: ${theme.fontSize.sm};
    }
  `}

  ${nLineEllipsis(1)}
`;

export default LinkPreview;
