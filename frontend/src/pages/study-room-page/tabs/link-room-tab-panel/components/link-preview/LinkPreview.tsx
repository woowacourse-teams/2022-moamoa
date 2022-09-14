import tw from '@utils/tw';

import { ApiLinkPreview } from '@api/link-preview';

import Card from '@components/card/Card';
import { RightUpArrowIcon } from '@components/icons';
import Image from '@components/image/Image';

import * as S from '@study-room-page/tabs/link-room-tab-panel/components/link-preview/LinkPreview.style';

export type LinkPreviewProps = {
  previewResult: ApiLinkPreview['get']['responseData'];
  linkUrl: string;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, linkUrl }) => {
  const domain = new URL(linkUrl);

  return (
    <S.PreviewContainer>
      <Card height="240px">
        <div css={tw`mb-16 flex-grow overflow-hidden`}>
          <Image
            shape="rectangular"
            alt={`${previewResult.title} 썸네일`}
            src={previewResult.imageUrl}
            width="100%"
            height="100%"
          />
        </div>
        <S.PreviewDomain>
          <RightUpArrowIcon />
          <span>{domain.hostname.replace('www.', '')}</span>
        </S.PreviewDomain>
        <div>
          <Card.Heading>{previewResult.title}</Card.Heading>
          <Card.Content>{previewResult.description}</Card.Content>
        </div>
      </Card>
    </S.PreviewContainer>
  );
};

export default LinkPreview;
