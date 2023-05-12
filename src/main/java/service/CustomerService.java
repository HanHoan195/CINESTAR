package service;

import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private UserService userService;
    //private List<User> userList = new ArrayList<>();
    public CustomerService(){
        userService = new UserService();
    }

    public List<User> getAllCustomer(){
        return userService.getCustomerList();
    }
    public List<User> searchByName(String name){
        List<User> customerList = getAllCustomer();
        List<User> result = new ArrayList<>();
        for(User customer : customerList){
            if(customer.getName().toUpperCase().contains(name.trim().toUpperCase())){
                result.add(customer);
            }
        }
        return result;
    }
    public void deleteById(long id){
        userService.deleteById(id);
    }
//    public void addNewCustomer(User user){
//        userService.add(user);
//    }

    public void addNewCustomer(User user){
        userService.add(user);
    }

    public void updateById(long id, User user){
        userService.updateUserById(id, user);
    }

    public User findById(long id){
        return userService.findById(id);
    }
    public void addNewOrder(Order order){
        List<Order> allOrder = userService.findById(order.getIdCustomer()).getOrderList();
        allOrder.add(order);
        userService.findById(order.getIdCustomer()).setOrderList(allOrder);
    }
    public boolean checkExistPhoneNumber(String number){
        //check số điện thoại đã tồn tại
        //true: đã tồn tại; false: chưa
        List<User> customerList = userService.getAllUser();
        for(User customer : customerList){
            if(customer.getPhoneNumber().equals(number.trim())){
                return true;
            }
        }
        return false;
    }
    public boolean checkExistUsername(String username){
        //check user đã tồn tại
        //true: đã tồn tại; false: chưa
        List<User> customerList = userService.getAllUser();
        for(User customer : customerList){
            if(customer.getNameAccount().equals(username.trim())){
                return true;
            }
        }
        return false;
    }

}