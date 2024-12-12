// 연락처와 관련된 스키마정보 파일.
// 연락처 정보가 어떻게 구성되어 있는지 지정해줌.
// 스키마도 몽구스를 통해서 만듦.

const mongoose = require("mongoose");
//contanctSchema : 스키마의 이름.
// mongoose.Schema : 몽구스에서 제공하는 스키마 생성함수
/* 사용하고자하는 자료 : {
   자료유형, 필수자료인지 아닌지에 대한 설정.
}
*/
const contactSchema = new mongoose.Schema({
    name : {
        type : String, // type은 대문자로 지정
        required : true
    },
    email : {
        type : String,
    },
    phone : {
        type : String,
        // 사용자가 필수 지정값을 입력하지 않았을 때 표시할 에러메시지도 
        // 저장 가능.
        required : [true, "전화번호는 꼭 기입해 주세요."]
    }
    },
    {
    timestamps : true // 자료가 작성 또는 수정되면 자동으로 시간을 기록해주는 속성.
    // 도큐먼트에 createdAt, updatedAt이라는 정보가 자동으로 생성.
    }
);

// 스키마 -> 모델
// mongoose.model(모델명, 스키마명) 이란 함수로 모델을 생성한다.
// 어떤 스키마를 사용을 해서 어떤 모델을 만들 건지 지정해주면 된다.

const Contact = mongoose.model("Contact", contactSchema);

module.exports = Contact;
