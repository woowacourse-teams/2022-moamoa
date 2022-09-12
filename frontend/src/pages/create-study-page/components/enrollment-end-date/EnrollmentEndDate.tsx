import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';
import { compareDateTime } from '@utils/dates';

import type { DateYMD, StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import Flex from '@design/components/flex/Flex';
import Input from '@design/components/input/Input';
import Label from '@design/components/label/Label';
import MetaBox from '@design/components/meta-box/MetaBox';

export type PeriodProps = {
  originalEnrollmentEndDate?: StudyDetail['enrollmentEndDate'];
};

const ENROLLMENT_END_DATE = 'enrollment-end-date';

const EnrollmentEndDate: React.FC<PeriodProps> = ({ originalEnrollmentEndDate }) => {
  const { register } = useFormContext();
  const today = useMemo(() => getToday('-'), []) as DateYMD;
  const minEndDate = originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today) : today;
  const maxEndDate = getNextYear(
    originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today, 'max') : today,
    '-',
  ) as DateYMD;

  return (
    <MetaBox>
      <MetaBox.Title>스터디 신청 마감일</MetaBox.Title>
      <MetaBox.Content>
        <Flex gap="10px" alignItems="center">
          <Label htmlFor={ENROLLMENT_END_DATE}>마감일자 :</Label>
          <Input
            id={ENROLLMENT_END_DATE}
            type="date"
            defaultValue={originalEnrollmentEndDate}
            {...register(ENROLLMENT_END_DATE, {
              min: minEndDate,
              max: maxEndDate,
            })}
          />
        </Flex>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default EnrollmentEndDate;
