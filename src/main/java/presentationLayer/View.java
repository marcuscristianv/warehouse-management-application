package presentationLayer;

import businessLayer.*;
import businessLayer.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class View extends JFrame implements Observer {
    private final JPanel mainPanel = new JPanel();

    // loginWindow
    private final JTextField user = new JTextField(10);
    private final JPasswordField pass = new JPasswordField(10);
    private final JButton loginButton = new JButton("Login");
    private final JButton registerButton = new JButton("Register");
    private final JButton exitAndSaveButton = new JButton("Exit and Save");

    // adminWindow
    private final JButton importButton = new JButton("Import from .csv");
    private final JButton report1Button = new JButton("Generate Report 1");
    private final JButton report2Button = new JButton("Generate Report 2");
    private final JButton report3Button = new JButton("Generate Report 3");
    private final JButton report4Button = new JButton("Generate Report 4");
    private final JButton addBaseProductButton = new JButton("Add Base Product");
    private final JButton editBaseProductButton = new JButton("Edit Menu Item");
    private final JButton addCompositeProductButton = new JButton("Add Composite Product");
    private final JButton removeMenuItem = new JButton("Remove Menu Item");
    private final JButton backToMain = new JButton("               < Back               ");

    // addBaseProductWindow
    private final JTextField title = new JTextField(15);
    private final JTextField rating = new JTextField(15);
    private final JTextField calories = new JTextField(15);
    private final JTextField protein = new JTextField(15);
    private final JTextField fat = new JTextField(15);
    private final JTextField sodium = new JTextField(15);
    private final JTextField price = new JTextField(15);
    private final JButton backToAdmin = new JButton("< Back");
    private final JButton confirmAddBaseProductButton = new JButton("Add");

    // editBaseProductWindow
    private final JButton searchBaseProductButton = new JButton("Search");
    private final JButton confirmEditBaseProductButton = new JButton("Confirm");
    private final JButton backToEditBaseProductQueryWindow = new JButton("< Back");

    // addCompositeProductWindow
    private final JButton buildCompositeProduct = new JButton("Finish");
    private final JButton addToCompositeProduct = new JButton("Add Item");

    // detailsCompositeProductWindow
    private final JButton confirmAddingCompositeProduct = new JButton("Add to Menu");
    private final JButton backToAddCompositeProductWindow = new JButton("< Back");

    // removeMenuItemWindow
    private final JButton searchMenuItemForRemovalButton = new JButton("Search and Delete");

    // report1Window
    private final JButton confirmReport1 = new JButton("Generate");
    private final JTextField startHour = new JTextField(10);
    private final JTextField endHour = new JTextField(10);

    // clientWindow
    private final JButton createOrderButton = new JButton("Create order");
    private final JButton searchByCriteriaButton = new JButton("          Search by criteria          ");

    // createOrderWindow
    private final JButton buildOrderButton = new JButton("Finish");
    private final JButton addToOrderButton = new JButton("Add Item");
    private final JButton backToClient = new JButton("< Back");

    // report2Window
    private final JTextField timesOrdered = new JTextField(10);
    private final JButton confirmReport2 = new JButton("Generate");

    // report3Window
    private final JTextField valueOrdered = new JTextField(10);
    private final JButton confirmReport3 = new JButton("Generate");

    // report4Window
    private final JTextField day = new JTextField(10);
    private final JButton confirmReport4 = new JButton("Generate");

    public View() {
        loginWindow();
    }

    public void loginWindow() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Username"));
        mainPanel.add(user);
        mainPanel.add(new JLabel("Password "));
        mainPanel.add(pass);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(exitAndSaveButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(450, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void adminWindow(HashSet<MenuItem> products) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(report1Button);
        mainPanel.add(report2Button);
        mainPanel.add(report3Button);
        mainPanel.add(report4Button);
        mainPanel.add(addBaseProductButton);
        mainPanel.add(editBaseProductButton);
        mainPanel.add(addCompositeProductButton);
        mainPanel.add(removeMenuItem);
        mainPanel.add(new JLabel("                  Menu Items                  "));

        java.util.List<String> cols = new ArrayList<>();
        cols.add("Title");
        cols.add("Rating");
        cols.add("Calories");
        cols.add("Protein");
        cols.add("Fat");
        cols.add("Sodium");
        cols.add("Price");

        java.util.List<MenuItem> menu = new ArrayList<>(products);
        java.util.List<String[]> values = new ArrayList<>();
        if(menu.size() != 0) {
            for (MenuItem menuItem : menu) {
                values.add(new String[]{menuItem.getTitle() + "", menuItem.getRating() + "", menuItem.getCalories() + "", menuItem.getProtein() + "", menuItem.getFat() + "", menuItem.getSodium() + "", "$" + menuItem.getPrice() + "",});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(500);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainPanel.add(backToMain);
        mainPanel.add(importButton);


        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(630, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void addBaseProductWindow() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Title       "));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(new JLabel("Rating   "));
        rating.setText("");
        rating.setEditable(true);
        mainPanel.add(rating);
        mainPanel.add(new JLabel("Calories"));
        calories.setText("");
        calories.setEditable(true);
        mainPanel.add(calories);
        mainPanel.add(new JLabel("Protein  "));
        protein.setText("");
        protein.setEditable(true);
        mainPanel.add(protein);
        mainPanel.add(new JLabel("Fat          "));
        fat.setText("");
        fat.setEditable(true);
        mainPanel.add(fat);
        mainPanel.add(new JLabel("Sodium "));
        sodium.setText("");
        sodium.setEditable(true);
        mainPanel.add(sodium);
        mainPanel.add(new JLabel("Price      "));
        price.setText("");
        price.setEditable(true);
        mainPanel.add(price);

        mainPanel.add(backToAdmin);
        mainPanel.add(confirmAddBaseProductButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(280, 260);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void editBaseProductQueryWindow() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Search title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(backToAdmin);
        mainPanel.add(searchBaseProductButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(320, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void editBaseProductWindow(MenuItem menuItem) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Title       "));
        title.setText(menuItem.getTitle());
        title.setEditable(false);
        mainPanel.add(title);
        mainPanel.add(new JLabel("Rating   "));
        rating.setText(menuItem.getRating() + "");
        rating.setEditable(true);
        mainPanel.add(rating);
        mainPanel.add(new JLabel("Calories"));
        calories.setText(menuItem.getCalories() + "");
        calories.setEditable(true);
        mainPanel.add(calories);
        mainPanel.add(new JLabel("Protein  "));
        protein.setText(menuItem.getProtein() + "");
        protein.setEditable(true);
        mainPanel.add(protein);
        mainPanel.add(new JLabel("Fat          "));
        fat.setText(menuItem.getFat() + "");
        fat.setEditable(true);
        mainPanel.add(fat);
        mainPanel.add(new JLabel("Sodium "));
        sodium.setText(menuItem.getSodium() + "");
        sodium.setEditable(true);
        mainPanel.add(sodium);
        mainPanel.add(new JLabel("Price      "));
        price.setText(menuItem.getPrice() + "");
        price.setEditable(true);
        mainPanel.add(price);

        mainPanel.add(backToEditBaseProductQueryWindow);
        mainPanel.add(confirmEditBaseProductButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(280, 260);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void addCompositeProductWindow(HashSet<MenuItem> products, java.util.List<MenuItem> currentList) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // tabel meniu
        mainPanel.add(new JLabel("                  Menu Items                  "));
        java.util.List<String> cols = new ArrayList<>();
        cols.add("Title");
        cols.add("Rating");
        cols.add("Calories");
        cols.add("Protein");
        cols.add("Fat");
        cols.add("Sodium");
        cols.add("Price");

        java.util.List<MenuItem> menu = new ArrayList<>(products);
        java.util.List<String[]> values = new ArrayList<>();
        if(menu.size() != 0) {
            for (MenuItem menuItem : menu) {
                values.add(new String[]{menuItem.getTitle() + "", menuItem.getRating() + "", menuItem.getCalories() + "", menuItem.getProtein() + "", menuItem.getFat() + "", menuItem.getSodium() + "", "$" + menuItem.getPrice() + "",});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(500);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);

        // tabel builder
        mainPanel.add(new JLabel("                  Composite Builder                  "));
        java.util.List<String> cols1 = new ArrayList<>();
        cols1.add("Title");

        java.util.List<String[]> values1 = new ArrayList<>();
        if(currentList != null && currentList.size() != 0) {
            for (MenuItem menuItem : currentList) {
                values1.add(new String[]{menuItem.getTitle() + ""});
            }
        }

        TableModel tableModel1 = new DefaultTableModel(values1.toArray(new Object[][] {}), cols1.toArray());
        JTable table1 = new JTable(tableModel1);

        table1.setEnabled(false);
        table1.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table1), BorderLayout.CENTER);
        mainPanel.add(table1.getTableHeader(), BorderLayout.NORTH);

        mainPanel.add(buildCompositeProduct);
        mainPanel.add(new JLabel("Base product title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(addToCompositeProduct);
        mainPanel.add(backToAdmin);


        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1440, 515);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
    }

    public void detailsCompositeProductWindow(CompositeProduct newComposite) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Composite Product Title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(new JLabel("    Computed Rating    "));
        rating.setText(newComposite.getRating() + "");
        rating.setEditable(false);
        mainPanel.add(rating);
        mainPanel.add(new JLabel("    Computed Calories    "));
        calories.setText(newComposite.getCalories() + "");
        calories.setEditable(false);
        mainPanel.add(calories);
        mainPanel.add(new JLabel("    Computed Protein    "));
        protein.setText(newComposite.getProtein() + "");
        protein.setEditable(false);
        mainPanel.add(protein);
        mainPanel.add(new JLabel("       Computed Fat       "));
        fat.setText(newComposite.getFat() + "");
        fat.setEditable(false);
        mainPanel.add(fat);
        mainPanel.add(new JLabel("    Computed Sodium    "));
        sodium.setText(newComposite.getSodium() + "");
        sodium.setEditable(false);
        mainPanel.add(sodium);
        mainPanel.add(new JLabel("     Computed Price     "));
        price.setText("$" + newComposite.getPrice() + "");
        price.setEditable(false);
        mainPanel.add(price);

        mainPanel.add(backToAddCompositeProductWindow);
        mainPanel.add(confirmAddingCompositeProduct);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(265, 410);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void removeMenuItemWindow() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Search title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(backToAdmin);
        mainPanel.add(searchMenuItemForRemovalButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(320, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void clientWindow(HashSet<MenuItem> products, java.util.List<MenuItem> currentList) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // tabel meniu
        mainPanel.add(new JLabel("                  Menu Items                  "));
        java.util.List<String> cols = new ArrayList<>();
        cols.add("Title");
        cols.add("Rating");
        cols.add("Calories");
        cols.add("Protein");
        cols.add("Fat");
        cols.add("Sodium");
        cols.add("Price");

        java.util.List<MenuItem> menu = new ArrayList<>(products);
        java.util.List<String[]> values = new ArrayList<>();
        if(menu.size() != 0) {
            for (MenuItem menuItem : menu) {
                values.add(new String[]{menuItem.getTitle() + "", menuItem.getRating() + "", menuItem.getCalories() + "", menuItem.getProtein() + "", menuItem.getFat() + "", menuItem.getSodium() + "", "$" + menuItem.getPrice() + "",});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(500);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);

        // tabel builder
        mainPanel.add(new JLabel("              Search results                  "));
        java.util.List<String> cols1 = new ArrayList<>();
        cols1.add("Title");
        cols1.add("Rating");
        cols1.add("Calories");
        cols1.add("Protein");
        cols1.add("Fat");
        cols1.add("Sodium");
        cols1.add("Price");

        java.util.List<String[]> values1 = new ArrayList<>();
        if(currentList != null && currentList.size() != 0) {
            for (MenuItem menuItem : currentList) {
                values1.add(new String[]{menuItem.getTitle() + "", menuItem.getRating() + "", menuItem.getCalories() + "", menuItem.getProtein() + "", menuItem.getFat() + "", menuItem.getSodium() + "", "$" + menuItem.getPrice() + "",});
            }
        }

        TableModel tableModel1 = new DefaultTableModel(values1.toArray(new Object[][] {}), cols1.toArray());
        JTable table1 = new JTable(tableModel1);

        table1.getColumnModel().getColumn(0).setPreferredWidth(500);
        table1.getColumnModel().getColumn(1).setPreferredWidth(150);
        table1.getColumnModel().getColumn(2).setPreferredWidth(150);
        table1.getColumnModel().getColumn(3).setPreferredWidth(150);
        table1.getColumnModel().getColumn(4).setPreferredWidth(150);
        table1.getColumnModel().getColumn(5).setPreferredWidth(150);
        table1.getColumnModel().getColumn(6).setPreferredWidth(150);

        table1.setEnabled(false);
        table1.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table1), BorderLayout.CENTER);
        mainPanel.add(table1.getTableHeader(), BorderLayout.NORTH);

        mainPanel.add(searchByCriteriaButton);

        mainPanel.add(new JLabel("Title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(new JLabel("Rating"));
        rating.setText("");
        rating.setEditable(true);
        mainPanel.add(rating);
        mainPanel.add(new JLabel("Calories"));
        calories.setText("");
        calories.setEditable(true);
        mainPanel.add(calories);
        mainPanel.add(new JLabel("Protein"));
        protein.setText("");
        protein.setEditable(true);
        mainPanel.add(protein);
        mainPanel.add(new JLabel("Fat"));
        fat.setText("");
        fat.setEditable(true);
        mainPanel.add(fat);
        mainPanel.add(new JLabel("Sodium"));
        sodium.setText("");
        sodium.setEditable(true);
        mainPanel.add(sodium);
        mainPanel.add(new JLabel("Price"));
        price.setText("");
        price.setEditable(true);
        mainPanel.add(price);

        mainPanel.add(backToMain);
        mainPanel.add(createOrderButton);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1530, 545);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void createOrderWindow(HashSet<MenuItem> products, java.util.List<MenuItem> currentList) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // tabel meniu
        mainPanel.add(new JLabel("                  Menu Items                  "));
        java.util.List<String> cols = new ArrayList<>();
        cols.add("Title");
        cols.add("Rating");
        cols.add("Calories");
        cols.add("Protein");
        cols.add("Fat");
        cols.add("Sodium");
        cols.add("Price");

        java.util.List<MenuItem> menu = new ArrayList<>(products);
        java.util.List<String[]> values = new ArrayList<>();
        if(menu.size() != 0) {
            for (MenuItem menuItem : menu) {
                values.add(new String[]{menuItem.getTitle() + "", menuItem.getRating() + "", menuItem.getCalories() + "", menuItem.getProtein() + "", menuItem.getFat() + "", menuItem.getSodium() + "", "$" + menuItem.getPrice() + "",});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(500);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);

        // tabel builder
        mainPanel.add(new JLabel("                  Order Builder                  "));
        java.util.List<String> cols1 = new ArrayList<>();
        cols1.add("Title");

        java.util.List<String[]> values1 = new ArrayList<>();
        if(currentList != null && currentList.size() != 0) {
            for (MenuItem menuItem : currentList) {
                values1.add(new String[]{menuItem.getTitle() + ""});
            }
        }

        TableModel tableModel1 = new DefaultTableModel(values1.toArray(new Object[][] {}), cols1.toArray());
        JTable table1 = new JTable(tableModel1);

        table1.setEnabled(false);
        table1.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table1), BorderLayout.CENTER);
        mainPanel.add(table1.getTableHeader(), BorderLayout.NORTH);

        mainPanel.add(buildOrderButton);
        mainPanel.add(new JLabel("Menu item title"));
        title.setText("");
        title.setEditable(true);
        mainPanel.add(title);
        mainPanel.add(addToOrderButton);
        mainPanel.add(backToClient);


        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1440, 515);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void report1Window() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Start Hour"));
        startHour.setText("");
        startHour.setEditable(true);
        mainPanel.add(startHour);
        mainPanel.add(new JLabel("   End Hour"));
        endHour.setText("");
        endHour.setEditable(true);
        mainPanel.add(endHour);

        mainPanel.add(backToAdmin);
        mainPanel.add(confirmReport1);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(255, 135);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void report2Window() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Times ordered"));
        timesOrdered.setText("");
        timesOrdered.setEditable(true);
        mainPanel.add(timesOrdered);

        mainPanel.add(backToAdmin);
        mainPanel.add(confirmReport2);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(255, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void report3Window() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Times ordered"));
        timesOrdered.setText("");
        timesOrdered.setEditable(true);
        mainPanel.add(timesOrdered);
        mainPanel.add(new JLabel("Value ordered"));
        valueOrdered.setText("");
        valueOrdered.setEditable(true);
        mainPanel.add(valueOrdered);

        mainPanel.add(backToAdmin);
        mainPanel.add(confirmReport3);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(255, 135);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void report4Window() {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(new JLabel("Day ordered    "));
        day.setText("");
        day.setEditable(true);
        mainPanel.add(day);

        mainPanel.add(backToAdmin);
        mainPanel.add(confirmReport4);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(255, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void employeeWindow(Collection<Collection<MenuItem>> orders) {
        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        java.util.List<String> cols = new ArrayList<>();
        cols.add("Orders");

        java.util.List<String[]> values = new ArrayList<>();
        if(orders.size() != 0) {
            for(Collection<MenuItem> crtOrder: orders) {
                String listOfItems = "";
                for(MenuItem item: crtOrder) {
                    listOfItems += item.getTitle() + ", ";
                }
                listOfItems = listOfItems.substring(0, listOfItems.length() - 2);
                values.add(new String[] {listOfItems});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(800);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainPanel.add(backToMain);

        this.add(mainPanel);
        this.setTitle("Food Delivery Management System");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(640, 515);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
    }

    public void addLoginListener(ActionListener l) {
        loginButton.addActionListener(l);
    }

    public void addRegisterListener(ActionListener l) {
        registerButton.addActionListener(l);
    }

    public void addExitAndSaveButtonListener(ActionListener l) {
        exitAndSaveButton.addActionListener(l);
    }

    public String getUserField() {
        return user.getText();
    }

    public String getPassField() {
        return String.valueOf(pass.getPassword());
    }

    public void emptyLoginFields() {
        user.setText("");
        pass.setText("");
    }

    public void addBackToMainListener(ActionListener l) {
        backToMain.addActionListener(l);
    }

    public void addImportButtonListener(ActionListener l) {
        importButton.addActionListener(l);
    }

    public void addAddBaseProductButtonListener(ActionListener l) {
        addBaseProductButton.addActionListener(l);
    }

    public void addBackToAdminButtonListener(ActionListener l) {
        backToAdmin.addActionListener(l);
    }

    public void addConfirmAddBaseProductButton(ActionListener l) {
        confirmAddBaseProductButton.addActionListener(l);
    }

    public void addEditBaseProductButtonListener(ActionListener l) {
        editBaseProductButton.addActionListener(l);
    }

    public void addSearchBaseProductButton(ActionListener l) {
        searchBaseProductButton.addActionListener(l);
    }

    public void addBackToEditBaseProductQueryWindow(ActionListener l) {
        backToEditBaseProductQueryWindow.addActionListener(l);
    }

    public void addConfirmEditBaseProductButton(ActionListener l) {
        confirmEditBaseProductButton.addActionListener(l);
    }

    public void addAddCompositeProductButtonListener(ActionListener l) {
        addCompositeProductButton.addActionListener(l);
    }

    public void addAddToCompositeProductListener(ActionListener l) {
        addToCompositeProduct.addActionListener(l);
    }

    public void addBuildCompositeProductListener(ActionListener l) {
        buildCompositeProduct.addActionListener(l);
    }

    public void addBackToAddCompositeProductWindowListener(ActionListener l) {
        backToAddCompositeProductWindow.addActionListener(l);
    }

    public void addConfirmAddingCompositeProductListener(ActionListener l) {
        confirmAddingCompositeProduct.addActionListener(l);
    }

    public void addRemoveMenuItemListener(ActionListener l) {
        removeMenuItem.addActionListener(l);
    }

    public void addSearchMenuItemForRemovalButtonListener(ActionListener l) {
        searchMenuItemForRemovalButton.addActionListener(l);
    }

    public void addReport1ButtonListener(ActionListener l) {
        report1Button.addActionListener(l);
    }

    public void addConfirmReport1Listener(ActionListener l) {
        confirmReport1.addActionListener(l);
    }

    public void addCreateOrderButtonListener(ActionListener l) {
        createOrderButton.addActionListener(l);
    }

    public void addBackToClientListener(ActionListener l) {
        backToClient.addActionListener(l);
    }

    public void addAddToOrderButtonListener(ActionListener l) {
        addToOrderButton.addActionListener(l);
    }

    public void addBuildOrderButtonListener(ActionListener l) {
        buildOrderButton.addActionListener(l);
    }

    public void addSearchByCriteriaButtonListener(ActionListener l) {
        searchByCriteriaButton.addActionListener(l);
    }

    public void addReport2ButtonListener(ActionListener l) {
        report2Button.addActionListener(l);
    }

    public void addConfirmReport2Listener(ActionListener l) {
        confirmReport2.addActionListener(l);
    }

    public void addReport3ButtonListener(ActionListener l) {
        report3Button.addActionListener(l);
    }

    public void addConfirmReport3Listener(ActionListener l) {
        confirmReport3.addActionListener(l);
    }

    public void addReport4ButtonListener(ActionListener l) {
        report4Button.addActionListener(l);
    }

    public void addConfirmReport4Listener(ActionListener l) {
        confirmReport4.addActionListener(l);
    }

    public void emptyTitle() {
        title.setText("");
    }

    public String getTitlee() {
        return title.getText();
    }

    public String getRating() {
        return rating.getText();
    }

    public String getCalories() {
        return calories.getText();
    }

    public String getProtein() {
        return protein.getText();
    }

    public String getFat() {
        return fat.getText();
    }

    public String getSodium() {
        return sodium.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public String getStartHour() {
        return startHour.getText();
    }

    public String getEndHour() {
        return endHour.getText();
    }

    public String getTimesOrdered() {
        return timesOrdered.getText();
    }

    public String getValueOrdered() {
        return valueOrdered.getText();
    }

    public String getDay() {
        return day.getText();
    }

    public void emptyReport1Fields() {
        startHour.setText("");
        endHour.setText("");
    }

    public void emptyReport2Field() {
        timesOrdered.setText("");
    }

    public void emptyReport3Fields() {
        timesOrdered.setText("");
        valueOrdered.setText("");
    }

    public void emptyReport4Field() {
        day.setText("");
    }

    public void showPrompt(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // delivery
    @Override
    public void update(Observable o, Object arg) {
        DeliveryService ds = (DeliveryService) o;
        List<Order> orders = (List<Order>) arg;

        java.util.List<String> cols = new ArrayList<>();
        cols.add("Orders");

        java.util.List<String[]> values = new ArrayList<>();
        if(orders.size() != 0) {
            for(Order crtOrder: orders) {
                values.add(new String[] {crtOrder.toString()});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), cols.toArray());
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(800);

        table.setEnabled(false);
        table.setAutoCreateRowSorter(true);
    }
}
