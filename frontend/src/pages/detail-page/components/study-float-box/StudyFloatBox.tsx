import yyyymmddTommdd from '@utils/yyyymmddTommdd';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-float-box/StudyFloatBox.style';

export type StudyFloatBoxProps = {
  studyId: number;
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  owner: string;
  handleRegisterBtnClick: (studyId: number) => React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  deadline,
  currentMemberCount,
  maxMemberCount,
  owner,
  handleRegisterBtnClick,
}) => {
  return (
    <S.StudyFloatBox>
      <S.StudyInfo>
        <S.Deadline>
          <span>{yyyymmddTommdd(deadline)}</span>
          까지 가입 가능
        </S.Deadline>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </S.MemberCount>
        <S.Owner>
          <span>스터디장</span>
          <span>{owner}</span>
        </S.Owner>
      </S.StudyInfo>
      <Button onClick={handleRegisterBtnClick(studyId)}>스터디 방 가입하기</Button>
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
