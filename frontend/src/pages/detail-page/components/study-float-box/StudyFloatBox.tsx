import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import { yyyymmddTommdd } from '@utils';

import type { StudyDetail, UserRole } from '@custom-types';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-float-box/StudyFloatBox.style';

export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  userRole?: UserRole;
  ownerName: string;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  userRole,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  const renderEnrollmentEndDateContent = () => {
    if (userRole === 'MEMBER' || userRole === 'OWNER') {
      return <span>이미 가입한 스터디입니다</span>;
    }

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

  const renderButton = () => {
    if (userRole === 'MEMBER' || userRole === 'OWNER') {
      return (
        <Link to={PATH.STUDY_ROOM(studyId)}>
          <Button type="button">스터디 방으로 이동하기</Button>
        </Link>
      );
    }

    return (
      <Button disabled={!isOpen} onClick={handleRegisterButtonClick}>
        {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
      </Button>
    );
  };

  return (
    <S.StudyFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>{renderEnrollmentEndDateContent()}</S.EnrollmentEndDate>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount ?? '∞'}
          </span>
        </S.MemberCount>
        <S.Owner>
          <span>스터디장</span>
          <span>{ownerName}</span>
        </S.Owner>
      </S.StudyInfo>
      {renderButton()}
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
