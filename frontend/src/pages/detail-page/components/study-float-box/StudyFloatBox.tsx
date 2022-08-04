import { yyyymmddTommdd } from '@utils';

import type { StudyDetail } from '@custom-types';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-float-box/StudyFloatBox.style';

export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  ownerName: string;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  const renderEnrollmentEndDateContent = () => {
    if (!isOpen) {
      return <span>모집 마감</span>;
    }

    if (!enrollmentEndDate) {
      return <span>모집중</span>;
    }

    return (
      <>
        <span>{yyyymmddTommdd(enrollmentEndDate)}</span>까지 가입 가능
      </>
    );
  };

  return (
    <S.StudyFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>{renderEnrollmentEndDateContent()}</S.EnrollmentEndDate>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </S.MemberCount>
        <S.Owner>
          <span>스터디장</span>
          <span>{ownerName}</span>
        </S.Owner>
      </S.StudyInfo>
      <Button disabled={!isOpen} onClick={handleRegisterButtonClick}>
        {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
      </Button>
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
