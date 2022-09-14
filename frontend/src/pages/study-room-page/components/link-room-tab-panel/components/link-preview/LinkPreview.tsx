import { ApiLinkPreview } from '@api/link-preview';

import CenterImage from '@components/center-image/CenterImage';
import { RightUpArrowSvg } from '@components/svg';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview.style';

export type LinkPreviewProps = {
  previewResult: ApiLinkPreview['get']['responseData'];
  linkUrl: string;
};

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, linkUrl }) => {
  try {
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
          <S.PreviewDescription>
            {previewResult.description ?? previewResult.domainName ?? linkUrl}
          </S.PreviewDescription>
        </S.PreviewContentContainer>
      </S.PreviewContainer>
    );
  } catch (error) {
    console.error(error);

    return (
      <S.PreviewContainer>
        <S.PreviewImageContainer>
          <CenterImage src={previewResult.imageUrl} alt="이미지 오류" />
        </S.PreviewImageContainer>
        <S.PreviewContentContainer>
          <S.PreviewTitle>{previewResult.title}</S.PreviewTitle>
          <S.PreviewDescription>
            {previewResult.description ?? previewResult.domainName ?? linkUrl}
          </S.PreviewDescription>
        </S.PreviewContentContainer>
      </S.PreviewContainer>
    );
  }
};

export default LinkPreview;