package repository;

import model.Order;

public class OrderRepository extends FileContext<Order>{
    public OrderRepository() {
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\order.csv";
        tClass = Order.class;
    }
}
