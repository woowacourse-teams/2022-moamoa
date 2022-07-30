import { css } from '@emotion/react';

import { useFormContext } from '@hooks/useForm';

import Button from '@components/button/Button';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/publish/Publish.style';

type PublishProps = {
  className?: string;
  onPublishButtonClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const Publish = ({ className, onPublishButtonClick }: PublishProps) => {
  const { formState } = useFormContext();

  return (
    <S.Publish className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 개설</MetaBox.Title>
        <MetaBox.Content>
          <Button
            css={css`
              border-radius: 6px;
              font-size: 16px;
              padding: 12px 10px;
            `}
            fluid={true}
            onClick={onPublishButtonClick}
            outline={true}
            isLoading={formState.isSubmitting}
          >
            개설하기
          </Button>
        </MetaBox.Content>
      </MetaBox>
    </S.Publish>
  );
};

export default Publish;
