import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/period/Period.style';

import { css } from '@emotion/react';

import { useFormContext } from '@hooks/useForm';

type PeriodProps = {
  className?: string;
};

const Period = ({ className }: PeriodProps) => {
  const { register } = useFormContext();

  return (
    <S.Period className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 운영 기간</MetaBox.Title>
        <MetaBox.Content>
          <div
            css={css`
              margin-bottom: 12px;
            `}
          >
            <label
              htmlFor="state-date"
              css={css`
                margin-right: 10px;
              `}
            >
              스터디 시작 :
            </label>
            <input
              type="date"
              id="start-date"
              min="2022-07-20"
              max="2030-07-20"
              defaultValue="2022-07-20"
              {...register('start-date')}
            ></input>
          </div>
          <div>
            <label
              htmlFor="end-date"
              css={css`
                margin-right: 10px;
              `}
            >
              스터디 종료 :
            </label>
            <input
              type="date"
              id="end-date"
              defaultValue="2022-07-20"
              min="2022-07-20"
              max="2030-07-20"
              {...register('end-date')}
            ></input>
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Period>
  );
};

export default Period;
