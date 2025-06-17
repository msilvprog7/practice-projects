# Game Poll Application

A real-time voting application built with Next.js, TypeScript, and Prisma. Users can create polls, vote on game-related questions, and see live results.

## Features

- Create custom polls with questions
- Vote on existing polls (answers are created dynamically)
- Real-time vote counting and results
- Responsive design for mobile and desktop
- PostgreSQL database with Prisma ORM

## Tech Stack

- **Frontend**: Next.js 15, React, TypeScript
- **Backend**: Next.js API Routes
- **Database**: PostgreSQL with Prisma ORM
- **Styling**: CSS Modules
- **Deployment**: Vercel-ready

## Prerequisites

- Node.js (16+ recommended)
- PostgreSQL database (local installation or Docker)
- Git

## Database Setup

### Option A: Using Docker (Recommended)

```bash
# Start PostgreSQL container
docker run --name postgres-gamepool \
  -e POSTGRES_PASSWORD=yourpassword \
  -e POSTGRES_DB=gamepool \
  -p 5432:5432 -d postgres
```

### Option B: Local PostgreSQL

- Install PostgreSQL locally
- Create a database named `gamepool`
- Note your username, password, and port

## Getting Started

1. **Clone and Install Dependencies**

```bash
git clone <your-repo-url>
cd game-poll
npm install
```

2. **Environment Configuration**

Create a `.env` file in the root directory:

```env
DATABASE_URL="postgresql://username:password@localhost:5432/gamepool"
```

Replace `username`, `password` with your actual credentials.

3. **Prisma Setup**

```bash
# Generate Prisma client
npx prisma generate

# Run database migrations
npx prisma migrate dev --name init

# (Optional) Seed the database with sample data
npx prisma db seed
```

4. **Verify Database Setup**

```bash
# Open Prisma Studio to view data
npx prisma studio

# Or check database connection
npx prisma db pull
```

5. **Start the Development Server**

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Database Schema

The application uses two main models:

```prisma
model Poll {
  id        Int      @id @default(autoincrement())
  question  String
  createdAt DateTime @default(now())
  answers   Answer[]
}

model Answer {
  id        Int      @id @default(autoincrement())
  text      String
  votes     Int      @default(0)
  createdAt DateTime @default(now())
  pollId    Int
  poll      Poll     @relation(fields: [pollId], references: [id], onDelete: Cascade)

  @@unique([pollId, text])
}
```

## API Endpoints

- `GET /api/polls` - Fetch all polls with answers
- `POST /api/polls` - Create a new poll
- `GET /api/polls/[id]` - Fetch a specific poll with answers
- `POST /api/polls/[id]` - Vote on a poll (creates or updates answer)

## Project Structure

```
├── prisma/
│   ├── schema.prisma      # Database schema
│   ├── migrations/        # Migration files
│   └── seed.ts           # Sample data (optional)
├── src/
│   ├── app/
│   │   ├── api/          # API routes
│   │   ├── components/   # React components
│   │   ├── hooks/        # Custom hooks
│   │   └── services/     # API service layer
├── .env                  # Environment variables
└── generated/prisma/     # Generated Prisma client
```

## Database Queries

### Using pgAdmin or PostgreSQL CLI

When querying the database directly, remember that Prisma creates table names with PascalCase, which requires quotes in PostgreSQL:

```sql
-- View all polls
SELECT * FROM "Poll"
ORDER BY id ASC

-- View all answers
SELECT * FROM "Answer"
ORDER BY id ASC

-- View polls with their answers (JOIN)
SELECT
  p.id as poll_id,
  p.question,
  p."createdAt" as poll_created,
  a.id as answer_id,
  a.text as answer_text,
  a.votes,
  a."createdAt" as answer_created
FROM "Poll" p
LEFT JOIN "Answer" a ON p.id = a."pollId"
ORDER BY p.id, a.votes DESC;

-- View polls with vote counts
SELECT
  p.id,
  p.question,
  COUNT(a.id) as answer_count,
  COALESCE(SUM(a.votes), 0) as total_votes
FROM "Poll" p
LEFT JOIN "Answer" a ON p.id = a."pollId"
GROUP BY p.id, p.question
ORDER BY total_votes DESC;

-- Find most popular answer for each poll
WITH RankedAnswers AS (
  SELECT
    a.*,
    ROW_NUMBER() OVER (PARTITION BY a."pollId" ORDER BY a.votes DESC) as rank
  FROM "Answer" a
)
SELECT
  p.question,
  ra.text as top_answer,
  ra.votes as top_votes
FROM "Poll" p
JOIN RankedAnswers ra ON p.id = ra."pollId" AND ra.rank = 1
ORDER BY ra.votes DESC;
```

### Check if tables exist:

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
AND table_name IN ('Poll', 'Answer');
```

## Troubleshooting

### Database Connection Issues:

```bash
# Test connection
npx prisma db pull
```

### Reset Database (if needed):

```bash
# WARNING: This deletes all data
npx prisma migrate reset
```

### View Generated SQL:

```bash
npx prisma migrate diff --from-empty --to-schema-datamodel prisma/schema.prisma --script
```

### Tables Don't Exist Error

If you get `ERROR: relation "Poll" does not exist`, it means the database tables haven't been created yet. Follow these steps:

1. **Check if your database is running:**

```bash
# For Docker
docker ps

# For local PostgreSQL
pg_isready -h localhost -p 5432
```

2. **Verify your DATABASE_URL in .env file:**

```env
DATABASE_URL="postgresql://username:password@localhost:5432/gamepool"
```

3. **Test database connection:**

```bash
npx prisma db pull
```

4. **Run migrations to create tables:**

```bash
# This creates the tables in your database
npx prisma migrate dev --name init
```

5. **Verify tables were created:**

```sql
-- Check if tables exist
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public';
```

6. **If migration fails, try force reset:**

```bash
# WARNING: This deletes all existing data
npx prisma migrate reset
```

### Common Issues:

- **Database not running** - Start PostgreSQL/Docker container
- **Wrong DATABASE_URL** - Check credentials and database name
- **Permission issues** - Ensure user has CREATE privileges
- **Database doesn't exist** - Create the database first: `CREATE DATABASE gamepool;`

## Learn More

To learn more about the technologies used:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API
- [Prisma Documentation](https://www.prisma.io/docs) - learn about Prisma ORM
- [PostgreSQL Documentation](https://www.postgresql.org/docs/) - learn about PostgreSQL

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Make sure to:

1. Set up your production database
2. Add your `DATABASE_URL` to Vercel environment variables
3. Run `npx prisma migrate deploy` in your build process

Check out our [Next.js deployment documentation](https://nextjs.org/docs/app/building-your-application/deploying) for more details.
