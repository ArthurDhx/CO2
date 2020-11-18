package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Optional;

public class ControllerActionGratuite implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerActionGratuite(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.hboxAction.setButtonActionGratuiteControler(this);
    }

    /**
     * Récupere la source de l'evenemenet et le traite selon la source
     * @param event L'evenement
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        if (source == viewGame.hboxAction.btnDeplacerScientifiqToProject) {
            // Affiche le ChoiceDialog qui permet de deplacer un scientifque
            viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog() ;
            if(viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
            Optional<Subvention> result = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
            result.ifPresent(projetChoisi -> {
                // Si un projet a ete choisi
                if(model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi))
                    viewGame.addScientifiqueToProject(projetChoisi.getIndex()+1, viewGame.imageViewScientifique, projetChoisi.getContinent());
                model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                return;
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToSommet) {
            if(model.moveScientificOnSommet(model.getCurrentPLayer().getCurrentScientifique().getSubvention())){
                viewGame.addScientifiqueToSommet(viewGame.imageViewScientifique, model.getCurrentPLayer().getCurrentScientifique());
                model.getCurrentPLayer().setDeplacerScientifiqueSommetDone(true);
            } else {
                viewGame.sommetInfo();
            }
            viewGame.hboxAction.resetHbox();
        }
        else if (source == viewGame.hboxAction.btnMarche) {
            // Affiche le ChoiceDialog qui permet d'acheter ou de vendre des CEPs
            Player curPlayer = model.getCurrentPLayer();
            viewGame.hboxAction.displayMarcheCEP();
            Optional<String> result = viewGame.hboxAction.dialogAcheterVendreCEP.showAndWait();
            result.ifPresent(choice -> {
                if(choice == "Acheter"){
                    if(curPlayer.getArgent() < model.currentPriceCEP){
                        viewGame.displayAlertWithoutHeaderText("Problème lors de l'achat","Vous n'avez pas assez d'argent pour acheter un CEP.");
                    }
                    else{
                        curPlayer.retirerArgent(model.currentPriceCEP);
                        curPlayer.addCEP();
                        model.getCurrentPLayer().setMarcheCEPDone(true);
                        model.achatCEP();
                    }
                }
                else{
                    if(curPlayer.getCEP() < 1){
                        viewGame.displayAlertWithoutHeaderText("Problème lors de la vente","Vous n'avez pas assez de CEP pour en vendre.");
                    }
                    else{
                        curPlayer.removeCEP();
                        curPlayer.gainArgent(model.currentPriceCEP);
                        model.getCurrentPLayer().setMarcheCEPDone(true);
                        model.venteCEP();
                    }
                }
                viewGame.reloadArgent();
                viewGame.reloadCEP();
                viewGame.hboxAction.resetHbox();
            });
        }
        else if (source == viewGame.hboxAction.btnJouerCarte) {
            System.out.println("bouton jouer carte");
        }
    }
}
