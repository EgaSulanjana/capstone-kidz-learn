const admin = require('firebase-admin');
const path = require('path');

require('dotenv').config();

const serviceAccountPath = process.env.FIREBASE_CREDENTIALS;
const serviceAccount = require(path.resolve(serviceAccountPath));

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;