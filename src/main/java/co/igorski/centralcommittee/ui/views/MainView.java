package co.igorski.centralcommittee.ui.views;

import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;

public class MainView extends AbstractAppRouterLayout {

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
        menu.addMenuItems(new AppLayoutMenuItem("Dashboard", ""),
                new AppLayoutMenuItem("Users", "Users"));
    }
}
