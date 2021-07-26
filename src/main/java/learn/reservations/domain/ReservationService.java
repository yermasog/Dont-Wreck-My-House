package learn.reservations.domain;

import learn.reservations.data.DataAccessException;
import learn.reservations.data.GuestRepository;
import learn.reservations.data.HostRepository;
import learn.reservations.data.ReservationRepository;
import learn.reservations.models.Guest;
import learn.reservations.models.Host;
import learn.reservations.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;


    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByEmail(String email) throws DataAccessException {
        Host host = hostRepository.findHostByEmail(email);

        List<Reservation> resByHostEmail = reservationRepository.findResByHostEmail(host);
        for (int i = 0; i < resByHostEmail.size(); i++) {
           Guest guest =  guestRepository.matchGuestId(resByHostEmail.get(i).getGuest().getId());
            resByHostEmail.get(i).setGuest(guest);
        }
        return resByHostEmail;
    }

    public Result addRes(Reservation res) throws DataAccessException {
        Result result = validate(res);

        if(!result.isSuccess()){
            return result;
        }

        result.setReservation(reservationRepository.add(res));
        return result;
    }

    public Result editRes(Reservation res) throws DataAccessException {

        Result result = validate(res);
        if (!result.isSuccess()) {
            return result;
        }
        if(reservationRepository.edit(res)){
            result.setReservation(res);
        } else {
            result.addMessage("Could not find a matching reservation.");
        }

        return result;
    }

    public Result cancelRes(Reservation res) throws DataAccessException {
        Result result = new Result();
        if (!reservationRepository.cancel(res)) {
            result.addMessage("Reservation was not found");
        }
        return result;
    }

    private Result validate(Reservation res) throws DataAccessException {

        Result result = new Result();

         if (res == null) {
             result.addMessage("Reservation is required.");
             return result;
         }

        validateNulls(res, result);
         if(!result.isSuccess()) {
             return result;
         }

        validateDates(res, result);
        if(!result.isSuccess()) {
            return result;
        }

        validateOverlapping(res, result);
        if(!result.isSuccess()){
            return result;
        }

        return result;
    }

    private void validateNulls(Reservation res, Result result) {
        if(res == null){
            result.addMessage("Reservation is required.");
        }
        if (res.getHost().getId() == null) {
            result.addMessage("Cannot find host Id.");
        }
        if (res.getGuest().getId() == 0) {
            result.addMessage("Cannot find guest Id.");
        }
        if (res.getTotal() == null) {
            result.addMessage("Total is required.");
        }
        if (res.getStartDate() == null) {
            result.addMessage("Start date is required.");
        }
        if (res.getEndDate() == null) {
            result.addMessage("End date is required.");
        }
    }

    private void validateDates(Reservation res, Result result) {



        //local dates are in the future
        if(res.getStartDate().isBefore(LocalDate.now())) {
            result.addMessage("Start date must be in the future.");
        }
        //validate start is before end date
        if(res.getStartDate().isAfter(res.getEndDate())){
            result.addMessage("Start date must be BEFORE end date.");
        }

    }

    private void validateOverlapping(Reservation res, Result result) throws DataAccessException {
        List<Reservation> all = findByEmail(res.getHost().getEmail());

        for(Reservation r : all) {
            if( !(res.getId() == r.getId())
                   && !(res.getStartDate().isAfter(r.getEndDate())
                    || res.getStartDate().isEqual(r.getEndDate()))

            && !(res.getEndDate().isBefore(r.getStartDate()) ||
                    res.getEndDate().isEqual(r.getStartDate()))) {
                result.addMessage("Cannot add overlapping reservation.");
            }
        }

    }

}
