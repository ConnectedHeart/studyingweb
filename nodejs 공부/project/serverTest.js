console.log("실행됨");
const http = require("http");

const server = http.createServer((req, res) => {
    res.setHeader("Content-type", "text/plain");
    const {url, method} = req;

    if (url === "/home" && method === "GET") {
        res.write("홈페이지");
        res.end();
    } else if (url === "/about" && method === "GET") {
        res.end("ABOUT");
    } else {
        res.end("NOT FOUND");
    }
});

server.listen(3000, () => {
    console.log("서버가 실행중");
})