import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';
import { compareDateTime } from '@utils/dates';

import type { StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import Flex from '@shared/flex/Flex';
import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';

export type PeriodProps = {
  originalEnrollmentEndDate?: StudyDetail['enrollmentEndDate'];
};

const ENROLLMENT_END_DATE = 'enrollment-end-date';

const EnrollmentEndDate: React.FC<PeriodProps> = ({ originalEnrollmentEndDate }) => {
  const { register } = useFormContext();
  const today = useMemo(() => getToday(), []);
  const minEndDate = originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today) : today;
  const maxEndDate = getNextYear(
    originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today, 'max') : today,
  );

  return (
    <MetaBox>
      <MetaBox.Title>스터디 신청 마감일</MetaBox.Title>
      <MetaBox.Content>
        <Flex columnGap="10px" alignItems="center">
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
