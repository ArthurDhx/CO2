package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Optional;

public class ControllerActionGratuite implements EventHandler<ActionEvent> {
    Model model;
    ViewGame viewGame;

    public ControllerActionGratuite(Model model, ViewGame viewGame) {
        this.model = model;
        this.viewGame = viewGame;
        viewGame.hboxAction.setButtonActionGratuiteControler(this);
    }

    /**
     * Récupere la source de l'evenemenet et le traite selon la source
     *
     * @param event L'evenement
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        if (source == viewGame.hboxAction.btnDeplacerScientifique) {
            viewGame.hboxAction.displayActionScientifique();
        } else if (source == viewGame.hboxAction.btnCancelActionScientifique) {
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionGratuite();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToProject) {
            // Affiche le ChoiceDialog qui permet de deplacer un scientifque
            viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog();
            if (viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
            if (model.getCurrentPLayer().getCurrentScientifique().getSubvention() != null){
                model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
            }
            Optional<Subvention> result = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
            result.ifPresent(projetChoisi -> {
                // Si un projet a ete choisi
                if (model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi)) {
                    viewGame.addScientifiqueToProject(projetChoisi.getIndex() + 1, viewGame.imageViewScientifiqueN1, projetChoisi.getContinent());
                    model.getCurrentPLayer().getCurrentScientifique().setSubvention(projetChoisi);
                    projetChoisi.setStaffed(true);
                    if (model.getCurrentPLayer().getCurrentScientifique().getSubvention().getTilesSolarProject() != null) {
                        // set la valeur solaire si le scientifique joué est sur un projet solaire
                        model.getCurrentPLayer().getCurrentScientifique().setSubject(new Subject(GreenEnergyTypes.SOLAR));
                    }
                }
                model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                return;
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToSommet) {
            // Affiche le ChoiceDialog qui permet de deplacer un scientifque
            viewGame.hboxAction.displayDeplacerScientifiqueSommetChoiceDialog();
            if (viewGame.hboxAction.dialogDeplacerScientifiqueSommet == null) return;
            Optional<SommetTile> result = viewGame.hboxAction.dialogDeplacerScientifiqueSommet.showAndWait();
            result.ifPresent(sommetChoisi -> {
                if (model.moveScientificOnSommet(model.getCurrentPLayer().getCurrentScientifique().getSubvention(), sommetChoisi)) {
                    model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                    viewGame.addScientifiqueToSommet(viewGame.imageViewScientifiqueN1, model.getCurrentPLayer().getCurrentScientifique(), sommetChoisi);
                    model.getCurrentPLayer().getCurrentScientifique().setSommetTile(sommetChoisi);
                    model.getCurrentPLayer().getCurrentScientifique().setSubvention(null);
                    model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                } else {
                    viewGame.sommetInfo();
                }
                viewGame.hboxAction.resetHbox();
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToReserve){
            //remettre le scientifique dans la réserve et gagner 1 d’expertise dans le type d’énergie du projet
            // gagner 1 d’expertise dans le type d’énergie du projet
            model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
            model.getCurrentPLayer().addExpertise(model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy(), 1);
            viewGame.displayAlertWithoutHeaderText("Gain d'expertise", "En remettant votre scientifique dans votre réserve, vous gagné 1 d’expertise dans le type d’énergie " + model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy() + " !");
            //remettre le scientifique dans la réserve
            viewGame.deplacerScientifiqueReserve(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique());
            model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
            viewGame.hboxAction.resetHbox();
            model.getCurrentPLayer().getCurrentScientifique().moveToReserve();

        } else if (source == viewGame.hboxAction.btnMarche) {
            // Affiche le ChoiceDialog qui permet d'acheter ou de vendre des CEPs
            Player curPlayer = model.getCurrentPLayer();
            viewGame.hboxAction.displayMarcheCEP();
            Optional<String> result = viewGame.hboxAction.dialogAcheterVendreCEP.showAndWait();
            result.ifPresent(choice -> {
                if (choice == "Acheter") {
                    if (curPlayer.getArgent() < model.currentPriceCEP) {
                        viewGame.displayAlertWithoutHeaderText("Problème lors de l'achat", "Vous n'avez pas assez d'argent pour acheter un CEP.");
                    } else {
                        curPlayer.retirerArgent(model.currentPriceCEP);
                        curPlayer.addCEP();
                        model.getCurrentPLayer().setMarcheCEPDone(true);
                        model.achatCEP();
                    }
                } else {
                    if (curPlayer.getCEP() < 1) {
                        viewGame.displayAlertWithoutHeaderText("Problème lors de la vente", "Vous n'avez pas assez de CEP pour en vendre.");
                    } else {
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
        } else if (source == viewGame.hboxAction.btnJouerCarte) {
            System.out.println("bouton jouer carte");
        }
    }
}
