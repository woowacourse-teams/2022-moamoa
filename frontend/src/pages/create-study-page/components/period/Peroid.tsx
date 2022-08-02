import { useMemo } from 'react';

import { css } from '@emotion/react';

import { getNextYear, getToday } from '@utils/index';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/period/Period.style';

type PeriodProps = {
  className?: string;
};

const Period = ({ className }: PeriodProps) => {
  const { register } = useFormContext();
  const today = useMemo(() => getToday('-'), []);
  const nextYear = getNextYear(today, '-');

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
              htmlFor="start-date"
              css={css`
                margin-right: 10px;
              `}
            >
              스터디 시작 :
            </label>
            <input
              type="date"
              id="start-date"
              min={today}
              max={nextYear}
              defaultValue={today}
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
            <input type="date" id="end-date" min={today} max={nextYear} {...register('end-date')}></input>
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Period>
  );
};

export default Period;
