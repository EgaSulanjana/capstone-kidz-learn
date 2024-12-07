const { Storage } = require("@google-cloud/storage");
const axios = require("axios");
const jwt = require("../utils/jwt");
const admin = require("../firebase/admin");
require("dotenv").config();

const storage = new Storage();
const bucketName = "kidzlearn-bucket";

const registerUser = async (request, h) => {
  const { username, email, password } = request.payload;

  if (!username || username.trim() === "") {
    return h
      .response({
        error: "Username cannot be empty",
      })
      .code(400);
  }

  try {
    const userRecord = await admin.auth().createUser({
      email,
      password,
      displayName: username,
    });

    const token = jwt.generateToken({ uid: userRecord.uid, email });

    return h
      .response({
        message: "User registered successfully",
        token,
      })
      .code(201);
  } catch (error) {
    return h.response({ error: error.message }).code(400);
  }
};

const loginUser = async (request, h) => {
  const { email, password } = request.payload;

  try {
    // Attempt to sign in using Firebase's signInWithPassword endpoint
    const response = await axios.post(
      `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${process.env.FIREBASE_API_KEY}`,
      {
        email,
        password,
        returnSecureToken: true,
      }
    );

    // Generate a custom JWT token
    const token = jwt.generateToken({ email });

    return h
      .response({
        message: "Login successful",
        token,
      })
      .code(200);
  } catch (error) {
    console.error(
      "Error response from Firebase:",
      error.response?.data || error.message
    );

    // Always show a generic error message
    return h
      .response({
        error: "Email or password is invalid",
      })
      .code(401);
  }
};

const getCategoryMaterials = async (request, h, category) => {
  try {
    // Define the subfolder structure for each category
    const categoryConfig = {
      animals: {
        prefix: "animals/",
        subfolders: {
          images: "images/",
          Pelafalan: "sounds/Pelafalan/",
          SuaraHewan: "sounds/SuaraHewan/",
        },
      },
      colors: {
        prefix: "colors/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
      colorAnimation: {
        prefix: "colorAnimation/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
      letters: {
        prefix: "letters/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
      letterAnimation: {
        prefix: "letterAnimation/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
      alphabet: {
        prefix: "alphabet/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
      alphabetAnimation: {
        prefix: "alphabetAnimation/",
        subfolders: {
          images: "images/",
          sounds: "sounds/",
        },
      },
    };

    // Get the configuration for the selected category
    const config = categoryConfig[category];
    if (!config) {
      return h.response({ error: "Invalid category" }).code(400);
    }

    const result = { urls: [] };

    // Iterate through subfolders and fetch files
    for (const [key, subfolderPath] of Object.entries(config.subfolders)) {
      const [files] = await storage.bucket(bucketName).getFiles({
        prefix: `${config.prefix}${subfolderPath}`,
      });

      files.forEach((file) => {
        const name = file.name.split("/").pop().split(".")[0]; // Extract name without extension

        // Find or create an entry for the file
        let entry = result.urls.find((item) => item.name === name);
        if (!entry) {
          entry = { name };
          result.urls.push(entry);
        }

        // Add the appropriate URL based on the subfolder
        const url = `https://storage.googleapis.com/${bucketName}/${file.name}`;
        if (key === "images") entry.urlImages = url;
        if (key === "Pelafalan") entry.urlPelafalan = url;
        if (key === "SuaraHewan") entry.urlSuara = url;
        if (key === "sounds") entry.urlSuara = url;
      });
    }

    return h.response(result).code(200);
  } catch (error) {
    console.error("Error fetching category materials:", error);
    return h.response({ error: error.message }).code(500);
  }
};

module.exports = { registerUser, loginUser, getCategoryMaterials };
