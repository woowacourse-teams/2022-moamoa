import { GetLinkPreviewResponseData } from '@api/link-preview';

import CenterImage from '@components/center-image/CenterImage';
import { RightUpArrowSvg } from '@components/svg';

import * as S from '@study-room-page/tabs/link-room-tab-panel/components/link-preview/LinkPreview.style';

export type LinkPreviewProps = {
  previewResult: GetLinkPreviewResponseData;
  linkUrl: string;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, linkUrl }) => {
  const domain = new URL(linkUrl);

  return (
    <S.PreviewContainer>
      <S.PreviewImageContainer>
        <CenterImage src={previewResult.imageUrl} alt={`${previewResult.title} 썸네일`} />
      </S.PreviewImageContainer>
      <S.PreviewDomain>
        <RightUpArrowSvg />
        <span>{domain.hostname.replace('www.', '')}</span>
      </S.PreviewDomain>
      <S.PreviewContentContainer>
        <S.PreviewTitle>{previewResult.title}</S.PreviewTitle>
        <S.PreviewDescription>{previewResult.description ?? previewResult.domainName ?? linkUrl}</S.PreviewDescription>
      </S.PreviewContentContainer>
    </S.PreviewContainer>
  );
};

export default LinkPreview;
