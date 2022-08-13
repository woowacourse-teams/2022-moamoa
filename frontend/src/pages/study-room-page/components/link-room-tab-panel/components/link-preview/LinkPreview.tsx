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

const MeatballMenuSvg = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 20 20"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z"></path>
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
        <S.PreviewMeatballMenuContainer>
          <S.MeatballMenuButton aria-label="수정 삭제 메뉴 버튼" type="button" onClick={handleKebabMenuClick}>
            <MeatballMenuSvg />
          </S.MeatballMenuButton>
        </S.PreviewMeatballMenuContainer>
      </S.PreviewImageContainer>
      <S.PreviewContentContainer>
        <S.PreviewTitle>{previewResult.title}</S.PreviewTitle>
        <S.PreviewDescription>{previewResult.description ?? previewResult.domainName}</S.PreviewDescription>
      </S.PreviewContentContainer>
    </S.PreviewContainer>
  );
};

export default LinkPreview;
