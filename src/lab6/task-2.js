const { printAsync } = require("./tasks");

// Napisz funkcje (bez korzytania z biblioteki async) wykonujaca
// rownolegle funkcje znajdujace sie w tablicy
// parallel_functions. Po zakonczeniu wszystkich funkcji
// uruchamia sie funkcja final_function. Wskazowka:  zastosowc
// licznik zliczajacy wywolania funkcji rownoleglych

function inparallel(parallelFunctions, finalFunction) {
  let counter = parallelFunctions.length;

  const boxedFunction = parallelFunctions.map((parallelFunction) => () => {
    parallelFunction(() => counter--);
  });

  for (const fun of boxedFunction) fun();

  const checkCondition = () => {
    if (counter <= 0) finalFunction();
    else setTimeout(checkCondition, 100);
  };

  checkCondition();
}

A = function (cb) {
  printAsync("A", cb);
};
B = function (cb) {
  printAsync("B", cb);
};
C = function (cb) {
  printAsync("C", cb);
};
D = function (cb) {
  printAsync("Done", cb);
};

// kolejnosc: A, B, C - dowolna, na koncu zawsze "Done"
inparallel([A, B, C], D);
