package ibf2022.paf.assessment.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// Task 7
@Service
public class TodoService {

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    @Transactional
    public int upsertTask(List<Task> taskList, String username) throws Exception {
        Optional<User> existingUser = userRepo.findUserByUsername(username);
        User taskUser;
        if (existingUser.isEmpty()) {
            taskUser = new User();
            taskUser.setUsername(username);
            // newUser.setName(username); future implementation
            taskUser.setUserId(userRepo.insertUser(taskUser));
        } else {
            taskUser = existingUser.get();
        }

        int taskcount = 0;
        for (Task newTask : taskList) {
            taskRepo.insertTask(newTask, taskUser.getUserId());
            taskcount++;
        }
        return taskcount;
    }

}
