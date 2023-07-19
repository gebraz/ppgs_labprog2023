import axios from 'axios';

export const rest = axios.create({
  //config de cors
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token, content-type',
  },

  baseURL: 'http://localhost:8080/api',
});
