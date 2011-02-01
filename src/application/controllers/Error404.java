package application.controllers;

import system.libraries.Controller;

/**
 *
 * @author tediscript
 */
public class Error404 extends Controller{

    @Override
    public void index() {
        this.load.view("error404.html");
    }
}
