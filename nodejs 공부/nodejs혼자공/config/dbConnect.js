const mongoose = require("mongoose"); // mongoose 모듈을 사용함.
require("dotenv").config(); // dotenv 모듈의 config 메소드 실행

// async, await
const dbConnect = async () => {
    try {
        const connect = await mongoose.connect(process.env.DB_CONNECT);
        // process.env : require("dotenv").config() 를 실행해야 env파일의 변수를 가져올 수 있음.
        console.log("DB Connected");
    } catch(err) {
        console.log(err);
    }
}

module.exports = dbConnect;