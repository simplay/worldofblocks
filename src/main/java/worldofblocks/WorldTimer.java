package worldofblocks;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

// A clock that notifies its observers once every second
public class WorldTimer {
  private final LinkedList<Subscriber> subscribers = new LinkedList<>();
  private final TimerTask repeatedTask;
  private final Timer timer;
  private boolean terminate = false;

  public void addSubscriber(Subscriber subscriber) {
    if (!subscribers.contains(subscriber)) {
      subscribers.add(subscriber);
    }
  }

  void start() {
    timer.scheduleAtFixedRate(repeatedTask, 0L, 1000);
  }

  void stop() {
    this.terminate = true;
    timer.cancel();
  }

  WorldTimer() {
    repeatedTask = new TimerTask() {
      @Override
      public void run() {
        for (Subscriber subscriber : subscribers) {
          subscriber.handleUpdate();
        }

        if (terminate) {
          cancel();
        }
      }
    };

    timer = new Timer();
  }
}
