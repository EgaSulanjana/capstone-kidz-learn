const Hapi = require('@hapi/hapi');
const authRoutes = require('./routes/routes.js');

require('dotenv').config(); // Load environment variables

const init = async () => {
  const server = Hapi.server({
    port: process.env.PORT || 9000,
    host: 'localhost',
  });

  // Register routes
  server.route(authRoutes);

  await server.start();
  console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
  console.log(err);
  process.exit(1);
});

init();