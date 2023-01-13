
package controller;

import javafx.stage.Stage;

public interface InterfacciaController {
    
    public void switchToHomeScene ( Stage currentStage, Controller controller );

    public void switchToProfiloArtistaScene ( Stage currentStage, Controller controller );

    public void switchToInsertMusicScene ( Stage currentStage, Controller controller );  

    public void switchToProfiloAscoltatoreScene ( Stage currentStage, Controller controller );

    public void exitApp ( );

    public void switchToAccountScene ( Stage currentStage, Controller controller);

}
