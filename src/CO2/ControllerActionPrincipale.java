package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Optional;

public class ControllerActionPrincipale implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerActionPrincipale(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.hboxAction.setButtonActionPrincipaleControler(this);
    }

    /**
     * Récupere la source de l'evenemenet et le traite selon la source
     * @param event L'evenement
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource() ;
        if (source == viewGame.hboxAction.btnProposerProjet) {
            actionProposerProjet();
        } else if (source == viewGame.hboxAction.btnMettreProjet){
            actionMettreProjet();
        } else if (source == viewGame.hboxAction.btnConstruire) {
            actionConstruire();
        }
    }

    private int[] calculMissingRessources(Subvention projetMisEnPlaceChoisi, Player currentPLayer) {
        int[] tab =  new int[3];
        tab[0] = projetMisEnPlaceChoisi.getProject().getCentralType().getExpertise() - model.getCurrentPLayer().getExpertise(projetMisEnPlaceChoisi.getProject().getEnergyType());
        tab[1] = projetMisEnPlaceChoisi.getProject().getCentralType().getCout()[0] - model.getCurrentPLayer().getArgent();
        tab[2] = projetMisEnPlaceChoisi.getProject().getCentralType().getCout()[1] - model.getCurrentPLayer().getResourcesTech();
        for (int i = 0; i< tab.length; i++) if (tab[i] < 0) tab[i] = 0;
        return tab;
    }

    /**
     * Effectue l'action proposer projet
     */
    public void actionProposerProjet(){
        // On affiche le ChoiceDialog qui permet de proposer un projet
        viewGame.hboxAction.displayProposerProjetChoiceDialog() ;
        // Le resultat du ChoiceDialog
        Optional<Continent> result = viewGame.hboxAction.dialogProposerProjet.showAndWait();
        result.ifPresent(continentChoisi -> {
            // Si le résultat est present ( l'utilisateur n'a pas quitter le ChoiceDialog et a donc choisi
            // un continent => continentChoisi )
            // choix de la subvention
            viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continentChoisi);
            // la subvention choisis par le joueur
            Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
            resulltSubv.ifPresent(subvention -> {
                // choisis l'energie parmis les 3 energies autorisees par la carte agenda du continent choisi
                viewGame.hboxAction.displayEnergyChoiceDialog(continentChoisi) ;
                Optional<greenEnergyTypes> resultEnergy = viewGame.hboxAction.dialogEnergie.showAndWait();
                resultEnergy.ifPresent(energyChoisi -> {
                    // Si la tuile peut etre ajouter
                    if(model.verrifAddProjectTileToSubvention(continentChoisi, subvention, energyChoisi)){
                        // Mets a jour le model : fait en sorte que le projet ne puisse pas etre réutilisé
                        model.addProjectTileToSubvention(subvention.getContinent(), subvention, energyChoisi);

                        // Affiche la tuile a l'écran
                        viewGame.changeProjectState(subvention.getIndex(), model.getCurEnergyChoice(), viewGame.PROPOSER_PROJET, continentChoisi);

                        // Récupération du type et application de l'effet de la subvention choisie
                        subventionTypes type = subvention.effect(model.getCurrentPLayer());
                        if(type == subventionTypes.RECHERCHE){ //Si il s'agit d'une subvention recheche
                            actionProposerProjetRecherche();
                        }
                        viewGame.reloadresourcesTech();
                        viewGame.reloadArgent();
                        // Le joueur en cours a effectuer son action principale
                        model.getCurrentPLayer().setActionPrincipaleDone(true);
                    }
                });
            });
        });
        // Si un dialog a été quitté reset la hbox
        viewGame.hboxAction.resetHbox();
    }

    /**
     * Si un projet est propose sur une case recherche en collaboration, choix:
     * - déplacer scientifique
     * - Ajouter scientifique
     */
    public void actionProposerProjetRecherche(){
        //On affiche la boite de dialogue pour choisir l'action à réaliser
        viewGame.hboxAction.displayChoisirRechecheChoiceDialog();
        Optional<String> resultAction = viewGame.hboxAction.dialogChoisirRecherche.showAndWait();
        //On recupère le résultat
        resultAction.ifPresent(action -> {
            if(action == "Déplacer un scientifique"){ //S'il choisi de déplacer un scientifique
                actionProposerProjetRechercheDeplacer();
            }
            else{ //S'il il choisir d'avoir un nouveau scientifique
                if(model.getCurrentPLayer().addScientifique()){ //Si le joueur à pu avoir un nouveau scientifique
                    //on met à jour la vue = déplacer le nouveau scientifique(le dernier de la liste du joueur) dans la réserve
                    viewGame.addScientifiqueToPane(model.getCurrentPLayer().getScientifiques().size()-1);
                    viewGame.deplacerScientifiqueReserve(model.getCurrentPLayer().getLastAddScientifique().getImgScientifique(),model.getCurrentPLayer().getScientifiques().size()-1);
                }
                else{ //Si le joueur à deja 4 scientifique, on affiche un message d'erreur et on quitte ici
                    viewGame.displayAlertWithoutHeaderText("Problème lors de l'action", "Vous avez déjà 4 scientifiques.");
                    return;
                }
            }
        });
    }

    /**
     * Si un projet est propose sur une case recherche en cllaboration et que le joueur décide de déplacer un scientifique
     */
    public void actionProposerProjetRechercheDeplacer(){
        //On affiche la boite de dialogue pour savoir quel scientifique choisir
        viewGame.hboxAction.displayActionScientifiqueChoice();
        Optional<String> resultChoixScientifique = viewGame.hboxAction.dialogActionScientifique.showAndWait();
        resultChoixScientifique.ifPresent(scientifique -> {
            if(scientifique.equals("")) return;
            else if(scientifique.equals("Scientifique n°1")){
                model.getCurrentPLayer().setCurrentScientifique(0);
            }else if(scientifique.equals("Scientifique n°2")){
                model.getCurrentPLayer().setCurrentScientifique(1);
            }else if(scientifique.equals("Scientifique n°3")){
                model.getCurrentPLayer().setCurrentScientifique(2);
            }else {
                model.getCurrentPLayer().setCurrentScientifique(3);
            }
        });

        //On affiche la boite de dialogue pour savoir comment il veut déplacer un scientifique
        viewGame.hboxAction.displayActionScientifiqueAfterRecherche();
        Optional<String> resultDeplacerScientifique = viewGame.hboxAction.dialogActionScientifiqueAfterRecherche.showAndWait();
        //On récupère le resultat
        resultDeplacerScientifique.ifPresent(deplacement -> {
            if(deplacement == "") return;
            else if(deplacement == "Déplacer sur un projet"){
                // Affiche le ChoiceDialog qui permet de deplacer un scientifque
                viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog();
                if (viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
                if (model.getCurrentPLayer().getCurrentScientifique().getSubvention() != null){
                    model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                }
                Optional<Subvention> resultProjet = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
                resultProjet.ifPresent(projetChoisi -> {
                    // Si un projet a ete choisi
                    if (model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi)) {
                        viewGame.addScientifiqueToProject(projetChoisi.getIndex(), model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(), projetChoisi.getContinent());
                        model.getCurrentPLayer().getCurrentScientifique().setSubvention(projetChoisi);
                        projetChoisi.setStaffed(true);
                        if (model.getCurrentPLayer().getCurrentScientifique().getSubvention().getProject() != null) {
                            // set la valeur de l'énergie si le scientifique joué est sur cette energie
                            model.getCurrentPLayer().getCurrentScientifique().setSubject(new Subject(projetChoisi.getEnergyTypes()));
                        }
                    }
                });
            }
            else if(deplacement == "Déplacer sur un sommet"){
                // Affiche le ChoiceDialog qui permet de deplacer un scientifque
                viewGame.hboxAction.displayDeplacerScientifiqueSommetChoiceDialog();
                if (viewGame.hboxAction.dialogDeplacerScientifiqueSommet == null) return;
                Optional<SommetTile> resultSommet = viewGame.hboxAction.dialogDeplacerScientifiqueSommet.showAndWait();
                resultSommet.ifPresent(sommetChoisi -> {
                    if (model.moveScientificOnSommet(model.getCurrentPLayer().getCurrentScientifique().getSubvention(), sommetChoisi)) {
                        model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                        viewGame.addScientifiqueToSommet(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(), model.getCurrentPLayer().getCurrentScientifique(), sommetChoisi);
                        model.getCurrentPLayer().getCurrentScientifique().setSommetTile(sommetChoisi);
                        model.getCurrentPLayer().getCurrentScientifique().setSubvention(null);
                        model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                    }
                });
            }
            else{
                //remettre le scientifique dans la réserve et gagner 1 d’expertise dans le type d’énergie du projet
                // gagner 1 d’expertise dans le type d’énergie du projet
                model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                model.getCurrentPLayer().addExpertise(model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy(), 1);
                viewGame.displayAlertWithoutHeaderText(
                        "Gain d'expertise",
                        "En remettant votre scientifique dans votre réserve, vous gagné 1 d’expertise dans le type d’énergie " +
                                model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy() +
                                " !"
                );
                //remettre le scientifique dans la réserve
                viewGame.deplacerScientifiqueReserve(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique(), model.getCurrentPLayer().getCurentScientifiqueId());
                viewGame.hboxAction.resetHbox();
                model.getCurrentPLayer().getCurrentScientifique().moveToReserve();
            }
        });
    }

    /**
     * Effectue l'action mettre en place un projet
     */
    public void actionMettreProjet(){
        // On affiche le ChoiceDialog qui permet de mettre en place un projet déjà proposé
        viewGame.hboxAction.displayMettreEnPlaceProjetChoiceDialog() ;
        if(viewGame.hboxAction.dialogMettreEnPlaceProjet == null) return;
        // Le resultat du ChoiceDialog
        Optional<Subvention> result = viewGame.hboxAction.dialogMettreEnPlaceProjet.showAndWait();
        result.ifPresent(projetChoisi -> {
            // Si un projet a ete choisi
            // On demande avec quoi il veut payer (player ou continents controlés)
            viewGame.hboxAction.displayBuyCEPBy();
            Optional<String> resultBuyCEPBy = viewGame.hboxAction.dialogBuyCEPBy.showAndWait();
            resultBuyCEPBy.ifPresent(BuyCEPBy -> {
                if(BuyCEPBy == "ma réserve"){
                    System.out.println("Joueur paye !!!");
                    if(model.mettreEnPlaceProjetByPlayer(projetChoisi.getEnergyTypes(), projetChoisi)) {
                        viewGame.changeProjectState(projetChoisi.getIndex() , projetChoisi.getEnergyTypes(), viewGame.METTRE_EN_PLACE_PROJET, projetChoisi.getContinent());
                        viewGame.reloadresourcesTech();
                        viewGame.reloadCEPRessTech();
                        viewGame.reloadArgent();
                    }else {
                        viewGame.displayAlertWithoutHeaderText("Pas assez de CEP", "Vous n'avez pas assez de CEP \npour mettre en place le projet "+projetChoisi.getEnergyTypes() + " \nsur la subvention " + projetChoisi.getType() + " du continent " + projetChoisi.getContinent());
                    }
                }
                else{
                    viewGame.hboxAction.displayBuyCEPByContinent();
                    Optional<Continent> resultContinentBuyCEP = viewGame.hboxAction.dialogBuyCEPByContinent.showAndWait();
                    resultContinentBuyCEP.ifPresent(BuyCEPByContinent -> {
                        if(model.mettreEnPlaceProjetByContinent(projetChoisi.getEnergyTypes(), projetChoisi, BuyCEPByContinent)){
                            viewGame.changeProjectState(projetChoisi.getIndex() , projetChoisi.getEnergyTypes(), viewGame.METTRE_EN_PLACE_PROJET, projetChoisi.getContinent());
                            viewGame.reloadresourcesTech();
                            viewGame.reloadCEPRessTech();
                            viewGame.reloadArgent();
                        }
                    });
                }
                return;
            });
        });
        // Sinon reset la hbox
        viewGame.hboxAction.resetHbox();
    }

    /**
     * Effectue l'action construire une central
     */
    public void actionConstruire(){
        viewGame.hboxAction.displayConstruireCentraleChoiceDialog();
        if(viewGame.hboxAction.dialogConstruireCentrale == null){
            viewGame.displayAlertWithoutHeaderText("Impossible", "Il n'y a pas de projet déjà mis en place");
            return;
        }
        Optional<Subvention> result = viewGame.hboxAction.dialogConstruireCentrale.showAndWait();
        result.ifPresent(projetMisEnPlaceChoisi -> {
            int index = model.putCentral(projetMisEnPlaceChoisi);
            if( index >= 0) {
                // remet la carte projet dans le paquet
                model.projectsPacket.put(model.getCurEnergyChoice().name(),model.projectsPacket.get(model.getCurEnergyChoice().name())+1);
                viewGame.addCentrale(projetMisEnPlaceChoisi.getProject().getCentralType() , projetMisEnPlaceChoisi.getContinent(), index);
                viewGame.resetSubvention(projetMisEnPlaceChoisi.getContinent(),projetMisEnPlaceChoisi.getIndex());
                projetMisEnPlaceChoisi.getProject().setMisEnPlace(false);
                projetMisEnPlaceChoisi.getProject().setSubventionPossible(true);
                projetMisEnPlaceChoisi.setEmpty(true);

                //gain : point victoire & 1 expertise dans le dommaine de la centrale
                model.curPlayer.addPointVictoire(projetMisEnPlaceChoisi.getProject().getCentralType().getPtsVictoire());
                model.curPlayer.addExpertise(projetMisEnPlaceChoisi.getProject().getEnergyType(),1);
                viewGame.reloadPointVictoire();
                viewGame.reloadPlayerExpertise(model.getCurrentPLayer());

                // reload payement
                viewGame.reloadresourcesTech();
                viewGame.reloadArgent();
                viewGame.reloadCEPRessTech();

                viewGame.reloadContinentControl(model.getCurrentPLayer());
            } else {
                if(index == -2) viewGame.displayAlertWithoutHeaderText("Erreur", "Impossible de placer la centrale car un scientifique se trouve sur le projet");
                if(index == -1) viewGame.displayAlertWithoutHeaderText("Erreur", "Impossible de placer la centrale");
                if(index == -3) {
                    int[] missingResources = calculMissingRessources(projetMisEnPlaceChoisi, model.getCurrentPLayer());
                    viewGame.displayAlertWithoutHeaderText(
                            "Erreur",
                            "Vous n'avez pas assez de ressources : il vous manque de "+
                                    missingResources[0] +
                                    " expertise, "+
                                    missingResources[1] +
                                    "€, "+
                                    missingResources[2] +" " +
                                    "ressources technologiques"
                    );
                }
            }
            viewGame.hboxAction.resetHbox();
            return;
        });
    }
}