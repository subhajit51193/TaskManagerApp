package com.taskmanager.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanager.app.entity.Role;
import com.taskmanager.app.entity.Task;
import com.taskmanager.app.entity.User;
import com.taskmanager.app.repository.TaskRepository;
import com.taskmanager.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
        Optional<User> optUser = userRepository.findByEmail(email);
        if(optUser.isEmpty()) {
            throw new UsernameNotFoundException("We can't find an account with the provided email!");
        } else {
            User user = optUser.get();
            Set<GrantedAuthority> authorities = new HashSet<>();
            Set<Role> roles = user.getRoles();
            
            for (Role role : roles) {
            	
            	SimpleGrantedAuthority sga=new SimpleGrantedAuthority(role.getRoleName());
            	System.out.println("siga "+sga);
            	authorities.add(sga);
            }
            
            System.out.println("granted authorities "+authorities);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }
	}

	@Override
	public Set<Task> getAlltasksFromDb() {
		
		List<Task> list =  taskRepository.findAll();
		Set<Task> allTasks = new HashSet<>();
		for(Task task: list) {
			allTasks.add(task);
		}
		return allTasks;
			
	}

}
