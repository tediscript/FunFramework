package application.controllers;

import system.libraries.Controller;

/**
 *
 * @author tediscript
 */
public class Welcome extends Controller {

    public Welcome() {
    }

    @Override
    public void index() {
        this.load.view("welcome.html");
    }
}
