import axios from 'axios';

// axios 객체 생성
export default axios.create({
  // baseURL: 'https://k3b104.p.ssafy.io/api',
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-type': 'application/json',
  },
});
