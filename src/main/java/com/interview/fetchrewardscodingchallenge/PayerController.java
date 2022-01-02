package com.interview.fetchrewardscodingchallenge;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class PayerController {
    int pointsTotal;
    ArrayList<Payer> listOfPayers = new ArrayList<>();

    @PostMapping("/add_transaction")
    public String addTransaction(@RequestBody Payer payer){
        boolean listContains = false; //checker to see if list contains the specific payer object
        for (Payer listOfPayer : listOfPayers) { //iterate through payer objects
            if (listOfPayer.getPayerName().equals(payer.getPayerName())) { //if the payer already exists
                if (payer.getPoints() < 0) { //checking if the new payers points are negative
                    pointsTotal = pointsTotal + payer.getPoints(); //reduce the points Total since the payer points are negative
                    listOfPayer.setPoints(listOfPayer.getPoints() + payer.getPoints()); //deleting value of points from payer
                    listContains = true; //already contained in list so do not add it again in the if statement below
                    break;
                } else {
                    break; //since you just add it in the next if statement
                }
            }
        }
        if (!listContains){ //if list of payer doesn't contain this payer or payer is above 0
            listOfPayers.add(payer); //add new payer
            pointsTotal = pointsTotal + payer.getPoints(); //add the new payer to poitns total
            Collections.sort(listOfPayers); //sort based on DateTime
        }

        return "Added Succesfully";
    }

    @DeleteMapping("/spend_points")
    public List<String> spendPoints(@RequestBody int points) throws Exception {
        ArrayList<String> returnList = new ArrayList<>();
        if (points <= 0) { //if spending points are negative throw exception
            throw new Exception("Spending points too low");
        }
        if (points > pointsTotal){ //if points are greater than points you have in account throw exception
            throw new Exception("Not enought points in account");
        }
        pointsTotal = pointsTotal - points; //reduce points total
        for (Payer listOfPayer : listOfPayers) {
            if (listOfPayer.getPoints() > points) { //if the players points are greater then the spending points left
                returnList.add("payer: " + listOfPayer.getPayerName() + " -" + points); //add amount taken from payer for return
                listOfPayer.setPoints(listOfPayer.getPoints() - points); //change the payers point amount - the rest of spending points
                return returnList; //return list of payers that paid.
            } else if (listOfPayer.getPoints() < points) { //if points are greater then spending points
                returnList.add("payer: " + listOfPayer.getPayerName() + " -" + listOfPayer.getPoints()); //add full amount of points to list since they will be 0 now
                points = points - listOfPayer.getPoints(); //reduce points amount
                listOfPayer.setPoints(0); //set points to 0 for this payer
            }
        }
        return returnList;
    }

    @GetMapping("/get_balance")
    public HashMap<String, Integer> getBalance(){ //method to return the Payer's points and the payer name in Payer/Points format
        List<Payer> tempPayerList = new ArrayList<>(listOfPayers);
        HashMap<String, Integer> returnMap = new HashMap<>();
        for (Payer value : tempPayerList) {
            if (returnMap.containsKey(value.getPayerName())) {
                int newValue = returnMap.get(value.getPayerName());
                returnMap.replace(value.getPayerName(), newValue + value.getPoints());
            } else {
                returnMap.put(value.getPayerName(), value.getPoints());
            }
        }
        return returnMap;
    }

}
