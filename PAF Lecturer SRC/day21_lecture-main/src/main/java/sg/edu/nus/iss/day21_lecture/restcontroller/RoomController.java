package sg.edu.nus.iss.day21_lecture.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.nus.iss.day21_lecture.exception.ResourceNotFoundException;
import sg.edu.nus.iss.day21_lecture.model.Room;
import sg.edu.nus.iss.day21_lecture.service.RoomService;

@Tag(description = "Room API ", name = "Room Resources")
@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    @Autowired
    RoomService roomSvc;

    // http://localhost:8080/api/rooms/count
    @GetMapping("/count")
    public ResponseEntity<Integer> getRoomCount() {
        Integer roomCount = roomSvc.count();

        //return new ResponseEntity<Integer>(roomCount, HttpStatus.OK);
        return ResponseEntity.ok().body(roomCount);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = new ArrayList<Room>();
        rooms = roomSvc.findAll();

        if (rooms.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // return ResponseEntity.noContent().build();
            throw new ResourceNotFoundException("No room found");
        } else {
            //return new ResponseEntity<>(rooms, HttpStatus.OK);
            return ResponseEntity.ok().body(rooms);
        }
    }

    @GetMapping("/{room-id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("room-id") Integer id) {
        Room room = roomSvc.findById(id);

        if (room == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(room);
    }

    @PostMapping
    public ResponseEntity<Boolean> createRoom(@RequestBody Room room) {
        Boolean created = roomSvc.save(room);

        if (created) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(created, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room) {
        int updated = 0;

        updated = roomSvc.update(room);

        if (updated == 1) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updated, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable("id") Integer id) {
        int deleted = 0;

        deleted = roomSvc.deleteById(id);

        if (deleted == 1) {
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
