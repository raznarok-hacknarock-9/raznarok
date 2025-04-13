import { PrismaClient } from '../../shared/prisma/index';

const prisma = new PrismaClient();
async function main() {}
main()
  .then(async () => {
    const foodTag = await prisma.tag.create({ data: { name: 'Food' } });
    const adventureTag = await prisma.tag.create({
      data: { name: 'Adventure' },
    });
    const cultureTag = await prisma.tag.create({ data: { name: 'Culture' } });
    const natureTag = await prisma.tag.create({ data: { name: 'Nature' } });
    const historyTag = await prisma.tag.create({ data: { name: 'History' } });
    const artTag = await prisma.tag.create({ data: { name: 'Art' } });
    const shoppingTag = await prisma.tag.create({ data: { name: 'Shopping' } });

    const hostUser = await prisma.user.create({
      data: {
        points: 1000,
        email: 'host@gmail.com',
        firstName: 'Adam',
        descriptionAsHost: `Hi! I'm a local from Kraków who loves showing guests the city's hidden gems—especially when it comes to food. From cozy traditional spots to trendy local favorites, I can point you to (or take you to!) some truly amazing restaurants. Let me help you experience Kraków like a local!
    `,
        tags: {
          connect: [{ id: foodTag.id }, { id: cultureTag.id }],
        },
        profilePictureFilename: 'host_profile.png',
        minPriceRange: 100,
        maxPriceRange: 2000,
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
        points: 120,
        email: 'traveler@gmail.com',
        firstName: 'Dominick',
        descriptionAsHost: `Hola! I'm a Barcelona native who loves sharing the city's vibrant culture, art, and food. Whether you're into exploring Gaudí's masterpieces, chilling by the beach, or tasting authentic tapas in tucked-away bars, I'd be thrilled to show you around. Let's make your Barcelona trip unforgettable—like a true local!
    `,
        minPriceRange: 100,
        maxPriceRange: 2000,
        tags: {
          connect: [{ id: adventureTag.id }],
        },
        profilePictureFilename: 'visitor_profile.jpg',
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
            'Dominick was a respectful and curious guest. Great conversation too.',
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

    await prisma.user.create({
      data: {
        email: 'user1@gmail.com',
        firstName: 'Michał',
        descriptionAsHost: ``,
        minPriceRange: 100,
        maxPriceRange: 150,
        tags: {
          connect: [{ id: historyTag.id }, { id: cultureTag.id }],
        },
        profilePictureFilename: '1.jpg',
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

    await prisma.user.create({
      data: {
        email: 'user2@gmail.com',
        firstName: 'Hubert',
        descriptionAsHost: ``,
        minPriceRange: 100,
        maxPriceRange: 200,
        tags: {
          connect: [{ id: foodTag.id }],
        },
        profilePictureFilename: '2.jpg',
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

    await prisma.user.create({
      data: {
        email: 'user3@gmail.com',
        firstName: 'Wojciech',
        descriptionAsHost: ``,
        minPriceRange: 100,
        maxPriceRange: 500,
        tags: {
          connect: [{ id: artTag.id }, { id: natureTag.id }],
        },
        profilePictureFilename: '3.jpg',
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

    await prisma.user.create({
      data: {
        email: 'user4@gmail.com',
        firstName: 'Izabela',
        descriptionAsHost: ``,
        minPriceRange: 100,
        maxPriceRange: 1000,
        tags: {
          connect: [{ id: shoppingTag.id }],
        },
        profilePictureFilename: '4.jpg',
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

    await prisma.user.create({
      data: {
        email: 'user5@gmail.com',
        firstName: 'Marco',
        descriptionAsHost: ``,
        profilePictureFilename: '5.jpg',
        minPriceRange: 300,
        maxPriceRange: 500,
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

    await prisma.user.create({
      data: {
        email: 'user6@gmail.com',
        firstName: 'Kim',
        descriptionAsHost: ``,
        profilePictureFilename: '6.jpg',
        minPriceRange: 200,
        maxPriceRange: 2000,
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

    await prisma.user.create({
      data: {
        email: 'user7@gmail.com',
        firstName: 'Sicilia',
        descriptionAsHost: ``,
        profilePictureFilename: '7.jpg',
        minPriceRange: 1500,
        maxPriceRange: 6000,
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

    await prisma.$disconnect();
  })
  .catch(async (e) => {
    console.error(e);
    await prisma.$disconnect();
  });
