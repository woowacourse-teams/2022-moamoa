import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import { yyyymmddTommdd } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail, UserRole } from '@custom-types';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-wide-float-box/StudyWideFloatBox.style';

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
          <Button css={tw`h-full py-0 px-20`} fluid={true} type="button">
            이동하기
          </Button>
        </Link>
      );
    }

    return (
      <Button css={tw`h-full py-0 px-20`} fluid={true} disabled={!isOpen} onClick={handleRegisterButtonClick}>
        {isOpen ? '가입하기' : '모집 마감'}
      </Button>
    );
  };

  return (
    <S.StudyWideFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>{renderEnrollmentEndDateContent()}</S.EnrollmentEndDate>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount ?? '∞'}
          </span>
        </S.MemberCount>
      </S.StudyInfo>
      <div>{renderButton()}</div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
