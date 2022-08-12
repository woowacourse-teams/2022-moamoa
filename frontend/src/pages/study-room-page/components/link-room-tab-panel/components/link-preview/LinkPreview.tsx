import type { GetLinkPreviewResponseData } from '@custom-types';

import CenterImage from '@components/center-image/CenterImage';
import KebabMenu from '@components/kebab-menu/KebabMenu';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-preview/LinkPreview.style';

export type LinkPreviewProps = {
  previewResult: GetLinkPreviewResponseData;
  onKebabMenuClick: React.MouseEventHandler<HTMLButtonElement>;
};

const ArrowRightUpSvg = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="3"
    viewBox="0 -5 24 24"
    strokeLinecap="round"
    strokeLinejoin="round"
    height="1em"
    width="1em"
    xmlns="http://www.w3.org/2000/svg"
  >
    <line x1="7" y1="17" x2="17" y2="7"></line>
    <polyline points="7 7 17 7 17 17"></polyline>
  </svg>
);

const LinkPreview: React.FC<LinkPreviewProps> = ({ previewResult, onKebabMenuClick: handleKebabMenuClick }) => {
  return (
    <S.PreviewContainer>
      <S.PreviewImageContainer>
        <CenterImage src={previewResult.imageUrl} alt={`${previewResult.title} 썸네일`} />
        <S.PreviewDomain>
          <ArrowRightUpSvg />
          <span>{previewResult.domainName}</span>
        </S.PreviewDomain>
        <S.PreviewKebabMenuContainer>
          <KebabMenu onClick={handleKebabMenuClick} />
        </S.PreviewKebabMenuContainer>
      </S.PreviewImageContainer>
      <S.PreviewContentContainer>
        <S.PreviewTitle>{previewResult.title}</S.PreviewTitle>
        <S.PreviewDescription>{previewResult.description ?? previewResult.domainName}</S.PreviewDescription>
      </S.PreviewContentContainer>
    </S.PreviewContainer>
  );
};

export default LinkPreview;
