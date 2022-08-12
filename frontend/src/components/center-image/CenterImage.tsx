import notFoundImage from '@assets/images/no-image-found.png';

import * as S from '@components/center-image/CenterImage.style';

export type ImageProps = {
  className?: string;
  src?: string;
  alt: string;
};

const Image: React.FC<ImageProps> = ({ className, src, alt }) => {
  const handleImageError = ({ currentTarget }: React.SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = notFoundImage;
  };

  return <S.CenterImage className={className} src={src} alt={alt} onError={handleImageError} />;
};

export default Image;
