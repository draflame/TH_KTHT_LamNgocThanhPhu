const express = require("express");
const mongoose = require("mongoose");

const app = express();
app.use(express.json());

mongoose.connect("mongodb://mongo:27017/mydb");

app.get("/", (req, res) => {
  res.send("Hello Node + Mongo");
});

app.listen(3000);
