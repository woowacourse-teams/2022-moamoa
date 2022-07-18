import { css } from '@emotion/react';

import Button from '@components/button/Button';

import * as S from './StudyFloatBox.style';

export type StudyFloatBoxProps = {
  studyId: string;
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  owner: string;
  onClickRegisterBtn: (studyId: string) => void;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  deadline,
  currentMemberCount,
  maxMemberCount,
  owner,
  onClickRegisterBtn,
}) => {
  const handleClickRegisterBtn = () => onClickRegisterBtn(studyId);

  return (
    <S.StudyFloatBox>
      <div
        css={css`
          margin-bottom: 30px;
        `}
      >
        <div
          css={css`
            margin-bottom: 20px;
          `}
        >
          <strong
            css={css`
              font-size: 24px;
            `}
          >
            {deadline}
          </strong>
          까지 가입 가능
        </div>
        <div
          css={css`
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
          `}
        >
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </div>
        <div
          css={css`
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
          `}
        >
          <span>스터디장</span>
          <span>{owner}</span>
        </div>
      </div>
      <Button onClick={handleClickRegisterBtn}>스터디 방 가입하기</Button>
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
