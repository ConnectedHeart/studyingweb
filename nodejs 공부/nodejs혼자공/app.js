const express = require("express");


const app = express();

dbConnect();

app.get("/", (req, res) => {
	res.send("Hello nodeJs");
})

// 바디파서 사용. json과 urlencoded
app.use(express.json());
app.use(express.urlencoded({extended: true}));

// 루트 경로로 요청했을 때 routes/contactRoutes 모듈을 미들웨어로 사용할 것이다.
app.use("/contacts", require("./routes/contactRoutes")); 
app.listen(3000, () => {
   
});
