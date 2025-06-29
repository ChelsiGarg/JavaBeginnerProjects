// backend

import java.util.Random;

public class RockPaperScissor {
    private static final String[] computerChoices = {"Rock", "Paper", "Scissors"};

    // store the computer choice so that we can retrieve the value & display it to the backend
    private String computerChoice;

    // use to generate a random number to randomly pick a chooice for the computer
    private Random random;

    // store the scores so that we can retirieve the values & display it to frontend
    private int computerScore, playerScore;

    // constructor- to initialize random obj
    public RockPaperScissor(){
        random = new Random();
    }

    public String getComputerChoice() {
        return computerChoice;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    // call this method to begin playing the game
    public String playRockPaperScissor(String playerChoice){
        // generate computer choice
        computerChoice = computerChoices[random.nextInt(computerChoices.length)];

        // will comtain the returning message indicating the result of the game 
        String result;

        // evaluate the winner
        if(computerChoice.equals("Rock")){
            if(playerChoice.equals("Paper")){
                result = "Player Wins";
                playerScore++;
            }else if(playerChoice.equals("Scissors")){
                result = "Computer Wins";
                computerScore++;
            }else{
                result = "Draw";
            }
        }else if(computerChoice.equals("Paper")){
            if(playerChoice.equals("Scissors")){
                result = "Player Wins";
                playerScore++;
            }else if(playerChoice.equals("Rock")){
                result = "Computer Wins";
                computerScore++;
            }else{
                result = "Draw";
            }
        }else{
            if(playerChoice.equals("Rock")){
                result = "Player Wins";
                playerScore++;
            }else if(playerChoice.equals("Paper")){
                result = "Computer Wins";
                computerScore++;
            }else{
                result = "Draw";
            }
        }

        return result;
    }
}
