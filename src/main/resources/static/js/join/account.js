let accode = ''; // 넣을 코드 데이터 값
usertypeForm(1); // 첫 화면 폼 그려주기

let allboxs = document.querySelectorAll('.chk');
let chkdboxs = document.querySelectorAll('.chk:checked');
for (box of allboxs) {
	box.addEventListener('click', () => {
		if (allboxs.length != chkdboxs.length) {
			document.querySelector('#chk_all').checked = false;
		}
	});
}
chk_all.addEventListener('click', () => {
	for (obj of allboxs) {
		if (chk_all.checked) {
			obj.checked = true;
		} else if (!chk_all.checked) {
			obj.checked = false;
		}
	}
});
function usertypeForm(num) {
	let form;
	document.getElementsByClassName('radios')[0].innerHTML = ''; // 바뀔때 비워주기용
	if (num == '1') {
		actype = 'b1';
		form = `<div class="checkout__input">
					<p>관심사</p>
				</div>
                <select name="ocmenu">
                    <option value="">선택</option>
                    <option value="c1">프로그래밍 언어</option>
                    <option value="c2">컴퓨터공학 전공</option>
                    <option value="c3">웹 개발</option>
                    <option value="c4">데이터 분석</option>
                    <option value="c5">IT 자격증</option>
                </select>
                    `;
	}
	else if (num == '2') {
		actype = 'd2';
		form = `<div class="checkout__input">
					<p>이력서 첨부 <span>*</span></p>
					</div>
					<input type="file" accept=".pdf, .xlsx, .hwp" id="file" name="file"/>`;
	}
	document.getElementsByClassName('radios')[0].innerHTML = form;
}


// 유효성 검사
let idAccep = false;
let pwAccep = false;
let mailAccep = false;
let phoneAccep = false;
async function idChk() {
	let inputId = id.value;
	let idStat;
	idAccep = false;
	if (inputId == '') {
		idStat = `<p>아이디를 입력해주세요</p>`;
	} else if (!(/^[a-zA-Z0-9]{4,20}$/.test(inputId))) {
		idStat = `<p>영문 대소문자, 숫자조합 4~20자로 입력해주세요</p>`;
	} else {
		await axios.get(`/common/idchk/${inputId}`)
			.then(res => {
				if (res.data.id != null) {
					idStat = `<p>사용할 수 없는 아이디입니다. 다른 아이디를 입력해주세요<p>`
				} else {
					idStat = `<p>사용가능한 아이디입니다</p>`;
					idAccep = true;
				}
			})
			.catch(err => console.log(err));

	}
	document.getElementsByClassName('idchk')[0].innerHTML = idStat;
}
function pwCheck() {
	let inputPw = pw.value;
	let inputPwChk = pwchk.value;
	let statPw = ``;
	let statPwChk = ``;
	pwAccep = false;
	if (inputPw == '') {
		statPw = `<p>비밀번호를 입력해주세요</p>`;
	} else if (!(/^[a-zA-Z0-9]{8,16}$/.test(inputPw))) {
		statPw = `<p>영문 대소문자, 숫자조합 8~16자로 입력해주세요</p>`;
	} else if (inputPw == inputPwChk) {
		// statpw = ``;
		statPwChk = `<p>일치</p>`;
		pwAccep = true;
	} else if (inputPwChk != '') {
		statPwChk = `<p>비밀번호가 일치하지 않습니다</p>`;
	}
	document.getElementsByClassName('pwchkstr')[0].innerHTML = statPw;
	document.getElementsByClassName('pwchkchkstr')[0].innerHTML = statPwChk;
}
function mailCheck() {
	let inputMail = email.value;
	let statMail = ``;
	mailAccep = false;
	if (inputMail == '') {
		statMail = `<p>이메일을 입력해주세요</p>`;
	} else if (!(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(inputMail))) {
		statMail = `<p>이메일 형식이 맞지 않습니다.</p>`;
	} else {
		mailAccep = true;
	}
	document.getElementsByClassName('mailchkstr')[0].innerHTML = statMail;
}

function phoneCheck() {
	let inputPhone = phone.value;
	let statphone = ``;
	phoneAccep = false;
	if (inputPhone == '') {
		statphone = `<p>휴대전화번호를 입력해주세요</p>`;
	} else if (!(/^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/.test(inputPhone))) {
		statphone = `<p>휴대전화번호 형식이 맞지 않습니다.</p>`;
	} else {
		phoneAccep = true;
	}
	document.getElementsByClassName('phonechkstr')[0].innerHTML = statphone;
}
function insertAccount() {
	// ^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i 이메일 정규식
	// 유효성검사
	sendSMS(phone.value);
	if (id.value == '') {
		alert("아이디를 입력해주세요");
		return;
	} else if (!idAccep) {
		alert("아이디가 올바르지 않습니다. 다시 확인해주세요.")
		return;
	}
	if (pw.value == '') {
		alert("비밀번호를 입력해주세요");
		return;
	}
	if (pwchk.value != pw.value) {
		alert("비밀번호가 일치하지 않습니다.");
		return;
	} else if (!pwAccep) {
		alert("비밀번호가 올바르지 않습니다. 다시 확인해주세요.")
		return;
	}
	if (email.value == '') {
		alert("이메일을 입력해주세요");
		return;
	} else if (!mailAccep) {
		alert("이메일 형식이 올바르지 않습니다. 다시 확인해주세요.")
		return;
	}
	if (phone.value == '') {
		alert("휴대전화번호를 입력해주세요");
		return;
	} else if (!phoneAccep) {
		alert("휴대전화번호가 올바르지 않습니다. 다시 확인해주세요.")
		return;
	}
	let agrees = document.querySelectorAll('.svc:checked');
	if (agrees.length != 2) {
		alert("필수 약관에 동의해야합니다.");
		return;
	}
}

// SMS전송요청
function sendSMS(phonenum){
	csrf_axios({
		method: 'post',
		url: '/sendsms',
		data: phonenum,
		})
		//axios.post("/sendsms", phonenum)
		.then(res => console.log("문자 전송 : "+res.data));
}