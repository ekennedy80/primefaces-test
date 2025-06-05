package org.primefaces.test;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.dashboard.DashboardModel;
import org.primefaces.model.dashboard.DefaultDashboardModel;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;


@Data
@Named
@ViewScoped
public class KanbanView {

    private static final String STYLE_CLASS = "col-12 lg:col-6 xl:col-4";

    private DashboardModel todoModel;
    private DashboardModel inProgressModel;
    private DashboardModel inReviewModel;
    private DashboardModel doneModel;

    @PostConstruct
    public void init() {
        todoModel = new DefaultDashboardModel();
        inProgressModel = new DefaultDashboardModel();
        inReviewModel = new DefaultDashboardModel();
        doneModel = new DefaultDashboardModel();

        System.out.println("KanbanView init()");
    }

    public void onDrop(@SuppressWarnings("rawtypes") DragDropEvent ddEvent) {
        String draggedId = ddEvent.getDragId(); // Client id of dragged component
        String droppedId = ddEvent.getDropId(); // Client id of dropped component
        System.out.println("The draggable object is "+draggedId+" and the droppable object is "+droppedId);
    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        String result = String.format("Dragged index: %d, Dropped Index: %d, Widget Index: %d",
                event.getSenderColumnIndex(), event.getColumnIndex(), event.getItemIndex());
        message.setDetail(result);

        addMessage(message);
    }

    public void handleClose(CloseEvent event) {
        System.out.println(event.getComponent().getId()+" closed!");
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Toggled",
                "Toggle panel ID:'" + event.getComponent().getId() + "' Status:" + event.getVisibility().name());

        addMessage(message);
    }

    // /**
    // * Dashboard panel has been resized.
    // *
    // * @param widget the DashboardPanel
    // * @param size the new size CSS
    // */
    // public void onDashboardResize(final String widget, final String size) {
    // final DashboardWidget dashboard = responsiveModel.getWidget(widget);
    // if (dashboard != null) {
    // final String newCss = dashboard.getStyleClass().replaceFirst("xl:col-\\d+",
    // size);
    // dashboard.setStyleClass(newCss);
    // }
    // }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public DashboardModel getTodoModel() {
        return todoModel;
    }

    public DashboardModel getInProgressModel() {
        return inProgressModel;
    }

    public DashboardModel getInReviewModel() {
        return inReviewModel;
    }

    public DashboardModel getDoneModel() {
        return doneModel;
    }
}

