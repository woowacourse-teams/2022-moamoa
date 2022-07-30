import { yyyymmddTommdd } from '@utils/index';

import { StudyDetail } from '@custom-types/index';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-float-box/StudyFloatBox.style';

// TODO: 스터디에 가입한 사람인지 아닌지 상태도 받아야 함
export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  ownerName: string;
  handleRegisterBtnClick: (studyId: number) => React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  handleRegisterBtnClick,
}) => {
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  return (
    <S.StudyFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>
          {isOpen ? (
            <>
              <span>{yyyymmddTommdd(enrollmentEndDate)}</span>
              까지 가입 가능
            </>
          ) : (
            <span>모집 마감</span>
          )}
        </S.EnrollmentEndDate>
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
      <Button disabled={!isOpen} onClick={handleRegisterBtnClick(studyId)}>
        {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
      </Button>
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
