import express from 'express';
import itemRoutes from './routes/itemRoutes';
import userRoutes from './routes/userRoutes';
import chatRoutes from './routes/chatRoutes';
import { errorHandler } from './middlewares/errorHandler';
import cors from 'cors';

const app = express();

app.use(cors());
app.use(express.json());
app.use('/assets', express.static('static'));

app.use('/api/items', itemRoutes);
app.use('/api/users', userRoutes);
app.use('/api/chats', chatRoutes);

app.use(errorHandler);

export default app;
