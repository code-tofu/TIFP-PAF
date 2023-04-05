package sg.edu.nus.iss.day21_lecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day21_lecture.exception.ResourceNotFoundException;
import sg.edu.nus.iss.day21_lecture.model.Room;
import sg.edu.nus.iss.day21_lecture.repository.IRoomRepository;

@Service
public class RoomService {
    
    @Autowired
    IRoomRepository roomRepo;

    public int count() {
        return roomRepo.count();
    }

    public Boolean save(Room room){
        return roomRepo.save(room);
    }
    
    public List<Room> findAll(){
        return roomRepo.findAll();
    }

    public Room findById(Integer id) throws ResourceNotFoundException{
        Room room = roomRepo.findById(id);

        return room;
    }

    public int update(Room room){
        return roomRepo.update(room);
    }

    public int deleteById(Integer id){
        return roomRepo.deleteById(id);
    }

    
}
