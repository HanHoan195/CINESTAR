package service;

import model.Role;
import model.User;
import repository.ISearch;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    public UserService(){
        userRepository = new UserRepository();
    }
    public List<User> getAllUser(){
        return userRepository.getAll();
    }
    public User checkLogin(String username, String pass){
        List<User> allUsers = getAllUser();
        for(User user: allUsers){
            if(user.getNameAccount().equals(username) && user.getPassword().equals(pass)){
                return user;
            }
        }
        return null;
    }
    public boolean checkLoginAdmin(String username, String pass){
        User user = getAllUser().get(0);
        if(user.getNameAccount().equals(username) && user.getPassword().equals(pass)){
            return true;
        }
        return false;
    }
    public List<User> getCustomerList(){
        List<User> allUsers = userRepository.getAll();
        List<User> customerList = new ArrayList<>();
        for(User user: allUsers){
            if(user.getRole() == Role.CUSTOMER){
                customerList.add(user);
            }
        }
        return customerList;
    }

//    public static void main(String[] args) {
//        UserRepository userRepository = new UserRepository();
//        UserService userService = new UserService();
//        List<User> userList = userService.getCustomerList();
//        System.out.println(userList.get(0));
//    }
    public void deleteById(long id){
        userRepository.deleteById(id);
    }
    public void add(User user){
        userRepository.add(user);
    }
    public void updateUserById(long id, User user){
        userRepository.updateById(id,user);
    }
    public User findById(long id){
        return userRepository.findById(id);
    }
    public List<User> searchByName(String name){
        ISearch<User> iSearch = new ISearch<User>() {
            @Override
            public boolean searchByName(User obj, String name) {
                String customerName = userRepository.findById(obj.getId()).getName();
                boolean checkSearch = customerName.toUpperCase().contains(name.toUpperCase());
                if (checkSearch) {
                    return true;
                }
                return false;
            }
        };
        return userRepository.searchByName(name,iSearch);
    }
}