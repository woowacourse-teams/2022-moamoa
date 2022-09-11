import { BoxButton } from '@design/components/button';
import MetaBox from '@design/components/meta-box/MetaBox';

type PublishProps = {
  title?: string;
  buttonText?: string;
  onPublishButtonClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const Publish = ({
  title = '스터디 개설',
  buttonText = '개설하기',
  onPublishButtonClick: handlePublishButtonClick,
}: PublishProps) => {
  return (
    <MetaBox>
      <MetaBox.Title>{title}</MetaBox.Title>
      <MetaBox.Content>
        <BoxButton type="submit" fluid onClick={handlePublishButtonClick} variant="secondary">
          {buttonText}
        </BoxButton>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default Publish;
