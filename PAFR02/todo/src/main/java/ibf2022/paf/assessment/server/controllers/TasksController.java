package ibf2022.paf.assessment.server.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;
import ibf2022.paf.assessment.server.services.TodoService;
import jakarta.json.Json;

// Task 4, Task 8
// @RestController
@Controller
public class TasksController {

    @Autowired
    TodoService todoSVC;

    // username=fred&description-0=task%201&priority-0=1&dueDate-0=2020-01-01&description-1=task%202&priority-1=2&dueDate-1=2002-02-02&description-2=task%202&priority-2=2&dueDate-2=0023-03-03
    @PostMapping("/task")
    public ModelAndView addNewTask(@RequestBody MultiValueMap<String, String> post) throws Exception {
        List<Task> newTaskList = new ArrayList<>();

        int i = 0;
        while (post.containsKey(("description-" + Integer.toString(i)))) {
            System.out.println(("description-" + Integer.toString(i)));
            // Task(String description, int priority, LocalDate dueDate)
            Task newTask = new Task(
                    post.getFirst(("description-" + Integer.toString(i))),
                    Integer.parseInt(post.getFirst(("priority-" + Integer.toString(i)))),
                    LocalDate.parse(post.getFirst(("dueDate-" + Integer.toString(i)))));
            System.out.println(newTask);
            newTaskList.add(newTask);
            i++;
        }

        int taskCount = todoSVC.upsertTask(newTaskList, post.getFirst("username"));
        System.out.println("Tasks_Submitted:" + i + "/Tasks_Inserted:" + taskCount);

        // NOT WORKING YET
        ModelAndView mav = new ModelAndView("result");
        mav.addObject("taskCount", taskCount);
        mav.addObject("username", post.getFirst("username"));
        mav.setStatus(HttpStatus.OK);
        // mav.setStatus(HttpStatusCode.valueOf(200));
        return mav;
    }

    // FOR TEST CONTROLLER
    @Autowired
    UserRepository userRepo;
    @Autowired
    TaskRepository taskRepo;

    // TEST CONTROLLERS
    @GetMapping("/test/{user}")
    @ResponseBody
    public ResponseEntity<String> testGetController(@PathVariable String user) {
        Optional<User> getUser = userRepo.findUserByUsername(user);
        if (getUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "USER DOES NOT EXIST").build().toString()));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getUser.get().toString());
    }

    @PostMapping("/test/newUser")
    public ResponseEntity<String> testPostControllerUser(@RequestBody MultiValueMap<String, String> post)
            throws Exception {
        User newUser = new User();
        newUser.setUsername(post.getFirst("username"));
        newUser.setName(post.getFirst("name"));
        String resultID = userRepo.insertUser(newUser);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultID);
    }

    @PostMapping("/test/newTask")
    public ResponseEntity<String> testPostControllerTask(@RequestBody MultiValueMap<String, String> post)
            throws Exception {
        Task newTask = new Task(
                post.getFirst("description"),
                Integer.parseInt(post.getFirst("priority")),
                LocalDate.parse(post.getFirst("dueDate")));

        System.out.println(taskRepo.insertTask(newTask, post.getFirst("user_id")));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body("Task Created");
    }

}
