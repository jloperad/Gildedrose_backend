const config = {
  "/api/": {
    target: "http://" + process.env.BACKEND_HOST + ":8080",
    secure: false,
    logLevel: "debug",
  },
};
module.exports = config;
