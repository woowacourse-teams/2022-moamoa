import { useMemo } from 'react';

import { getNextYear, getToday, isDateYMD } from '@utils';
import { compareDateTime } from '@utils/dates';

import type { DateYMD, StudyDetail } from '@custom-types';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import Flex from '@shared/flex/Flex';
import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';

import { END_DATE } from '@create-study-page/components/period/Period';

export type PeriodProps = {
  originalEnrollmentEndDate?: StudyDetail['enrollmentEndDate'];
};

const ENROLLMENT_END_DATE = 'enrollment-end-date';

const EnrollmentEndDate: React.FC<PeriodProps> = ({ originalEnrollmentEndDate }) => {
  const {
    register,
    getField,
    formState: { errors },
  } = useFormContext();

  const today = useMemo(() => getToday(), []);
  const minEndDate = originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today) : today;
  const maxEndDate = getNextYear(
    originalEnrollmentEndDate ? compareDateTime(originalEnrollmentEndDate, today, 'max') : today,
  );

  const isValid = !errors[ENROLLMENT_END_DATE]?.hasError;

  return (
    <MetaBox>
      <MetaBox.Title>스터디 신청 마감일</MetaBox.Title>
      <MetaBox.Content>
        <Flex columnGap="10px" alignItems="center">
          <Label htmlFor={ENROLLMENT_END_DATE}>마감일자 :</Label>
          <Input
            id={ENROLLMENT_END_DATE}
            type="date"
            invalid={!isValid}
            defaultValue={originalEnrollmentEndDate}
            {...register(ENROLLMENT_END_DATE, {
              validate: (value: DateYMD) => {
                const studyEndDateElement = getField(END_DATE);
                if (!studyEndDateElement)
                  return makeValidationResult(true, ' %ERROR% 스터디 종료일 입력 요소가 존재하지 않습니다.');

                const studyEndDate = studyEndDateElement.fieldElement.value;
                if (!studyEndDate) return makeValidationResult(false);
                if (!isDateYMD(studyEndDate)) return makeValidationResult(true, '잘못된 형식입니다.');

                if (compareDateTime(studyEndDate, value) === studyEndDate)
                  return makeValidationResult(true, '스터디 신청 마감일은 스터디 종료일 이전이어야 합니다.');

                return makeValidationResult(false);
              },
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
