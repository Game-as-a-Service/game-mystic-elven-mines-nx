import { fetcher } from './fetcher';
import { IplayCard } from './type';

const API_URL = import.meta.env.PUBLIC_API_URL;
console.log({ API_URL });

let gameId = 51651561561;

// [GET] 建立遊戲
export const createGame = async (gameId: string = 'gameId01') => {
  const url = `${API_URL}/api/test/createGame/${gameId}`;
  return await fetcher(url);
};

// [GET] 查詢遊戲
export const findGame = async (gameId: string = 'gameId01') => {
  const url = `${API_URL}/api/test/findGame/${gameId}`;
  return await fetcher(url);
};

// [GET] 查玩家手上有甚麼牌
export const getHandCards = async () => {
  const url = `${API_URL}/card/getHandCards?playerId={id}`;
  return await fetcher(url);
};

// [POST] 玩家打牌
export const playCard = async (body: IplayCard) => {
  const url = `${API_URL}/api/games/${gameId}:playCard`;
  return await fetcher(url, body);
};

// [POST] 玩家棄牌
export const foldCard = async (body: IplayCard) => {
  const url = `${API_URL}/api/games/${gameId}:foldCard`;
  return await fetcher(url, body);
};

// [GET] 取得地圖資訊
export const mapCards = async (body: IplayCard) => {
  const url = `${API_URL}/api/games/${gameId}/mapCards`;
  return await fetcher(url, body);
};

// [GET] 取得玩家資訊
export const players = async (body: IplayCard) => {
  const url = `${API_URL}/api/games/${gameId}/players`;
  return await fetcher(url, body);
};

// TEST 測試後端有沒有正常運作
export const helloTest = async () => {
  const url = `${API_URL}/api/test/hello`;
  return await fetcher(url);
};
