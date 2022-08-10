import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';

import { useFormContext } from '@hooks/useForm';

import * as S from '@create-study-page/components/enrollment-end-date/EnrollmentEndDate.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

type PeriodProps = {
  className?: string;
};

const EnrollmentEndDate = ({ className }: PeriodProps) => {
  const { register } = useFormContext();
  const today = useMemo(() => getToday('-'), []);
  const nextYear = getNextYear(today, '-');

  return (
    <S.EnrollmentEndDate className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 신청 마감일</MetaBox.Title>
        <MetaBox.Content>
          <div>
            <S.Label htmlFor="enrollment-end-date">마감일자 :</S.Label>
            <S.Input
              type="date"
              id="enrollment-end-date"
              min={today}
              max={nextYear}
              {...register('enrollment-end-date')}
            />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.EnrollmentEndDate>
  );
};

export default EnrollmentEndDate;
