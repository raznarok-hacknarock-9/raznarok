import express from 'express';
import itemRoutes from './routes/itemRoutes';
import userRoutes from './routes/userRouters';
import { errorHandler } from './middlewares/errorHandler';
import cors from 'cors';

const app = express();

app.use(cors());
app.use(express.json());

app.use('/api/items', itemRoutes);
app.use('/api/users', userRoutes);

app.use(errorHandler);

export default app;
