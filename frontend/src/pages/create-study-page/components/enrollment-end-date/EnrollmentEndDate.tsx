import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';

import type { StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import * as S from '@create-study-page/components/enrollment-end-date/EnrollmentEndDate.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

export type PeriodProps = {
  className?: string;
  originalEnrollmentEndDate?: StudyDetail['enrollmentEndDate'];
};

const EnrollmentEndDate: React.FC<PeriodProps> = ({ className, originalEnrollmentEndDate }) => {
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
              id="enrollment-end-date"
              type="date"
              min={today}
              max={nextYear}
              defaultValue={originalEnrollmentEndDate}
              {...register('enrollment-end-date')}
            />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.EnrollmentEndDate>
  );
};

export default EnrollmentEndDate;
