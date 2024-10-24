const express = require("express");
const app = express(); // http모듈에서는 createServer로 서버를 만듦.
                       // express에서는 express() 호출만으로 만들 수 있음.
                       
const router = express.Router();
                      
/* 라우팅 설정 post put delete 등을 사용가능 */
app.get("/", (req,res) => {
	res.send("Hello, Node!");
});

// 동일 경로의 get방식과 post 방식을 동시에 처리 가능
router.route("/contacts")
	.get((req, res) => {
		res.send("Contacts Page");
	})
	.post((req, res) => {
		res.send("Create Contacts");
	}); 

router.route("/contacts/:id")
	.get((req,res) => {
		res.send(`Views Contact for ID : ${req.params.id}`);
	})
	.put((req,res) => {
		res.send(`Update Contact for ID : ${req.params.id}`);
	})
	.delete((req,res) => {
		res.send(`Delete Contact for ID :${req.params.id}`);
	});

// 앱에 router 미들웨어를 사용했다는 것을 알려줌.
app.use(router);
 
app.listen(3000, () => {
   
});
