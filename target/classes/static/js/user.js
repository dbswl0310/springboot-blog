let index = {
	init: function() {
		$("#btn-save").on("click", ()=> {		// function(), {} => {} this를 바인딩 하기 위해서 
			this.save();
		});
	},
	
	save: function() {
		// alert('user의 save함수');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호츌
		// ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		$.ajax({
			// 회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			
			// data는 http의 body 데이터이다. -> MIMETYPE이 필요하다
			data: JSON.stringify(data), 	//JSON.stringify(data) 해주는 이유 -> data라고 하면 자바스크립트의 오브젝트라서 자바가 이해를 못한다. 그래서JSON으로 변경해야 한다.
			contentType: "application/json; charset=utf-8",	// bodydata가 어떤 타입인지 정의해준다. (MIMETYPE)
			
			dataType: "json" //서버에 요청해서 응답을 받았을 때 기본적으로 모든것이 Buffer로 오기 떄문에 String이다. (생긴게 json이라면) => javascript object라고 변경해준다.
		}).done(function(resp) { // 컨트롤러를 탐 -> 응답의 결과가 함수의 파라미터로 전달됨.(response), 응답의 결과가 문자열이 아니라 javascript결과로 돌아온다.
			// 성공 시 실행
			alert("회원가입이 완료되었습니다.");
			// console.log(resp);
			location.href="/";
		}).fail(function(error) {
			// 실패 시 실행
			alert(JSON.stringify(error));
		});	//ajax통신을 이용해서 3개의 파라미터를 json로 변경하여 insert 요청.
	}
}

index.init();