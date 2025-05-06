//package org.example.dandd;
//
//import org.example.dandd.model.entities.Action;
//import org.example.dandd.model.entities.Monster;
//import org.example.dandd.model.entities.enums.ActionType;
//import org.example.dandd.model.entities.enums.Danger;
//import org.example.dandd.service.GameService;
//
//import java.util.ArrayList;
//
//public class Test
//{
//    public static void main(String[] args)
//    {
//        System.out.println("TEST COMBATTIMENTO (CONSOLE)");
//
//        TroubleShooter attaccante = new TroubleShooter();
//        attaccante.setId(1L);
//        attaccante.setName("Vincenzo");
//        attaccante.setDescription("Odia i froci ");
//        attaccante.setHp(100);
//        attaccante.setAtk(20);
//        attaccante.setDef(10);
//        attaccante.setSpeed(10);
//        attaccante.setActions(new java.util.ArrayList<>());
//        attaccante.setEquipments(new java.util.ArrayList<>());
//
//        Monster difensore = new Monster();
//        difensore.setId(2L);
//        difensore.setName("Gigi");
//        difensore.setDescription("Gli piace la ciola");
//        difensore.setHp(100);
//        difensore.setAtk(10);
//        difensore.setDef(10);
//        difensore.setSpeed(10);
//        difensore.setActions(new ArrayList<>());
//        difensore.setDanger(Danger.LOW);
//
//        Action attaccoBase = new Action();
//        attaccoBase.setId(101L);
//        attaccoBase.setNameAction("Attacco Base");
//        attaccoBase.setDescriptionAction("Colpo Standard");
//        attaccoBase.setPrecision(100);
//        attaccoBase.setMolt(1);
//        attaccoBase.setMaxNumTarget(1);
//        attaccoBase.setCooldown(0);
//        attaccoBase.setActionType(ActionType.BASE);
//
//        System.out.println("\nStato iniziale");
//        System.out.println("Attaccante: " + attaccante.getName() + " (HP: " + attaccante.getHp() + ", ATK: " + attaccante.getAtk() + ")");
//        System.out.println("Difensore: " + difensore.getName() + " (HP: " + difensore.getHp() + ", ATK: " + difensore.getAtk() + ")");
//        System.out.println("Azione usata: '" + attaccoBase.getNameAction() + "' (Tipo: " + attaccoBase.getActionType() + ")");
//
//        System.out.println("\nProva Attacco");
//
//        int dannoCalcolato = attaccoBase.baseDmgCalculation(difensore.getDef(), attaccante.getAtk());
//        System.out.println("Danno: " + dannoCalcolato);
//
//        int controlloDannoPrecisione = GameService.precisionCheck(dannoCalcolato, attaccoBase.getPrecision());
//
//        int hpRimanente = difensore.getHp() - controlloDannoPrecisione;
//        if (hpRimanente < 0)
//            hpRimanente = 0;
//
//        difensore.setHp(hpRimanente);
//        System.out.println("Difensore: " + difensore.getName() + " (HP: " + difensore.getHp() + ")");
//
//        if (difensore.getHp() <= 0)
//            System.out.println(difensore.getName() + " Difensore Morto");
//
//        System.out.println("\nStato finale");
//        System.out.println("Attaccante: " + attaccante.getName() + " (HP: " + attaccante.getHp() + ", ATK: " + attaccante.getAtk() + ")");
//        System.out.println("Difensore: " + difensore.getName() + " (HP: " + difensore.getHp() + ", ATK: " + difensore.getAtk() + ")");
//        System.out.println("Azione usata: '" + attaccoBase.getNameAction() + "' (Tipo: " + attaccoBase.getActionType() + ")");
//
//        System.out.println("Fine");
//    }
//}
