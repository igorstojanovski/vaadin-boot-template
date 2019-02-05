package co.igorski.centralcommittee.ui.dashboard;

import co.igorski.centralcommittee.ui.views.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class Dashboard extends VerticalLayout {

    public Dashboard() {
        Component content = new Span(new H3("HOME"));
        add(content);
    }

}
