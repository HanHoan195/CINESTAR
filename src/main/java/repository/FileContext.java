package repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContext<T extends IModel> {
    protected Class<T> tClass;
    protected String filePath;
    private FileService fileService;
    public FileContext(){
        fileService = new FileService();
    }
    public List<T> getAll() {
        return fileService.readData(filePath,tClass);
    }

    public T findById(long id) {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> iModel = (IModel<T>) list.get(i);
            if (iModel.getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }

    public void deleteById(long id) {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> iModel = (IModel<T>) list.get(i);
            if (iModel.getId() == id) {
                list.remove(i);
                break;
            }
        }
        fileService.writeData(filePath, list);
    }

    public void add(T newObj) {
        List<T> list = getAll();
        list.add(newObj);
        fileService.writeData(filePath,list);
    }

    public List<T> searchByName(String name, ISearch<T> iSearch) {
        List<T> resultList = new ArrayList<>();
        List<T> list =getAll();
        for (int i = 0; i < list.size(); i++) {
            if (iSearch.searchByName(list.get(i), name)) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }
    public void updateById(long id, T obj){
        List<T> list = getAll();
        for (T item : list) {
            IModel<T> iModel = (IModel<T>) item;
            if (iModel.getId() == id) {
                iModel.update(obj);
            }
        }
        fileService.writeData(filePath, list);
    }

    public void addList(List<T> list) {
        fileService.writeData(filePath, list);
    }
    public int checkID(long id) throws IOException {
        List<T> list = getAll();
        for (int i = 0; i < list.size(); i++) {
            IModel<T> imodel = (IModel<T>) list.get(i);
            if (imodel.getId() == id) {
                return 1;
            }
        }
        return -1;
    }
}
