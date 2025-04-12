import app from './app';
import config from './config/config';
import prisma from './config/prisma';

async function startServer() {
  try {
    await prisma.$connect();
    console.log('Connected to the database');

    const server = app.listen(config.port, '0.0.0.0', () => {
      console.log(`Server running on port ${config.port}`);
    });

    const shutdown = async () => {
      console.log('Shutting down server...');
      await prisma.$disconnect();
      server.close(() => {
        console.log('Server closed.');
        process.exit(0);
      });
    };

    process.on('SIGINT', shutdown);
    process.on('SIGTERM', shutdown);
  } catch (err) {
    console.error('Failed to start server:', err);
    process.exit(1);
  }
}

startServer();
