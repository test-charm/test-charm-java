package org.testcharm.dal.extensions.inspector.cucumber.page;

import org.testcharm.dal.extensions.inspector.cucumber.page.e.Element;
import org.testcharm.dal.extensions.inspector.cucumber.page.e.Tab;
import org.testcharm.dal.extensions.inspector.cucumber.page.e.Tabs;
import org.testcharm.pf.Elements;

import static org.testcharm.pf.By.css;

public class WorkbenchPanel extends Tab {

    private final Tabs<WorkspacePanel, Element> workspaces;

    public WorkbenchPanel(Element header, Element element) {
        super(header, element);
        workspaces = new Tabs<WorkspacePanel, Element>(locate("css[.workspaces]").single()) {
        };
    }

    public Elements<Element> DAL() {
        return locate("placeholder[DAL expression]");
    }

    public OutputPanel Current() {
        return workspaces.getCurrent().Current();
    }

    public OutputPanel Output(String name) {
        return workspaces.getCurrent().Output(name);
    }

    public void execute() {
        workspaces.getCurrent().execute();
    }

    public boolean isConnected() {
        return !getHeader().find(css(".session-state.connected")).list().isEmpty();
    }

    public void Release() {
        perform("css[.release].click");
    }

    public void Pass() {
        perform("css[.pass].click");
    }

    public void newWorkspace() {
        perform("css[.new].click");
    }

    public WorkspacePanel Workspace(String target) {
        return target.equals("Current") ? workspaces.getCurrent() : workspaces.switchTo(target);
    }
}
