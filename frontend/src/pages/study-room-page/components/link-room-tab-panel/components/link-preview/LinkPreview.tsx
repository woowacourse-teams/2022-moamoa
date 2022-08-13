import type { GetLinkPreviewResponseData } from '@custom-types';

import CenterImage from '@components/center-image/CenterImage';
import { RightUpArrowSvg } from '@components/svg';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview.style';

export type LinkPreviewProps = {
  previewResult: GetLinkPreviewResponseData;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult }) => {
  return (
    <S.PreviewContainer>
      <S.PreviewImageContainer>
        <CenterImage src={previewResult.imageUrl} alt={`${previewResult.title} 썸네일`} />
      </S.PreviewImageContainer>
      <S.PreviewDomain>
        <RightUpArrowSvg />
        <span>{previewResult.domainName}</span>
      </S.PreviewDomain>
      <S.PreviewContentContainer>
        <S.PreviewTitle>{previewResult.title}</S.PreviewTitle>
        <S.PreviewDescription>{previewResult.description ?? previewResult.domainName}</S.PreviewDescription>
      </S.PreviewContentContainer>
    </S.PreviewContainer>
  );
};

export default LinkPreview;
