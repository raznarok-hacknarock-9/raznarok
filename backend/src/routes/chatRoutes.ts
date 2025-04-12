import { Router } from 'express';
import {
  createChat,
  createMessage,
  getChatById,
  getChats,
  commentMeeting,
  updateMessageStatus,
} from '../controllers/chatController';

const router = Router();

router.post('/', createChat);
router.get('/', getChats);
router.get('/:id', getChatById);

router.post('/:id/messages', createMessage);
router.put('/messages/:messageId', updateMessageStatus);
router.post('/:id/comment', commentMeeting);

export default router;
