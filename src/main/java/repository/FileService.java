package repository;

import model.Ticket;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public <T extends IModel<T>> List<T> readData(String filePath, Class<T> cls){
        List<T> list = new ArrayList<>();
        try{
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                IModel<T> iModel = cls.getDeclaredConstructor().newInstance();
                T temp = iModel.parseData(line);
                list.add(temp);
            }
            bufferedReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static <T> void writeData(String filePath, List<T> list){
        try{
            FileWriter writer = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(writer);
            for(int i = 0; i < list.size(); i++){
                printWriter.write(list.get(i).toString());
                if(i != (list.size()-1)){
                    printWriter.write("\n");
                }
            }
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
