module com.mycompany.cakoiphieuluuky {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.cakoiphieuluuky to javafx.fxml;
    exports com.mycompany.cakoiphieuluuky;
}
