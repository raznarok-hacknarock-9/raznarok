import { PrismaClient } from '../../shared/prisma/index';

const prisma = new PrismaClient();
async function main() {}
main()
  .then(async () => {
    const foodTag = await prisma.tag.create({ data: { name: 'Food' } });
    const adventureTag = await prisma.tag.create({
      data: { name: 'Adventure' },
    });
    const cultureTag = await prisma.tag.create({
      data: { name: 'Culture' },
    });

    const hostUser = await prisma.user.create({
      data: {
        email: 'host@gmail.com',
        firstName: 'Adam',
        descriptionAsHost: `
     Hi! I'm a local from Kraków who loves showing guests the city's hidden gems—especially when it comes to food. From cozy traditional spots to trendy local favorites, I can point you to (or take you to!) some truly amazing restaurants. Let me help you experience Kraków like a local!
    `,
        tags: {
          connect: [{ id: foodTag.id }, { id: cultureTag.id }],
        },
        profilePictureFilename: 'host_profile.png',
        availabilities: {
          create: [
            {
              location: 'Kraków',
              dateFrom: new Date('2025-05-01T10:00:00Z'),
              dateTo: new Date('2025-05-04T14:00:00Z'),
            },
            {
              location: 'Kraków',
              dateFrom: new Date('2025-06-01T12:00:00Z'),
              dateTo: new Date('2025-07-03T12:00:00Z'),
            },
          ],
        },
      },
    });

    const visitorUser = await prisma.user.create({
      data: {
        email: 'visitor@gmail.com',
        firstName: 'Lucía',
        descriptionAsHost: `
    Hola! I'm a Barcelona native who loves sharing the city's vibrant culture, art, and food. Whether you're into exploring Gaudí's masterpieces, chilling by the beach, or tasting authentic tapas in tucked-away bars, I'd be thrilled to show you around. Let's make your Barcelona trip unforgettable—like a true local!
    `,
        tags: {
          connect: [{ id: adventureTag.id }],
        },
        profilePictureFilename: 'visitor_profile.png',
        availabilities: {
          create: [
            {
              location: 'Barcelona',
              dateFrom: new Date('2025-05-10T09:00:00Z'),
              dateTo: new Date('2025-05-15T18:00:00Z'),
            },
            {
              location: 'Barcelona',
              dateFrom: new Date('2025-07-01T10:00:00Z'),
              dateTo: new Date('2025-07-10T20:00:00Z'),
            },
          ],
        },
      },
    });

    await prisma.comment.createMany({
      data: [
        {
          content:
            'Adam was an amazing host! Super knowledgeable and fun to hang with.',
          rating: 5,
          authorId: visitorUser.id,
          userId: hostUser.id,
        },
        {
          content:
            'Loved the food recommendations. Would definitely tour with him again!',
          rating: 4,
          authorId: visitorUser.id,
          userId: hostUser.id,
        },
      ],
    });

    await prisma.comment.createMany({
      data: [
        {
          content:
            'Lucía was a respectful and curious guest. Great conversation too.',
          rating: 5,
          authorId: hostUser.id,
          userId: visitorUser.id,
        },
        {
          content: 'We had a fun time exploring Kraków together!',
          rating: 4,
          authorId: hostUser.id,
          userId: visitorUser.id,
        },
      ],
    });

    await prisma.$disconnect();
  })
  .catch(async (e) => {
    console.error(e);
    await prisma.$disconnect();
  });
