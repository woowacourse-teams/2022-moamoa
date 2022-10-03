import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';
import tw from '@utils/tw';

import { ApiLinkPreview } from '@api/link-preview';

import Card from '@components/card/Card';
import { RightUpArrowIcon } from '@components/icons';
import Image from '@components/image/Image';

export type LinkPreviewProps = {
  previewResult: ApiLinkPreview['get']['responseData'];
  linkUrl: string;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, linkUrl }) => {
  const domain = new URL(linkUrl);

  return (
    <Self>
      <Card custom={{ height: '240px' }}>
        <div css={tw`mb-16 flex-grow overflow-hidden`}>
          <Image shape="rectangular" alt={`${previewResult.title} 썸네일`} src={previewResult.imageUrl} />
        </div>
        <PreviewDomain>
          <RightUpArrowIcon />
          <span>{domain.hostname.replace('www.', '')}</span>
        </PreviewDomain>
        <div>
          <Card.Heading>{previewResult.title}</Card.Heading>
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
