const express = require("express");
const router = express.Router();// http모듈에서는 createServer로 서버를 만듦.
// express에서는 express() 호출만으로 만들 수 있음.

// 동일 경로의 get방식과 post 방식을 동시에 처리 가능
router
    .route("/")
	.get((req, res) => {
		res.send("Contacts Page");
	})
	.post((req, res) => {
        const {name, email, phone} = req.body;

        if (!name || !email || !phone) {
            return res.send("필수 값이 입력되지 않았습니다.");
        }
		res.send("Create Contacts");
	}); 

router
    .route("/:id")
	.get((req,res) => {
		res.send(`Views Contact for ID : ${req.params.id}`);
	})
	.put((req,res) => {
		res.send(`Update Contact for ID : ${req.params.id}`);
	})
	.delete((req,res) => {
		res.send(`Delete Contact for ID :${req.params.id}`);
	});

module.exports = router;