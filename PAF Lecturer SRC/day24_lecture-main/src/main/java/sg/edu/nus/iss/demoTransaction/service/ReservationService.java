package sg.edu.nus.iss.demoTransaction.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.demoTransaction.exception.BookException;
import sg.edu.nus.iss.demoTransaction.model.Book;
import sg.edu.nus.iss.demoTransaction.model.Resv;
import sg.edu.nus.iss.demoTransaction.model.ResvDetails;
import sg.edu.nus.iss.demoTransaction.repository.BookRepo;
import sg.edu.nus.iss.demoTransaction.repository.ResvDetailsRepo;
import sg.edu.nus.iss.demoTransaction.repository.ResvRepo;

@Service
public class ReservationService {
    @Autowired
    BookRepo bRepo;

    @Autowired
    ResvRepo rRepo;

    @Autowired
    ResvDetailsRepo rdRepo;

    @Transactional
    // @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation =
    // Propagation.REQUIRED)
    public Boolean reserveBooks(List<Book> books, String reservePersonName) {
        Boolean bReservationCompleted = false;

        // check for book availability by quantity
        Boolean bBooksAvailable = true;
        List<Book> libBooks = bRepo.findAll();
        for (Book book : books) {
            List<Book> filteredBook = libBooks.stream().filter(b -> b.getId().equals(book.getId()))
                    .collect(Collectors.toList());

            if (!filteredBook.isEmpty()) {
                if (filteredBook.get(0).getQuantity() == 0) {
                    bBooksAvailable = false;
                    break;
                } else {
                    // QuantityUpdate Marker
                    bRepo.update(book.getId());
                }
            } else {
                bBooksAvailable = false;
                break;
            }
        }

        // if books available
        // minus quantity from the books (requires transaction)
        // QuantityUpdate Marker

        if (bBooksAvailable) {
            // create the reservation record (requires transaction)
            Resv reservation = new Resv();
            reservation.setFullName(reservePersonName);
            reservation.setResvDate(Date.valueOf(LocalDate.now()));
            Integer reservationId = rRepo.createResv(reservation);

            // create the reservation details' records (requires transaction)
            for (Book book : books) {
                ResvDetails reservationDetail = new ResvDetails();
                reservationDetail.setBookId(book.getId());
                reservationDetail.setResvId(reservationId);

                rdRepo.createResvDetails(reservationDetail);
            }

            bReservationCompleted = true;
        } else {
            throw new BookException("Book is not available.");
        }

        return bReservationCompleted;
    }
}
