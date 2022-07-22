import * as S from '@create-study-page/components/excerpt/Excerpt.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import cn from 'classnames';

import { useFormContext } from '@hooks/useForm';

type ExcerptProps = {
  className?: string;
};

const Excerpt = ({ className }: ExcerptProps) => {
  const {
    formState: { errors },
    register,
  } = useFormContext();

  return (
    <S.Excerpt className={className}>
      <MetaBox>
        <MetaBox.Title>한줄소개</MetaBox.Title>
        <MetaBox.Content>
          <textarea
            placeholder="한줄소개를 입력해주세요"
            className={cn({ invalid: errors['excerpt'] })}
            {...register('excerpt', {
              validate: (val: string) => {
                if (!val || val.length === 0) {
                  return '한줄 소개를 입력해 주세요';
                }
                return true;
              },
            })}
          />
        </MetaBox.Content>
      </MetaBox>
    </S.Excerpt>
  );
};

export default Excerpt;
