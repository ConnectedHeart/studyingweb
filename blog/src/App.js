import { useState } from 'react';
import logo from './logo.svg';
import './App.css';

import Hello from './Hello.js';
function App() {
  let blogTitle = "리액트 블로그";

  let [postTitle, changePostTitle] = useState(['강남 맛집 탐방', '신사동 맛집 추천', '성수 맛집 추천'])
  let [postContent, changePostContent] = useState(['강남 고기 맛집', '신사동 파스타 맛집', '성수 초밥 맛집'])
  let [postDate, changePostDate] = useState(['2024-01-01', '2024-01-02', '2024-01-03'])
  let [postLike, changePostLike] = useState([0, 0, 0]);
  let [selectedTitle, changeSelectedTitle] = useState();
  let hi = "안녕";
  function addLike (index) {
    let like = [...postLike]
    like[index] = like[index] + 1;
    changePostLike(like)
    
  }

  function showModal(index) {
    document.querySelector('.modal').style.display = 'block';
    let title = postTitle[index];
    changeSelectedTitle(title);
  }
  return (
    <div className="App">
      <div className="nav-bar">
        {blogTitle}
      </div>
      <img src={logo} style={ {width : '200px', height : '200px'} }></img>
      <Hello/>
      <div onClick={() => {showModal(0)} } className="blog-list">
        <div className="post-title">
          {postTitle[0]}<span onClick={ () => {addLike(0) }}>👍</span><span className="like-count">{postLike[0]}</span>
        </div>
        <div className="post-content">
        {postContent[0]}
        </div>
        <div className="post-date">
        {postDate[0]}
        </div>
        <hr/>
      </div>

      <div onClick={() => {showModal(1)} } className="blog-list">
        <div className="post-title">
        {postTitle[1]}<span onClick={ () => {addLike(1) }}>👍</span><span className="like-count">{postLike[1]}</span>
        </div>
        <div className="post-content">
        {postContent[1]}
        </div>
        <div className="post-date">
        {postDate[1]} 
        </div>
        <hr/>
      </div>

      <div onClick={() => {showModal(2)} } className="blog-list">
        <div className="post-title">
        {postTitle[2]}<span onClick={ () => {addLike(2) }}>👍</span><span className="like-count">{postLike[2]}</span>
        </div>
        <div className="post-content">
        {postContent[2]}
        </div>
        <div className="post-date">
        {postDate[2]} 
        </div>
        <hr/>
      </div>
      <Modal props1={selectedTitle}/>
    </div>    
  );
}



function Modal(props) {
  /* props */
  return(
    <>
    <div className="modal">
      <div>
        {props.props1}
      </div>
    </div>
    </>
  );
}

export default App;
