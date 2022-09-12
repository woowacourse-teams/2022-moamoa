import { useMemo } from 'react';

import { getNextYear, getToday } from '@utils';
import { compareDateTime } from '@utils/dates';
import tw from '@utils/tw';

import type { DateYMD, StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import Input from '@design/components/input/Input';
import Label from '@design/components/label/Label';
import MetaBox from '@design/components/meta-box/MetaBox';

type PeriodProps = {
  originalStartDate?: StudyDetail['startDate'];
  originalEndDate?: StudyDetail['endDate'];
};

const START_DATE = 'start-date';
const END_DATE = 'end-date';

const Period: React.FC<PeriodProps> = ({ originalStartDate, originalEndDate }) => {
  const { register } = useFormContext();

  const today = useMemo(() => getToday('-'), []) as DateYMD;

  const minStartDate = originalStartDate ? compareDateTime(originalStartDate, today) : today;
  const maxStartDate = getNextYear(
    originalStartDate ? compareDateTime(originalStartDate, today, 'max') : today,
    '-',
  ) as DateYMD;

  const minEndDate = originalEndDate ? compareDateTime(originalEndDate, today) : today;
  const maxEndDate = getNextYear(
    originalEndDate ? compareDateTime(originalEndDate, today, 'max') : today,
    '-',
  ) as DateYMD;

  return (
    <MetaBox>
      <MetaBox.Title>스터디 운영 기간</MetaBox.Title>
      <MetaBox.Content>
        <div css={tw`mb-12`}>
          <Label htmlFor={START_DATE}>*스터디 시작 :</Label>
          <Input
            type="date"
            id={START_DATE}
            defaultValue={originalStartDate || today}
            {...register(START_DATE, {
              min: minStartDate,
              max: maxStartDate,
              required: true,
            })}
          />
        </div>
        <div>
          <Label htmlFor={END_DATE}>스터디 종료 :</Label>
          <Input
            type="date"
            id={END_DATE}
            defaultValue={originalEndDate}
            {...register(END_DATE, {
              min: minEndDate,
              max: maxEndDate,
            })}
          />
        </div>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default Period;
