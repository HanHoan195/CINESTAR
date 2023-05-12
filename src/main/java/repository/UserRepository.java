package repository;

import model.User;

public class UserRepository extends FileContext<User> {
    public UserRepository(){
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\user.csv";
        tClass = User.class;
    }
}
