package sg.edu.nus.iss.demoTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.demoTransaction.payload.ReservationRequest;
import sg.edu.nus.iss.demoTransaction.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    ReservationService resvSvc;

    @PostMapping
    public ResponseEntity<Boolean> reserveBooks(@RequestBody ReservationRequest rq) {

        Boolean bReserveSuccessful = resvSvc.reserveBooks(rq.getBooks(), rq.getFullName());

        if (bReserveSuccessful)
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.OK);
        else
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
