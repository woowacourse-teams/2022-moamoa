import { css } from '@emotion/react';

import { yyyymmddTommdd } from '@utils';

import type { StudyDetail, UserRole } from '@custom-types';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-wide-float-box/StudyWideFloatBox.style';

// TODO: 스터디에 가입한 사람인지 아닌지 상태도 받아야 함
export type StudyWideFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  userRole?: UserRole;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  userRole,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
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
    <S.StudyWideFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>{renderEnrollmentEndDateContent()}</S.EnrollmentEndDate>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </S.MemberCount>
      </S.StudyInfo>
      <div>
        <Button
          css={css`
            height: 100%;
            padding: 0 20px;
          `}
          fluid={true}
          disabled={!isOpen}
          onClick={handleRegisterButtonClick}
        >
          {isOpen ? '가입하기' : '모집 마감'}
        </Button>
      </div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
