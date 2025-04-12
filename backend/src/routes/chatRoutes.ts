import { Router } from 'express';
import {
  confirmMeeting,
  createChat,
  createMessage,
  getChatById,
  getChats,
  commentMeeting,
} from '../controllers/chatController';

const router = Router();

router.post('/', createChat);
router.get('/', getChats);
router.get('/:id', getChatById);

router.post('/:id/messages', createMessage);
router.put('/:id/confirm', confirmMeeting);
router.post('/:id/comment', commentMeeting);

export default router;
