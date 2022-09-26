import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';

import type { DateYMD, StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/period/Period.style';

type PeriodProps = {
  className?: string;
  originalStartDate?: StudyDetail['startDate'];
  originalEndDate?: StudyDetail['endDate'];
};

const Period: React.FC<PeriodProps> = ({ className, originalStartDate, originalEndDate }) => {
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
              defaultValue={originalStartDate || today}
              {...register('start-date', {
                min: originalStartDate || today,
                max: nextYear,
                required: true,
              })}
            />
          </S.Container>
          <div>
            <S.Label htmlFor="end-date">스터디 종료 :</S.Label>
            <S.Input
              type="date"
              id="end-date"
              defaultValue={originalEndDate}
              {...register('end-date', {
                min: today,
                max: nextYear,
              })}
            />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Period>
  );
};

export default Period;
