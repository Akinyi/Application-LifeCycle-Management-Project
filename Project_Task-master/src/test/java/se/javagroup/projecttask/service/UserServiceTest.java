package se.javagroup.projecttask.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.javagroup.projecttask.repository.TeamRepository;
import se.javagroup.projecttask.repository.UserRepository;
import se.javagroup.projecttask.repository.WorkItemRepository;
import se.javagroup.projecttask.repository.data.Team;
import se.javagroup.projecttask.repository.data.User;
import se.javagroup.projecttask.repository.data.WorkItem;
import se.javagroup.projecttask.repository.data.WorkItemStatus;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    // Team team1;
    @Autowired
    Service service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

   /* @Before
    public void setupUser(){
      //  teamRepository.deleteAll();
        team1 = new Team("Backend",true);
        teamRepository.save(team1);
    }*/

    @Before
    public void cleanDb() {
        // teamRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindUserByUsernumber()  {

        Team team = new Team("sjysstaTeamet", true);

        teamRepository.save(team);

        User user = new User();
        user.setFirstName("Joakim");
        user.setLastName("Anell");
        user.setUsername("HejsanHoppsan");
        user.setUserNumber(555L);
        user.setStatus(true);
        user.setTeam(team);

        userRepository.save(user);

        assertEquals(userRepository.findAll().size(), 1);

        List<User> users = service.getAllUsers(null, null, null, null, "555");

        assertEquals(users.size(), 1);
    }


    @Test
    public void testCreateUser(){
        User user = new User();
        user.setFirstName("Trix");
        user.setLastName("Hamilton");
        user.setUsername("TrixHamilton2018");
        user.setUserNumber(455L);
        user.setStatus(true);

        userRepository.save(user);

        assertEquals(userRepository.findAll().size(), 1);

        List<User> users2 = service.getAllUsers("Trix", "Hamilton", "TrixHamilton2018", null, "455");

        assertEquals(users2.size(), 1);
    }

   /* @Test
    public void testCreateTeam(){
        team1 = new Team("Frontend",true);
        teamRepository.save(team1);
        assertEquals(teamRepository.findAll().size(), 1);

    }*/

    @Test
    public void testAddUserToTeam(){

        Team team1 = new Team("Frontend",true);
        teamRepository.save(team1);


        User user = new User();
        user.setFirstName("Trix");
        user.setLastName("Hamilton");
        user.setUsername("TrixHamilton2018");
        user.setUserNumber(455L);
        userRepository.save(user);

        Long teamID = team1.getId();

        Long userNumber = user.getUserNumber();
        service.addUserToTeam(teamID, userNumber);
        Iterable<Team> getAllTeams = service.getAllTeams();
        assertTrue(getAllTeams.iterator().next().getId() == teamID);
        List<User> users = service.getAllUsers(null, null, null, null, "455");
        assertEquals(users.size(), 1);

    }

   /* @Test
    public void testCreateUserwithTeam(){
        team1 = new Team("Frontend",true);
        teamRepository.save(team1);
        User user = new User();
        Long id = 355L;
        user.setFirstName("Virginia");
        user.setLastName("Wolff");
        user.setUsername("VWolff4444");
        user.setUserNumber(677L);
        user.setStatus(true);
        user.setTeam(team1);

        userRepository.save(user);

        assertEquals(userRepository.findAll().size(), 1);

        List<User> users2 = service.getAllUsers("Virginia", "Wolff", "VWolff4444", "team1", "677");

        assertEquals(users2.size(), 1);
    }*/
}