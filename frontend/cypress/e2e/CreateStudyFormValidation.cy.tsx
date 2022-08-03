import { ACCESS_TOKEN_KEY, DESCRIPTION_LENGTH, EXCERPT_LENGTH, PATH, TITLE_LENGTH } from '@constants';

describe('스터디 개설 페이지 폼 유효성 테스트', () => {
  before(() => {
    window.sessionStorage.setItem(ACCESS_TOKEN_KEY, 'asdfasf');
    cy.visit(PATH.CREATE_STUDY);
  });

  const minText = 'a';
  const maxText30 = 'abcdefghijklmnopqrstuvwxyzabcd';
  const redOutlineCss = 'rgb(255, 0, 0) solid 2px';
  it(`스터디 제목은 최소 ${TITLE_LENGTH.MIN.VALUE}글자에서 최대 ${TITLE_LENGTH.MAX.VALUE}글자까지 입력할 수 있다.`, () => {
    cy.findByPlaceholderText('스터디 이름').type(minText);
    cy.findByPlaceholderText('스터디 이름').should('not.have.css', 'outline', redOutlineCss);

    cy.findByPlaceholderText('스터디 이름').clear().type(maxText30);
    cy.findByPlaceholderText('스터디 이름').should('not.have.css', 'outline', redOutlineCss);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('스터디 이름').type(minText).clear();
    cy.findByPlaceholderText('스터디 이름').should('have.css', 'outline', redOutlineCss);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MAX.VALUE}글자를 초과하면 초과한 글자는 입력되지 않는다.`, () => {
    cy.findByPlaceholderText('스터디 이름').type(maxText30 + 'a');
    cy.findByPlaceholderText('스터디 이름').should('have.value', maxText30);
  });

  it(`스터디 제목이 ${TITLE_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('스터디 이름').type(minText).clear();
    cy.findByText('개설하기').click();
    cy.findByPlaceholderText('스터디 이름').should('have.css', 'outline', redOutlineCss);
  });

  it(`스터디 소개글은 최소 ${DESCRIPTION_LENGTH.MIN.VALUE}글자를 입력할 수 있다.`, () => {
    cy.findByPlaceholderText('(20000자 제한)').type(minText);
    cy.findByPlaceholderText('(20000자 제한)').should('not.have.css', 'outline', redOutlineCss);
  });

  it(`스터디 소개글이 ${DESCRIPTION_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('(20000자 제한)').type(minText).clear();
    cy.findByPlaceholderText('(20000자 제한)').should('have.css', 'outline', redOutlineCss);
  });

  it(`스터디 소개글이 ${DESCRIPTION_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('(20000자 제한)').type(minText).clear();
    cy.findByText('개설하기').click();
    cy.findByPlaceholderText('(20000자 제한)').should('have.css', 'outline', redOutlineCss);
  });

  const maxText50 = maxText30 + '12345678901234567890';
  it(`스터디 한줄 소개는 최소 ${EXCERPT_LENGTH.MIN.VALUE}글자에서 최대 ${EXCERPT_LENGTH.MAX.VALUE}글자까지 입력할 수 있다.`, () => {
    cy.findByPlaceholderText('한줄소개를 입력해주세요').type(minText);
    cy.findByPlaceholderText('한줄소개를 입력해주세요').should('not.have.css', 'outline', redOutlineCss);

    cy.findByPlaceholderText('한줄소개를 입력해주세요').clear().type(maxText50);
    cy.findByPlaceholderText('한줄소개를 입력해주세요').should('not.have.css', 'outline', redOutlineCss);
  });

  it(`스터디 한줄 소개가 ${EXCERPT_LENGTH.MIN.VALUE}글자 미만이면 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('한줄소개를 입력해주세요').type(minText).clear();
    cy.findByPlaceholderText('한줄소개를 입력해주세요').should('have.css', 'outline', redOutlineCss);
  });

  it(`스터디 한줄 소개가 ${EXCERPT_LENGTH.MAX.VALUE}글자를 초과하면 초과한 글자는 입력되지 않는다.`, () => {
    cy.findByPlaceholderText('한줄소개를 입력해주세요').type(maxText50 + '1');
    cy.findByPlaceholderText('한줄소개를 입력해주세요').should('have.value', maxText50);
  });

  it(`스터디 소개글이 ${EXCERPT_LENGTH.MIN.VALUE}글자 미만이면 개설하기 버튼을 눌렀을 때 입력창 테두리가 빨갛게 된다.`, () => {
    cy.findByPlaceholderText('한줄소개를 입력해주세요').type(minText).clear();
    cy.findByText('개설하기').click();
    cy.findByPlaceholderText('한줄소개를 입력해주세요').should('have.css', 'outline', redOutlineCss);
  });

  it('스터디 시작 날짜는 필수로 입력해야 한다.', () => {
    cy.findByLabelText('스터디 시작 :').invoke('val').should('have.length', 10);
  });
});
