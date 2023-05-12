package view;

import model.Role;
import model.User;
import utils.CSVUtils;

import java.util.List;
import java.util.Scanner;

public class LoginView {
    public User login(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.println("Mật khẩu: ");
        String password = scanner.nextLine();
        Role role = authenticate(username, password);
        return new User(username,password, role);
    }

    private model.Role authenticate (String username, String password){
        try {
            List<String> lines = CSVUtils.read("D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\login.csv");
            for (String line : lines) {
                String[] fields = line.split(",");
                if (fields.length == 3 && fields[0].equals(username) && fields[1].equals(password)) {
                    return model.Role.fromValue(fields[2]);
                }
            }
            throw new IllegalArgumentException("Sai tên đăng nhập hoặc mật khẩu");

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Kiểm tra đường dẫ đế file CSV", e);
        }
    }

}
