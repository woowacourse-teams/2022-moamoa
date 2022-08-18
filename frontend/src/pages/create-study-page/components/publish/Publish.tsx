import tw from '@utils/tw';

import Button from '@components/button/Button';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/publish/Publish.style';

type PublishProps = {
  className?: string;
  title?: string;
  buttonText?: string;
  onPublishButtonClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const Publish = ({
  className,
  title = '스터디 개설',
  buttonText = '개설하기',
  onPublishButtonClick: handlePublishButtonClick,
}: PublishProps) => {
  return (
    <S.Publish className={className}>
      <MetaBox>
        <MetaBox.Title>{title}</MetaBox.Title>
        <MetaBox.Content>
          <Button
            css={tw`rounded-[6px] text-16 py-12 px-10`}
            fluid={true}
            onClick={handlePublishButtonClick}
            outline={true}
          >
            {buttonText}
          </Button>
        </MetaBox.Content>
      </MetaBox>
    </S.Publish>
  );
};

export default Publish;
