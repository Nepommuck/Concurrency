function printAsync(s, cb) {
  var delay = Math.floor(Math.random() * 1000 + 500);
  setTimeout(function () {
    console.log(s);
    if (cb) cb();
  }, delay);
}

function task1(cb) {
  printAsync("1", function () {
    task2(cb);
  });
}

function task2(cb) {
  printAsync("2", function () {
    task3(cb);
  });
}

function task3(cb) {
  printAsync("3", cb);
}


exports.printAsync = printAsync;
exports.task1 = task1;
exports.task2 = task2;
exports.task3 = task3;
