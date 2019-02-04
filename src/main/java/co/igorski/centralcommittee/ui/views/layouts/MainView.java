package co.igorski.centralcommittee.ui.views.layouts;

import co.igorski.centralcommittee.ui.user.Users;
import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.design.AppLayoutDesign;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.notification.entitiy.DefaultNotification;
import com.github.appreciated.app.layout.notification.entitiy.Priority;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;

import static com.github.appreciated.app.layout.entity.Section.HEADER;
import static com.github.appreciated.app.layout.notification.entitiy.Priority.MEDIUM;

@Push(transport = Transport.LONG_POLLING)
@StyleSheet("src/css/app-layout.css")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Route("")
public class MainView extends AppLayoutRouterLayout {
    DefaultNotificationHolder notifications;
    DefaultBadgeHolder badge;
    private Behaviour variant;
    private Thread currentThread;

    @Override
    public AppLayout getAppLayout() {
        if (variant == null) {
            variant = Behaviour.LEFT_HYBRID;
            notifications = new DefaultNotificationHolder(newStatus -> {
            });
            badge = new DefaultBadgeHolder();
        }
        //reloadNotifications();

        if (!variant.isTop()) {
            return AppLayoutBuilder.get(variant)
                    .withAppBar(
                            AppBarBuilder.get().add(new AppBarNotificationButton(VaadinIcon.BELL, notifications)).build())
                    .withDesign(AppLayoutDesign.MATERIAL)
                    .withAppMenu(
                            LeftAppMenuBuilder.get()
                                    .addToSection(new MenuHeaderComponent("Vaadin + Spring Boot", "", "frontend/images/logo.png"), HEADER)
                                    .add(LeftSubMenuBuilder.get("Admin", VaadinIcon.USER.create())
                                            .add(new LeftNavigationComponent("Users", VaadinIcon.USERS.create(), Users.class))
                                            .build()
                                    )

                                    .build()

                    ).build();
        } else {
            return AppLayoutBuilder.get(variant)
                    .withTitle("App Layout")
                    .withAppBar(AppBarBuilder.get()
                            .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                            .build()
                    )
                    .withDesign(AppLayoutDesign.MATERIAL)
                    .build();
        }
    }

    private void reloadNotifications() {
        if (currentThread != null && !currentThread.isInterrupted()) {
            currentThread.interrupt();
        }
        badge.clearCount();
        notifications.clearNotifications();
        currentThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(5000);
                    addNotification(MEDIUM);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        currentThread.start();
    }

    private void addNotification(Priority priority) {
        getUI().ifPresent(ui -> ui.accessSynchronously(() -> {
            badge.increase();
            notifications.addNotification(new DefaultNotification("Title" + badge.getCount(), "Description" + badge.getCount(), priority));
        }));
    }
}
