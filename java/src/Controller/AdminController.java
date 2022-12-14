package Controller;

import Model.User.Admin;
import view.AdminView;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * The Class AdminController.
 */

public class AdminController {

    private final DatabaseController databaseController = DatabaseController.getInstance();

    private final AdminView adminView = new AdminView();
    
    /**
	 * Instantiates a new admin controller.
	 */

    public AdminController(){
    }
    
    /**
	 * Adds the admin.
	 *
	 * @param admin the admin
	 */

    public void addAdmin(Admin admin) {
        List<Admin> adminList = databaseController.getAdminFromDB();
        adminList.add(admin);
        databaseController.updateAdminFromDB(adminList);
    }
    
    /**
	 * Authorize admin.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */

    public boolean authorizeAdmin(String username, String password){
        List<Admin> adminList = databaseController.getAdminFromDB();
        for(int i = 0; i < adminList.size(); i++) {
            Admin user = adminList.get(i);
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    

    public boolean printAdminMenu(Scanner sc, AdminController adminController, MovieController movieController, CinemaController cinemaController, PriceController priceController) {
        return adminView.adminMenu(sc, adminController, movieController, cinemaController, priceController);
    }
}
