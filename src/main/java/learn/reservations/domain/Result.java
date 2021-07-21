package learn.reservations.domain;

import learn.reservations.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private final ArrayList<String> messages = new ArrayList<>();
    private Reservation res;

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

  public void setReservation(Reservation res) {
        this.res = res;
  }

  public Reservation getRes() {
        return res;
  }

}
