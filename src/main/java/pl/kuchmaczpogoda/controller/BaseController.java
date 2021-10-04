package pl.kuchmaczpogoda.controller;

import pl.kuchmaczpogoda.view.ViewFactory;

public abstract class BaseController {
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public BaseController() {

    }

    public String getFxmlName() {
        return fxmlName;
    }

    public abstract void closeWindowByX();


}
