import { DESCRIPTION_LENGTH, EXCERPT_LENGTH, PATH, TITLE_LENGTH } from '@constants';

const studyTitle = 'studyTitle';
const description = 'description';
const excerpt = 'excerpt';
const submitButton = 'submitButton';
const startDate = 'startDate';

describe('스터디 개설 페이지 폼 유효성 테스트', () => {
  before(() => {
    cy.visit(`${PATH.LOGIN}?code=hihihih`).then(() => {
      cy.wait(1000);
      cy.visit(PATH.CREATE_STUDY);
    });
  });

  beforeEach(() => {
    cy.findByPlaceholderText('*스터디 이름').as(studyTitle);
    cy.findByPlaceholderText('*스터디 소개글(20000자 제한)').as(description);
    cy.findByText('개설하기').as(submitButton);
    cy.findByPlaceholderText('*한줄소개를 입력해주세요').as(excerpt);
    cy.findByLabelText('*스터디 시작 :').as(startDate);
  });

  const minText = 'a';
  const maxText30 = 'abcdefghijklmnopqrstuvwxyzabcd';
  const redBorderCss = '1px solid rgb(255, 0, 0)';
  const border = 'border';

  it(`스터디 제목은 최소 ${TITLE_LENGTH.MIN.VALUE}글자에서 최대 ${TITLE_LENGTH.MAX.VALUE}글자까지 입력할 수 있다.`, () => {
    cy.get(`@${studyTitle}`).type(minText);
    cy.get(`@${studyTitle}`).should('not.have.css', border, redBorderCss);

    cy.get(`@${studyTitle}`).clear().type(maxText30);
    cy.get(`@${studyTitle}`).should('not.have.css', border, redBorderCss);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${studyTitle}`).type(minText).clear();
    cy.get(`@${studyTitle}`).should('have.css', border, redBorderCss);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MAX.VALUE}글자를 초과하면 초과한 글자는 입력되지 않는다.`, () => {
    cy.get(`@${studyTitle}`).type(maxText30 + 'a');
    cy.get(`@${studyTitle}`).should('have.value', maxText30);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${studyTitle}`).type(minText).clear();
    cy.get(`@${submitButton}`).click();
    cy.get(`@${studyTitle}`).should('have.css', border, redBorderCss);
  });

  it(`스터디 소개글은 최소 ${DESCRIPTION_LENGTH.MIN.VALUE}글자를 입력할 수 있다.`, () => {
    cy.get(`@${description}`).type(minText);
    cy.get(`@${description}`).should('not.have.css', border, redBorderCss);
  });

  it(`스터디 소개글이 ${DESCRIPTION_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${description}`).type(minText).clear();
    cy.get(`@${description}`).should('have.css', border, redBorderCss);
  });

  it(`스터디 소개글이 ${DESCRIPTION_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${description}`).type(minText).clear();
    cy.get(`@${submitButton}`).click();
    cy.get(`@${description}`).should('have.css', border, redBorderCss);
  });

  const maxText50 = maxText30 + '12345678901234567890';
  it(`스터디 한줄 소개는 최소 ${EXCERPT_LENGTH.MIN.VALUE}글자에서 최대 ${EXCERPT_LENGTH.MAX.VALUE}글자까지 입력할 수 있다.`, () => {
    cy.get(`@${excerpt}`).type(minText);
    cy.get(`@${excerpt}`).should('not.have.css', border, redBorderCss);

    cy.get(`@${excerpt}`).clear().type(maxText50);
    cy.get(`@${excerpt}`).should('not.have.css', border, redBorderCss);
  });

  it(`스터디 한줄 소개가 ${EXCERPT_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${excerpt}`).type(minText).clear();
    cy.get(`@${excerpt}`).should('have.css', border, redBorderCss);
  });

  it(`스터디 한줄 소개가 ${EXCERPT_LENGTH.MAX.VALUE}글자를 초과하면 초과한 글자는 입력되지 않는다.`, () => {
    cy.get(`@${excerpt}`).type(maxText50 + '1');
    cy.get(`@${excerpt}`).should('have.value', maxText50);
  });

  it(`스터디 소개글이 ${EXCERPT_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.get(`@${excerpt}`).type(minText).clear();
    cy.get(`@${submitButton}`).click();
    cy.get(`@${excerpt}`).should('have.css', border, redBorderCss);
  });

  it('스터디 시작 날짜는 필수로 입력해야 한다.', () => {
    cy.get(`@${startDate}`).invoke('val').should('have.length', 10);
  });
});
