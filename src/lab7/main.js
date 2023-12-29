const async = require("async");
const fs = require("node:fs");

const NUMBER_OF_PHILOSOPHERS = 5;
// const NUMBER_OF_PHILOSOPHERS = 8;
// const NUMBER_OF_PHILOSOPHERS = 10;
const EATING_TIME = 5;

var Fork = function () {
  this.state = 0;
  return this;
};

Fork.prototype.acquire = function (cb, waitTime = 1) {
  // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
  // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
  // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
  // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
  //    i ponawia probe itd.
  if (this.state != 0) {
    console.log("Waiting " + waitTime + "ms to acquire fork")
    setTimeout(() => this.acquire(cb, 2 * waitTime), waitTime);
  } else {
    this.state = 1;
    setTimeout(() => {
      const totalWaitTime = 2 * waitTime - 1;
      cb(totalWaitTime);
    }, waitTime);
  }
};

Fork.prototype.release = function () {
  this.state = 0;
};

var Waiter = function () {
  this.currentlyWaiting = 0;
  this.maxWaiting = Math.ceil(NUMBER_OF_PHILOSOPHERS / 2);
  return this;
};

Waiter.prototype.acquire = function (cb, totalWaitTime = 0) {
  if (this.currentlyWaiting >= this.maxWaiting) {
    const waitTime = 100;
    setTimeout(() => this.acquire(cb, totalWaitTime + waitTime), waitTime);
  } else {
    this.currentlyWaiting++;
    cb(totalWaitTime);
  }
};

Waiter.prototype.release = function () {
  this.currentlyWaiting--;
};

const WAITER = new Waiter();

var PersistenceManager = function () {
  this.totalWaitTimes = Array(NUMBER_OF_PHILOSOPHERS).fill(0);
  return this;
};

PersistenceManager.prototype.addWaitTime = function (philosoperId, waitTime) {
  this.totalWaitTimes[philosoperId] += waitTime;
};

PersistenceManager.prototype.logResults = function () {
  console.log(this.totalWaitTimes);
};

PersistenceManager.prototype.saveResultsToFile = function (filename) {
  fs.writeFile(
    `results/${filename}`,
    JSON.stringify(this.totalWaitTimes),
    (err) => {
      if (err) {
        console.error(err);
      }
    }
  );
};

const PERSISTENCE_MANAGER = new PersistenceManager();

var Philosopher = function (id, forks) {
  this.id = id;
  this.forks = forks;
  this.f1 = id % forks.length;
  this.f2 = (id + 1) % forks.length;
  return this;
};

const logAcquiredFork = (philosoperId, isRight, totalWaitTime) => {
  console.log(
    `Philosopher ${philosoperId}: Acquired ${
      isRight ? "right" : "left"
    } fork in ${totalWaitTime}ms`
  );
};

const logFinishedEating = (philosoperId) => {
  console.log(`Philosopher ${philosoperId}: Finished eating`);
};

Philosopher.prototype.startNaive = function (count) {
  var forks = this.forks,
    leftFork = forks[this.f1],
    rightFork = forks[this.f2],
    id = this.id;

  // zaimplementuj rozwiazanie naiwne
  // kazdy filozof powinien 'count' razy wykonywac cykl
  // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
  const philosopherCycle = (cb) => {
    leftFork.acquire((totalLeftTime) => {
      logAcquiredFork(id, false, totalLeftTime);

      rightFork.acquire((totalRightTime) => {
        logAcquiredFork(id, true, totalRightTime);

        setTimeout(() => {
          leftFork.release();
          rightFork.release();
          logFinishedEating(id);
          cb();
        }, EATING_TIME);
      });
    });
  };

  async.waterfall(Array(count).fill(philosopherCycle), (err, _) => {
    if (err) {
      console.error("Error: ", err);
    }
  });
};

Philosopher.prototype.startAsym = function (count) {
  var forks = this.forks,
    leftFork = forks[this.f1],
    rightFork = forks[this.f2],
    id = this.id;

  // zaimplementuj rozwiazanie asymetryczne
  // kazdy filozof powinien 'count' razy wykonywac cykl
  // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
  const philosopherCycle =
    id % 2 == 0
      ? // Half of philosophers take the right fork first
        (cb) => {
          leftFork.acquire((totalLeftTime) => {
            logAcquiredFork(id, false, totalLeftTime);

            rightFork.acquire((totalRightTime) => {
              logAcquiredFork(id, true, totalRightTime);

              setTimeout(() => {
                leftFork.release();
                rightFork.release();
                logFinishedEating(id);

                PERSISTENCE_MANAGER.addWaitTime(
                  id,
                  totalLeftTime + totalRightTime
                );
                cb();
              }, EATING_TIME);
            });
          });
        }
      : // Half of philosophers take the left fork first
        (cb) => {
          rightFork.acquire((totalRightTime) => {
            logAcquiredFork(id, true, totalRightTime);

            leftFork.acquire((totalLeftTime) => {
              logAcquiredFork(id, false, totalLeftTime);

              setTimeout(() => {
                rightFork.release();
                leftFork.release();
                logFinishedEating(id);

                PERSISTENCE_MANAGER.addWaitTime(
                  id,
                  totalLeftTime + totalRightTime
                );
                cb();
              }, EATING_TIME);
            });
          });
        };

  async.waterfall(
    Array(count)
      .fill(philosopherCycle)
      .concat(() =>
        PERSISTENCE_MANAGER.saveResultsToFile(
          `asym-${NUMBER_OF_PHILOSOPHERS}.json`
        )
      ),
    (err, _) => {
      if (err) {
        console.error("Error: ", err);
      }
    }
  );
};

Philosopher.prototype.startConductor = function (count) {
  var forks = this.forks,
    leftFork = forks[this.f1],
    rightFork = forks[this.f2],
    id = this.id;

  // zaimplementuj rozwiazanie z kelnerem
  // kazdy filozof powinien 'count' razy wykonywac cykl
  // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
  const philosopherCycle = (cb) => {
    WAITER.acquire((totalWaiterTime) => {
      console.log(
        `Philosopher ${id}: Got waiter approval in ${totalWaiterTime}ms`
      );
      leftFork.acquire((totalLeftTime) => {
        logAcquiredFork(id, false, totalLeftTime);

        rightFork.acquire((totalRightTime) => {
          logAcquiredFork(id, true, totalRightTime);
          WAITER.release();

          setTimeout(() => {
            leftFork.release();
            rightFork.release();
            logFinishedEating(id);

            PERSISTENCE_MANAGER.addWaitTime(
              id,
              totalLeftTime + totalRightTime + totalWaiterTime
            );
            cb();
          }, EATING_TIME);
        });
      });
    });
  };

  async.waterfall(
    Array(count)
      .fill(philosopherCycle)
      .concat(() =>
        PERSISTENCE_MANAGER.saveResultsToFile(
          `conductor-${NUMBER_OF_PHILOSOPHERS}.json`
        )
      ),
    (err, _) => {
      if (err) {
        console.error("Error: ", err);
      }
    }
  );
};

var forks = [];
var philosophers = [];
for (var i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
  forks.push(new Fork());
}

for (var i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
  philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
  // philosophers[i].startNaive(10);
  philosophers[i].startAsym(10);
  // philosophers[i].startConductor(10);
}
