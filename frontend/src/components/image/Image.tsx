import notFoundImage from '@assets/images/no-image-found.png';

interface ImageProps {
  className?: string;
  src: string;
  alt: string;
}

const Image: React.FC<ImageProps> = ({ className, src, alt }) => {
  const handleImageError = ({ currentTarget }: React.SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = notFoundImage;
  };

  return <img className={className} src={src} alt={alt} onError={handleImageError} />;
};

export default Image;
