# raznarok

### Node

Node version: 20.

### 🐳 Running with Docker Compose

This project includes a `docker-compose.yml` file that sets up:

- **PostgreSQL** database (`postgres`)
- **pgAdmin 4** web UI (`pgadmin`) at port `5050`

#### 🚀 Start the services

To start the PostgreSQL and pgAdmin containers:

```bash
docker-compose up -d
```

### Prisma CLI

Creating migration:

```
npx prisma migrate dev --name <migration_name>
```
This will run `generate` under the hood.


To run `generate` only (it will generate prisma client files):
```
npx prisma generate
```

Pushing schema change without migration:
```
npx prisma db push
```






