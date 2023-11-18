const { task1, printAsync } = require("./tasks");

/*
 ** Zadanie:
 ** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej
 ** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
 */
function loop(n) {
  if (n <= 0) printAsync("done", () => null);
  else task1(() => loop(n - 1));
}

loop(4);
