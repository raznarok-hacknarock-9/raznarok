import express from 'express';
import userRoutes from './routes/userRoutes';
import chatRoutes from './routes/chatRoutes';
import { errorHandler } from './middlewares/errorHandler';
import cors from 'cors';
import morgan from 'morgan';

const app = express();

app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

app.use('/assets', express.static('static'));

app.use('/api/users', userRoutes);
app.use('/api/chats', chatRoutes);

app.use(errorHandler);

export default app;
