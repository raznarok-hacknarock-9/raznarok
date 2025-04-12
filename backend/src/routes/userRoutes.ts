import { Router } from 'express';
import {
  getUserById,
  getUsers,
  loginUser,
} from '../controllers/userController';

const router = Router();

router.get('/', getUsers);
router.get('/:id', getUserById);
router.post('/login', loginUser);

export default router;
