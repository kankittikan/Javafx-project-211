package ku.cs.report.models;

import java.util.HashSet;

public class Category {

    private String category;
    private HashSet<String> categoryList;

    public Category() {
        categoryList = new HashSet();
    }

    public void newCategory(String category) {
        categoryList.add(category);
    }

    public void removeCategory(String category) {
        categoryList.remove(category);
    }

    public boolean findCategory(String category) {
        return categoryList.contains(category);
    }

    public String getCategory(int index) {
        String[] buffer = (String[]) categoryList.toArray(new String[0]);
        return buffer[index];
    }

    public String getCategory(String category) {
        for (String i : categoryList) {
            if (i.equals(category)) {
                return i;
            }
        }
        return "No category";
    }

    public void changeName(String oldName, String newName) {
        for (String s : categoryList) {
            if (s.equals(oldName)) {
                removeCategory(oldName);
                newCategory(newName);
                break;
            }
        }
    }

    public HashSet<String> getAllList() {
        return categoryList;
    }


//    How to use
//    public static void main(String[] args) {
//        DataSource<Category> dataSource = new CategoryListFileDataSource("data","CategoryReport.csv");
//
//        Category categoryTest = dataSource.readData();
//        categoryTest.newCategory("categoryTest");
//        dataSource.writeData(categoryTest);
//
//        for (String i: categoryTest.getAllList()){
//            System.out.println(i);
//        }
//
//        System.out.println(categoryTest.findCategory("categoryTest"));
//        System.out.println(categoryTest.getCategory("Education"));
//    }

}
