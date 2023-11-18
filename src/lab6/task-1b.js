const async = require("async");
const { task1 } = require("./tasks");

async.waterfall(Array(3).fill(task1), (err, _) => {
  if (err) {
    console.error("Error: ", err);
  } else {
    console.log("Done");
  }
});
