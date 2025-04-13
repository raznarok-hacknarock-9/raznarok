import { Router } from 'express';
import {
  getUserById,
  getUsers,
  loginUser,
  addPoints,
} from '../controllers/userController';

const router = Router();

router.get('/', getUsers);
router.get('/:id', getUserById);
router.post('/login', loginUser);
router.put('/:id/points', addPoints);

export default router;
