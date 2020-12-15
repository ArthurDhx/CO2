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
            viewGame.hboxAction.displayActionChoixScientifique();
        } else if (source == viewGame.hboxAction.btnScientifique1) {
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionScientifique();
            model.getCurrentPLayer().setCurrentScientifique(0);
        } else if (source == viewGame.hboxAction.btnScientifique2) {
            model.getCurrentPLayer().setCurrentScientifique(1);
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionScientifique();
        }else if (source == viewGame.hboxAction.btnScientifique3) {
            model.getCurrentPLayer().setCurrentScientifique(2);
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionScientifique();
        }else if (source == viewGame.hboxAction.btnScientifique4) {
            model.getCurrentPLayer().setCurrentScientifique(3);
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionScientifique();
        } else if (source == viewGame.hboxAction.btnCancelActionScientifique) {
            viewGame.hboxAction.resetHbox();
            viewGame.hboxAction.displayActionGratuite();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToProject) {
            // Affiche le ChoiceDialog qui permet de deplacer un scientifque
            // TODO boite de dialogue pour choisir quelle scientifique on veut bouger
            viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog();
            if (viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
            if (model.getCurrentPLayer().getCurrentScientifique().getSubvention() != null){
                model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
            }
            Optional<Subvention> result = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
            result.ifPresent(projetChoisi -> {
                // Si un projet a ete choisi
                if (model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi)) {
                    viewGame.addScientifiqueToProject(projetChoisi.getIndex(), model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(), projetChoisi.getContinent());
                    model.getCurrentPLayer().getCurrentScientifique().setSubvention(projetChoisi);
                    projetChoisi.setStaffed(true);
                    if (model.getCurrentPLayer().getCurrentScientifique().getSubvention().getProject() != null) {
                        // set la valeur solaire si le scientifique joué est sur un projet solaire
                        model.getCurrentPLayer().getCurrentScientifique().setSubject(new Subject(greenEnergyTypes.SOLAR));
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
                    viewGame.addScientifiqueToSommet(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(), model.getCurrentPLayer().getCurrentScientifique(), sommetChoisi);
                    model.getCurrentPLayer().getCurrentScientifique().setSommetTile(sommetChoisi);
                    model.getCurrentPLayer().getCurrentScientifique().setSubvention(null);
                    model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                    sommetChoisi.setStaffedOnEnergy(model.getCurrentPLayer().getCurrentScientifique().getSubject());
                }
                viewGame.hboxAction.resetHbox();
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToReserve){
            //remettre le scientifique dans la réserve et gagner 1 d’expertise dans le type d’énergie du projet
            model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
            // gagner 1 d’expertise dans le type d’énergie du projet
            //model.getCurrentPLayer().addExpertise(model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy(), 1);
            //viewGame.displayAlertWithoutHeaderText("Gain d'expertise", "En remettant votre scientifique dans votre réserve, vous gagné 1 d’expertise dans le type d’énergie " + model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy() + " !");
            //remettre le scientifique dans la réserve
            viewGame.deplacerScientifiqueReserve(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(),model.getCurrentPLayer().getCurentScientifiqueId() );
            model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
            viewGame.hboxAction.resetHbox();
            model.getCurrentPLayer().getCurrentScientifique().moveToReserve();
            //viewGame.reloadPlayerExpertise(model.getCurrentPLayer());

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
                        model.getCurrentPLayer().setActionMarche(1);
                        model.achatCEP();
                    }
                } else {
                    if (curPlayer.getCEP() < 1) {
                        viewGame.displayAlertWithoutHeaderText("Problème lors de la vente", "Vous n'avez pas assez de CEP pour en vendre.");
                    } else {
                        curPlayer.removeCEP();
                        curPlayer.gainArgent(model.currentPriceCEP);
                        model.getCurrentPLayer().setMarcheCEPDone(true);
                        model.getCurrentPLayer().setActionMarche(2);
                        model.venteCEP();
                    }
                }
                viewGame.reloadArgent();
                viewGame.reloadCEP();
                viewGame.hboxAction.resetHbox();
            });
        } else if (source == viewGame.hboxAction.btnJouerCarteLobby) {
            playLobbyCard();
            viewGame.reloadArgent();
            viewGame.reloadCEP();
            viewGame.reloadPointVictoire();
            viewGame.reloadresourcesTech();
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnMarquerPointOnu) {
            marquePointOnu();
            viewGame.reloadPointVictoire();
            viewGame.reloadresourcesTech();
            viewGame.hboxAction.resetHbox();
        }
    }

    private void marquePointOnu() {
        viewGame.hboxAction.displayMarqueOnuChoiceDialog();
        Optional<OnuCard> resultOnuCard = viewGame.hboxAction.dialogMarqueOnuCard.showAndWait();
        resultOnuCard.ifPresent(onuCard -> {
            try {
                model.giveVictoryPointsOnuCards(onuCard);
                model.getCurrentPLayer().setCardDone(true);
            } catch (Exception e) {
                viewGame.displayAlertWithoutHeaderText("Impossible de marquer les points" , e.getMessage());
            }
        });
    }

    private void playLobbyCard() {
        viewGame.hboxAction.displayPlayLobbyCardChoiceDialog();
        Optional<LobbyCard> resultLobby = viewGame.hboxAction.dialogJouerCarteLobby.showAndWait();
        resultLobby.ifPresent(lobbyCard -> {
            viewGame.hboxAction.displayMajeurMineurChoiceDialog();
            Optional<String> resultType = viewGame.hboxAction.dialogMajeurMineur.showAndWait();
            resultType.ifPresent(type -> {
                boolean resultat = false ;
                if (type.equals("Majeur")) resultat = model.playLobbyCard(lobbyCard,true);
                else{
                    resultat = true;
                    model.getCurrentPLayer().playMinorLobbyCard(lobbyCard);
                }
                if (!resultat) viewGame.displayAlertWithoutHeaderText("Action Impossible", "Veuillez finir vos tâches avant d'essayer de jouer la carte");
            });
            return;
        });
    }
}