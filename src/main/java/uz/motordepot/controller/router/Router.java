package uz.motordepot.controller.router;


import lombok.Getter;

import static uz.motordepot.controller.navigation.PageNavigation.DEFAULT;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

@Getter
public class Router {

    private String page = DEFAULT;

    private PageChangeType type = FORWARD;

    public enum PageChangeType {
        FORWARD, REDIRECT;
    }

    public Router() {
    }

    public Router(String page, PageChangeType type) {
        this.page = (page != null ? page : DEFAULT);
        this.type = (type != null ? type : FORWARD);
    }

    public Router(String page) {
        this.page = page != null ? page : DEFAULT;
    }

    public void setPage(String page) {
        this.page = (page != null ? page : DEFAULT);
    }

    public void setRedirect() {
        this.type = REDIRECT;
    }

    public void setForward() {
        this.type = FORWARD;
    }

}
