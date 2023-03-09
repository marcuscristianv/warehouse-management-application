package presentationLayer;

import businessLayer.*;
import businessLayer.MenuItem;
import dataLayer.Serializator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {
    private final DeliveryService ds;
    private ArrayList<MenuItem> currentItems;
    private CompositeProduct newComposite = null;
    private int clientID;
    public final View v;

    public Controller(DeliveryService ds, View v) {
        this.v = v;
        this.ds = Serializator.deserialize();
        currentItems = new ArrayList<>();

        // loginWindow
        v.addRegisterListener(new RegisterListener());
        v.addLoginListener(new LoginListener());
        v.addExitAndSaveButtonListener(new ExitAndSaveButtonListener());

        // adminWindow
        v.addBackToMainListener(new BackToMainListener());
        v.addImportButtonListener(new ImportButtonListener());
        v.addAddBaseProductButtonListener(new AddBaseProductButtonListener());
        v.addEditBaseProductButtonListener(new EditBaseProductButtonListener());
        v.addAddCompositeProductButtonListener(new AddCompositeProductButtonListener());
        v.addRemoveMenuItemListener(new RemoveMenuItemListener());
        v.addReport1ButtonListener(new Report1ButtonListener());
        v.addReport2ButtonListener(new Report2ButtonListener());
        v.addReport3ButtonListener(new Report3ButtonListener());
        v.addReport4ButtonListener(new Report4ButtonListener());

        // addBaseProductWindow
        v.addBackToAdminButtonListener(new BackToAdminButtonListener());
        v.addConfirmAddBaseProductButton(new ConfirmAddBaseProductButton());

        // editBaseProductQueryWindow
        v.addSearchBaseProductButton(new SearchBaseProductButton());

        // editBaseProductWindow
        v.addBackToEditBaseProductQueryWindow(new BackToEditBaseProductQueryWindow());
        v.addConfirmEditBaseProductButton(new ConfirmEditBaseProductButton());

        // addAddToCompositeProductWindow
        v.addAddToCompositeProductListener(new AddToCompositeProductListener());
        v.addBuildCompositeProductListener(new BuildCompositeProductListener());
        v.addBackToAddCompositeProductWindowListener(new BackToAddCompositeProductWindowListener());
        v.addConfirmAddingCompositeProductListener(new ConfirmAddingCompositeProductListener());

        // removeMenuItemWindow
        v.addSearchMenuItemForRemovalButtonListener(new SearchMenuItemForRemovalButtonListener());

        // report1Window
        v.addConfirmReport1Listener(new ConfirmReport1Listener());

        // clientWindow
        v.addCreateOrderButtonListener(new CreateOrderButtonListener());
        v.addSearchByCriteriaButtonListener(new SearchByCriteriaButtonListener());

        // createOrderWindow
        v.addBackToClientListener(new BackToClientListener());
        v.addAddToOrderButtonListener(new AddToOrderButtonListener());
        v.addBuildOrderButtonListener(new BuildOrderButtonListener());

        // report2Window
        v.addConfirmReport2Listener(new ConfirmReport2Listener());

        // report3Window
        v.addConfirmReport3Listener(new ConfirmReport3Listener());

        // report4Window
        v.addConfirmReport4Listener(new ConfirmReport4Listener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(v.getUserField().equals("") || v.getPassField().equals("")) {
                v.showPrompt("Error: invalid fields!");
            } else {
                if(ds.getAccounts().register(new Client(v.getUserField(), v.getPassField()))) {
                    v.showPrompt("Client account created!\nYou may now login.");
                    v.emptyLoginFields();
                } else {
                    v.showPrompt("Error: Account already exists!\nYou may login.");
                }
            }
        }
    }

    class ExitAndSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Serializator.serialize(ds);
            System.exit(0);
        }
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(v.getUserField().equals("") || v.getPassField().equals("")) {
                v.showPrompt("Invalid fields!");
            } else if(v.getUserField().equals("admin") && v.getPassField().equals("admin")) {
                v.showPrompt("Login successful!");
                v.emptyLoginFields();
                v.adminWindow(ds.getProducts());
            } else if(v.getUserField().equals("employee") && v.getPassField().equals("employee")) {
                v.showPrompt("Login successful!");
                v.emptyLoginFields();
                v.employeeWindow(ds.getListOfOrders());
            } else {
                int res = ds.getAccounts().login(v.getUserField(), v.getPassField());
                if(res == -1) {
                    v.showPrompt("Invalid credentials!");
                } else {
                    v.showPrompt("Login successful!");
                    v.emptyLoginFields();
                    clientID = res;
                    v.clientWindow(ds.getProducts(), currentItems);
                }
            }
        }
    }

    class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.emptyLoginFields();
            v.loginWindow();
        }
    }

    class ImportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.setVisible(false);
            ds.importFromCsv();
            v.adminWindow(ds.getProducts());
            v.showPrompt("Menu items imported successfully!");
            v.setVisible(true);
        }
    }

    class AddBaseProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.addBaseProductWindow();
        }
    }

    class BackToAdminButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.setVisible(false);
            currentItems = new ArrayList<>();
            v.adminWindow(ds.getProducts());
            v.setVisible(true);
        }
    }

    class ConfirmAddBaseProductButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                String title = v.getTitlee();
                double rating = Double.parseDouble(v.getRating());
                int calories = Integer.parseInt(v.getCalories());
                int protein = Integer.parseInt(v.getProtein());
                int fat = Integer.parseInt(v.getFat());
                int sodium = Integer.parseInt(v.getSodium());
                double price = Double.parseDouble(v.getPrice());

                if(rating < 0 || calories < 0 || protein < 0 || fat < 0 || sodium <0 || price < 0) {
                    throw new NumberFormatException();
                }

                ds.addMenuItem(new BaseProduct(title, rating, calories, protein, fat, sodium, price));
                v.showPrompt("Base Product added successfully!");
            } catch(NumberFormatException | NullPointerException ex) {
                v.showPrompt("Error: Invalid fields!");
            }
        }
    }

    class EditBaseProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.editBaseProductQueryWindow();
        }
    }

    class SearchBaseProductButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                java.util.List<MenuItem> menu = new ArrayList<>(ds.getProducts());
                boolean wasFound = false;
                for(MenuItem crt: menu) {
                    if(crt.getTitle().toLowerCase().contains(v.getTitlee().toLowerCase())) {
                        v.editBaseProductWindow(crt);
                        wasFound = true;
                        break;
                    }
                }

                if(!wasFound) {
                    throw new IllegalArgumentException();
                }
            } catch(NullPointerException ex) {
                v.showPrompt("Error: Invalid field!");
            } catch(IllegalArgumentException ex1) {
                v.showPrompt("Error: couldn't find the product!");
            }
        }
    }

    class BackToEditBaseProductQueryWindow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.editBaseProductQueryWindow();
        }
    }

    class ConfirmEditBaseProductButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                java.util.List<MenuItem> menu = new ArrayList<>(ds.getProducts());
                MenuItem reqItem = null;
                for(MenuItem crt: menu) {
                    if(crt.getTitle().toLowerCase().contains(v.getTitlee().toLowerCase())) {
                        reqItem = crt;
                        break;
                    }
                }

                String title = v.getTitlee();
                double rating = Double.parseDouble(v.getRating());
                int calories = Integer.parseInt(v.getCalories());
                int protein = Integer.parseInt(v.getProtein());
                int fat = Integer.parseInt(v.getFat());
                int sodium = Integer.parseInt(v.getSodium());
                double price = Double.parseDouble(v.getPrice());

                if(rating < 0 || calories < 0 || protein < 0 || fat < 0 || sodium <0 || price < 0) {
                    throw new NumberFormatException();
                }

                reqItem.setRating(rating);
                reqItem.setCalories(calories);
                reqItem.setProtein(protein);
                reqItem.setFat(fat);
                reqItem.setSodium(sodium);
                reqItem.setPrice(price);
                v.showPrompt("Base Product \"" + title +"\"\nedited successfully!");
            } catch(NumberFormatException | NullPointerException ex) {
                v.showPrompt("Error: Invalid fields!");
            }
        }
    }

    class AddCompositeProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.addCompositeProductWindow(ds.getProducts(), currentItems);
        }
    }

    class AddToCompositeProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                java.util.List<MenuItem> menu = new ArrayList<>(ds.getProducts());
                boolean wasFound = false;
                for(MenuItem crt: menu) {
                    if(crt.getTitle().toLowerCase().contains(v.getTitlee().toLowerCase())) {
                        currentItems.add(crt);
                        v.setVisible(false);
                        v.addCompositeProductWindow(ds.getProducts(), currentItems);
                        v.setVisible(true);
                        wasFound = true;
                        break;
                    }
                }

                if(!wasFound) {
                    throw new IllegalArgumentException();
                }
            } catch(NullPointerException ex) {
                v.showPrompt("Error: Invalid field!");
            } catch(IllegalArgumentException ex1) {
                v.showPrompt("Error: couldn't find the product!");
            }
        }
    }

    class BuildCompositeProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(currentItems.size() == 0) {
                    throw new IllegalArgumentException();
                }

                newComposite = new CompositeProduct("", currentItems);
                newComposite.computeRating();
                newComposite.computeCalories();
                newComposite.computeFats();
                newComposite.computePrice();
                newComposite.computeProteins();
                newComposite.computeSodium();
                v.detailsCompositeProductWindow(newComposite);
            } catch(IllegalArgumentException ex) {
                v.showPrompt("Error: Empty composite product!");
            }
        }
    }

    class BackToAddCompositeProductWindowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.addCompositeProductWindow(ds.getProducts(), currentItems);
        }
    }

    class ConfirmAddingCompositeProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                newComposite.setTitle(v.getTitlee());
                ds.addMenuItem(newComposite);
                v.showPrompt("Composite Product \"" + newComposite.getTitle() + "\" has been added to the menu!");
                currentItems = new ArrayList<>();
                newComposite = null;
                v.addCompositeProductWindow(ds.getProducts(), currentItems);

            } catch(NullPointerException ex) {
                v.showPrompt("Error: invalid composite product title name!");
            }
        }
    }

    class RemoveMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.removeMenuItemWindow();
        }
    }

    class SearchMenuItemForRemovalButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                java.util.List<MenuItem> menu = new ArrayList<>(ds.getProducts());
                boolean wasFound = false;
                for(MenuItem crt: menu) {
                    if(crt.getTitle().toLowerCase().contains(v.getTitlee().toLowerCase())) {
                        v.showPrompt("Menu item \"" + crt.getTitle() + "\" was removed!");
                        v.emptyTitle();
                        ds.removeMenuItem(crt);
                        wasFound = true;
                        break;
                    }
                }

                if(!wasFound) {
                    throw new IllegalArgumentException();
                }
            } catch(NullPointerException ex) {
                v.showPrompt("Error: Invalid field!");
            } catch(IllegalArgumentException ex1) {
                v.showPrompt("Error: couldn't find the product!");
            }
        }
    }

    class Report1ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.report1Window();
        }
    }

    class ConfirmReport1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getStartHour().equals("") || v.getEndHour().equals("")) {
                    throw new NullPointerException();
                }

                int startHour = Integer.parseInt(v.getStartHour());
                int endHour = Integer.parseInt(v.getEndHour());
                if(!(startHour >= 0 && startHour <= 23) || !(endHour >= 0 && endHour <= 23)) {
                    throw new NumberFormatException();
                }

                ds.generateReport1(startHour + "", endHour + "");
                v.emptyReport1Fields();
                v.showPrompt("Report type 1 generated successfully!");
            } catch(NullPointerException | NumberFormatException ex) {
                v.showPrompt("Error: Invalid fields!");
            }
        }
    }

    class CreateOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.createOrderWindow(ds.getProducts(), currentItems);
        }
    }

    class BackToClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.setVisible(false);
            currentItems = new ArrayList<>();
            v.clientWindow(ds.getProducts(), currentItems);
            v.setVisible(true);
        }
    }

    class AddToOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTitlee().equals("")) {
                    throw new NullPointerException();
                }

                java.util.List<MenuItem> menu = new ArrayList<>(ds.getProducts());
                boolean wasFound = false;
                for(MenuItem crt: menu) {
                    if(crt.getTitle().toLowerCase().contains(v.getTitlee().toLowerCase())) {
                        currentItems.add(crt);
                        v.setVisible(false);
                        v.createOrderWindow(ds.getProducts(), currentItems);
                        v.setVisible(true);
                        wasFound = true;
                        break;
                    }
                }

                if(!wasFound) {
                    throw new IllegalArgumentException();
                }
            } catch(NullPointerException ex) {
                v.showPrompt("Error: Invalid field!");
            } catch(IllegalArgumentException ex1) {
                v.showPrompt("Error: couldn't find the menu item!");
            }
        }
    }

    class BuildOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(currentItems.size() == 0) {
                    throw new IllegalArgumentException();
                }

                ds.addOrder(new Order(ds.getOrders().size() + 1, clientID), currentItems);
                v.showPrompt("Order and bill generated!");
                currentItems = new ArrayList<>();
                v.setVisible(false);
                v.createOrderWindow(ds.getProducts(), currentItems);
                v.setVisible(true);
            } catch(IllegalArgumentException ex) {
                v.showPrompt("Error: empty order!");
            }
        }
    }

    class SearchByCriteriaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String title = v.getTitlee();
                double rating = Double.parseDouble(v.getRating());
                int calories = Integer.parseInt(v.getCalories());
                int protein = Integer.parseInt(v.getProtein());
                int fat = Integer.parseInt(v.getFat());
                int sodium = Integer.parseInt(v.getSodium());
                double price = Double.parseDouble(v.getPrice());

                if(rating < 0 || calories < 0 || protein < 0 || fat < 0 || sodium <0 || price < 0) {
                    throw new NumberFormatException();
                }

                v.setVisible(false);
                v.clientWindow(ds.getProducts(), ds.findByCriteria(title, rating, calories, protein, fat, sodium, price));
                v.setVisible(true);
            } catch(NumberFormatException | NullPointerException ex) {
                v.showPrompt("Error: Invalid fields!");
            }
        }
    }

    class Report2ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.report2Window();
        }
    }

    class ConfirmReport2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTimesOrdered().equals("")) {
                    throw new NullPointerException();
                }

                int timesOrdered = Integer.parseInt(v.getTimesOrdered());
                if(timesOrdered < 0) {
                    throw new NumberFormatException();
                }

                ds.generateReport2(timesOrdered);
                v.emptyReport2Field();
                v.showPrompt("Report type 2 generated successfully!");
            } catch(NullPointerException | NumberFormatException ex) {
                v.showPrompt("Error: Invalid field!");
            }
        }
    }

    class Report3ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.report3Window();
        }
    }

    class ConfirmReport3Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getTimesOrdered().equals("") || v.getValueOrdered().equals("")) {
                    throw new NullPointerException();
                }

                int timesOrdered = Integer.parseInt(v.getTimesOrdered());
                int valueOrdered = Integer.parseInt(v.getValueOrdered());
                if(timesOrdered < 0 || valueOrdered < 0) {
                    throw new NumberFormatException();
                }

                ds.generateReport3(timesOrdered, valueOrdered);
                v.emptyReport3Fields();
                v.showPrompt("Report type 3 generated successfully!");
            } catch(NullPointerException | NumberFormatException ex) {
                v.showPrompt("Error: Invalid fields!");
            }
        }
    }

    class Report4ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.report4Window();
        }
    }

    class ConfirmReport4Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(v.getDay().equals("")) {
                    throw new NullPointerException();
                }

                int day = Integer.parseInt(v.getDay());
                if(!(day >= 1 && day <= 31)) {
                    throw new NumberFormatException();
                }

                ds.generateReport4(day);
                v.emptyReport4Field();
                v.showPrompt("Report type 4 generated successfully!");
            } catch(NullPointerException | NumberFormatException ex) {
                v.showPrompt("Error: Invalid field!");
            }
        }
    }
}
