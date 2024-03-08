package org.bearmetal.pits_inventory_tracking_system.controllers;

import java.util.ArrayList;

import org.bearmetal.pits_inventory_tracking_system.ApplicationCache;
import org.bearmetal.pits_inventory_tracking_system.models.Category;
import org.bearmetal.pits_inventory_tracking_system.models.Item;
import org.bearmetal.pits_inventory_tracking_system.models.Location;
import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;
import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ItemsController {
    PageLoader pageLoader = new PageLoader();

    @FXML
    protected TreeView<Object> itemTreeView;

    @FXML
    protected void onLocationManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "locationManager.fxml");
    }
    @FXML
    protected void onItemsManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "itemsManager.fxml");
    }
    @FXML
    protected void onCheckoutManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "checkoutManager.fxml");
    }
    @FXML
    protected void onLogoClick(ActionEvent event) {
        pageLoader.openNewPage(event, "main.fxml");
    }
    @FXML
    protected void onSettingsClick(ActionEvent event) {
        pageLoader.openNewPage(event, "settings.fxml");
    }

    @FXML
    protected void onSearchClick(ActionEvent event) {pageLoader.openNewPage(event, "searchPage.fxml");}
    @FXML
    protected void onAddItemsClick(ActionEvent event) {pageLoader.openNewPage(event, "addItems.fxml");}
    @FXML
    protected void onRemoveItemsClick(ActionEvent event) {pageLoader.openNewPage(event, "removeItems.fxml");}

    //I hate this.
    /** Creates TreeItems for the item TreeView and packages them into an ArrayList.
     * <p>
     * Very expensive, needs optimization!!
     * 
     * 
     * @return
     * An ArrayList of TreeItems to apply to the tree root.
     */
    public ArrayList<TreeItem<Object>> getTreeViewItems(){
        //List of nodes to apply to the tree root.
        ArrayList<TreeItem<Object>> treeItemsList = new ArrayList<TreeItem<Object>>();
        for (Category category : ApplicationCache.categories){
            System.out.println("Creating root node for " + category.getLocationID());
            //Create the root node for this category.
            TreeItem<Object> categoryRoot = new TreeItem<Object>(category.getLocationName());
            //Does this category have any child locations?
            if (category.getChildLocations().size() > 0){
                for (Location childLocation : category.getChildLocations()){
                    //Create a node for each one.
                    System.out.println("Creating node for child location " + childLocation.getLocationID());
                    TreeItem<Object> categoryChild = new TreeItem<Object>(childLocation.getLocationName());
                    for (Item childItem : childLocation.getChildItems()){
                        System.out.println("Creating node for child item " + childItem.getID());
                        String itemName = "(" + childItem.getID() + ") " + childItem.getName() + " - " + childItem.getDescription();
                        //Add this item to the child location node.
                        categoryChild.getChildren().add(new TreeItem<Object>(itemName));
                    }
                    //Add the child location node to the category node.
                    categoryRoot.getChildren().add(categoryChild);
                }
            } else {
                //No child locations? This must be a standalone location.
                //Just add the Category's children to the category node.
                for (Item childItem : category.getChildItems()){
                    String itemName = "(" + childItem.getID() + ") " + childItem.getName() + " - " + childItem.getDescription();
                    //Add items contained in this location to the child location node.
                    categoryRoot.getChildren().add(new TreeItem<Object>(itemName));
                }
            }
            System.out.println("Adding category to treeItemsList");
            treeItemsList.add(categoryRoot);
        }
        return treeItemsList;
    }

    @FXML
    public void initialize(){
        TreeItem<Object> treeRoot = new TreeItem<Object>("Items");
        treeRoot.setExpanded(true);
        ArrayList<TreeItem<Object>> items = getTreeViewItems();
        System.out.println("Applying tree view items to root.");
        for (TreeItem<Object> item : items){
            treeRoot.getChildren().add(item);
        }
        this.itemTreeView.setRoot(treeRoot);
        itemTreeView.setVisible(true);
        System.out.println("Done.");
    }
}
