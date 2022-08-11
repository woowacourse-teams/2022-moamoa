import { useMemo } from 'react';

import { css } from '@emotion/react';

import { getNextYear, getToday } from '@utils';

import { DateYMD } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/period/Period.style';

type PeriodProps = {
  className?: string;
};

const Period = ({ className }: PeriodProps) => {
  const { register } = useFormContext();
  const today = useMemo(() => getToday('-'), []) as DateYMD;
  const nextYear = getNextYear(today, '-') as DateYMD;

  return (
    <S.Period className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 운영 기간</MetaBox.Title>
        <MetaBox.Content>
          <S.Container>
            <S.Label htmlFor="start-date">*스터디 시작 :</S.Label>
            <S.Input
              type="date"
              id="start-date"
              min={today}
              max={nextYear}
              defaultValue={today}
              {...register('start-date', {
                min: today,
                max: nextYear,
                required: true,
              })}
            />
          </S.Container>
          <div>
            <S.Label htmlFor="end-date">스터디 종료 :</S.Label>
            <S.Input type="date" id="end-date" min={today} max={nextYear} {...register('end-date')} />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Period>
  );
};

export default Period;
